import { useEffect, useState, createContext, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { linkServiceFactory } from "../services/linkServiceFactory";
import { useAuthContext } from "./AuthContext";

export const LinkContext = createContext();

export const LinkProvider = ({ children }) => {
  const navigate = useNavigate();
  const { token } = useAuthContext();
  const linkService = linkServiceFactory(token);
  const [links, setLinks] = useState([]);
  const [createdLink, setCreatedLink] = useState({});
  const [selectedLink, setSelectedLink] = useState({});

  const [error, setError] = useState({});
  const [errors, setErrors] = useState({});

  useEffect(() => {
    Promise.all([linkService.getAll()]).then(([links]) => {
      setLinks(links);
      setCreatedLink({});
    });
  }, []);

  const onCreateLinkSubmitHandler = async (data) => {
    setError({});
    const result = await linkService.create(data);
    if (result[0] === 400) {
      setErrors(result[1].fieldErrors);
    }

    if (result[0] === 500) {
      setCreatedLink({});
    }
    if (result[0] === 201) {
      let newLink = result[1];
      setLinks((state) => [...state, newLink]);
      setCreatedLink(newLink);

      setErrors({});
      navigate("/");
    }
  };

  const onEditLinkSubmitHandler = async (data) => {
    /* const editedLink = await dogService.edit(data._id, data);
    if (editedLink) {
      setLinks((state) =>
        state.map((x) => (x._id === data._id ? editedLink : x))
      );
      setLatestLinks((state) =>
        state.map((x) => (x._id === data._id ? editedLink : x))
      );
      navigate(`/dogs/${data._id}`);
    }*/
    setSelectedLink({});
  };

  const onLinkDelete = async (linkId) => {
    setError({});
    const deletedLink = await linkService.remove(linkId);
    if (deletedLink === true) {
      setLinks((state) => state.filter((x) => x.id !== linkId));
    }
    if (deletedLink === false) {
      setError({ isDelete: error });
    }
  };

  const getSelectedLink = async (dogId) => {
    const dog = await linkService.getById(dogId);
    setSelectedLink(dog);
    navigate(`/links/edit`);
  };

  const clearErrors = () => {
    setError({});
  };

  const context = {
    onCreateLinkSubmitHandler,

    onEditLinkSubmitHandler,
    onLinkDelete,
    getSelectedLink,
    clearErrors,
    error,
    errors,
    links,
    createdLink,
    selectedLink,
  };

  return (
    <>
      <LinkContext.Provider value={context}>{children}</LinkContext.Provider>
    </>
  );
};
export const useLinkContext = () => {
  const context = useContext(LinkContext);
  return context;
};
