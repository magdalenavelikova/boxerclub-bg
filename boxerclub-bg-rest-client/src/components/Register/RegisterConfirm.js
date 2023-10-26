import { useNavigate, useSearchParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import { Row, Container, Col } from "react-bootstrap";
export const RegisterConfirm = () => {
  const [searchParams] = useSearchParams();

  const token = searchParams.get("token");

  const { onRegisterVerifyHandler, errors, success } = useContext(AuthContext);
  const [error, setError] = useState();
 
  let navigate = useNavigate();
  useEffect(() => {
    onRegisterVerifyHandler(token);
    setError(errors);
  }, [token]);

  return (
    <>
      {Object.keys(success).length !== 0 && (
        <Container fluid className=' m-auto p-5'>
          <Row xs={1} md={2}>
            <Col className='m-auto border border-secondary'>
              <h2> {Object.values(success)}</h2>
              <h6>Please login with your account</h6>
            </Col>
          </Row>
        </Container>
      )}
      {Object.keys(errors).length !== 0 && (
        <Container fluid className='m-auto p-5'>
          <Row xs={1} md={2}>
            <Col className='m-auto border-secondary'>
              <h2 className='text-center'>{Object.values(errors)}</h2>
              <h6 className='text-center'>Please register yourself again</h6>
            </Col>
          </Row>
        </Container>
      )}
    </>
  );
};
