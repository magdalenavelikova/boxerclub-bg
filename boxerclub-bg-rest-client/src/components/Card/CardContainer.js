import { Container } from "react-bootstrap";
import { CardItem } from "./Card";

export const CardContainer = ({ dogs }) => {
  return (
    <Container fluid className=' mt-4 p-4 justify-content-center'>
      <CardItem dog={[dogs]} />
    </Container>
  );
};
