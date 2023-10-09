import { Button, Container, Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { useAuthContext } from "../../contexts/AuthContext";
import { useForm } from "../../hooks/useForm";
import { useState } from "react";

export const LoginPage = ({ onSelectHandler }) => {
  const { t } = useTranslation();
  const { onLoginSubmitHandler, errors } = useAuthContext();
  const [eye, setEye] = useState(true);
  const [type, setType] = useState(false);
  const [password, setPassword] = useState("password");

  const Eye = () => {
    if (password == "password") {
      setPassword("text");
      setEye(false);
      setType(true);
    } else {
      setPassword("password");
      setEye(true);
      setType(false);
    }
  };

  const LoginFormKeys = {
    Username: "username",
    Password: "password",
  };

  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [LoginFormKeys.Username]: "",
      [LoginFormKeys.Password]: "",
    },
    onLoginSubmitHandler
  );

  return (
    <Container className='m-auto container-sm'>
      <Form
        noValidate
        validated={validated}
        className='m-auto p-5'
        method='POST'
        onSubmit={onSubmit}>
        <Form.Group className='mb-3' controlId='formBasicEmail'>
          <Form.Label>{t("forms.Email")} </Form.Label>
          <Form.Control
            required
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
          <div className='form'>
            <Form.Control
              required
              name={LoginFormKeys.Password}
              value={formValues[LoginFormKeys.Password]}
              onChange={onChangeHandler}
              type={password}
              placeholder={t("forms.Password")}
            />
            <i
              onClick={Eye}
              className={`fa ${eye ? "fa-eye-slash" : "fa-eye"}`}></i>
          </div>

          {Object.keys(errors).length !== 0 && (
            <Form.Control.Feedback className='text-danger'>
              {t("forms.Login.Validation")}
            </Form.Control.Feedback>
          )}
        </Form.Group>

        <Button
          variant='secondary'
          className='col-md-3  m-auto mt-4 mb-3'
          type='submit'>
          {t("forms.Button.Login")}
        </Button>
        <Container className='m-auto container-sm'>
          <Link
            className={"link-info"}
            style={{ textDecoration: "none" }}
            onClick={() => onSelectHandler("register")}>
            {t("linkRegister")}
          </Link>
        </Container>
      </Form>
    </Container>
  );
};
