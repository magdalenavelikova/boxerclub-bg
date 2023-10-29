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
  const [error, setError] = useState({});
  const [errors, setErrors] = useState({});
  const [spinner, setSpinner] = useState(false);
  const [modal, setModal] = useState(true);
  useEffect(() => {
    Promise.all([linkService.getAll()]).then(([links]) => {
      setLinks(links);
    });
  }, []);

  const onCreateLinkSubmitHandler = async (data) => {
   
    setSpinner(true);
    const result = await linkService.create(data);
    setError({});

    if (result.status == "BAD_REQUEST") {
      setSpinner(false);
      setErrors(result.fieldErrors);
    } else {
      setLinks((state) => [...state, result]);
      setErrors({});
      setSpinner(false);
      navigate("/links");
    }
  };

  const onEditLinkSubmitHandler = async (data) => {
    const editedLink = await linkService.update(data.id, data);
    if (editedLink) {
      setLinks((state) =>
        state.map((l) => (l.id === data.id ? editedLink : l))
      );
      navigate(`/links`);
    }
  };

  const onLinkDelete = async (linkId) => {
    try {
      await linkService.remove(linkId);
    } catch (error) {
      setErrors(error);
    }
    setLinks((state) => state.filter((x) => x.id !== linkId));
  };

  const clearErrors = () => {
    setError({});
  };

  const context = {
    onCreateLinkSubmitHandler,
    onEditLinkSubmitHandler,
    onLinkDelete,
    clearErrors,
    error,
    errors,
    links,
    spinner,
    modal,
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
