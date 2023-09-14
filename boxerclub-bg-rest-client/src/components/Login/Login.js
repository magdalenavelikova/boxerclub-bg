import { Button, Container, Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { useAuthContext } from "../../contexts/AuthContext";
import { useForm } from "../../hooks/useForm";

export const LoginPage = () => {
  const { t } = useTranslation();
  const { onLoginSubmitHandler } = useAuthContext();
  const LoginFormKeys = {
    Username: "username",
    Password: "password",
  };

  const { formValues, onChangeHandler, onSubmit } = useForm(
    {
      [LoginFormKeys.Username]: "",
      [LoginFormKeys.Password]: "",
    },
    onLoginSubmitHandler
  );

  return (
    <Container className='m-auto container-sm'>
      <Form
        className='m-auto mt-5 mb-5 border border-secondary rounded p-5'
        method='POST'
        onSubmit={onSubmit}>
        <Form.Label className='d-inline-block pb-3'>
          {t("nav.MembersArea.Login")}
        </Form.Label>
        <Form.Group className='mb-3' controlId='formBasicEmail'>
          <Form.Label>{t("forms.Email")} </Form.Label>
          <Form.Control
            name={LoginFormKeys.Username}
            type='email'
            placeholder={t("forms.Email")}
            value={formValues[LoginFormKeys.Username]}
            onChange={onChangeHandler}
          />
          <Form.Text className='text-muted'>{t("forms.Email.Text")}</Form.Text>
        </Form.Group>

        <Form.Group className='mb-3' controlId='formBasicPassword'>
          <Form.Label>{t("forms.Password")}</Form.Label>
          <Form.Control
            name={LoginFormKeys.Password}
            value={formValues[LoginFormKeys.Password]}
            onChange={onChangeHandler}
            type='password'
            placeholder={t("forms.Password")}
          />
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
