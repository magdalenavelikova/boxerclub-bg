import { useDogContext } from "../../contexts/DogContext";
import { CardContainer } from "./CardContainer";
import { Carousel } from "react-bootstrap";
import { Row, Col, Grid, Container } from "react-bootstrap";
import { CardItem } from "./Card";
import Card from "react-bootstrap/Card";

export const CarouselLayout = () => {
  const { dogs } = useDogContext();
  const boxer = require("../../assets/dogs/boxer-vector.png");

  const array_chunks = (array, chunk_size) =>
    Array(Math.ceil(array.length / chunk_size))
      .fill()
      .map((_, index) => index * chunk_size)
      .map((begin) => array.slice(begin, begin + chunk_size));

  // dogs && (chunkSize = dogs.length / 3 / 3);
  const chunks = array_chunks(dogs, 3);
  /* console.log(Array.prototype.flat.call(chunks[0]));
  console.log(Object.keys(chunks)); // ['0', '1', '2', '5']
  console.log(chunks.length); */
  console.log(chunks);
  return (
    <Carousel className={"pb-5"} data-bs-theme='dark'>
      {chunks.map((chuk, idx) => {
        return (
          <Carousel.Item key={idx}>
            <Container className='d-flex mt-4 p-4 justify-content-center'>
              {chuk.map((c) => {
                return (
                  <Card className='mx-3' style={{ width: "16rem" }}>
                    <Card.Img
                      variant='top'
                      src={
                        c.pictureUrl !== "" && c.pictureUrl !== "NULL"
                          ? c.pictureUrl
                          : boxer
                      }
                    />
                    <Card.Body>
                      <Card.Title>{c && c.name}</Card.Title>
                      <Card.Text>
                        `id:{c.id}` Some quick example text to build on the card
                        title and make up the bulk of the card's content.
                      </Card.Text>
                    </Card.Body>
                  </Card>
                );
              })}
            </Container>
          </Carousel.Item>
        );
      })}
    </Carousel>
  );
  /* <Carousel className={"pb-5"} data-bs-theme='dark'>
      {chunks &&
        chunks.map((dogsChunk, idx) => (
          <Carousel.Item key={idx}>
            <CardContainer dogs={dogsChunk[idx]} />
             <Button variant='secondary'>Details</Button>
          </Carousel.Item>
        ))}
    </Carousel>*/
};
