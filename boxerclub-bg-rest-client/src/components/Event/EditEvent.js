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
    Id: "id",
    Title: "title",
    UrlLink: "urlLink",
    StartDate: "startDate",
    ExpiryDate: "expiryDate",
    Location: "location",
    Description: "description",
  };

  const { formValues, onChangeHandler, onSubmit, validated, changeValues } =
    useForm(
      {
        [EventFormKeys.Id]: "",
        [EventFormKeys.Title]: "",
        [EventFormKeys.UrlLink]: "",
        [EventFormKeys.StartDate]: "",
        [EventFormKeys.ExpiryDate]: "",
        [EventFormKeys.Location]: "",
        [EventFormKeys.Description]: "",
      },
      onEditEventSubmitHandler
    );

  useEffect(() => {
    changeValues(event);
  }, []);

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
        id='eventModal'
        size='lg'
        aria-labelledby='example-modal-sizes-title-lg'
        show={show}
        onHide={handleClose}
        backdrop='static'
        keyboard={false}>
        <Modal.Header closeButton>
          <Modal.Title>{t("Details")}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form
            noValidate
            validated={validated}
            method='POST'
            onSubmit={onSubmit}
            className='row g-3 m-auto mt-2 mb-5  p-4'>
            <p className='mb-3'>{t("nav.Events.AddEvent")}</p>
            <Row className='col-md-12 m-auto'>
              {true === false && (
                <Form.Control
                  required
                  name={EventFormKeys.Id}
                  value={formValues[EventFormKeys.Id]}
                  onChange={onChangeHandler}
                  type='text'
                />
              )}
              <Form.Group className='col-md-6 mb-3' controlId='formBasicTitle'>
                <Form.Label>{t("forms.Title")}</Form.Label>
                <Form.Control
                  required
                  name={EventFormKeys.Title}
                  value={formValues[EventFormKeys.Title]}
                  onChange={onChangeHandler}
                  type='text'
                />
                <Form.Control.Feedback type='invalid' className='text-danger'>
                  {t("validation")}
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group
                className='col-md-6 mb-3'
                controlId='formBasicUrlLink'>
                <Form.Label>{t("forms.UrlLink")}</Form.Label>
                <Form.Control
                  required
                  name={EventFormKeys.UrlLink}
                  value={formValues[EventFormKeys.UrlLink]}
                  onChange={onChangeHandler}
                  type='text'
                />
                <Form.Control.Feedback type='invalid' className='text-danger'>
                  {t("validation")}
                </Form.Control.Feedback>
              </Form.Group>
            </Row>
            <Row className='col-md-12 m-auto'>
              <Form.Group
                className='col-md-6 mb-3'
                controlId='formBasicStartDate'>
                <Form.Label>{t("forms.StartDate")}</Form.Label>
                <Form.Control
                  name={EventFormKeys.StartDate}
                  value={formValues[EventFormKeys.StartDate]}
                  onChange={onChangeHandler}
                  type='date'
                />
                <Form.Control.Feedback type='invalid' className='text-danger'>
                  {t("validation")}
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group
                className='col-md-6 mb-3'
                controlId='formBasicExpiryDate'>
                <Form.Label>{t("forms.EndDate")}</Form.Label>
                <Form.Control
                  name={EventFormKeys.ExpiryDate}
                  value={formValues[EventFormKeys.ExpiryDate]}
                  onChange={onChangeHandler}
                  type='date'
                />
                <Form.Control.Feedback type='invalid' className='text-danger'>
                  {t("validation")}
                </Form.Control.Feedback>
              </Form.Group>
            </Row>
            <Row className='col-md-12 m-auto'>
              <Form.Group
                className='col-md-6 mb-3'
                controlId='formBasicLocation'>
                <Form.Label>{t("forms.Location")}</Form.Label>
                <Form.Select
                  required
                  size='sm'
                  name={EventFormKeys.Location}
                  value={formValues[EventFormKeys.Location]}
                  onChange={onChangeHandler}>
                  <option value=''>Изберете локация</option>
                  <option value='Bulgarian'>Bulgarian</option>
                  <option value='International'>International</option>
                </Form.Select>
                <Form.Control.Feedback type='invalid' className='text-danger'>
                  {t("validation")}
                </Form.Control.Feedback>
              </Form.Group>
              <Form.Group
                className='col-md-6 mb-3'
                controlId='formBasicPosition'>
                <Form.Label>{t("forms.Description")}</Form.Label>
                <Form.Control
                  as='textarea'
                  rows={6}
                  required
                  name={EventFormKeys.Description}
                  value={formValues[EventFormKeys.Description]}
                  onChange={onChangeHandler}
                />
                <Form.Control.Feedback type='invalid' className='text-danger'>
                  {t("validation")}
                </Form.Control.Feedback>
              </Form.Group>
            </Row>

            <Row className='col-md-6 m-auto'>
              <Button
                className='col-md-6  m-auto mt-4 mb-3'
                variant='secondary'
                type='submit'>
                {t("forms.Button.Submit")}
              </Button>
            </Row>
          </Form>
        </Modal.Body>
      </Modal>
    </>
  );
};
