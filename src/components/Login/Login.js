import { Button, Container, Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
export const LoginPage = () => {
  const { t } = useTranslation();
  return (
    <Container className='m-auto container-sm'>
      <Form className='m-auto mt-5 mb-5 border border-secondary rounded p-5'>
        <Form.Label className='d-inline-block pb-3'>
          {t("nav.MembersArea.Login")}
        </Form.Label>
        <Form.Group className='mb-3' controlId='formBasicEmail'>
          <Form.Label>{t("forms.Email")} </Form.Label>
          <Form.Control type='email' placeholder={t("forms.Email")} />
          <Form.Text className='text-muted'>{t("forms.Email.Text")}</Form.Text>
        </Form.Group>

        <Form.Group className='mb-3' controlId='formBasicPassword'>
          <Form.Label>{t("forms.Password")}</Form.Label>
          <Form.Control type='password' placeholder={t("forms.Password")} />
        </Form.Group>

        <Button variant='secondary' type='submit'>
          {t("forms.Button.Login")}
        </Button>
      </Form>
      <Container className='m-auto container-sm'>
        <Link
          className={"link-secondary"}
          to={"/register"}
          style={{ textDecoration: "none" }}>
          {t("linkRegister")}
        </Link>
      </Container>
    </Container>
  );
};
