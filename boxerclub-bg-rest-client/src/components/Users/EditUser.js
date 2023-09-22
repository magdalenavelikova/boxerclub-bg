import { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import { useTranslation } from "react-i18next";
import { useAuthContext } from "../../contexts/AuthContext";
import { useForm } from "../../hooks/useForm";

export const EditUser = ({ onEdit, onCloseClick, user }) => {
  const [show, setShow] = useState(true);
  const { t } = useTranslation();
  const { errors } = useAuthContext();
  const [email, setEmail] = useState({});
  const [firstName, setFirstName] = useState({});
  const [lastName, setLastName] = useState({});
  const [userRoles, setUserRoles] = useState([]);
  const handleClose = () => {
    onCloseClick();
    setShow(false);
  };

  const RegisterFormKeys = {
    Email: "email",
    FirstName: "firstName",
    LastName: "lastName",
    Country: "country",
    City: "city",
    Roles: ["ADMIN", "MODERATOR", "MEMBER", "USER"],
  };

  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [RegisterFormKeys.Email]: user.email,
      [RegisterFormKeys.FirstName]: user.firstName,
      [RegisterFormKeys.LastName]: user.lastName,
      [RegisterFormKeys.Country]: user.country,
      [RegisterFormKeys.City]: user.city,
      [RegisterFormKeys.Roles]: [],
    },
    onEdit
  );
  useEffect(() => {
    let arr = [];
    Object.values(user.roles).forEach((obj) => {
      for (const [key, value] of Object.entries(obj)) {
        arr.push(value);
      }
    });
    setUserRoles(arr);
  }, []);

  useEffect(() => {
    if (errors === null) {
      setEmail({});
      setFirstName({});
      setLastName({});
    } else {
      for (const [key, value] of Object.entries(errors)) {
        switch (key) {
          case "email":
            setEmail(value);
            break;
          case "firstName":
            setFirstName(value);
            break;
          case "lastName":
            setLastName(value);
            break;
          default:
            break;
        }
      }
    }
  }, [errors]);

  return (
    <>
      <Modal
        show={show}
        fullscreen='true'
        onHide={handleClose}
        backdrop='static'
        keyboard={false}>
        <Modal.Header closeButton>
          <Modal.Title>User Details</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form
            noValidate
            validated={validated}
            method='POST'
            onSubmit={onSubmit}
            className=' m-auto p-1'>
            <Form.Group className='mb-3' controlId='formBasicFirstName'>
              <Form.Label>{t("forms.FirstName")}</Form.Label>
              <Form.Control
                required
                name={RegisterFormKeys.FirstName}
                value={formValues[RegisterFormKeys.FirstName]}
                onChange={onChangeHandler}
                type='text'
                placeholder={t("EnterFirstName")}
              />
              {Object.keys(firstName).length !== 0 && (
                <Form.Control.Feedback className='text-danger'>
                  {firstName}
                </Form.Control.Feedback>
              )}
            </Form.Group>
            <Form.Group className=' mb-3' controlId='formBasicLastName'>
              <Form.Label>{t("forms.LastName")}</Form.Label>
              <Form.Control
                required
                name={RegisterFormKeys.LastName}
                value={formValues[RegisterFormKeys.LastName]}
                onChange={onChangeHandler}
                type='text'
                placeholder={t("EnterLastName")}
              />
              {Object.keys(lastName).length !== 0 && (
                <Form.Control.Feedback className='text-danger'>
                  {lastName}
                </Form.Control.Feedback>
              )}
            </Form.Group>
            <Form.Group className=' mb-3' controlId='formBasicCountry'>
              <Form.Label>{t("forms.Country")}</Form.Label>
              <Form.Control
                name={RegisterFormKeys.Country}
                value={formValues[RegisterFormKeys.Country]}
                onChange={onChangeHandler}
                type='text'
                placeholder={t("EnterCountry")}
              />
            </Form.Group>
            <Form.Group className=' mb-3' controlId='formBasicCity'>
              <Form.Label>{t("forms.City")}</Form.Label>
              <Form.Control
                name={RegisterFormKeys.City}
                value={formValues[RegisterFormKeys.City]}
                onChange={onChangeHandler}
                type='text'
                placeholder={t("EnterCity")}
              />
            </Form.Group>

            <Form.Group className=' mb-3' controlId='formBasicEmail'>
              <Form.Label>{t("forms.Email")} </Form.Label>
              <Form.Control
                required
                name={RegisterFormKeys.Email}
                value={formValues[RegisterFormKeys.Email]}
                onChange={onChangeHandler}
                type='email'
                placeholder={t("forms.Email")}
              />
              {Object.keys(email).length !== 0 && (
                <Form.Control.Feedback className='text-danger'>
                  {email}
                </Form.Control.Feedback>
              )}
            </Form.Group>
            <Form.Label>{t("forms.Roles")} </Form.Label>
            <br />
            {RegisterFormKeys.Roles.map((role, index) => (
              <Form.Check
                key={index}
                inline
                required
                name={role}
                checked={userRoles.includes(role)}
                label={role}
                onChange={onChangeHandler}
                placeholder={t("forms.Roles")}
              />
            ))}
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant='secondary' onClick={() => onCloseClick()}>
            Close
          </Button>
          <Button variant='primary' onClick={() => onEdit()}>
            Save Changes
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
