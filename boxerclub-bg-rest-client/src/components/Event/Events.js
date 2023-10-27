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

  const { events, onEventDelete } = useContext(EventContext);

  const [deleteEventShow, setDeleteEventShow] = useState(false);
  const [editEventShow, setEditEventShow] = useState(null);
  const [eventList, setEventList] = useState([]);
  const [selectedEvent, setSelectedEvent] = useState({});
  const [passedEventsBG, setPassedEventsBG] = useState([]);
  const [passedEventsInt, setPassedEventsInt] = useState([]);
  const [upcomingEventsBg, setUpcomingEventsBg] = useState([]);
  const [upcomingEventsInt, setUpcomingEventsInt] = useState([]);

  useEffect(() => {
    setPassedEventsBG(events["passedBg"]);
    setPassedEventsInt(events["passedInt"]);
    setUpcomingEventsBg(events["upcomingBg"]);
    setUpcomingEventsInt(events["upcomingBg"]);
  }, []);
  useEffect(() => {
    setPassedEventsBG(events["passedBg"]);
    setPassedEventsInt(events["passedInt"]);
    setUpcomingEventsBg(events["upcomingBg"]);
    setUpcomingEventsInt(events["upcomingBg"]);
  }, [events]);
  console.log(passedEventsInt);
  console.log(upcomingEventsBg);
  console.log(upcomingEventsInt);
  console.log(passedEventsBG);
  const firstRow =
    Array.isArray(passedEventsInt) && passedEventsInt.length
      ? passedEventsInt[0]
      : {};
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
        <Container fluid className=' mb-3 pt-3'>
          <Table
            className='align-middle project-list mt-5 mb-5'
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
