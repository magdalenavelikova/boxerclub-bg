import { useEffect, useState, createContext, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { dogServiceFactory } from "../services/dogServiceFactory";
import { useAuthContext } from "./AuthContext";
//todo if !dogs => navigate something static
export const DogContext = createContext();

export const DogProvider = ({ children }) => {
  const navigate = useNavigate();
  const { token, isAuthenticated, authorities } = useAuthContext();
  const dogService = dogServiceFactory(token);
  const [dogs, setDogs] = useState([]);
  const [createdDog, setCreatedDog] = useState({});
  const [selectedDog, setSelectedDog] = useState({});
  const [chartDog, setChartDog] = useState({});
  const [parent, setParent] = useState({});
  const [error, setError] = useState({});
  const [errors, setErrors] = useState({});
  const [success, setSuccess] = useState(false);
  const [spinner, setSpinner] = useState(false);

  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));
  useEffect(() => {
    getDogs();
    setSpinner(false);
    setCreatedDog({});
    setSelectedDog({});
    setParent({});
    setErrors({});
  }, []);

  useEffect(() => {
    getDogs();
    setCreatedDog({});
    setParent({});
  }, [isAuthenticated, authorities, token]);

  const getDogs = () => {
    if (isAuthorized) {
      Promise.all([dogService.getAll(token)]).then(([dogs]) => {
        setDogs(dogs);
      });
    } else {
      Promise.all([dogService.getAllApproved()]).then(([dogs]) => {
        setDogs(dogs);
      });
    }
  };

  const onCreateDogSubmitHandler = async (
    data,
    isEmptyFile,
    id,
    registrationNum,
    childId
  ) => {
    setSpinner(true);
    if (!isEmptyFile || registrationNum == "") {
      setError({});
      const result = await dogService.create(data);
      if (result[0] === 400) {
        setErrors(result[1].fieldErrors);
        setSpinner(false);
      }

      if (result[0] === 500) {
        setCreatedDog({});
        setParent({});
        setSpinner(false);
      }
      if (result[0] === 201) {
        let newDog = result[1];
        if (isAuthorized) {
          Promise.all([dogService.getAll(token)]).then(([dogs]) => {
            setDogs(dogs);
          });
        }
        setSpinner(false);
        setCreatedDog(newDog);
        setParent({});
        setErrors({});
        navigate("dogs/register/parents");
      }
    }
  };
  const onAddParentToCreatedDog = (editedDog) => {
    setCreatedDog(editedDog);
    setParent({});
    setSpinner(false);
    setErrors({});
    navigate("dogs/register/parents");
  };

  const onCreateParentDogSubmitHandler = async (
    data,
    isEmptyFile,
    id,
    registrationNum,
    childId
  ) => {
    setSpinner(true);

    if (!isEmptyFile || !childId.includes("Newborn")) {
      const result = await dogService.createParent(data);
      setParent({});

      if (result[0] === 400 || result[0].status === "BAD_REQUEST") {
        setErrors(result[1].fieldErrors);
        setSpinner(false);
      }

      if (result[0] === 409) {
        let errorMessage = result[1];
        setErrors({ birthday: errorMessage.description });
        setSpinner(false);
      }
      if (result[0] === 500) {
        setParent({});
        setSpinner(false);
        return;
      }
      if (result[0] === 201) {
        let parentDog = result[1];
        setParent(parentDog);
        setErrors({});
        setSpinner(false);
        getDogs();
      }
    }
  };
  const onAddParentDogSubmitHandler = async (data) => {
    setParent({});
    const result = await dogService.addParent(data);

    if (result[0] === 409) {
      setParent({});
      let errorMessage = result[1];
      setError(errorMessage.description);
    }

    if (result[0] === 500) {
      setParent({});
      return;
    }
    if (result[0] === 200) {
      let parentDog = result[1];
      setParent(parentDog);
      setError({});
    }
  };

  const onChangeOwnershipSubmitHandler = async (data) => {
    setError({});

    const exist = dogs.filter(
      (x) => x.registrationNum === data.registrationNum
    );

    if (exist.length === 0) {
      setError("Error");
    } else {
      setSpinner(true);
      setError({});

      try {
        const result = await dogService.changeOwner(data);
        setErrors({});
        setSuccess({
          message: result.message,
        });
        setSpinner(false);
      } catch (error) {
        setErrors(error);
        setSpinner(false);
      }
    }
  };

  const onChangeOwnershipVerifyHandler = async (registrationNum, newOwner) => {
    try {
      const result = await dogService.verify(registrationNum, newOwner);
      setErrors({});
      setSuccess({
        message: result.message,
      });

      Promise.all([dogService.getAllApproved()]).then(([dogs]) => {
        setDogs(dogs);
      });
    } catch (error) {
      setErrors(error);
    }
  };
  const onEditDogSubmitHandler = async (data, isEmptyFile, id) => {
    const result = await dogService.update(id, data);
    setSpinner(true);
    setErrors({});
    setSuccess(false);
    if (result[0] === 400 || result[0].status === "BAD_REQUEST") {
      setErrors(result[1].fieldErrors);
      setSpinner(false);
    }
    if (result[0] === 409) {
      let errorMessage = result[1];
      setError(errorMessage.description);
      setErrors({ birthday: errorMessage.description });
      setSpinner(false);
    }

    if (result[0] === 500) {
      setSpinner(false);
      return;
    }
    if (result[0] === 200) {
      let editedDog = result[1];
      setSuccess(true);
      setSpinner(false);
      setDogs((state) =>
        state.map((x) => (x.id == editedDog.id ? editedDog : x))
      );
      setError({});
      navigate(`/dogs`);
    }
  };

  const onDogDelete = async (dogId) => {
    setError({});
    const deletedDog = await dogService.remove(dogId);

    if (deletedDog === false) {
      setError({ isDelete: error });
    } else {
      setDogs((state) => state.filter((x) => x.id !== dogId));
    }
  };

  const getSelectedDog = async (dogId) => {
    const dog = await dogService.getById(dogId);
    setSelectedDog(dog);
    navigate(`/dogs/edit`);
  };

  const getDogChart = async (dogId) => {
    const dog = await dogService.getDogChartById(dogId);
    setChartDog(dog);
    navigate(`dogs/chart`);
  };

  const getDogDetails = async (dogId) => {
    const dog = await dogService.getDetailsById(dogId);
    setSelectedDog(dog);
    navigate(`/dogs/details`);
  };
  const approveDog = async (dogId) => {
    const dog = await dogService.approveDogById(dogId);
    setSelectedDog(dog);
  };
  const onDownloadPedigree = async (dogId) => {
    await dogService.getPedigreeById(dogId);
  };

  const clearErrors = () => {
    setError({});
    setErrors({});
  };
  const clear = () => {
    setSelectedDog({});
  };

  const context = {
    onCreateDogSubmitHandler,
    onAddParentDogSubmitHandler,
    onCreateParentDogSubmitHandler,
    onChangeOwnershipSubmitHandler,
    onChangeOwnershipVerifyHandler,
    onEditDogSubmitHandler,
    onDownloadPedigree,
    getDogDetails,
    getDogChart,
    onDogDelete,
    getSelectedDog,
    clearErrors,
    approveDog,
    clear,
    onAddParentToCreatedDog,
    error,
    spinner,
    success,
    errors,
    parent,
    dogs,
    createdDog,
    selectedDog,
    chartDog,
  };

  return (
    <>
      <DogContext.Provider value={context}>{children}</DogContext.Provider>
    </>
  );
};
export const useDogContext = () => {
  const context = useContext(DogContext);
  return context;
};
