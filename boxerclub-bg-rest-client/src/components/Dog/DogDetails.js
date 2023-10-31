import { Button, Container, Col, Row, Card, Table } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useDogContext } from "../../contexts/DogContext";
import { useContext, useEffect, useState } from "react";

import { AuthContext } from "../../contexts/AuthContext";
import * as formatString from "../../utils/StringUtils";
export const DogDetails = () => {
  const { t } = useTranslation();
  const { isAuthenticated, authorities } = useContext(AuthContext);
  const boxer = require("../../assets/dogs/boxer-vector.png");
  const { selectedDog, onDownloadPedigree } = useDogContext();
  const isAdmin =
    isAuthenticated && authorities.some((item) => item === "ROLE_ADMIN");

  return (
    <Container fluid className='mt-5 pt-5 fill-left content-padding'>
      <Row className='mb-5 p-3' style={{ backgroundColor: "#f1f1f1" }}>
        <Col md={2} className='ps-5'>
          <img
            src={
              selectedDog.dog.pictureUrl !== "" && selectedDog.dog.pictureUrl
                ? selectedDog.dog.pictureUrl
                : boxer
            }
            className='d-none d-lg-block rounded-circle  avatar-md '
            alt=''
          />
        </Col>
        <Col md={10}>
          <h1 className='text-success'>
            {formatString.formatStringToUpperCaseWithSpaces(
              selectedDog.dog.name
            )}
          </h1>
        </Col>
      </Row>

      <Row className='mt-5 p-3 '>
        <Col className='col-lg-6 mb-3'>
          <Card className='m-auto'>
            <Card.Body>
              <Card.Title className='text-success mb-3'>
                {t("Details")}
              </Card.Title>
              <Card.Text>
                <Table
                  className='align-middle table-borderless'
                  responsive='md'>
                  <tbody>
                    <tr>
                      <td className='pb-2'>{t("forms.Name")}</td>
                      <td className='pb-2'>{selectedDog.dog.name}</td>
                    </tr>
                    <tr>
                      <td className='pb-2'>{t("forms.RegistrationNum")}</td>
                      <td className='pb-2'>
                        {selectedDog.dog.registrationNum}
                      </td>
                    </tr>
                    <tr>
                      <td className='pb-2'>{t("forms.Microchip")}</td>
                      <td className='pb-2'>{selectedDog.dog.microChip}</td>
                    </tr>
                    <tr>
                      <td className='pb-2'>{t("forms.Sex")}</td>
                      <td className='pb-2'>{selectedDog.dog.sex}</td>
                    </tr>
                    <tr>
                      <td className='pb-2'>{t("forms.Color")}</td>
                      <td className='pb-2'>{selectedDog.dog.color}</td>
                    </tr>
                    <tr>
                      <td className='pb-2'>{t("forms.HealthStatus")}</td>
                      <td className='pb-2'>{selectedDog.dog.healthStatus}</td>
                    </tr>
                    <tr>
                      <td className='pb-2'>{t("forms.Kennel")}</td>
                      <td className='pb-2'>{selectedDog.dog.kennel}</td>
                    </tr>
                  </tbody>
                </Table>
              </Card.Text>
              {selectedDog.dog.hasPedigree && (
                <Button
                  variant='secondary'
                  size='sm'
                  onClick={() => onDownloadPedigree(selectedDog.dog.id)}>
                  {t("Download Pedigree")}
                </Button>
              )}
            </Card.Body>
          </Card>
        </Col>
        {selectedDog.parents.length > 0 && (
          <Col className='col-lg-6 mb-3'>
            <Card className='m-auto '>
              <Card.Body>
                <Card.Title className='text-success'>{t("Parents")}</Card.Title>
                <Card.Text>
                  <Table
                    className='align-middle table-borderless'
                    responsive='md'>
                    <tbody>
                      {selectedDog.parents[0] && (
                        <>
                          <tr className='text-success fw-bold'>
                            <td colSpan={2}>{t("Mother")}</td>
                          </tr>
                          <tr>
                            <td className='pb-2'>{t("forms.Name")}</td>
                            <td className='pb-2'>
                              {selectedDog.parents[0].name}
                            </td>
                          </tr>
                          <tr>
                            <td className='pb-2'>
                              {t("forms.RegistrationNum")}
                            </td>
                            <td className='pb-2'>
                              {selectedDog.parents[0].registrationNum}
                            </td>
                          </tr>

                          <tr>
                            <td className='pb-2'>{t("forms.Color")}</td>
                            <td className='pb-2'>
                              {selectedDog.parents[0].color}
                            </td>
                          </tr>

                          <tr>
                            <td className='pb-2'>{t("forms.Kennel")}</td>
                            <td className='pb-2'>
                              {selectedDog.parents[0].kennel}
                            </td>
                          </tr>
                        </>
                      )}
                      {selectedDog.parents[1] && (
                        <>
                          <tr className='text-success fw-bold'>
                            <td colSpan={2}>{t("Father")}</td>
                          </tr>
                          <tr>
                            <td className='pb-2'>{t("forms.Name")}</td>
                            <td className='pb-2'>
                              {selectedDog.parents[1].name}
                            </td>
                          </tr>
                          <tr>
                            <td className='pb-2'>
                              {t("forms.RegistrationNum")}
                            </td>
                            <td className='pb-2'>
                              {selectedDog.parents[1].registrationNum}
                            </td>
                          </tr>

                          <tr>
                            <td className='pb-2'>{t("forms.Color")}</td>
                            <td className='pb-2'>
                              {selectedDog.parents[1].color}
                            </td>
                          </tr>

                          <tr>
                            <td className='pb-2'>{t("forms.Kennel")}</td>
                            <td className='pb-2'>
                              {selectedDog.parents[1].kennel}
                            </td>
                          </tr>
                        </>
                      )}
                    </tbody>
                  </Table>
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
        )}
      </Row>
    </Container>
  );
};
