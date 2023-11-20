import { useEffect, useState } from "react";

import {
  Alert,
  Button,
  Col,
  Container,
  Form,
  Row,
  Spinner,
} from "react-bootstrap";
import { useAuthContext } from "../../contexts/AuthContext";
import { useForm } from "../../hooks/useForm";
import { useTranslation } from "react-i18next";
import { SuccessModalNewPassword } from "../../components/Modal/SuccessModalNewPassword";
import { useSearchParams } from "react-router-dom";

export const NewPasswordForgottenPassword = (props) => {
  const { t } = useTranslation();
  const [show, setShow] = useState(true);
  const [eye, setEye] = useState(true);
  const [confirmEye, setConfirmEye] = useState(true);
  const [passwordField, setPasswordField] = useState("password");
  const [confirmPasswordField, setConfirmPasswordField] = useState("password");
  const [isLoading, setIsLoading] = useState(false);
  const [password, setPassword] = useState({});
  const [confirmPassword, setConfirmPassword] = useState({});
  const [error, setError] = useState({});
  const [showSuccess, setShowSuccess] = useState({});
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");
  const {
    onForgottenPasswordNewPasswordSubmitHandler,
    errors,
    spinner,
    success,
  } = useAuthContext();

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

  const ForgottenPasswordFormKeys = {
    VerificationToken: "verificationToken",
    Password: "password",
    ConfirmPassword: "confirmPassword",
  };

  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [ForgottenPasswordFormKeys.VerificationToken]: token,
      [ForgottenPasswordFormKeys.Password]: "",
      [ForgottenPasswordFormKeys.ConfirmPassword]: "",
    },
    onForgottenPasswordNewPasswordSubmitHandler
  );
  useEffect(() => {
    setIsLoading(spinner);
  }, [spinner]);
  useEffect(() => {
    if (success) {
      setShowSuccess(success);
    }
  }, [success]);
  useEffect(() => {
    setPassword({});
    setConfirmPassword({});
    setError({});

    if (errors === null) {
      setPassword({});
      setConfirmPassword({});
      setError({});
    } else {
      for (const [key, value] of Object.entries(errors)) {
        switch (key) {
          case "password":
            setPassword(value);
            break;
          case "confirmPassword":
            setConfirmPassword(value);
            break;
          case "message":
            setError(Object.values(errors));
            break;
          default:
            break;
        }
      }
    }
  }, [errors]);
  console.log(error);
  return (
    <>
      {Object.keys(showSuccess).length !== 0 && (
        <SuccessModalNewPassword message={success} />
      )}

      <Container
        className='m-auto container-fluid-md  pt-5 mt-5'
        style={{ width: "50%" }}>
        <Row xl={12} xs={1} md={1}>
          <Form
            noValidate
            validated={validated}
            method='POST'
            onSubmit={onSubmit}
            className='row g-3 m-auto mt-5 mb-5 border border-secondary rounded p-4'>
            {show === false && (
              <Form.Control
                required
                name={ForgottenPasswordFormKeys.VerificationToken}
                value={formValues[ForgottenPasswordFormKeys.VerificationToken]}
                onChange={onChangeHandler}
                type='text'
              />
            )}
            <p className='mb-3'>{t("Change password")}</p>

            {error && Object.keys(error).length !== 0 && (
              <Row xs={1} md={2} className=' mt-4'>
                <Alert
                  className='col-md-6 m-auto  text-center'
                  variant='danger'>
                  {error}
                </Alert>
              </Row>
            )}
            <Form.Group
              className='col-xl-6 mb-3'
              controlId='formBasicRegisterPassword'>
              <Form.Label>{t("forms.Password")}</Form.Label>
              <div className='form'>
                <Form.Control
                  required
                  name={ForgottenPasswordFormKeys.Password}
                  value={formValues[ForgottenPasswordFormKeys.Password]}
                  onChange={onChangeHandler}
                  type={passwordField}
                  placeholder={t("forms.Password")}
                />
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

            <Form.Group
              className='col-xl-6 mb-3'
              controlId='formConfirmPassword'>
              <Form.Label>{t("forms.ConfirmPassword")}</Form.Label>
              <div className='form'>
                <Form.Control
                  required
                  name={ForgottenPasswordFormKeys.ConfirmPassword}
                  value={formValues[ForgottenPasswordFormKeys.ConfirmPassword]}
                  onChange={onChangeHandler}
                  type={confirmPasswordField}
                  placeholder={t("forms.ConfirmPassword")}
                />
                {Object.keys(confirmPassword).length !== 0 && (
                  <Form.Control.Feedback className='text-danger'>
                    {confirmPassword}
                  </Form.Control.Feedback>
                )}
                <i
                  onClick={ConfirmEye}
                  className={`fa ${
                    confirmEye ? "fa-eye-slash" : "fa-eye"
                  }`}></i>
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
              {t("forms.Button.Submit")}
            </Button>
          </Form>
        </Row>
      </Container>
    </>
  );
};
