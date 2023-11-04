import {
  Button,
  Container,
  Col,
  Row,
  Card,
  Table,
  Modal,
} from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useDogContext } from "../../contexts/DogContext";
import { useState } from "react";

import * as formatString from "../../utils/StringUtils";
import { Link, useNavigate } from "react-router-dom";
export const DogDetails = () => {
  const { t } = useTranslation();
  const boxer = require("../../assets/dogs/boxer-vector.png");
  const { selectedDog, onDownloadPedigree, getDogDetails, clear } =
    useDogContext();
  const navigate = useNavigate();
  const [show, setShow] = useState(true);

  const handleClose = () => {
    setShow(false);

    window.history.back();
    //clear();
    // navigate("/");
  };
  const onInfoClick = (dogId) => {
    getDogDetails(dogId);
  };

  return (
    <Modal show={show} fullscreen onHide={() => handleClose()}>
      <Modal.Header closeButton>
        <Modal.Title className='text-secondary'>{t("Details")}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container fluid className='mt-3 pt-5 fill-left content-padding'>
          <Row className='mb-5 p-3' style={{ backgroundColor: "#f1f1f1" }}>
            <Col md={2} className='ps-5'>
              <Container>
                <img
                  src={
                    selectedDog.dog.pictureUrl !== "" &&
                    selectedDog.dog.pictureUrl
                      ? selectedDog.dog.pictureUrl
                      : boxer
                  }
                  className='d-none d-lg-block rounded-circle  avatar-md '
                  alt=''
                />
              </Container>
            </Col>
            <Col md={10}>
              <Container>
                <h1 className='text-success'>
                  {formatString.formatStringToUpperCaseWithSpaces(
                    selectedDog.dog.name
                  )}
                </h1>
              </Container>
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
                    <Container>
                      <Table
                        className='align-middle table-borderless'
                        responsive='md'>
                        <tbody>
                          <tr>
                            <td className='pb-2'>{t("name")}</td>
                            <td className='pb-2'>{selectedDog.dog.name}</td>
                          </tr>
                          <tr>
                            <td className='pb-2'>{t("registrationNum")}</td>
                            <td className='pb-2'>
                              {selectedDog.dog.registrationNum}
                            </td>
                          </tr>
                          <tr>
                            <td className='pb-2'>{t("microChip")}</td>
                            <td className='pb-2'>
                              {selectedDog.dog.microChip}
                            </td>
                          </tr>
                          <tr>
                            <td className='pb-2'>{t("sex")}</td>
                            <td className='pb-2'>{selectedDog.dog.sex}</td>
                          </tr>
                          <tr>
                            <td className='pb-2'>{t("color")}</td>
                            <td className='pb-2'>{selectedDog.dog.color}</td>
                          </tr>
                          <tr>
                            <td className='pb-2'>{t("healthStatus")}</td>
                            <td className='pb-2'>
                              {selectedDog.dog.healthStatus}
                            </td>
                          </tr>
                          <tr>
                            <td className='pb-2'>{t("kennel")}</td>
                            <td className='pb-2'>{selectedDog.dog.kennel}</td>
                          </tr>
                        </tbody>
                      </Table>
                    </Container>
                  </Card.Text>
                  <Container>
                    {selectedDog.dog.hasPedigree && (
                      <Button
                        variant='secondary'
                        size='sm'
                        onClick={() => onDownloadPedigree(selectedDog.dog.id)}>
                        {t("Download Pedigree")}
                      </Button>
                    )}
                  </Container>
                </Card.Body>
              </Card>
            </Col>
            {selectedDog.parents.length > 0 && (
              <Col className='col-lg-6 mb-3'>
                <Card className='m-auto '>
                  <Card.Body>
                    <Card.Title className='text-success'>
                      {t("Parents")}
                    </Card.Title>
                    <Card.Text>
                      <Container>
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
                                  <td className='pb-2'>{t("name")}</td>
                                  <td className='pb-2'>
                                    <Link
                                      className='link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover'
                                      onClick={() =>
                                        onInfoClick(selectedDog.parents[0].id)
                                      }>
                                      {selectedDog.parents[0].name}
                                    </Link>
                                  </td>
                                </tr>
                                <tr>
                                  <td className='pb-2'>
                                    {t("registrationNum")}
                                  </td>
                                  <td className='pb-2'>
                                    {selectedDog.parents[0].registrationNum}
                                  </td>
                                </tr>

                                <tr>
                                  <td className='pb-2'>{t("color")}</td>
                                  <td className='pb-2'>
                                    {selectedDog.parents[0].color}
                                  </td>
                                </tr>

                                <tr>
                                  <td className='pb-2'>{t("kennel")}</td>
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
                                  <td className='pb-2'>{t("name")}</td>
                                  <td className='pb-2'>
                                    <Link
                                      className='link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover'
                                      onClick={() =>
                                        onInfoClick(selectedDog.parents[0].id)
                                      }>
                                      {selectedDog.parents[1].name}
                                    </Link>
                                  </td>
                                </tr>
                                <tr>
                                  <td className='pb-2'>
                                    {t("registrationNum")}
                                  </td>
                                  <td className='pb-2'>
                                    {selectedDog.parents[1].registrationNum}
                                  </td>
                                </tr>

                                <tr>
                                  <td className='pb-2'>{t("color")}</td>
                                  <td className='pb-2'>
                                    {selectedDog.parents[1].color}
                                  </td>
                                </tr>

                                <tr>
                                  <td className='pb-2'>{t("kennel")}</td>
                                  <td className='pb-2'>
                                    {selectedDog.parents[1].kennel}
                                  </td>
                                </tr>
                              </>
                            )}
                          </tbody>
                        </Table>
                      </Container>
                    </Card.Text>
                  </Card.Body>
                </Card>
              </Col>
            )}
          </Row>
        </Container>
      </Modal.Body>
    </Modal>
  );
};
