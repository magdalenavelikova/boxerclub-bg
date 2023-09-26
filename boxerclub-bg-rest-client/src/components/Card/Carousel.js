import { useDogContext } from "../../contexts/DogContext";
import { CardContainer } from "./CardContainer";
import { Carousel } from "react-bootstrap";
import { CardItem } from "./Card";
import Card from "react-bootstrap/Card";

export const CarouselLayout = () => {
  const { dogs } = useDogContext();
  const chunkSize = dogs.length / 3 / 3;
  const chunks = [];
  const boxer = require("../../assets/dogs/boxer-vector.jpg");
  for (let i = 0; i < dogs.length; i += chunkSize) {
    const chunk = dogs.slice(i, i + chunkSize);
    chunks.push(chunk);
  }

  /* console.log(Array.prototype.flat.call(chunks[0]));
  console.log(Object.keys(chunks)); // ['0', '1', '2', '5']
  console.log(chunks.length); */
  return (
    <Carousel className={"pb-5"} data-bs-theme='dark'>
      {chunks &&
        chunks.map((dogsChunk, idx) => (
          <Carousel.Item key={idx}>
            <CardContainer dogs={dogsChunk[idx]} />
          </Carousel.Item>
        ))}
    </Carousel>
  );
};
/*  */
