import { Button, Container, Form, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useForm } from "../../hooks/useForm";
import { useEventContext } from "../../contexts/EventContext";
import { useState, useEffect } from "react";

export const NewEvent = () => {
  const { t } = useTranslation();
  const { onCreateEventSubmitHandler, errors } = useEventContext();
  const [expiryDate, setExpiryDate] = useState({});
  const [startDate, setStartDate] = useState({});

  useEffect(() => {
    setExpiryDate({});
    setStartDate({});

    if (errors === null) {
      setExpiryDate({});
      setStartDate({});
    } else {
      for (const [key, value] of Object.entries(errors)) {
        switch (key) {
          case "expiryDate":
            setExpiryDate(value);
            break;
          case "startDate":
            setStartDate(value);
            break;
          default:
            break;
        }
      }
    }
  }, [errors]);
  const EventFormKeys = {
    Title: "title",
    UrlLink: "urlLink",
    StartDate: "startDate",
    ExpiryDate: "expiryDate",
    Location: "location",
    Description: "description",
  };

  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [EventFormKeys.Title]: "",
      [EventFormKeys.UrlLink]: "",
      [EventFormKeys.StartDate]: "",
      [EventFormKeys.ExpiryDate]: "",
      [EventFormKeys.Location]: "",
      [EventFormKeys.Description]: "",
    },
    onCreateEventSubmitHandler
  );

  return (
    <>
      <Container className='m-auto container-fluid-md  pt-5 mt-5'>
        <Form
          noValidate
          validated={validated}
          method='POST'
          onSubmit={onSubmit}
          className='row g-3 m-auto mt-5 mb-5 border border-secondary rounded p-4'>
          <p className='mb-3'>{t("nav.Events.AddEvent")}</p>
          <Row className='col-md-12 m-auto'>
            <Form.Group className='col-md-6 mb-3' controlId='formBasicTitle'>
              <Form.Label>{t("title")}</Form.Label>
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
            <Form.Group className='col-md-6 mb-3' controlId='formBasicUrlLink'>
              <Form.Label>{t("urlLink")}</Form.Label>
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
              <Form.Label>{t("startDate")}</Form.Label>
              <Form.Control
                required
                name={EventFormKeys.StartDate}
                value={formValues[EventFormKeys.StartDate]}
                onChange={onChangeHandler}
                type='date'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
              {Object.keys(startDate).length !== 0 && (
                <Form.Control.Feedback className='text-danger'>
                  {startDate}
                </Form.Control.Feedback>
              )}
            </Form.Group>
            <Form.Group
              className='col-md-6 mb-3'
              controlId='formBasicExpiryDate'>
              <Form.Label>{t("expiryDate")}</Form.Label>
              <Form.Control
                required
                name={EventFormKeys.ExpiryDate}
                value={formValues[EventFormKeys.ExpiryDate]}
                onChange={onChangeHandler}
                type='date'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
              {Object.keys(expiryDate).length !== 0 && (
                <Form.Control.Feedback className='text-danger'>
                  {expiryDate}
                </Form.Control.Feedback>
              )}
            </Form.Group>
          </Row>
          <Row className='col-md-12 m-auto'>
            <Form.Group className='col-md-6 mb-3' controlId='formBasicLocation'>
              <Form.Label>{t("location")}</Form.Label>
              <Form.Select
                required
                size='sm'
                name={EventFormKeys.Location}
                onChange={onChangeHandler}>
                <option value=''>Изберете локация</option>
                <option value='Bulgarian'>Bulgarian</option>
                <option value='International'>International</option>
              </Form.Select>
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className='col-md-6 mb-3' controlId='formBasicPosition'>
              <Form.Label>{t("description")}</Form.Label>
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
      </Container>
    </>
  );
};
