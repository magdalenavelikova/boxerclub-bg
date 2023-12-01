import { useContext, useState } from "react";
import Modal from "react-bootstrap/Modal";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../contexts/AuthContext";
export const SuccessModalNewPassword = () => {
  const [show, setShow] = useState(true);
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { clear, onLogoutHandler } = useContext(AuthContext);
  const handleClose = () => {
    navigate("../users/login");
    clear();
    setShow(false);
    onLogoutHandler();
  };
  return (
    <Modal show={show} onHide={handleClose} size='md' top>
      <Modal.Header closeButton>
        <Modal.Title id='contained-modal-title-vcenter'>
          {t("Success")}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p>{t("ChangePasswordSuccess")}</p>
      </Modal.Body>
    </Modal>
  );
};
