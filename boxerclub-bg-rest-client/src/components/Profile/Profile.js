import { useState, useEffect } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import * as formatDate from "../../utils/DateUtils";
import { useTranslation } from "react-i18next";
import { useAuthContext } from "../../contexts/AuthContext";
import { Container, Row, Col, Tab, Tabs } from "react-bootstrap";
import { OwnedDogs } from "../Dog/OwnedDogs";
import { EditUser } from "../Users/EditUser";
import { useNavigate } from "react-router-dom";

export const Profile = (showModal) => {
  const [show, setShow] = useState(true);
  const [editShow, setEditShow] = useState(false);
  const { t } = useTranslation();
  const { activeUser, onUserEdit } = useAuthContext();
  const { roles, ...userInfo } = activeUser;
  const [key, setKey] = useState("profile");
  const [userRoles, setUserRoles] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    setShow(showModal);
    let arr = [];
    Object.values(roles).forEach((obj) => {
      for (const [key, value] of Object.entries(obj)) {
        arr.push(value);
      }
    });
    setUserRoles(arr);
  }, []);

  const handleClose = () => {
    setShow(false);
    navigate("/");
  };
  const onCloseClick = () => {
    setEditShow(false);
  };

  return (
    <>
      <Modal
        id='modal'
        show={show}
        fullscreen={true}
        onHide={handleClose}
        backdrop='static'
        keyboard={false}>
        <Modal.Header closeButton></Modal.Header>
        <Modal.Body>
          <Container fluid className=' m-auto p-'>
            <Row xs={1} md={1}>
              <Col className=' m-auto mt-0 border-secondary'>
                <Tabs justify activeKey={key} onSelect={(k) => setKey(k)}>
                  <Tab
                    className='border border-top-0 pb-3 mb-3'
                    eventKey='profile'
                    title={t("ActiveUserDetails")}>
                    <Row xs={1} md={2}>
                      <div className='text-center mt-5 mb-5'>
                        <p className='text-center mt-5'>
                          {t("forms.Name")}{" "}
                          <span className='info'>{activeUser.firstName}</span>{" "}
                          {activeUser.lastName}
                        </p>
                        <p className='text-center '>
                          {t("forms.Country")} {activeUser.country} <br />{" "}
                          {t("forms.City")} {activeUser.city}
                          <br />
                        </p>
                        <p className='text-center '>
                          {t("forms.Created")}{" "}
                          {formatDate.formatDateForUserDetails(
                            activeUser.created
                          )}
                        </p>
                        <Button
                          variant='secondary'
                          onClick={() => setEditShow(true)}>
                          {t("forms.Button.Edit")}
                        </Button>
                      </div>

                      {editShow && (
                        <EditUser
                          user={activeUser}
                          userRoles={userRoles}
                          onCloseClick={onCloseClick}
                        />
                      )}
                    </Row>
                  </Tab>
                  <Tab
                    className='border border-top-0 pb-3 mb-3'
                    eventKey='dogs'
                    title={t("ActiveUserDogs")}>
                    <OwnedDogs owner={String(activeUser.id)} />
                  </Tab>
                </Tabs>
              </Col>
            </Row>
          </Container>
        </Modal.Body>
        <Modal.Footer>
          <Button variant='secondary' onClick={() => handleClose()}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
