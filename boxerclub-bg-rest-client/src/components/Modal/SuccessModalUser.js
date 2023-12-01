import { useContext, useState } from "react";
import Modal from "react-bootstrap/Modal";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../contexts/AuthContext";
export const SuccessModalUser = () => {
  const [show, setShow] = useState(true);
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { clear } = useContext(AuthContext);
  const handleClose = () => {
    navigate("/");
    clear();
    setShow(false);
  };
  return (
    <Modal show={show} onHide={handleClose} size='lg' centered>
      <Modal.Header closeButton>
        <Modal.Title id='contained-modal-title-vcenter'>
          {t("RegisterSuccess")}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p>{t("RegisterUserSuccess")}</p>
      </Modal.Body>
    </Modal>
  );
};
