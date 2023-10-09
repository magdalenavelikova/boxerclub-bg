import { Button, Row, Col, Container, Form, FormGroup } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useMultiPartForm } from "../../hooks/useMultiPartForm";
import { useDogContext } from "../../contexts/DogContext";

export const UploadPedigree = () => {
  const { t } = useTranslation();

  const { onPedigreeUploadSubmitHandler, createdDog } = useDogContext();

  const PedigreeFormKeys = {
    Name: "name",
    RegistrationNum: "registrationNum",
    File: "file",
    Id: "id",
  };

  const { onFileSelectedHandler, onSubmit, validated } = useMultiPartForm(
    {
      [PedigreeFormKeys.File]: "",
      [PedigreeFormKeys.Id]: `${createdDog.id}`,
    },
    onPedigreeUploadSubmitHandler
  );

  return (
    <>
      <Container className='m-auto container-fluid-md'>
        <Form
          noValidate
          validated={validated}
          method='POST'
          onSubmit={onSubmit}
          className='row g-3 m-auto mt-5 border border-secondary rounded p-3'>
          <Form.Label className='d-inline-block pb-2'>
            {t("UploadPedigree")}
          </Form.Label>
          <Row>
            <Form.Group className='col-md-4 mb-3' controlId='formFileSm'>
              <Form.Label>{t("forms.File")}</Form.Label>
              <Form.Control
                required
                type='file'
                className='prevent-validation-styles'
                accept='image/jpeg,image/gif,image/png,application/pdf'
                size='sm'
                title='Choose a pdf or image file please'
                onChange={onFileSelectedHandler}
              />
            </Form.Group>
          </Row>
          <Row>
            <Button
              className='col-md-2 m-auto mt-3 mb-3'
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
