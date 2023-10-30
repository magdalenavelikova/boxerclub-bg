import { Button, Container, Form, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useDogContext } from "../../contexts/DogContext";
import { useContext, useEffect, useState } from "react";

import { AuthContext } from "../../contexts/AuthContext";
export const DogDetails = () => {
  const { t } = useTranslation();
  const { isAuthenticated, authorities } = useContext(AuthContext);

  const { selectedDog } = useDogContext();
  const isAdmin =
    isAuthenticated && authorities.some((item) => item === "ROLE_ADMIN");
  console.log(selectedDog);
  return (
    <Container className='m-auto container-fluid-md pt-5'>
      <h1>{selectedDog.dog.name}</h1>
    </Container>
  );
};
