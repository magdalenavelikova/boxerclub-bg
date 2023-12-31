import { useEffect, useState } from "react";
import Modal from "react-bootstrap/Modal";
import { Alert, Button, Container, Form } from "react-bootstrap";
import { useAuthContext } from "../../contexts/AuthContext";
import { useForm } from "../../hooks/useForm";
import { useTranslation } from "react-i18next";
export const ForgottenPasswordModal = () => {
  const [show, setShow] = useState(true);
  const { t } = useTranslation();
  const handleClose = () => {
    setShow(false);
  };
  const { onForgottenPasswordSubmitHandler, errors, success } =
    useAuthContext();
  const [email, setEmail] = useState({});

  const ForgottenPasswordFormKeys = {
    Username: "username",
  };
  useEffect(() => {
    setShow(true);
  }, []);
  useEffect(() => {
    if (Object.keys(success).length > 0) {
      setShow(false);
    }
  }, [success]);
  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [ForgottenPasswordFormKeys.Username]: "",
    },
    onForgottenPasswordSubmitHandler
  );
  useEffect(() => {
    setEmail({});

    if (errors === null) {
      setEmail({});
    } else {
      for (const [key, value] of Object.entries(errors)) {
        switch (key) {
          case "email":
            setEmail(value);
            break;

          default:
            break;
        }
      }
    }
  }, [errors]);
  return (
    <Modal
      show={show}
      onHide={handleClose}
      size='lg'
      aria-labelledby='contained-modal-title-vcenter'
      centered>
      <Modal.Header closeButton>
        <Modal.Title id='contained-modal-title-vcenter'>
          {t("ForgottenPassword")}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container className='m-auto pt-2 mb-3'>
          <Alert className=' m-auto  text-center' variant='success'>
            {t("Forgotten password")}
          </Alert>
        </Container>
        <Container className='m-auto container-fluid-md'>
          <Form
            noValidate
            validated={validated}
            method='POST'
            onSubmit={onSubmit}
            className='row g-3 m-auto   rounded p-2 pt-5'>
            <Form.Group
              className='col-md-12  mb-3'
              controlId='formBasicRegisterEmail'>
              <Form.Label>{t("email")} </Form.Label>
              <Form.Control
                required
                name={ForgottenPasswordFormKeys.Username}
                value={formValues[ForgottenPasswordFormKeys.Username]}
                onChange={onChangeHandler}
                type='email'
                placeholder={t("email")}
              />
              {Object.keys(email).length !== 0 && (
                <Form.Control.Feedback className='text-danger'>
                  {email}
                </Form.Control.Feedback>
              )}
            </Form.Group>

            <Button
              className='col-md-4  m-auto mt-4 mb-3'
              variant='secondary'
              type='submit'>
              {t("forms.Button.Submit")}
            </Button>
          </Form>
        </Container>
      </Modal.Body>
    </Modal>
  );
};
