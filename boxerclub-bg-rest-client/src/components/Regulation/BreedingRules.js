import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import { Worker, Viewer } from "@react-pdf-viewer/core";
import "@react-pdf-viewer/core/lib/styles/index.css";

export const BreedingRules = () => {
  const pdfPath = require("../../assets/membership/BreedingRules.pdf");

  return (
    <Container className='m-auto container-fluid-md pt-5'>
      <Row className='row g-3 m-auto mt-5 border border-secondary rounded p-3'>
        <Col>
          <Worker workerUrl='https://unpkg.com/pdfjs-dist@3.4.120/build/pdf.worker.min.js'>
            <Viewer fileUrl={pdfPath} />
          </Worker>
        </Col>
      </Row>
    </Container>
  );
};
