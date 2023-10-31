import { useEffect, useState, createContext, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { eventServiceFactory } from "../services/eventServiceFactory";
import { useAuthContext } from "./AuthContext";

export const EventContext = createContext();

export const EventProvider = ({ children }) => {
  const navigate = useNavigate();
  const { token } = useAuthContext();
  const eventService = eventServiceFactory(token);
  const [success, setSuccess] = useState(false);
  const [events, setEvents] = useState({});
  const [error, setError] = useState({});
  const [errors, setErrors] = useState({});

  useEffect(() => {
    try {
      Promise.all([eventService.getAll()]).then(([events]) => {
        setEvents(events);
      });
    } catch (error) {
      navigate("/maintenance");
    }
  }, []);

  const onCreateEventSubmitHandler = async (data) => {
    setError({});
    const { startDate, ...eventData } = data;
    const start = new Date(startDate);
    const end = new Date(eventData.expiryDate);
    if (start > end) {
      setErrors({ startDate: "Start date must be before expiry date!" });
      setErrors({ expiryDate: "Expiry date must be after start date!" });
      return;
    }
    const result = await eventService.create(data);

    if (result.status === "400" || result.status === "BAD_REQUEST") {
      setErrors(result.fieldErrors);
    } else {
      Promise.all([eventService.getAll()]).then(([events]) => {
        setEvents(events);
      });
      setErrors({});
      navigate("/");
    }
  };

  const onEditEventSubmitHandler = async (data) => {
    const { startDate, ...eventData } = data;
    const start = new Date(startDate);
    const end = new Date(eventData.expiryDate);
    if (start > end) {
      setErrors({ startDate: "Start date must be before expiry date!" });
      setErrors({ expiryDate: "Expiry date must be after start date!" });
      return;
    }
    setSuccess(false);
    const editedEvent = await eventService.update(data.id, data);
    if (editedEvent.status == "400") {
      setErrors(editedEvent.fieldErrors);
    } else {
      setSuccess(true);
      Promise.all([eventService.getAll()]).then(([events]) => {
        setEvents(events);
      });
    }
  };

  const onEventDelete = async (eventId) => {
    try {
      await eventService.remove(eventId);
    } catch (error) {
      setErrors(error);
    }

    Promise.all([eventService.getAll()]).then(([events]) => {
      setEvents(events);
    });
    /* setEvents((state) =>
      Object.values(state).forEach((obj) =>
        obj.filter((e) => (e) => e.id !== eventId)
      )
    );*/
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
    success,
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
