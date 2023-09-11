import { Button, Container, Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
export const RegisterPage = () => {
  const { t } = useTranslation();
  return (
    <Container className='m-auto container-sm'>
      <Form className='row g-3 m-auto mt-5 border border-secondary rounded p-5'>
        <Form.Label className='d-inline-block pb-3'>
          {t("nav.MembersArea.Register")}
        </Form.Label>

        <Form.Group className='col-md-6 mb-3' controlId='formBasicFirstName'>
          <Form.Label>{t("forms.FirstName")}</Form.Label>
          <Form.Control type='text' placeholder={t("EnterFirstName")} />
        </Form.Group>
        <Form.Group className='col-md-6 mb-3' controlId='formBasicLastName'>
          <Form.Label>{t("forms.LastName")}</Form.Label>
          <Form.Control type='text' placeholder={t("EnterLastName")} />
        </Form.Group>
        <Form.Group className='col-md-6 mb-3' controlId='formBasicCountry'>
          <Form.Label>{t("forms.Country")}</Form.Label>
          <Form.Control type='text' placeholder={t("EnterCountry")} />
        </Form.Group>
        <Form.Group className='col-md-6 mb-3' controlId='formBasicCity'>
          <Form.Label>{t("forms.City")}</Form.Label>
          <Form.Control type='text' placeholder={t("EnterCity")} />
        </Form.Group>

        <Form.Group className='col-md-12  mb-3' controlId='formBasicEmail'>
          <Form.Label>{t("forms.Email")} </Form.Label>
          <Form.Control type='email' placeholder={t("forms.Email")} />
        </Form.Group>

        <Form.Group className='col-md-6 mb-3' controlId='formBasicPassword'>
          <Form.Label>{t("forms.Password")}</Form.Label>
          <Form.Control type='password' placeholder={t("forms.Password")} />
        </Form.Group>
        <Form.Group className='col-md-6 mb-3' controlId='formConfirmPassword'>
          <Form.Label>{t("forms.ConfirmPassword")}</Form.Label>
          <Form.Control
            type='password'
            placeholder={t("forms.ConfirmPassword")}
          />
        </Form.Group>

        <Button className='col-md-2  mb-3' variant='secondary' type='submit'>
          {t("forms.Button.Register")}
        </Button>
      </Form>
    </Container>
  );
};
