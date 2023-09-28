import { useEffect, useState, createContext, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { dogServiceFactory } from "../services/dogServiceFactory";
import { useAuthContext } from "./AuthContext";

export const DogContext = createContext();

export const DogProvider = ({ children }) => {
  const navigate = useNavigate();
  const [dogs, setDogs] = useState([]);
  const [createdDog, setCreatedDog] = useState({});
  const { token } = useAuthContext();
  const dogService = dogServiceFactory(token);
  const [parent, setParent] = useState({});

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
    let newDog = await dogService.create(data);
    if (newDog.status === 500) {
      setCreatedDog({});
      setParent({});
    } else {
      setDogs((state) => [...state, newDog]);
      setCreatedDog(newDog);
      setParent({});
      navigate("dogs/register/parents");
    }
  };
  const onCreateParentDogSubmitHandler = async (data) => {
    setParent({});
    let parentDog = await dogService.createParent(data);
    if (parentDog.status === 500) {
      return;
    } else {
      setParent(parentDog);
      console.log(parentDog.status);
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
    onDogEditSubmitHandler,
    onDeleteDogHandler,
    selectDog,
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
