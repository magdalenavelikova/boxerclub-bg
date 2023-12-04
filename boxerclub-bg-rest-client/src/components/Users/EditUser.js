import { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import { useTranslation } from "react-i18next";
import { useAuthContext } from "../../contexts/AuthContext";
import { useForm } from "../../hooks/useForm";
import { Spinner } from "react-bootstrap";
import { SuccessModalNewPassword } from "../Modal/SuccessModalNewPassword";
import { SuccessModalEditUser } from "../Modal/SuccessModalEditUser";

export const EditUser = ({ onCloseClick, user, userRoles }) => {
  const [show, setShow] = useState(true);
  const { t } = useTranslation();
  const { errors, onUserEdit, isAuthenticated, authorities, spinner, success } =
    useAuthContext();
  const [email, setEmail] = useState({});
  const [firstName, setFirstName] = useState({});
  const [lastName, setLastName] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [showSuccess, setShowSuccess] = useState({});
  const isAdmin =
    isAuthenticated && authorities.some((item) => item === "ROLE_ADMIN");
  useEffect(() => {
    setIsLoading(spinner);
  }, [spinner]);
  const RegisterFormKeys = {
    Id: "id",
    Email: "email",
    FirstName: "firstName",
    LastName: "lastName",
    Country: "country",
    City: "city",
    Admin: "ROLE_Admin",
    Moderator: "ROLE_Moderator",
    Member: "ROLE_Member",
    User: "ROLE_User",
  };

  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [RegisterFormKeys.Id]: user.id,
      [RegisterFormKeys.Email]: user.email,
      [RegisterFormKeys.FirstName]: user.firstName,
      [RegisterFormKeys.LastName]: user.lastName,
      [RegisterFormKeys.Country]: user.country,
      [RegisterFormKeys.City]: user.city,
      [RegisterFormKeys.Admin]: userRoles && userRoles.includes("ADMIN"),
      [RegisterFormKeys.Moderator]:
        userRoles && userRoles.includes("MODERATOR"),
      [RegisterFormKeys.Member]: userRoles && userRoles.includes("MEMBER"),
      [RegisterFormKeys.User]: userRoles && userRoles.includes("USER"),
    },
    onUserEdit
  );
  const handleClose = () => {
    onCloseClick();
    setShow(false);
  };
  useEffect(() => {
    if (success) {
      setShowSuccess(success);
    }
  }, [success]);
  useEffect(() => {
    setEmail({});
    setFirstName({});
    setLastName({});
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
      {Object.keys(showSuccess).length !== 0 && !isAdmin && (
        <SuccessModalEditUser message={success} />
      )}
      <Modal
        id='modal'
        show={show}
        onHide={handleClose}
        backdrop='static'
        keyboard={false}>
        <Modal.Header closeButton>
          <Modal.Title>{t("Details")}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form
            noValidate
            validated={validated}
            method='PATCH'
            onSubmit={onSubmit}
            className='m-auto p-1'>
            <Form.Group>
              {show === false && (
                <Form.Control
                  required
                  name={RegisterFormKeys.Id}
                  value={formValues[RegisterFormKeys.Id]}
                  onChange={onChangeHandler}
                  type='text'
                />
              )}
            </Form.Group>
            <Form.Group className='mb-2' controlId='formBasicFirstName'>
              <Form.Label>{t("firstName")}</Form.Label>

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
            <Form.Group className='mb-2' controlId='formBasicLastName'>
              <Form.Label>{t("lastName")}</Form.Label>
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
            <Form.Group className='mb-2' controlId='formBasicCountry'>
              <Form.Label>{t("country")}</Form.Label>
              <Form.Control
                name={RegisterFormKeys.Country}
                value={formValues[RegisterFormKeys.Country]}
                onChange={onChangeHandler}
                type='text'
                placeholder={t("EnterCountry")}
              />
            </Form.Group>
            <Form.Group className='mb-2' controlId='formBasicCity'>
              <Form.Label>{t("city")}</Form.Label>
              <Form.Control
                name={RegisterFormKeys.City}
                value={formValues[RegisterFormKeys.City]}
                onChange={onChangeHandler}
                type='text'
                placeholder={t("EnterCity")}
              />
            </Form.Group>

            <Form.Group className='mb-2' controlId='formBasicEmail'>
              <Form.Label>{t("email")} </Form.Label>
              <Form.Control
                required
                name={RegisterFormKeys.Email}
                value={formValues[RegisterFormKeys.Email]}
                onChange={onChangeHandler}
                type='email'
                placeholder={t("email")}
              />
              {Object.keys(email).length !== 0 && (
                <Form.Control.Feedback className='text-danger'>
                  {t("UniqueEmail")}
                </Form.Control.Feedback>
              )}
            </Form.Group>
            {isAdmin && (
              <>
                <Form.Label>{t("roles")} </Form.Label>
                <br />
                <Form.Check
                  inline
                  name={RegisterFormKeys.Admin}
                  value={formValues[RegisterFormKeys.Admin]}
                  checked={formValues[RegisterFormKeys.Admin]}
                  label={RegisterFormKeys.Admin.split("_")[1]}
                  onChange={onChangeHandler}
                />
                <Form.Check
                  inline
                  name={RegisterFormKeys.Moderator}
                  value={formValues[RegisterFormKeys.Moderator]}
                  checked={formValues[RegisterFormKeys.Moderator]}
                  label={RegisterFormKeys.Moderator.split("_")[1]}
                  onChange={onChangeHandler}
                />
                <Form.Check
                  inline
                  name={RegisterFormKeys.Member}
                  value={formValues[RegisterFormKeys.Member]}
                  checked={formValues[RegisterFormKeys.Member]}
                  label={RegisterFormKeys.Member.split("_")[1]}
                  onChange={onChangeHandler}
                />
                <Form.Check
                  inline
                  name={RegisterFormKeys.User}
                  value={formValues[RegisterFormKeys.User]}
                  checked={formValues[RegisterFormKeys.User]}
                  label={RegisterFormKeys.User.split("_")[1]}
                  onChange={onChangeHandler}
                />
              </>
            )}
            <Button className='mt-3' variant='secondary' type='submit'>
              {isLoading && (
                <Spinner
                  as='span'
                  animation='border'
                  size='sm'
                  role='status'
                  aria-hidden='true'
                  className='me-1'
                />
              )}
              {t("Save Changes")}
            </Button>
          </Form>
        </Modal.Body>
      </Modal>
    </>
  );
};
