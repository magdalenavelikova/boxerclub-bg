import { Button, Container, Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useAuthContext } from "../../contexts/AuthContext";
import { useForm } from "../../hooks/useForm";
import { useEffect, useState } from "react";
import { SuccessModalUser } from "../Modal/SuccessModalUser";

export const RegisterPage = () => {
  const { t } = useTranslation();
  const { onRegisterSubmitHandler, errors, success } = useAuthContext();
  const [email, setEmail] = useState({});
  const [password, setPassword] = useState({});
  const [confirmPassword, setConfirmPassword] = useState({});
  const [firstName, setFirstName] = useState({});
  const [lastName, setLastName] = useState({});
  const [showSuccess, setShowSuccess] = useState({});
  const [eye, setEye] = useState(true);
  const [type, setType] = useState(false);
  const [confirmEye, setConfirmEye] = useState(true);
  const [confirmType, setConfirmType] = useState(false);
  const [passwordField, setPasswordField] = useState("password");
  const [confirmPasswordField, setConfirmPasswordField] = useState("password");

  const Eye = () => {
    if (passwordField == "password") {
      setPasswordField("text");
      setEye(false);
      setType(true);
    } else {
      setPasswordField("password");
      setEye(true);
      setType(false);
    }
  };
  const ConfirmEye = () => {
    if (confirmPasswordField == "password") {
      setConfirmPasswordField("text");
      setConfirmEye(false);
      setConfirmType(true);
    } else {
      setConfirmPasswordField("password");
      setConfirmEye(true);
      setConfirmType(false);
    }
  };
  const RegisterFormKeys = {
    Email: "email",
    Password: "password",
    ConfirmPassword: "confirmPassword",
    FirstName: "firstName",
    LastName: "lastName",
    Country: "country",
    City: "city",
  };

  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [RegisterFormKeys.Email]: "",
      [RegisterFormKeys.Password]: "",
      [RegisterFormKeys.ConfirmPassword]: "",
      [RegisterFormKeys.FirstName]: "",
      [RegisterFormKeys.LastName]: "",
      [RegisterFormKeys.Country]: "",
      [RegisterFormKeys.City]: "",
    },
    onRegisterSubmitHandler
  );
  useEffect(() => {
    if (success) {
      setShowSuccess(success);
    }
  }, [success]);

  useEffect(() => {
    setEmail({});
    setPassword({});
    setConfirmPassword({});
    setFirstName({});
    setLastName({});
    if (errors === null) {
      setEmail({});
      setPassword({});
      setConfirmPassword({});
      setFirstName({});
      setLastName({});
    } else {
      for (const [key, value] of Object.entries(errors)) {
        switch (key) {
          case "email":
            setEmail(value);
            break;
          case "password":
            setPassword(value);
            break;
          case "confirmPassword":
            setConfirmPassword(value);
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
      {Object.keys(showSuccess).length !== 0 && <SuccessModalUser />}
      <Container className='m-auto container-sm'>
        <Form
          noValidate
          validated={validated}
          method='POST'
          onSubmit={onSubmit}
          className='row g-3 m-auto   rounded p-5'>
          <Form.Group className='col-md-6 mb-3' controlId='formBasicFirstName'>
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
          <Form.Group className='col-md-6 mb-3' controlId='formBasicLastName'>
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
          <Form.Group className='col-md-6 mb-3' controlId='formBasicCountry'>
            <Form.Label>{t("forms.Country")}</Form.Label>
            <Form.Control
              name={RegisterFormKeys.Country}
              value={formValues[RegisterFormKeys.Country]}
              onChange={onChangeHandler}
              type='text'
              placeholder={t("EnterCountry")}
            />
          </Form.Group>
          <Form.Group className='col-md-6 mb-3' controlId='formBasicCity'>
            <Form.Label>{t("forms.City")}</Form.Label>
            <Form.Control
              name={RegisterFormKeys.City}
              value={formValues[RegisterFormKeys.City]}
              onChange={onChangeHandler}
              type='text'
              placeholder={t("EnterCity")}
            />
          </Form.Group>

          <Form.Group
            className='col-md-12  mb-3'
            controlId='formBasicRegisterEmail'>
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

          <Form.Group
            className='col-md-6 mb-3'
            controlId='formBasicRegisterPassword'>
            <Form.Label>{t("forms.Password")}</Form.Label>
            <div className='form'>
              <Form.Control
                required
                name={RegisterFormKeys.Password}
                value={formValues[RegisterFormKeys.Password]}
                onChange={onChangeHandler}
                type={passwordField}
                placeholder={t("forms.Password")}
              />
              <i
                onClick={Eye}
                className={`fa ${eye ? "fa-eye-slash" : "fa-eye"}`}></i>
            </div>
            {Object.keys(password).length !== 0 && (
              <Form.Control.Feedback className='text-danger'>
                {password}
              </Form.Control.Feedback>
            )}
          </Form.Group>
          <Form.Group className='col-md-6 mb-3' controlId='formConfirmPassword'>
            <Form.Label>{t("forms.ConfirmPassword")}</Form.Label>
            <div className='form'>
              <Form.Control
                required
                name={RegisterFormKeys.ConfirmPassword}
                value={formValues[RegisterFormKeys.ConfirmPassword]}
                onChange={onChangeHandler}
                type={confirmPasswordField}
                placeholder={t("forms.ConfirmPassword")}
              />
              <i
                onClick={ConfirmEye}
                className={`fa ${confirmEye ? "fa-eye-slash" : "fa-eye"}`}></i>
            </div>
            {Object.keys(confirmPassword).length !== 0 && (
              <Form.Control.Feedback className='text-danger'>
                {confirmPassword}
              </Form.Control.Feedback>
            )}
          </Form.Group>

          <Button
            className='col-md-4  m-auto mt-4 mb-3'
            variant='secondary'
            type='submit'>
            {t("forms.Button.Register")}
          </Button>
        </Form>
      </Container>
    </>
  );
};
