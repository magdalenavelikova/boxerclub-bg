import { useState } from "react";
import { Container, Row } from "react-bootstrap";

export const Maintenance = (props) => {
  return (
    <Container fluid className='mt-5 mb-5 text-center '>
      <Row className='col-md-12 p-5'>
        <h1 className='mb-5'>App closed for maintenance</h1>
        <h3 className='mb-5'>
          You can see my project at my github{" "}
          <a
            href='https://github.com/magdalenavelikova/boxerclub-bg'
            target='_blank'
            rel='noreferrer'>
            profile
          </a>
        </h3>
      </Row>
    </Container>
  );
};
