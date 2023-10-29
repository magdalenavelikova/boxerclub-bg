import { useContext, useEffect, useState, useTransition } from "react";
import { ContactContext } from "../../contexts/ContactContext";
import { Button, Col, Container, Row } from "react-bootstrap";

import { ContactItem } from "./ContactItem";
import { DeleteContact } from "./DeleteContact";
import { EditContact } from "./EditContact";

export const Contacts = () => {
  const { contacts, onContactDelete } = useContext(ContactContext);
  const [contactList, setContactList] = useState([]);
  const [deleteContactShow, setDeleteContactShow] = useState(false);
  const [editContactShow, setEditContactShow] = useState(null);
  const [selectedContact, setSelectedContact] = useState({});

  const onCloseClick = () => {
    setDeleteContactShow(null);
    setEditContactShow(null);
  };

  const onContactDeleteHandler = () => {
    onContactDelete(deleteContactShow);
    setDeleteContactShow(null);
    setSelectedContact({});
  };

  const onDeleteClick = (contactId) => {
    setSelectedContact(contactList.filter((l) => l.id === contactId));
    setDeleteContactShow(contactId);
  };
  const onEditClick = (contactId) => {
    setSelectedContact(contactList.filter((l) => l.id === contactId));
    setEditContactShow(contactId);
  };
  useEffect(() => {
    setContactList(contacts);
  }, []);

  useEffect(() => {
    setContactList(contacts);
  }, [contacts]);

  return (
    <>
      {deleteContactShow && (
        <DeleteContact
          contact={selectedContact[0]}
          onCloseClick={onCloseClick}
          onDelete={onContactDeleteHandler}
        />
      )}

      {editContactShow && (
        <EditContact contact={selectedContact[0]} onCloseClick={onCloseClick} />
      )}
      <Container>
        <Row className='align-items-md-center'>
          {contactList.length !== 0 &&
            contactList.map((c) => {
              return (
                <Col
                  key={c.id}
                  className='m-auto mt-5 mb-5 col-xl-4 col-sm-6 p-2'>
                  <ContactItem
                    key={c.id}
                    contact={c}
                    onDeleteClick={onDeleteClick}
                    onEditClick={onEditClick}></ContactItem>
                </Col>
              );
            })}
        </Row>
      </Container>
    </>
  );
};
