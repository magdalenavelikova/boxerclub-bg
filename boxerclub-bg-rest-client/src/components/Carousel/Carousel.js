import { DogContext } from "../../contexts/DogContext";

import { Button, Carousel } from "react-bootstrap";
import { Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import Card from "react-bootstrap/Card";
import { Maintenance } from "../Maintenance/Maintenance";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../contexts/AuthContext";

export const CarouselLayout = () => {
  const { dogs, getDogDetails, getDogChart } = useContext(DogContext);
  const { isAuthenticated, authorities } = useContext(AuthContext);

  const { t } = useTranslation();
  const boxer = require("../../assets/dogs/boxer-vector.png");
  const [dogsList, setDogsList] = useState([]);
  const [isRendered, setIsRendered] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setIsRendered(true);
    }, 2000);
    return () => clearTimeout(timer);
  }, []);

  const isAdminOrModerator =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));
  useEffect(() => {
    isAdminOrModerator
      ? setDogsList(dogs)
      : setDogsList(dogs.filter((d) => d.ownerId !== null));
  }, []);

  useEffect(() => {
    isAdminOrModerator
      ? setDogsList(dogs)
      : setDogsList(dogs.filter((d) => d.ownerId !== null));
  }, [dogs]);

  const array_chunks = (array, chunk_size) =>
    Array(Math.ceil(array.length / chunk_size))
      .fill()
      .map((_, index) => index * chunk_size)
      .map((begin) => array.slice(begin, begin + chunk_size));

  const chunks = array_chunks(dogsList, 3);

  const onInfoClick = (dogId) => {
    getDogDetails(dogId);
  };
  const onChartClick = (dogId) => {
    getDogChart(dogId);
  };
  return (
    <>
      {dogsList.length !== 0 && (
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
                          className='pb-2'
                          variant='top'
                          src={
                            c.pictureUrl !== "" && c.pictureUrl
                              ? c.pictureUrl
                              : boxer
                          }
                        />
                        <Card.Body>
                          <Card.Title>
                            {c && c.name}{" "}
                            <Button
                              className='me-1 mb-2 custom-sm-button'
                              variant='outline-light'
                              title={t("nav.Pedigree")}
                              onClick={() => onChartClick(c.id)}>
                              <i class='fas fa-thin fa-diagram-project'></i>
                            </Button>
                          </Card.Title>
                          <Card.Text className='d-none d-lg-block'>
                            {t("birthday")}: {c.birthday}
                            <br />
                            {t("registrationNum")}: {c.registrationNum}
                            <br />
                            {t("sex")}: {t(`${c.sex}`)} <br />
                            {t("color")}: {t(`${c.color}`)}
                            <br />
                            {t("kennel")}: {c.kennel}
                          </Card.Text>
                        </Card.Body>
                        <Button
                          className='col-md-4 p-1 m-auto mt-2 mb-3 text-secondary'
                          variant='light'
                          size='sm'
                          onClick={() => onInfoClick(c.id)}>
                          {t("Details")}
                        </Button>
                      </Card>
                    );
                  })}
                </Container>
              </Carousel.Item>
            );
          })}
        </Carousel>
      )}
      {Array.isArray(dogs) && dogs.length === 0 && isRendered && (
        <Maintenance />
      )}
    </>
  );
};
