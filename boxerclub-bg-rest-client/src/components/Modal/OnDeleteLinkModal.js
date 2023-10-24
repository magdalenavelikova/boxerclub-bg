import { useState } from "react";
import Modal from "react-bootstrap/Modal";
import { useTranslation } from "react-i18next";

export const OnDeleteLinkModal = (props) => {
  const [show, setShow] = useState(true);
  const { t } = useTranslation();

  const handleClose = () => {
    setShow(false);
  };
  return (
    <Modal show={show} onHide={handleClose} {...props} size='lg' centered>
      <Modal.Header variant='danger' closeButton>
        <Modal.Title id='contained-modal-title-vcenter'>
          <i
            className='fas fa-exclamation-triangle me-5'
            style={{ color: "#ef0101" }}></i>
          {t("Error")}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p> {t("ParentErrorMessage")}</p>
      </Modal.Body>
      <Modal.Footer></Modal.Footer>
    </Modal>
  );
};
