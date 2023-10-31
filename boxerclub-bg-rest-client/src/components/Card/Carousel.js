import { useDogContext } from "../../contexts/DogContext";

import { Carousel } from "react-bootstrap";
import { Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import Card from "react-bootstrap/Card";
import { Maintenance } from "../Maintenance/Maintenance";

export const CarouselLayout = () => {
  const { dogs } = useDogContext();
  const { t } = useTranslation();
  const boxer = require("../../assets/dogs/boxer-vector.png");

  const array_chunks = (array, chunk_size) =>
    Array(Math.ceil(array.length / chunk_size))
      .fill()
      .map((_, index) => index * chunk_size)
      .map((begin) => array.slice(begin, begin + chunk_size));

  const chunks = array_chunks(dogs, 3);

  return (
    <>
      {dogs.length !== 0 && (
        <Carousel className='pt-5 pb-5' data-bs-theme='dark'>
          {chunks.map((chuk, idx) => {
            return (
              <Carousel.Item key={idx}>
                <Container className='d-flex mt-4 p-4 justify-content-center'>
                  {chuk.map((c) => {
                    return (
                      <Card
                        key={c.id}
                        className='mx-2'
                        style={{ width: "18rem" }}>
                        <Card.Img
                          variant='top'
                          src={
                            c.pictureUrl !== "" && c.pictureUrl
                              ? c.pictureUrl
                              : boxer
                          }
                        />
                        <Card.Body>
                          <Card.Title>{c && c.name}</Card.Title>
                          <Card.Text>
                            {t("forms.Birthday")}: {c.birthday}
                            <br />
                            {t("forms.RegistrationNum")}: {c.registrationNum}
                            <br />
                            {t("forms.Sex")}: {c.sex} <br />
                            {t("forms.Color")}: {c.color} <br />
                            {t("forms.Kennel")}: {c.kennel}
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
      )}
    </>
  );
};
