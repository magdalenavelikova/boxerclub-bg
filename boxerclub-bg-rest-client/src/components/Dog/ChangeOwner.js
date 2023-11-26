import { Alert, Button, Container, Form, Row, Spinner } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { DogContext } from "../../contexts/DogContext";
import { useAuthContext } from "../../contexts/AuthContext";
import { useContext, useEffect, useState } from "react";
import { useForm } from "../../hooks/useForm";

export const ChangeOwner = ({ nb }) => {
  const [isLoading, setIsLoading] = useState(false);
  const [show, setShow] = useState(false);
  const { t } = useTranslation();
  const { userId } = useAuthContext();
  const [registrationNum, setRegistrationNum] = useState({});
  const { onChangeOwnershipSubmitHandler, spinner, success, error } =
    useContext(DogContext);

  const RegisterFormKeys = {
    RegistrationNum: "registrationNum",
    NewOwner: "newOwnerId",
  };
  useEffect(() => {
    setIsLoading(spinner);
  }, [spinner]);

  useEffect(() => {
    if (Object.keys(success) !== 0) {
      setShow(true);
    }
  }, [success]);

  useEffect(() => {
    setRegistrationNum(error);
  }, [error]);

  useEffect(() => {
    setRegistrationNum(error);
    setShow(false);
  }, []);

  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [RegisterFormKeys.RegistrationNum]: "",
      [RegisterFormKeys.NewOwner]: `${userId}`,
    },
    onChangeOwnershipSubmitHandler
  );

  return (
    <Container className='m-auto container-fluid-md pt-5'>
      {!show && (
        <Form
          noValidate
          validated={validated}
          method='POST'
          onSubmit={onSubmit}
          className='row g-3 m-auto mt-5 border border-secondary rounded p-3'>
          <Form.Label className='d-inline-block pb-2'>
            {t("nav.DogChangeOwner")}
          </Form.Label>

          <Form.Group
            className='col-md-4 mb-2'
            controlId='formBasicRegistrationNum'>
            <Form.Label>{t("registrationNum")}</Form.Label>
            <Form.Control
              required
              name={RegisterFormKeys.RegistrationNum}
              value={formValues[RegisterFormKeys.RegistrationNum]}
              onChange={onChangeHandler}
              type='text'
              placeholder={t("EnterRegistrationNum")}
            />
            <Form.Control.Feedback type='invalid' className='text-danger'>
              {t("validation")}
            </Form.Control.Feedback>
            {Object.keys(registrationNum).length !== 0 && (
              <Form.Control.Feedback className='text-danger'>
                {t("unknownRegNum")}
              </Form.Control.Feedback>
            )}
          </Form.Group>

          <Row xs={1} md={1} className=' mt-3'>
            <Button
              className='col-md-4 m-auto mt-3  mb-3'
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
              {t("nav.DogChangeOwner")}
            </Button>
          </Row>
        </Form>
      )}
      {show && (
        <Row className='mt-5'>
          <Alert className='col-md-6 m-auto  text-center' variant='success'>
            {success.message}
          </Alert>
        </Row>
      )}
    </Container>
  );
};
