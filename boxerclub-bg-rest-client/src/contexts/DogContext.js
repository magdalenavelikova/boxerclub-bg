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
    if (isAuthorized) {
      Promise.all([dogService.getAll(token)]).then(([dogs]) => {
        setDogs(dogs);
      });
    } else {
      Promise.all([dogService.getAllApproved(token)]).then(([dogs]) => {
        setDogs(dogs);
      });
    }
    setSpinner(false);
    setCreatedDog({});
    setSelectedDog({});
    setParent({});
  }, []);

  useEffect(() => {
    if (isAuthorized) {
      Promise.all([dogService.getAll(token)]).then(([dogs]) => {
        setDogs(dogs);
      });
    } else {
      Promise.all([dogService.getAllApproved()]).then(([dogs]) => {
        setDogs(dogs);
      });
    }
    setCreatedDog({});
    setParent({});
  }, [isAuthenticated, authorities, token]);

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

  const onCreateParentDogSubmitHandler = async (
    data,
    isEmptyFile,
    id,
    registrationNum,
    childId
  ) => {
    setSpinner(true);

    if (!isEmptyFile || !childId.includes("NewBorn")) {
      const result = await dogService.createParent(data);
      setParent({});

      if (result[0] === 400 || result[0].status === "BAD_REQUEST") {
        setErrors(result[1].fieldErrors);
        setSpinner(false);
      }
      if (result[0] === 409) {
        let errorMessage = result[1];
        setErrors(errorMessage.description);
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
      setError(
        "В нашият регистър не съществува куче с такъв номер на племенната книга. Моля, въведете коректен номер!"
      );
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
    setSuccess(false);
    if (result[0] === 409) {
      let errorMessage = result[1];
      setError(errorMessage.description);
    }

    if (result[0] === 500) {
      return;
    }
    if (result[0] === 200) {
      let editedDog = result[1];
      setSuccess(true);
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

    if (deletedDog === true) {
      setDogs((state) => state.filter((x) => x.id !== dogId));
    }
    if (deletedDog === false) {
      setError({ isDelete: error });
    }
  };

  const getSelectedDog = async (dogId) => {
    const dog = await dogService.getById(dogId);
    setSelectedDog(dog);
    navigate(`/dogs/edit`);
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
    onDogDelete,
    getSelectedDog,
    clearErrors,
    approveDog,
    clear,
    error,
    spinner,
    success,
    errors,
    parent,
    dogs,
    createdDog,
    selectedDog,
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
