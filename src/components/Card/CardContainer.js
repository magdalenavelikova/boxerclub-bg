import { Row, Col, Container } from "react-bootstrap";
import { CardItem } from "./Card";

export const CardContainer = () => {
  const image1 = require("../../assets/dogs/1.png");
  const image2 = require("../../assets/dogs/2.png");
  const image3 = require("../../assets/dogs/3.png");
  const images = [];
  images.push(image1);
  images.push(image2);
  images.push(image3);
  images.push(image1);
  return (
    <Container className='d-flex mt-4 p-4 justify-content-center'>
      <Row xs={1} md={4} className='g-4'>
        {Array.from({ length: 4 }).map((_, idx) => (
          <Col key={idx}>
            <CardItem image={images[idx]} />
          </Col>
        ))}
      </Row>
    </Container>
  );
};
