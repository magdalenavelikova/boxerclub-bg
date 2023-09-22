import { useState } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import { useTranslation } from "react-i18next";

export const DeleteUser = ({ onDelete, onCloseClick, user }) => {
  const [show, setShow] = useState(true);
  const { t } = useTranslation();
  const handleClose = () => {
    onCloseClick();
    setShow(false);
  };

  return (
    <>
      <Modal
        show={show}
        onHide={handleClose}
        backdrop='static'
        keyboard={false}>
        <Modal.Header variant='danger' closeButton>
          <Modal.Title>
            <i
              className='fas fa-exclamation-triangle me-5'
              style={{ color: "#ef0101" }}></i>

            {t("modal.Sure")}
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {t("modal.Delete")}
          <div className='mt-2'>
            {t("forms.Email")}: {user.email}
            <br />
            {t("forms.FirstName")}: {user.firstName}
            <br />
            {t("forms.LastName")}: {user.lastName}
            <br />
          </div>
        </Modal.Body>
        <Modal.Footer>
          <Button
            variant='secondary'
            onClick={() => onCloseClick()}
            data-dismiss='modal'>
            Cancel
          </Button>
          <Button variant='danger' onClick={() => onDelete()}>
            Delete
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
