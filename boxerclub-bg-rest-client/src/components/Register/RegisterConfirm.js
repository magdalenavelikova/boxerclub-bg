import { useSearchParams } from "react-router-dom";
import { useContext, useEffect } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import { Row, Container, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";
export const RegisterConfirm = () => {
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");
  const { t } = useTranslation();
  const { onRegisterVerifyHandler, errors, success } = useContext(AuthContext);

  useEffect(() => {
    onRegisterVerifyHandler(token);
  }, [token]);

  return (
    <>
      {Object.keys(success).length !== 0 && (
        <Container fluid className=' m-auto mt-5 p-5'>
          <Row xs={1} md={2} className='pt-5'>
            <Col className='m-auto  '>
              <h2 className='pt-5'> {t("RegisterVerifySuccess")}</h2>
            </Col>
          </Row>
        </Container>
      )}
      {Object.keys(errors).length !== 0 && (
        <Container fluid className='m-auto p-5'>
          <Row xs={1} md={2}>
            <Col className='m-auto border-secondary'>
              <h2 className='text-center'>{Object.values(errors)}</h2>
              <h6 className='text-center'>Please, register yourself again</h6>
            </Col>
          </Row>
        </Container>
      )}
    </>
  );
};
