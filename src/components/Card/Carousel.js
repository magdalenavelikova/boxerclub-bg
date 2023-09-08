import { CardContainer } from "./CardContainer";
import { Carousel } from "react-bootstrap";

export const CarouselLayout = () => {
  return (
    <Carousel className={"pb-5"} data-bs-theme='dark'>
      <Carousel.Item>
        <CardContainer />
      </Carousel.Item>
      <Carousel.Item>
        <CardContainer />
      </Carousel.Item>
      <Carousel.Item>
        <CardContainer />
      </Carousel.Item>
    </Carousel>
  );
};
