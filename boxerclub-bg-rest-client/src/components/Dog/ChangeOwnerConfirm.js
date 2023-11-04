import { useSearchParams } from "react-router-dom";
import { useContext, useEffect, useState } from "react";

import { Row, Container, Col } from "react-bootstrap";
import { DogContext } from "../../contexts/DogContext";
export const ChangeOwnerConfirm = () => {
  const [searchParams] = useSearchParams();
  const registrationNum = searchParams.get("registrationNum");
  const newOwner = searchParams.get("newOwner");
  const { onChangeOwnershipVerifyHandler, errors, success } =
    useContext(DogContext);

  useEffect(() => {
    onChangeOwnershipVerifyHandler(registrationNum, newOwner);
  }, [registrationNum, newOwner]);

  return (
    <>
      {Object.keys(success).length !== 0 && (
        <Container fluid className=' m-auto mt-5 p-5'>
          <Row xs={1} md={2} className='pt-5 text-center'>
            <Col className='m-auto'>
              <h2 className='pt-5'> {Object.values(success)}</h2>
            </Col>
          </Row>
        </Container>
      )}
      {Object.keys(errors).length !== 0 && (
        <Container fluid className='m-auto p-5'>
          <Row xs={1} md={2} text-center>
            <Col className='m-auto border-secondary'>
              <h2 className='text-center'>{Object.values(errors)}</h2>
              <h6 className='text-center'>Please try again</h6>
            </Col>
          </Row>
        </Container>
      )}
    </>
  );
};
