import { useNavigate, useSearchParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import { Row, Container, Col } from "react-bootstrap";
export const RegisterConfirm = () => {
  const [searchParams] = useSearchParams();

  const token = searchParams.get("token");

  const { onRegisterVerifyHandler, errors, success } = useContext(AuthContext);
  const [error, setError] = useState();
  console.log(success);

  let navigate = useNavigate();
  useEffect(() => {
    onRegisterVerifyHandler(token);
    setError(errors);
  }, [token]);
  useEffect(() => {
    setError(errors);
  }, [errors]);
  console.log(error);
  return (
    <>
      {success && (
        <Container fluid className=' m-auto p-5'>
          <Row xs={1} md={2}>
            <Col className='m-auto border border-secondary'>
              <h2> success</h2>
              <h6>Please login with your account</h6>
            </Col>
          </Row>
        </Container>
      )}
      {errors && (
        <Container fluid className=' m-auto p-5'>
          <Row xs={1} md={2}>
            <Col className=' m-auto border-secondary'>
              <h2>errors</h2>
            </Col>
          </Row>
        </Container>
      )}
    </>
  );
};
