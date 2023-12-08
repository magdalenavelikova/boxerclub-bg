import React, { useContext, useEffect, useState } from "react";
import {
  Container,
  Row,
  Col,
  Button,
  Alert,
  Form,
  Spinner,
  Nav,
} from "react-bootstrap";

import { useTranslation } from "react-i18next";
import { AuthContext } from "../../contexts/AuthContext";
import { useForm } from "../../hooks/useForm";
import { Link } from "react-router-dom";
import { SuccessModalMembership } from "../Modal/SuccessModalMembership";

export const Membership = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [show, setShow] = useState(false);
  const { t } = useTranslation();

  const [registrationNum, setRegistrationNum] = useState({});
  const { onMembershipRequestSubmitHandler, email, spinner, success, error } =
    useContext(AuthContext);

  const MembershipFormKeys = {
    Username: "username",
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

  const { formValues, onSubmit, validated } = useForm(
    {
      [MembershipFormKeys.Username]: `${email}`,
    },
    onMembershipRequestSubmitHandler
  );
  return (
    <Container className='m-auto container-fluid-md pt-5'>
      <Form
        noValidate
        validated={validated}
        method='POST'
        onSubmit={onSubmit}
        className='row g-3 m-auto mt-5 border border-secondary rounded p-3'>
        <Form.Label className='d-inline-block pb-2'>
          {t("MembershipApplication")}
        </Form.Label>
        <Row xs={1} md={2} className='pt-5'>
          <Alert className='col-md-10 m-auto  text-center' variant='success'>
            {t("MembershipText")
              .split("\n")
              .map((line, index) => (
                <p key={index}>{line}</p>
              ))}
            <Nav.Link
              as={Link}
              className='me-2'
              to={"../../regulations/act-of-incorporation"}>
              {t("nav.Regulations.ActOfIncorporation")}
            </Nav.Link>
          </Alert>
        </Row>
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
            {t("MembershipApplication")}
          </Button>
        </Row>
      </Form>
      {show && <SuccessModalMembership />}
    </Container>
  );
};
