import { useEffect, useState, createContext, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { dogServiceFactory } from "../services/dogServiceFactory";
import { useAuthContext } from "./AuthContext";

export const DogContext = createContext();

export const DogProvider = ({ children }) => {
  const navigate = useNavigate();
  const [dogs, setDogs] = useState([]);
  // const [latestDogs, setLatestDogs] = useState([]);
  const { token } = useAuthContext();
  const dogService = dogServiceFactory(token);

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
      // setLatestDogs(latestDogs);
    });
  }, []);

  const onCreateDogSubmitHandler = async (data) => {
    /*  let formObj = {};
    for (var pair of data.entries()) {
      formObj[pair[0]] = pair[1];
    }
    console.log(formObj);*/

    /* if (formObj.file === "null") {
      newDog = await dogService.createWithoutFile(data.dto);
    } else {
      newDog = await dogService.createWithFile(data);
    }*/
    // console.log(newDog);

    let newDog = await dogService.create(data);
    if (newDog) {
      setDogs((state) => [...state, newDog]);
      //    setLatestDogs((state) => [newDog, ...state]);
      navigate("/dogs");
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
    onDogEditSubmitHandler,
    onDeleteDogHandler,
    selectDog,
    dogs,
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
