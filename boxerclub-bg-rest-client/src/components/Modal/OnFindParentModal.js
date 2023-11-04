import { useState } from "react";
import Modal from "react-bootstrap/Modal";
import { useTranslation } from "react-i18next";

import { Button } from "react-bootstrap";
export const OnFindParentModal = ({
  onSetParentHandler,
  onCloseClick,
  parent,
}) => {
  const [show, setShow] = useState(true);

  const { t } = useTranslation();

  const handleClose = () => {
    onCloseClick();
    setShow(false);
  };
  return (
    <Modal
      show={show}
      onHide={handleClose}
      size='lg'
      //aria-labelledby='contained-modal-title-vcenter'
      centered>
      <Modal.Header closeButton>
        <Modal.Title id='contained-modal-title-vcenter'>Внимание</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <p> Съществува регистрация на куче с този номер от племенна книга</p>
        <p>
          {t("forms.FirstName")}: {parent && <span>{parent.name}</span>}
        </p>
        <p>
          {t("registrationNum")}:{" "}
          {parent && <span>{parent.registrationNum}</span>}
        </p>
        <p>
          {" "}
          Потвърждавате ли, че това куче е родител на кучето, което
          регистрирате?
        </p>
      </Modal.Body>
      <Modal.Footer>
        <Button
          variant='secondary'
          onClick={() => onCloseClick()}
          data-dismiss='modal'>
          Cancel
        </Button>
        <Button variant='info' onClick={() => onSetParentHandler()}>
          Confirm
        </Button>
      </Modal.Footer>
    </Modal>
  );
};
