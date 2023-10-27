import { Container, NavDropdown } from "react-bootstrap";

import Card from "react-bootstrap/Card";

import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { useAuthContext } from "../../contexts/AuthContext";

export const ContactItem = ({ contact, onEditClick, onDeleteClick }) => {
  const man = require("../../assets/contacts/man.png");
  const woman = require("../../assets/contacts/woman.png");
  const { t } = useTranslation();
  const lang = localStorage.getItem("lang");
  const { isAuthenticated, authorities } = useAuthContext();

  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));

  return (
    <Card className='mx-3 m-2 p-2' style={{ width: "20rem" }}>
      {isAuthorized && (
        <NavDropdown id='nav-dropdown'>
          <NavDropdown.Item as={Link} onClick={() => onEditClick(contact.id)}>
            {t("nav.Edit")}
          </NavDropdown.Item>
          <NavDropdown.Item as={Link} onClick={() => onDeleteClick(contact.id)}>
            {t("nav.Delete")}
          </NavDropdown.Item>
        </NavDropdown>
      )}
      <Container fluid className='align-items-centerm-2 p-2'></Container>
      <Card.Img
        className='avatar-md rounded-circle img-thumbnail'
        src={
          contact.picture && contact.picture.length !== ""
            ? contact.picture
            : contact.sex === "M"
            ? man
            : woman
        }
      />
      <Card.Body>
        <Container className='flex-1 ms-3'>
          <Card.Title className='font-size-16 mb-1'>
            {contact && lang === "en" ? contact.name : contact.nameBG}
          </Card.Title>
          <span className='badge badge-soft-success mb-2'>
            {contact && lang === "en" ? contact.position : contact.positionBG}
          </span>
        </Container>
        <Container className='mt-3 pt-1'>
          <Card.Text className='text-muted mb-2'>
            <Link
              className='myLinks text-secondary'
              to='#'
              onClick={(e) => {
                window.location.href = `tel: ${contact.phone}`;
                e.preventDefault();
              }}>
              <i className='fas fa-solid fa-mobile font-size-15 align-middle pe-2 text-success'></i>{" "}
              {contact && contact.phone}
            </Link>
          </Card.Text>
          <Card.Text className='text-muted mb-2'>
            <Link
              className='myLinks text-secondary'
              to='#'
              onClick={(e) => {
                window.location.href = `mailto: ${contact.email}`;
                e.preventDefault();
              }}>
              <i className='fas fa-envelope font-size-15 align-middle pe-2 text-success'></i>{" "}
              {contact && contact.email}
            </Link>
          </Card.Text>
          <Card.Text className='text-muted mb-3 text-success'>
            <i className='fas fa-home  font-size-15 align-middle pe-2 text-success'></i>{" "}
            {contact && lang === "en" ? contact.country : contact.countryBG},{" "}
            {contact && lang === "en" ? contact.city : contact.cityBG},{" "}
            {contact && contact.zip},{" "}
            {contact && lang === "en" ? contact.address : contact.addressBG}
          </Card.Text>
        </Container>
      </Card.Body>
    </Card>
  );
};
