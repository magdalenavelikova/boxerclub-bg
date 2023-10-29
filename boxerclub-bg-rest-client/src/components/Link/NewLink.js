import { Button, Container, Form, Row, Spinner } from "react-bootstrap";
import { useTranslation } from "react-i18next";

import { useForm } from "../../hooks/useForm";
import { useEffect, useState } from "react";
import { useLinkContext } from "../../contexts/LinkContext";

export const NewLink = () => {
  const { t } = useTranslation();
  const { onCreateLinkSubmitHandler, errors, spinner } = useLinkContext();
  const [isLoading, setIsLoading] = useState(false);
  const [title, setTitle] = useState({});
  const [urlLink, setUrlLink] = useState({});

  const LinkFormKeys = {
    Type: "type",
    Title: "title",
    Description: "description",
    UrlLink: "urlLink",
  };

  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [LinkFormKeys.Type]: "",
      [LinkFormKeys.Title]: "",
      [LinkFormKeys.Description]: "",
      [LinkFormKeys.UrlLink]: "",
    },
    onCreateLinkSubmitHandler
  );
  useEffect(() => {
    setIsLoading(spinner);
  }, [spinner]);
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

  return (
    <>
      <Container className='m-auto container-fluid-md  mt-5'>
        <Form
          noValidate
          validated={validated}
          method='POST'
          onSubmit={onSubmit}
          className='row g-3 m-auto mt-5 mb-5 border border-secondary rounded p-4'>
          <p className='mb-3'>{t("nav.Links.AddLink")}</p>
          <Row className='col-md-12 m-auto'>
            <Form.Group className='col-md-6 mb-3' controlId='formBasicSex'>
              <Form.Label>{t("forms.Type")}</Form.Label>
              <Form.Select
                required
                size='sm'
                name={LinkFormKeys.Type}
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
                name={LinkFormKeys.Title}
                value={formValues[LinkFormKeys.Title]}
                onChange={onChangeHandler}
                type='text'
              />
              {Object.keys(title).length !== 0 && (
                <Form.Control.Feedback type='invalid' className='text-danger'>
                  {t("validation")}
                </Form.Control.Feedback>
              )}
            </Form.Group>
          </Row>
          <Row className='col-md-12 m-auto'>
            <Form.Group className='col-md-6 mb-3' controlId='formBasicLastName'>
              <Form.Label>{t("forms.Description")}</Form.Label>
              <Form.Control
                name={LinkFormKeys.Description}
                value={formValues[LinkFormKeys.Description]}
                onChange={onChangeHandler}
                type='text'
              />
            </Form.Group>
            <Form.Group className='col-md-6 mb-3' controlId='formBasicCountry'>
              <Form.Label>{t("forms.UrlLink")}</Form.Label>
              <Form.Control
                required
                name={LinkFormKeys.UrlLink}
                value={formValues[LinkFormKeys.UrlLink]}
                onChange={onChangeHandler}
                type='text'
              />
              {Object.keys(urlLink).length !== 0 && (
                <Form.Control.Feedback type='invalid' className='text-danger'>
                  {t("validation")}
                </Form.Control.Feedback>
              )}
            </Form.Group>
          </Row>
          <Row className='col-md-12 m-auto'>
            <Button
              className='col-md-4  m-auto mt-4 mb-3'
              variant='secondary'
              type='submit'>
              {" "}
              {isLoading && (
                <Spinner
                  as='span'
                  animation='border'
                  size='sm'
                  role='status'
                  aria-hidden='true'
                  className='me-1'
                />
              )}
              {t("forms.Button.Submit")}
            </Button>
          </Row>
        </Form>
      </Container>
    </>
  );
};
