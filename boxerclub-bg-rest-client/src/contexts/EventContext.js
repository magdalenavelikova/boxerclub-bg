import { useEffect, useState, createContext, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { eventServiceFactory } from "../services/eventServiceFactory";
import { useAuthContext } from "./AuthContext";

export const EventContext = createContext();

export const EventProvider = ({ children }) => {
  const navigate = useNavigate();
  const { token } = useAuthContext();
  const eventService = eventServiceFactory(token);

  const [events, setEvents] = useState({});
  const [error, setError] = useState({});
  const [errors, setErrors] = useState({});

  useEffect(() => {
    Promise.all([eventService.getAll()]).then(([events]) => {
      setEvents(events);
    });
  }, []);

  const onCreateEventSubmitHandler = async (data) => {
    const result = await eventService.create(data);
    setError({});

    if (result.status == "400") {
      setErrors(result.fieldErrors);
    } else {
      // setEvents((state) => [...state, result]);
      setErrors({});
      navigate("/events");
    }
  };

  const onEditEventSubmitHandler = async (data) => {
    const editedEvent = await eventService.update(data.id, data);
    if (editedEvent.status == "400") {
      setErrors(editedEvent.fieldErrors);
    } else {
      //  setEvents((state) =>        state.map((l) => (l.id === data.id ? editedEvent : l))      );
      navigate(`/events`);
    }
  };

  const onEventDelete = async (eventId) => {
    try {
      await eventService.remove(eventId);
    } catch (error) {
      setErrors(error);
    }
    //   setEvents((state) => state.filter((x) => x.id !== eventId));
  };

  const clearErrors = () => {
    setError({});
  };

  const context = {
    onCreateEventSubmitHandler,
    onEditEventSubmitHandler,
    onEventDelete,
    clearErrors,
    error,
    errors,

    events,
  };

  return (
    <>
      <EventContext.Provider value={context}>{children}</EventContext.Provider>
    </>
  );
};
export const useEventContext = () => {
  const context = useContext(EventContext);
  return context;
};
