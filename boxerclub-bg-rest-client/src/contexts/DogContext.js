import { useEffect, useState, createContext, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { dogServiceFactory } from "../services/dogServiceFactory";
import { useAuthContext } from "./AuthContext";
//todo if !dogs => navigate something static
export const DogContext = createContext();

export const DogProvider = ({ children }) => {
  const navigate = useNavigate();
  const [dogs, setDogs] = useState([]);
  const [createdDog, setCreatedDog] = useState({});
  const { token } = useAuthContext();
  const dogService = dogServiceFactory(token);
  const [parent, setParent] = useState({});
  const [error, setError] = useState({});

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

  const onCreateDogSubmitHandler = async (data) => {
    setError({});
    const result = await dogService.create(data);

    if (result[0] === 403) {
      let errorMessage = result[1];
      setError(errorMessage.description);
    }

    if (result[0] === 500) {
      setCreatedDog({});
      setParent({});
    }
    if (result[0] === 200) {
      let newDog = result[1];
      setDogs((state) => [...state, newDog]);
      setCreatedDog(newDog);
      setParent({});
      setError({});
      navigate("dogs/register/parents");
    }
  };

  const onCreateParentDogSubmitHandler = async (data) => {
    const result = await dogService.createParent(data);
    setParent({});
    if (result[0] === 403) {
      alert("ERROR");
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
    }
  };
  const onPedigreeUploadSubmitHandler = async (data) => {
    let result = await dogService.uploadPedigree(data);
    if (result.status === 500) {
      return;
    } else {
      navigate(`/`);
      console.log(result);
    }
  };

  const onDogEditSubmitHandler = async (data) => {
    const editedDog = await dogService.edit(data._id, data);
    if (editedDog) {
      setDogs((state) =>
        state.map((x) => (x._id === data._id ? editedDog : x))
      );
      /*setLatestDogs((state) =>
        state.map((x) => (x._id === data._id ? editedDog : x))
      );*/
      navigate(`/dogs/${data._id}`);
    }
  };

  const onDeleteDogHandler = (dogId) => {
    setDogs((state) => state.filter((x) => x._id !== dogId));
  };

  const selectDog = (dogId) => {
    return dogs.find((dog) => (dog._id = dogId));
  };

  const context = {
    onCreateDogSubmitHandler,
    onCreateParentDogSubmitHandler,
    onPedigreeUploadSubmitHandler,
    onDogEditSubmitHandler,
    onDeleteDogHandler,
    selectDog,
    error,
    parent,
    dogs,
    createdDog,
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
