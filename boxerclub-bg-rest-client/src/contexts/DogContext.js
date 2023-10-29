import { useEffect, useState, createContext, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { dogServiceFactory } from "../services/dogServiceFactory";
import { useAuthContext } from "./AuthContext";
//todo if !dogs => navigate something static
export const DogContext = createContext();

export const DogProvider = ({ children }) => {
  const navigate = useNavigate();
  const { token } = useAuthContext();
  const dogService = dogServiceFactory(token);
  const [dogs, setDogs] = useState([]);
  const [createdDog, setCreatedDog] = useState({});
  const [selectedDog, setSelectedDog] = useState({});
  const [parent, setParent] = useState({});
  const [error, setError] = useState({});
  const [errors, setErrors] = useState({});
  const [success, setSuccess] = useState(false);

  /* useEffect(() => {
    Promise.all([dogService.getAll(), dogService.getLatest()]).then(
      ([dogs, latestDogs]) => {
        setDogs(dogs);
        // setLatestDogs(latestDogs);
      }
    );
  }, []);*/
  useEffect(() => {
    Promise.all([dogService.getAll()]).then(([dogs]) => {
      setDogs(dogs);
      setCreatedDog({});
      setParent({});
    });
  }, []);

  const onCreateDogSubmitHandler = async (data, isEmptyFile) => {
    if (!isEmptyFile) {
      setError({});
      const result = await dogService.create(data);
      if (result[0] === 400) {
        setErrors(result[1].fieldErrors);
      }

      /* if (result[0] === 403) {
      let errorMessage = result[1];
      setError(errorMessage.description);
    }*/

      if (result[0] === 500) {
        setCreatedDog({});
        setParent({});
      }
      if (result[0] === 201) {
        let newDog = result[1];
        setDogs((state) => [...state, newDog]);
        setCreatedDog(newDog);
        setParent({});
        setErrors({});
        navigate("dogs/register/parents");
      }
    }
  };

  const onCreateParentDogSubmitHandler = async (data, isEmptyFile) => {
    if (!isEmptyFile) {
      const result = await dogService.createParent(data);
      setParent({});

      if (result[0] === 400) {
        setErrors(result[1].fieldErrors);
      }
      if (result[0] === 500) {
        setParent({});
        return;
      }
      if (result[0] === 201) {
        let parentDog = result[1];
        setParent(parentDog);
        setErrors({});
      }
    }
  };
  const onAddParentDogSubmitHandler = async (data) => {
    setParent({});
    const result = await dogService.addParent(data);

    if (result[0] === 403) {
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
  /* const onPedigreeUploadSubmitHandler = async (data, isEmptyFile) => {
    if (!isEmptyFile) {
      let result = await dogService.uploadPedigree(data);
      if (result.status === 500) {
        return;
      } else {
        navigate(`/`);
      }
    }
  };*/

  const onEditDogSubmitHandler = async (data, isEmptyFile, id) => {
    const result = await dogService.update(id, data);
    setSuccess(false);
    if (result[0] === 403) {
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

  const clearErrors = () => {
    setError({});
  };

  const context = {
    onCreateDogSubmitHandler,
    onAddParentDogSubmitHandler,
    onCreateParentDogSubmitHandler,

    onEditDogSubmitHandler,
    onDogDelete,
    getSelectedDog,
    clearErrors,
    error,
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
