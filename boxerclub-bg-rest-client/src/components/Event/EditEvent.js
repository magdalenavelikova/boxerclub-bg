import { Button, Container, Form, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import Modal from "react-bootstrap/Modal";
import { useForm } from "../../hooks/useForm";
import { useEffect, useState } from "react";
import { useEventContext } from "../../contexts/EventContext";

export const EditEvent = ({ onCloseClick, event }) => {
  const { t } = useTranslation();
  const { onEditEventSubmitHandler, errors } = useEventContext();
  const [show, setShow] = useState(true);
  const [title, setTitle] = useState({});
  const [urlEvent, setUrlEvent] = useState({});

  const EventFormKeys = {
    Type: "type",
    Title: "title",
    Description: "description",
    UrlEvent: "urlEvent",
  };

  const { formValues, validated, onChangeHandler, onSubmit, changeValues } =
    useForm(
      {
        [EventFormKeys.Type]: "",
        [EventFormKeys.Title]: "",
        [EventFormKeys.Description]: "",
        [EventFormKeys.UrlEvent]: "",
      },
      onEditEventSubmitHandler
    );

  useEffect(() => {
    changeValues(event);
  }, [event]);

  useEffect(() => {
    setTitle({});
    setUrlEvent({});

    if (errors === null) {
      setTitle({});
      setUrlEvent({});
    } else {
      for (const [key, value] of Object.entries(errors)) {
        switch (key) {
          case "title":
            setTitle(value);
            break;
          case "urlEvent":
            setUrlEvent(value);
            break;

          default:
            break;
        }
      }
    }
  }, [errors]);

  const handleClose = () => {
    onCloseClick();
    setShow(false);
  };

  return (
    <>
      <Modal
        id='modal'
        size='lg'
        aria-labelledby='example-modal-sizes-title-lg'
        show={show}
        onHide={handleClose}
        backdrop='static'
        keyboard={false}>
        <Modal.Header closeButton>
          <Modal.Title>{t("UserDetails")}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form
            noValidate
            validated={validated}
            method='POST'
            onSubmit={onSubmit}
            className='mt-5 mb-5  p-2'>
            <Row className='col-md-12 m-auto'>
              <Form.Group className='col-md-6 mb-3' controlId='formBasicSex'>
                <Form.Label>{t("forms.Type")}</Form.Label>
                <Form.Select
                  required
                  size='sm'
                  name={EventFormKeys.Type}
                  value={formValues[EventFormKeys.Type]}
                  onChange={onChangeHandler}>
                  <option label='Select type' value=''>
                    Select type
                  </option>
                  <option value='BOXER CLUBS'>BOXER CLUBS</option>
                  <option value='CANINE ORGANISATIONS'>
                    CANINE ORGANISATIONS
                  </option>
                </Form.Select>
                <Form.Control.Feedback type='invalid' className='text-danger'>
                  {t("validation")}
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group
                className='col-md-6 mb-3'
                controlId='formBasicFirstName'>
                <Form.Label>{t("forms.Title")}</Form.Label>
                <Form.Control
                  required
                  name={EventFormKeys.Title}
                  value={formValues[EventFormKeys.Title]}
                  onChange={onChangeHandler}
                  type='text'
                />
                {Object.keys(title).length !== 0 && (
                  <Form.Control.Feedback className='text-danger'>
                    {title}
                  </Form.Control.Feedback>
                )}
              </Form.Group>
            </Row>
            <Row className='col-md-12 m-auto'>
              <Form.Group
                className='col-md-6 mb-3'
                controlId='formBasicLastName'>
                <Form.Label>{t("forms.Description")}</Form.Label>
                <Form.Control
                  name={EventFormKeys.Description}
                  value={formValues[EventFormKeys.Description]}
                  onChange={onChangeHandler}
                  type='text'
                />
              </Form.Group>
              <Form.Group
                className='col-md-6 mb-3'
                controlId='formBasicCountry'>
                <Form.Label>{t("forms.UrlEvent")}</Form.Label>
                <Form.Control
                  required
                  name={EventFormKeys.UrlEvent}
                  value={formValues[EventFormKeys.UrlEvent]}
                  onChange={onChangeHandler}
                  type='text'
                />
                {Object.keys(urlEvent).length !== 0 && (
                  <Form.Control.Feedback className='text-danger'>
                    {urlEvent}
                  </Form.Control.Feedback>
                )}
              </Form.Group>
            </Row>
            <Row className='col-md-12 m-auto'>
              <Button
                className='col-md-4  m-auto mt-4 mb-3'
                variant='secondary'
                type='submit'>
                {t("forms.Button.Submit")}
              </Button>
            </Row>
          </Form>
        </Modal.Body>
        <Modal.Footer>
          <Button variant='secondary' onClick={() => onCloseClick()}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
