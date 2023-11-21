import { useState, useEffect, useContext } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import * as formatDate from "../../utils/DateUtils";
import { useTranslation } from "react-i18next";
import { AuthContext, useAuthContext } from "../../contexts/AuthContext";
import { Container, Row, Col, Tab, Tabs } from "react-bootstrap";
import { OwnedDogs } from "../Dog/OwnedDogs";
import { EditUser } from "../Users/EditUser";
import { useNavigate } from "react-router-dom";
import { ChangePassword } from "../Users/ChangePassword";

export const Profile = (showModal) => {
  const { t } = useTranslation();
  const [show, setShow] = useState(true);
  const [editShow, setEditShow] = useState(false);
  const [newPasswordShow, setNewPasswordShow] = useState(false);
  const { activeUser, success } = useContext(AuthContext);
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
    if (success) {
      setEditShow(false);
    }
  }, []);
  useEffect(() => {
    if (success) {
      setEditShow(false);
    }
  }, [success]);
  const handleClose = () => {
    setShow(false);
    setNewPasswordShow(false);
    window.history.back();
  };
  const onCloseClick = () => {
    setEditShow(false);
    setNewPasswordShow(false);
    window.history.back();
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
          <Container fluid className='p-2 m-auto'>
            <Row xs={1} md={1}>
              <Col className=' m-auto mt-0 border-secondary'>
                <Tabs justify activeKey={key} onSelect={(k) => setKey(k)}>
                  <Tab
                    className='border border-top-0 pb-3 mb-3'
                    eventKey='profile'
                    title={t("ActiveUserDetails")}>
                    <Row xs={1} md={2}>
                      <div className='text-center mt-5 mb-5'>
                        <h6 className='text-center mt-5'>
                          {t("name")}{" "}
                          <span className='info'>{activeUser.firstName}</span>{" "}
                          {activeUser.lastName}
                        </h6>
                        <h6 className='text-center '>
                          {t("country")} {activeUser.country} <br /> {t("city")}{" "}
                          {activeUser.city}
                          <br />
                        </h6>
                        <p className='text-center mb-5'>
                          {t("created")}{" "}
                          {formatDate.formatDateForUserDetails(
                            activeUser.created
                          )}
                        </p>
                        <Button
                          className='me-2'
                          variant='secondary'
                          onClick={() => setEditShow(true)}>
                          {t("forms.Button.Edit")}
                        </Button>
                        <Button
                          variant='secondary'
                          onClick={() => setNewPasswordShow(true)}>
                          {t("Change password")}
                        </Button>
                      </div>

                      {editShow && (
                        <EditUser
                          user={activeUser}
                          userRoles={userRoles}
                          onCloseClick={onCloseClick}
                        />
                      )}
                      {newPasswordShow && (
                        <ChangePassword onCloseClick={onCloseClick} />
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
      </Modal>
    </>
  );
};
