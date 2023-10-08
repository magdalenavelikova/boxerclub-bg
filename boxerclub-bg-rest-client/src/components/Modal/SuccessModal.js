import { useState } from "react";
import Modal from "react-bootstrap/Modal";
import { useTranslation } from "react-i18next";
export const SuccessModal = (props) => {
  const [show, setShow] = useState(true);
  const { t } = useTranslation();
  const handleClose = () => {
    setShow(false);
  };
  return (
    <Modal show={show} onHide={handleClose} {...props} size='lg' centered>
      <Modal.Header closeButton>
        <Modal.Title id='contained-modal-title-vcenter'>
          {t("RegisterSuccess")}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p>
          {t("RegisterSuccessParent")} {props.parent.name}
          {t("RegisterParentAs")}
          {props.createdDog.name}
        </p>
      </Modal.Body>
    </Modal>
  );
};
