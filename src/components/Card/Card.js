import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";

export const CardItem = ({ image }) => {
  return (
    <Card className='mx-3' style={{ width: "16rem" }}>
      <Card.Img variant='top' src={image} />
      <Card.Body>
        <Card.Title>Card Title</Card.Title>
        <Card.Text>
          Some quick example text to build on the card title and make up the
          bulk of the card's content.
        </Card.Text>
        <Button variant='secondary'>Details</Button>
      </Card.Body>
    </Card>
  );
};
