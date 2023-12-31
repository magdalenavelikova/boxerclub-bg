import { Button, Container, Form, Spinner } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useAuthContext } from "../../contexts/AuthContext";
import { useForm } from "../../hooks/useForm";
import { useEffect, useState } from "react";
import { SuccessModalUser } from "../Modal/SuccessModalUser";

export const RegisterPage = () => {
  const { t } = useTranslation();
  const { onRegisterSubmitHandler, errors, success, spinner } =
    useAuthContext();
  const [email, setEmail] = useState({});
  const [password, setPassword] = useState({});
  const [confirmPassword, setConfirmPassword] = useState({});
  const [firstName, setFirstName] = useState({});
  const [lastName, setLastName] = useState({});
  const [showSuccess, setShowSuccess] = useState({});
  const [eye, setEye] = useState(true);
  const [confirmEye, setConfirmEye] = useState(true);
  const [passwordField, setPasswordField] = useState("password");
  const [confirmPasswordField, setConfirmPasswordField] = useState("password");
  const [isLoading, setIsLoading] = useState(false);

  const Eye = () => {
    // eslint-disable-next-line eqeqeq
    if (passwordField == "password") {
      setPasswordField("text");
      setEye(false);
    } else {
      setPasswordField("password");
      setEye(true);
    }
  };
  const ConfirmEye = () => {
    // eslint-disable-next-line eqeqeq
    if (confirmPasswordField == "password") {
      setConfirmPasswordField("text");
      setConfirmEye(false);
    } else {
      setConfirmPasswordField("password");
      setConfirmEye(true);
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
    setIsLoading(spinner);
  }, [spinner]);
  useEffect(() => {
    if (success !== null) {
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
      <Container className='m-auto container-fluid-md'>
        <Form
          noValidate
          validated={validated}
          method='POST'
          onSubmit={onSubmit}
          className='row g-3 m-auto   rounded p-2 pt-5'>
          <Form.Group className='col-md-6 mb-3' controlId='formBasicFirstName'>
            <Form.Label>{t("firstName")}</Form.Label>
            <Form.Control
              required
              name={RegisterFormKeys.FirstName}
              value={formValues[RegisterFormKeys.FirstName]}
              onChange={onChangeHandler}
              type='text'
              placeholder={t("EnterFirstName")}
            />
            <Form.Control.Feedback type='invalid' className='text-danger'>
              {t("validation")}
            </Form.Control.Feedback>
            {Object.keys(firstName).length !== 0 && (
              <Form.Control.Feedback className='text-danger'>
                {firstName}
              </Form.Control.Feedback>
            )}
          </Form.Group>
          <Form.Group className='col-md-6 mb-3' controlId='formBasicLastName'>
            <Form.Label>{t("lastName")}</Form.Label>
            <Form.Control
              required
              name={RegisterFormKeys.LastName}
              value={formValues[RegisterFormKeys.LastName]}
              onChange={onChangeHandler}
              type='text'
              placeholder={t("EnterLastName")}
            />
            <Form.Control.Feedback type='invalid' className='text-danger'>
              {t("validation")}
            </Form.Control.Feedback>
            {Object.keys(lastName).length !== 0 && (
              <Form.Control.Feedback className='text-danger'>
                {lastName}
              </Form.Control.Feedback>
            )}
          </Form.Group>
          <Form.Group className='col-md-6 mb-3' controlId='formBasicCountry'>
            <Form.Label>{t("country")}</Form.Label>
            <Form.Control
              name={RegisterFormKeys.Country}
              value={formValues[RegisterFormKeys.Country]}
              onChange={onChangeHandler}
              type='text'
              placeholder={t("EnterCountry")}
            />
          </Form.Group>
          <Form.Group className='col-md-6 mb-3' controlId='formBasicCity'>
            <Form.Label>{t("city")}</Form.Label>
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
            <Form.Label>{t("email")} </Form.Label>
            <Form.Control
              required
              name={RegisterFormKeys.Email}
              value={formValues[RegisterFormKeys.Email]}
              onChange={onChangeHandler}
              type='email'
              placeholder={t("email")}
            />
            <Form.Control.Feedback type='invalid' className='text-danger'>
              {t("validation")}
            </Form.Control.Feedback>
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
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
              {Object.keys(password).length !== 0 && (
                <Form.Control.Feedback className='text-danger'>
                  {password}
                </Form.Control.Feedback>
              )}
              <i
                onClick={Eye}
                className={`fa ${eye ? "fa-eye-slash" : "fa-eye"}`}></i>
            </div>
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
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
              {Object.keys(confirmPassword).length !== 0 && (
                <Form.Control.Feedback className='text-danger'>
                  {confirmPassword}
                </Form.Control.Feedback>
              )}
              <i
                onClick={ConfirmEye}
                className={`fa ${confirmEye ? "fa-eye-slash" : "fa-eye"}`}></i>
            </div>
          </Form.Group>

          <Button
            className='col-md-4  m-auto mt-4 mb-3'
            variant='secondary'
            type='submit'>
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
            {t("forms.Button.Register")}
          </Button>
        </Form>
      </Container>
    </>
  );
};
