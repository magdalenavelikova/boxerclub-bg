import { useEffect, useState, createContext, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { contactServiceFactory } from "../services/contactServiceFactory";
import { useAuthContext } from "./AuthContext";

export const ContactContext = createContext();

export const ContactProvider = ({ children }) => {
  const navigate = useNavigate();
  const { token } = useAuthContext();
  const contactService = contactServiceFactory(token);
  const [contacts, setContacts] = useState([]);
  const [error, setError] = useState({});
  const [errors, setErrors] = useState({});

  useEffect(() => {
    Promise.all([contactService.getAll()]).then(([contacts]) => {
      setContacts(contacts);
    });
  }, []);

  const onCreateContactSubmitHandler = async (data) => {
    const result = await contactService.create(data);
    setError({});
    console.log(result);
    if (result.status == "400") {
      setErrors(result.fieldErrors);
    } else {
      setContacts((state) => [...state, result]);
      setErrors({});
      navigate("/contacts");
    }
  };

  const onEditContactSubmitHandler = async (data) => {
    const editedContact = await contactService.update(data.id, data);
    if (editedContact.status == "400") {
      setErrors(editedContact.fieldErrors);
    } else {
      setContacts((state) =>
        state.map((l) => (l.id === data.id ? editedContact : l))
      );
      navigate(`/contacts`);
    }
  };

  const onContactDelete = async (contactId) => {
    try {
      await contactService.remove(contactId);
    } catch (error) {
      setErrors(error);
    }
    setContacts((state) => state.filter((x) => x.id !== contactId));
  };

  const clearErrors = () => {
    setError({});
  };

  const context = {
    onCreateContactSubmitHandler,
    onEditContactSubmitHandler,
    onContactDelete,
    clearErrors,
    error,
    errors,
    contacts,
  };

  return (
    <>
      <ContactContext.Provider value={context}>
        {children}
      </ContactContext.Provider>
    </>
  );
};
export const useContactContext = () => {
  const context = useContext(ContactContext);
  return context;
};
