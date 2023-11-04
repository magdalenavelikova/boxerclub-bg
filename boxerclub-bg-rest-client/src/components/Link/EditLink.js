import { Button, Container, Form, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import Modal from "react-bootstrap/Modal";
import { useForm } from "../../hooks/useForm";
import { useEffect, useState } from "react";
import { useLinkContext } from "../../contexts/LinkContext";

export const EditLink = ({ onCloseClick, link }) => {
  const { t } = useTranslation();
  const { onEditLinkSubmitHandler, errors } = useLinkContext();
  const [show, setShow] = useState(true);
  const [title, setTitle] = useState({});
  const [urlLink, setUrlLink] = useState({});

  const LinkFormKeys = {
    Id: "id",
    Type: "type",
    Title: "title",
    Description: "description",
    UrlLink: "urlLink",
  };

  const { formValues, validated, onChangeHandler, onSubmit, changeValues } =
    useForm(
      {
        [LinkFormKeys.Id]: "",
        [LinkFormKeys.Type]: "",
        [LinkFormKeys.Title]: "",
        [LinkFormKeys.Description]: "",
        [LinkFormKeys.UrlLink]: "",
      },
      onEditLinkSubmitHandler
    );

  useEffect(() => {
    changeValues(link);
  }, []);

  useEffect(() => {
    setTitle({});
    setUrlLink({});

    if (errors === null) {
      setTitle({});
      setUrlLink({});
    } else {
      for (const [key, value] of Object.entries(errors)) {
        switch (key) {
          case "title":
            setTitle(value);
            break;
          case "urlLink":
            setUrlLink(value);
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
          <Modal.Title>{t("Details")}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form
            noValidate
            validated={validated}
            method='POST'
            onSubmit={onSubmit}
            className='mt-5 mb-5  p-2'>
            <Row className='col-md-12 m-auto'>
              {true === false && (
                <Form.Control
                  required
                  name={LinkFormKeys.Id}
                  value={formValues[LinkFormKeys.Id]}
                  onChange={onChangeHandler}
                  type='text'
                />
              )}
              <Form.Group className='col-md-6 mb-3' controlId='formBasicSex'>
                <Form.Label>{t("type")}</Form.Label>
                <Form.Select
                  required
                  size='sm'
                  name={LinkFormKeys.Type}
                  value={formValues[LinkFormKeys.Type]}
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
                <Form.Label>{t("title")}</Form.Label>
                <Form.Control
                  required
                  name={LinkFormKeys.Title}
                  value={formValues[LinkFormKeys.Title]}
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
                <Form.Label>{t("description")}</Form.Label>
                <Form.Control
                  name={LinkFormKeys.Description}
                  value={formValues[LinkFormKeys.Description]}
                  onChange={onChangeHandler}
                  type='text'
                />
              </Form.Group>
              <Form.Group
                className='col-md-6 mb-3'
                controlId='formBasicCountry'>
                <Form.Label>{t("urlLink")}</Form.Label>
                <Form.Control
                  required
                  name={LinkFormKeys.UrlLink}
                  value={formValues[LinkFormKeys.UrlLink]}
                  onChange={onChangeHandler}
                  type='text'
                />
                {Object.keys(urlLink).length !== 0 && (
                  <Form.Control.Feedback className='text-danger'>
                    {urlLink}
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
      </Modal>
    </>
  );
};
