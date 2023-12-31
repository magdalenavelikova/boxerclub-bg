import { useContext, useState } from "react";
import Modal from "react-bootstrap/Modal";
import { useTranslation } from "react-i18next";
import { DogContext } from "../../contexts/DogContext";
export const OnDeleteParentModal = (props) => {
  const [show, setShow] = useState(true);
  const { t } = useTranslation();
  const { clearErrors } = useContext(DogContext);
  const handleClose = () => {
    clearErrors();
    setShow(false);
  };
  return (
    <Modal
      show={show}
      onHide={handleClose}
      {...props}
      size='lg'
      //aria-labelledby='contained-modal-title-vcenter'
      centered>
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
