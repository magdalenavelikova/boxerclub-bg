import { useContext, useState } from "react";
import Modal from "react-bootstrap/Modal";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../contexts/AuthContext";
export const SuccessModalEditUser = () => {
  const [show, setShow] = useState(true);
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { clear, onLogoutHandler, isAuthenticated, authorities } =
    useContext(AuthContext);
  const handleClose = () => {
    if (!isAuthorized) {
      navigate("../users/login");
      onLogoutHandler();
    }
    clear();
    setShow(false);
  };

  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));

  return (
    <Modal show={show} onHide={handleClose} size='md' top>
      <Modal.Header closeButton>
        <Modal.Title id='contained-modal-title-vcenter'>
          {t("Success")}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p>{t("EditUserSuccess")}</p>
      </Modal.Body>
    </Modal>
  );
};
