import {
  Button,
  Container,
  Col,
  Row,
  Card,
  Table,
  Modal,
  Tabs,
  Tab,
  Alert,
} from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { DogContext } from "../../contexts/DogContext";
import { useContext, useEffect, useState } from "react";
import { TbBinaryTree } from "react-icons/tb";
import { FaRegImage } from "react-icons/fa";
import * as formatString from "../../utils/StringUtils";
import { Link, useNavigate } from "react-router-dom";
export const DogDetails = () => {
  const { t } = useTranslation();
  const boxer = require("../../assets/dogs/boxer-vector.png");
  const { selectedDog, onDownloadPedigree, getDogDetails, getDogChart } =
    useContext(DogContext);
  const [key, setKey] = useState("siblings");
  const [show, setShow] = useState(true);
  const [activeDog, setActiveDog] = useState(selectedDog);
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();
  const currentUrl = window.location.href;
  console.log(currentUrl);
  const handleModal = () => {
    setShowModal(!showModal);
  };
  useEffect(() => {
    setActiveDog(selectedDog);
  }, []);

  useEffect(() => {
    setActiveDog(selectedDog);
  }, [selectedDog]);
  const handleClose = () => {
    setShow(false);

    navigate("/");
  };
  const onInfoClick = (dogId) => {
    getDogDetails(dogId);
  };

  const onChartClick = (dogId) => {
    getDogChart(dogId);
  };

  return (
    <Modal show={show} fullscreen onHide={() => handleClose()}>
      <Modal.Header closeButton>
        <Modal.Title className='text-secondary'>{t("Details")}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Container fluid className='fill-left content-padding'>
          <Row className=' p-3' style={{ backgroundColor: "#f1f1f1" }}>
            <Col md={2} className='ps-5'>
              <Container>
                <img
                  src={
                    activeDog.dog.pictureUrl !== "" && activeDog.dog.pictureUrl
                      ? activeDog.dog.pictureUrl
                      : boxer
                  }
                  className='d-none d-lg-block rounded-circle  avatar-md'
                  alt=''
                  onClick={handleModal}
                  style={{ cursor: "pointer" }}
                />

                <Modal show={showModal} onHide={handleModal}>
                  <Modal.Header closeButton>
                    <Modal.Title className='text-success'>
                      {formatString.formatStringToUpperCase(activeDog.dog.name)}
                    </Modal.Title>
                  </Modal.Header>
                  <Modal.Body>
                    <img
                      src={
                        activeDog.dog.pictureUrl !== "" &&
                        activeDog.dog.pictureUrl
                          ? activeDog.dog.pictureUrl
                          : boxer
                      }
                      alt=''
                      className='img-fluid'
                    />
                  </Modal.Body>
                </Modal>
              </Container>
            </Col>
            <Col md={10}>
              <Container>
                <h1 className='text-success'>
                  {formatString.formatStringToUpperCase(activeDog.dog.name)}{" "}
                  <Button
                    className='me-1 mb-2 custom-sm-button'
                    variant='outline-success'
                    title={t("nav.Pedigree")}
                    onClick={() => onChartClick(activeDog.dog.id)}>
                    <TbBinaryTree className='me-2' />
                  </Button>
                  <Button
                    className='me-1 mb-2 custom-sm-button d-lg-none d-md-none d-xs-block'
                    variant='outline-success'
                    title={t("nav.Picture")}
                    onClick={handleModal}>
                    <FaRegImage />
                  </Button>
                </h1>
              </Container>
            </Col>
          </Row>

          <Row className='mt-2 mb-2 p-3 '>
            <Col className='col-lg-6 mb-3'>
              <Card className='m-auto'>
                <Card.Body>
                  <Card.Title className='text-success mb-3'>
                    {t("Details")}
                  </Card.Title>

                  <Container>
                    <Table
                      className='align-middle table-borderless'
                      responsive='md'>
                      <tbody>
                        <tr>
                          <td className='pb-2'>{t("name")}</td>
                          <td className='pb-2'>{activeDog.dog.name}</td>
                        </tr>
                        <tr>
                          <td className='pb-2'>{t("registrationNum")}</td>
                          <td className='pb-2'>
                            {activeDog.dog.registrationNum}
                          </td>
                        </tr>
                        {activeDog.dog.microChip && (
                          <tr>
                            <td className='pb-2'>{t("microChip")}</td>
                            <td className='pb-2'>{activeDog.dog.microChip}</td>
                          </tr>
                        )}
                        {activeDog.dog.birthday && (
                          <tr>
                            <td className='pb-2'>{t("birthday")}</td>
                            <td className='pb-2'>{activeDog.dog.birthday}</td>
                          </tr>
                        )}
                        {activeDog.dog.dateOfDecease && (
                          <tr>
                            <td className='pb-2'>{t("dateOfDecease")}</td>
                            <td className='pb-2'>
                              {activeDog.dog.dateOfDecease}
                            </td>
                          </tr>
                        )}
                        <tr>
                          <td className='pb-2'>{t("sex")}</td>
                          <td className='pb-2'>{t(`${activeDog.dog.sex}`)}</td>
                        </tr>
                        <tr>
                          <td className='pb-2'>{t("color")}</td>
                          <td className='pb-2'>
                            {t(`${activeDog.dog.color}`)}
                          </td>
                        </tr>
                        {activeDog.dog.healthStatus && (
                          <tr>
                            <td className='pb-2'>{t("healthStatus")}</td>
                            <td className='pb-2'>
                              {activeDog.dog.healthStatus}
                            </td>
                          </tr>
                        )}
                        {activeDog.dog.kennel && (
                          <tr>
                            <td className='pb-2'>{t("kennel")}</td>
                            <td className='pb-2'>{activeDog.dog.kennel}</td>
                          </tr>
                        )}
                      </tbody>
                    </Table>
                  </Container>

                  <Container>
                    {activeDog.dog.hasPedigree && (
                      <Button
                        className='me-1 mb-2 custom-sm-button'
                        variant='secondary'
                        size='sm'
                        onClick={() => onDownloadPedigree(activeDog.dog.id)}>
                        {t("Download Pedigree")}
                      </Button>
                    )}
                  </Container>
                </Card.Body>
              </Card>
            </Col>
            {activeDog.parents.length > 0 && (
              <Col className='col-lg-6 mb-1'>
                <Card className='m-auto '>
                  <Card.Body>
                    <Card.Title className='text-success'>
                      {t("Parents")}
                    </Card.Title>

                    <Container>
                      <Table
                        className='align-middle table-borderless'
                        responsive='md'>
                        <tbody>
                          {activeDog.parents[0] && (
                            <>
                              <tr className='text-success fw-bold'>
                                {activeDog.parents[0].sex === "Female" && (
                                  <td colSpan={2}>{t("Mother")}</td>
                                )}
                                {activeDog.parents[0].sex === "Male" && (
                                  <td colSpan={2}>{t("Father")}</td>
                                )}
                              </tr>
                              <tr>
                                <td className='pb-1'>{t("name")}</td>
                                <td className='pb-1'>
                                  <Link
                                    className='link-success link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover'
                                    onClick={() =>
                                      onInfoClick(activeDog.parents[0].id)
                                    }>
                                    {activeDog.parents[0].name}
                                  </Link>
                                </td>
                              </tr>
                              <tr>
                                <td className='pb-1'>{t("registrationNum")}</td>
                                <td className='pb-1'>
                                  {activeDog.parents[0].registrationNum}
                                </td>
                              </tr>
                              {activeDog.parents[0].birthday && (
                                <tr>
                                  <td className='pb-2'>{t("birthday")}</td>
                                  <td className='pb-2'>
                                    {activeDog.parents[0].birthday}
                                  </td>
                                </tr>
                              )}
                              <tr>
                                <td className='pb-1'>{t("color")}</td>
                                <td className='pb-1'>
                                  {t(`${activeDog.parents[0].color}`)}
                                </td>
                              </tr>
                              {activeDog.parents[0].kennel && (
                                <tr>
                                  <td className='pb-1'>{t("kennel")}</td>
                                  <td className='pb-1'>
                                    {activeDog.parents[0].kennel}
                                  </td>
                                </tr>
                              )}
                            </>
                          )}
                          {activeDog.parents[1] &&
                            activeDog.parents[1].sex === "Male" && (
                              <>
                                <tr className='text-success fw-bold'>
                                  <td colSpan={2}>{t("Father")}</td>
                                </tr>
                                <tr>
                                  <td className='pb-1'>{t("name")}</td>
                                  <td className='pb-1'>
                                    <Link
                                      className='link-success link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover'
                                      onClick={() =>
                                        onInfoClick(activeDog.parents[0].id)
                                      }>
                                      {activeDog.parents[1].name}
                                    </Link>
                                  </td>
                                </tr>
                                <tr>
                                  <td className='pb-1'>
                                    {t("registrationNum")}
                                  </td>
                                  <td className='pb-1'>
                                    {activeDog.parents[1].registrationNum}
                                  </td>
                                </tr>
                                {activeDog.parents[1].birthday && (
                                  <tr>
                                    <td className='pb-2'>{t("birthday")}</td>
                                    <td className='pb-2'>
                                      {activeDog.parents[1].birthday}
                                    </td>
                                  </tr>
                                )}
                                <tr>
                                  <td className='pb-1'>{t("color")}</td>
                                  <td className='pb-1'>
                                    {t(`${activeDog.parents[1].color}`)}
                                  </td>
                                </tr>
                                {activeDog.parents[1].kennel && (
                                  <tr>
                                    <td className='pb-1'>{t("kennel")}</td>
                                    <td className='pb-1'>
                                      {activeDog.parents[1].kennel}
                                    </td>
                                  </tr>
                                )}
                              </>
                            )}
                        </tbody>
                      </Table>
                    </Container>
                  </Card.Body>
                </Card>
              </Col>
            )}
          </Row>

          <Row>
            <Col className=' m-auto border-secondary'>
              <Tabs
                className='custom-tab'
                justify
                activeKey={key}
                onSelect={(k) => setKey(k)}>
                <Tab
                  className='border border-top-0 '
                  eventKey='siblings'
                  title={t("Siblings")}>
                  {activeDog.siblings.length > 0 && (
                    <>
                      <Container className='col-md-2 m-auto pt-5 mb-5'>
                        <Table
                          className='align-middle table-borderless '
                          responsive='md'>
                          <tbody>
                            {activeDog.siblings.length > 0 &&
                              activeDog.siblings.map((s) => (
                                <tr key={s.id}>
                                  {/*  <td className='pb-1'>{t("name")}</td>*/}
                                  <td className='pb-1'>
                                    <Link
                                      className='link-success link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover'
                                      onClick={() => onInfoClick(s.id)}>
                                      {s.name}
                                    </Link>
                                  </td>
                                </tr>
                              ))}
                          </tbody>
                        </Table>
                      </Container>
                    </>
                  )}
                  {activeDog.siblings.length === 0 && (
                    <Container className='m-auto pt-5 mb-5'>
                      <Alert
                        className='col-md-6 m-auto  text-center'
                        variant='success'>
                        {t("No siblings")}
                      </Alert>
                    </Container>
                  )}
                </Tab>
                <Tab
                  className='m-auto border border-top-0'
                  eventKey='descendants'
                  title={t("Descendants")}>
                  {activeDog.descendants.length > 0 && (
                    <>
                      <Container className='col-md-2 m-auto pt-3  mb-5'>
                        <Table
                          className='align-middle table-borderless'
                          responsive='md'>
                          <tbody>
                            {activeDog.descendants.length > 0 &&
                              activeDog.descendants.map((d) => (
                                <tr key={d.id}>
                                  <td>
                                    <Link
                                      className='  link-success link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover'
                                      onClick={() => onInfoClick(d.id)}>
                                      {d.name}
                                    </Link>
                                  </td>
                                </tr>
                              ))}
                          </tbody>
                        </Table>
                      </Container>
                    </>
                  )}
                  {activeDog.descendants.length === 0 && (
                    <Container className='m-auto pt-5 mb-5'>
                      <Alert
                        className='col-md-6 m-auto  text-center'
                        variant='success'>
                        {t("No descendants")}
                      </Alert>
                    </Container>
                  )}
                </Tab>
              </Tabs>
            </Col>
          </Row>
        </Container>
      </Modal.Body>
    </Modal>
  );
};
