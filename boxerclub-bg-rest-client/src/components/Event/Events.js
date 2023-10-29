import { useContext, useEffect, useState } from "react";
import { TableHeaderActions } from "../TableHeader/TableHeaderActions";
import { TableHeader } from "../TableHeader/TableHeader";
import { Container, Table } from "react-bootstrap";

import { DeleteEvent } from "./DeleteEvent";

import { EventContext } from "../../contexts/EventContext";
import { EventItem } from "./EventItem";
import { AuthContext } from "../../contexts/AuthContext";
import { EditEvent } from "./EditEvent";

export const Events = ({ location, status }) => {
  const { isAuthenticated, authorities } = useContext(AuthContext);
  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));

  const { events, onEventDelete, success } = useContext(EventContext);

  const [deleteEventShow, setDeleteEventShow] = useState(false);
  const [editEventShow, setEditEventShow] = useState(null);
  const [eventList, setEventList] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState({});

  useEffect(() => {
    if (location == "bg" && status == "upcoming") {
      setEventList(events["upcomingBg"]);
    }
    if (location == "int" && status == "upcoming") {
      setEventList(events["upcomingInt"]);
    }
    if (location == "bg" && status == "passed") {
      setEventList(events["passedBg"]);
    }
    if (location == "int" && status == "passed") {
      setEventList(events["passedInt"]);
    }
    setEditEventShow(null);
  }, []);

  useEffect(() => {
    if (location == "bg" && status == "upcoming") {
      setEventList(events["upcomingBg"]);
    }
    if (location == "int" && status == "upcoming") {
      setEventList(events["upcomingInt"]);
    }
    if (location == "bg" && status == "passed") {
      setEventList(events["passedBg"]);
    }
    if (location == "int" && status == "passed") {
      setEventList(events["passedInt"]);
    }
  }, [events, location, status]);
  useEffect(() => {
    if (success) {
      setEditEventShow(null);
    }
  }, [success]);

  const firstRow =
    Array.isArray(eventList) && eventList.length ? eventList[0] : {};
  const headerTitle = Object.keys(firstRow);

  let arr = headerTitle.filter(
    (state) => state !== "id" && state !== "location"
  );
  const onCloseClick = () => {
    setDeleteEventShow(null);
    setEditEventShow(null);
  };

  const onEventDeleteHandler = () => {
    onEventDelete(deleteEventShow);
    setDeleteEventShow(null);
    setSelectedEvent({});
  };

  const onDeleteClick = (eventId) => {
    setSelectedEvent(eventList.filter((l) => l.id === eventId));

    setDeleteEventShow(eventId);
  };
  const onEditClick = (eventId) => {
    setSelectedEvent(eventList.filter((l) => l.id === eventId));
    setEditEventShow(eventId);
  };

  return (
    <>
      {deleteEventShow && (
        <DeleteEvent
          event={selectedEvent[0]}
          onCloseClick={onCloseClick}
          onDelete={onEventDeleteHandler}
        />
      )}

      {editEventShow && (
        <EditEvent event={selectedEvent[0]} onCloseClick={onCloseClick} />
      )}

      {eventList && eventList.length !== 0 && (
        <Container fluid className=' mb-3 pt-5'>
          <Table
            className='align-middle project-list  mb-5'
            responsive='md'
            hover>
            {isAuthorized && <TableHeaderActions title={arr} />}
            {!isAuthorized && <TableHeader title={arr} />}
            <tbody>
              {eventList.length !== 0 &&
                eventList.map((e) => (
                  <EventItem
                    className='m-auto'
                    key={e.id}
                    info={e}
                    onDeleteClick={onDeleteClick}
                    onEditClick={onEditClick}
                  />
                ))}
            </tbody>
          </Table>
        </Container>
      )}
    </>
  );
};
