import { useEffect, useState } from "react";

import {
  Alert,
  Button,
  Col,
  Container,
  Form,
  Modal,
  Row,
  Spinner,
} from "react-bootstrap";
import { useAuthContext } from "../../contexts/AuthContext";
import { useForm } from "../../hooks/useForm";
import { useTranslation } from "react-i18next";
import { SuccessModalNewPassword } from "../Modal/SuccessModalNewPassword";

export const ChangePassword = ({ onCloseClick }) => {
  const { t } = useTranslation();
  const [show, setShow] = useState(true);
  const [eye, setEye] = useState(true);
  const [confirmEye, setConfirmEye] = useState(true);
  const [oldPasswordField, setOldPasswordField] = useState("password");
  const [newPasswordField, setNewPasswordField] = useState("password");
  const [confirmPasswordField, setConfirmPasswordField] = useState("password");
  const [isLoading, setIsLoading] = useState(false);
  const [oldPassword, setOldPassword] = useState({});
  const [newPassword, setNewPassword] = useState({});
  const [confirmNewPassword, setConfirmNewPassword] = useState({});
  const [error, setError] = useState({});
  const [showSuccess, setShowSuccess] = useState({});

  const { onChangePasswordSubmitHandler, errors, spinner, success } =
    useAuthContext();

  const OldEye = () => {
    // eslint-disable-next-line eqeqeq
    if (oldPasswordField == "password") {
      setOldPasswordField("text");
      setEye(false);
    } else {
      setOldPasswordField("password");
      setEye(true);
    }
  };
  const NewEye = () => {
    // eslint-disable-next-line eqeqeq
    if (newPasswordField == "password") {
      setNewPasswordField("text");
      setEye(false);
    } else {
      setNewPasswordField("password");
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
  const handleClose = () => {
    onCloseClick();
    setShow(false);
  };
  const ChangePasswordFormKeys = {
    OldPassword: "oldPassword",
    NewPassword: "newPassword",
    ConfirmNewPassword: "confirmNewPassword",
  };

  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [ChangePasswordFormKeys.OldPassword]: "",
      [ChangePasswordFormKeys.NewPassword]: "",
      [ChangePasswordFormKeys.ConfirmNewPassword]: "",
    },
    onChangePasswordSubmitHandler
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
    setOldPassword({});
    setNewPassword({});
    setConfirmNewPassword({});
    setError({});

    if (errors === null) {
      setOldPassword({});
      setNewPassword({});
      setConfirmNewPassword({});
      setError({});
    } else {
      for (const [key, value] of Object.entries(errors)) {
        switch (key) {
          case "oldPassword":
            setOldPassword(value);
            break;
          case "newPassword":
            setNewPassword(value);
            break;
          case "confirmNewPassword":
            setConfirmNewPassword(value);
            break;
          case "oldPasswordNotMatch":
            setError(Object.values(errors));
            break;
          default:
            break;
        }
      }
    }
  }, [errors]);

  return (
    <>
      {Object.keys(showSuccess).length !== 0 && (
        <SuccessModalNewPassword message={success} />
      )}
      <Modal
        id='modal'
        show={show}
        onHide={handleClose}
        backdrop='static'
        keyboard={false}>
        <Modal.Header closeButton>
          <Modal.Title>{t("Change password")}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form
            noValidate
            validated={validated}
            method='POST'
            onSubmit={onSubmit}
            className='row g-3 m-auto mt-5 mb-5 border border-secondary rounded p-4'>
            {error && Object.keys(error).length !== 0 && (
              <Row xs={1} md={2} className=' mt-4'>
                <Alert
                  className='col-md-12 m-auto  text-center'
                  variant='danger'>
                  {t("oldPasswordNotMatch")}
                </Alert>
              </Row>
            )}

            <Form.Group
              className='col-xl-12 mb-3'
              controlId='formBasicOldPassword'>
              <Form.Label>{t("forms.OldPassword")}</Form.Label>
              <div className='form'>
                <Form.Control
                  required
                  name={ChangePasswordFormKeys.OldPassword}
                  value={formValues[ChangePasswordFormKeys.OldPassword]}
                  onChange={onChangeHandler}
                  type={oldPasswordField}
                  placeholder={t("forms.OldPassword")}
                />
                {Object.keys(oldPassword).length !== 0 && (
                  <Form.Control.Feedback className='text-danger'>
                    {oldPassword}
                  </Form.Control.Feedback>
                )}
                <i
                  onClick={OldEye}
                  className={`fa ${eye ? "fa-eye-slash" : "fa-eye"}`}></i>
              </div>
            </Form.Group>
            <Form.Group
              className='col-xl-12 mb-3'
              controlId='formBasicRegisterNewPassword'>
              <Form.Label>{t("forms.NewPassword")}</Form.Label>
              <div className='form'>
                <Form.Control
                  required
                  name={ChangePasswordFormKeys.NewPassword}
                  value={formValues[ChangePasswordFormKeys.NewPassword]}
                  onChange={onChangeHandler}
                  type={newPasswordField}
                  placeholder={t("forms.NewPassword")}
                />
                {Object.keys(newPassword).length !== 0 && (
                  <Form.Control.Feedback className='text-danger'>
                    {newPassword}
                  </Form.Control.Feedback>
                )}
                <i
                  onClick={NewEye}
                  className={`fa ${eye ? "fa-eye-slash" : "fa-eye"}`}></i>
              </div>
            </Form.Group>
            <Form.Group
              className='col-xl-12 mb-3'
              controlId='formConfirmPassword'>
              <Form.Label>{t("forms.ConfirmPassword")}</Form.Label>
              <div className='form'>
                <Form.Control
                  required
                  name={ChangePasswordFormKeys.ConfirmNewPassword}
                  value={formValues[ChangePasswordFormKeys.ConfirmNewPassword]}
                  onChange={onChangeHandler}
                  type={confirmPasswordField}
                  placeholder={t("forms.ConfirmPassword")}
                />
                {Object.keys(confirmNewPassword).length !== 0 && (
                  <Form.Control.Feedback className='text-danger'>
                    {confirmNewPassword}
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
        </Modal.Body>
      </Modal>
    </>
  );
};
