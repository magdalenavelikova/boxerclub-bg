import { Button, Container, Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";

import { useMultiPartForm } from "../../hooks/useMultiPartForm";

import { useDogContext } from "../../contexts/DogContext";
import { useAuthContext } from "../../contexts/AuthContext";
import { useEffect, useState } from "react";
export const NewDog = () => {
  const [registrationNum, setRegistrationNum] = useState({});
  const { t } = useTranslation();
  const { userId } = useAuthContext();
  const { onCreateDogSubmitHandler, error } = useDogContext();
  const RegisterFormKeys = {
    Name: "name",
    RegistrationNum: "registrationNum",
    File: "file",
    Sex: "sex",
    Color: "color",
    Birthday: "birthday",
    MicroChip: "microChip",
    HealthStatus: "healthStatus",
    Kennel: "kennel",
    Owner: "ownerId",
    Mother: "motherId",
    Father: "fatherId",
  };

  useEffect(() => {
    setRegistrationNum({});

    if (error === null) {
      setRegistrationNum({});
    } else {
      setRegistrationNum(error);
    }
  }, [error]);

  const {
    formValues,
    onChangeHandler,
    onFileSelectedHandler,
    onSubmit,
    validated,
  } = useMultiPartForm(
    {
      [RegisterFormKeys.Name]: "",
      [RegisterFormKeys.RegistrationNum]: "",
      [RegisterFormKeys.MicroChip]: "",
      [RegisterFormKeys.File]: "",
      [RegisterFormKeys.Sex]: "",
      [RegisterFormKeys.Color]: "",
      [RegisterFormKeys.Birthday]: "",
      [RegisterFormKeys.HealthStatus]: "",
      [RegisterFormKeys.Kennel]: "",
      [RegisterFormKeys.Owner]: `${userId}`,
      [RegisterFormKeys.Mother]: "",
      [RegisterFormKeys.Father]: "",
    },
    onCreateDogSubmitHandler
  );

  return (
    <Container className='m-auto container-fluid-md'>
      <Form
        noValidate
        validated={validated}
        method='POST'
        onSubmit={onSubmit}
        className='row g-3 m-auto mt-5 border border-secondary rounded p-3'>
        <Form.Label className='d-inline-block pb-2'>
          {t("nav.MembersArea.Register")}
        </Form.Label>

        <Form.Group className='col-md-4 mb-2' controlId='formBasicName'>
          <Form.Label>{t("forms.FirstName")}</Form.Label>
          <Form.Control
            required
            name={RegisterFormKeys.Name}
            value={formValues[RegisterFormKeys.Name]}
            onChange={onChangeHandler}
            type='text'
            placeholder={t("EnterName")}
          />
          <Form.Control.Feedback type='invalid' className='text-danger'>
            {t("validation")}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group
          className='col-md-4 mb-2'
          controlId='formBasicRegistrationNum'>
          <Form.Label>{t("forms.RegistrationNum")}</Form.Label>
          <Form.Control
            required
            name={RegisterFormKeys.RegistrationNum}
            value={formValues[RegisterFormKeys.RegistrationNum]}
            onChange={onChangeHandler}
            type='text'
            placeholder={t("EnterRegistrationNum")}
          />
          <Form.Control.Feedback type='invalid' className='text-danger'>
            {t("validation")}
          </Form.Control.Feedback>
          {Object.keys(registrationNum).length !== 0 && (
            <Form.Control.Feedback className='text-danger'>
              {registrationNum}
            </Form.Control.Feedback>
          )}
        </Form.Group>
        <Form.Group className='col-md-4 mb-2' controlId='formBasicMicroChip'>
          <Form.Label>{t("forms.Microchip")}</Form.Label>
          <Form.Control
            required
            name={RegisterFormKeys.MicroChip}
            value={formValues[RegisterFormKeys.MicroChip]}
            onChange={onChangeHandler}
            type='text'
            placeholder={t("EnterMicroChip")}
          />
          <Form.Control.Feedback type='invalid' className='text-danger'>
            {t("validation")}
          </Form.Control.Feedback>
        </Form.Group>

        <Form.Group className='col-md-4 mb-3' controlId='formFileSm'>
          <Form.Label>{t("forms.PictureUrl")}</Form.Label>

          <Form.Control
            type='file'
            accept='image/png, image/jpeg'
            size='sm'
            onChange={onFileSelectedHandler}
            placeholder={t("EnterPictureUrl")}
          />
        </Form.Group>

        <Form.Group className='col-md-4 mb-3' controlId='formBasicSex'>
          <Form.Label>{t("forms.Sex")}</Form.Label>
          <Form.Select
            required
            size='sm'
            name={RegisterFormKeys.Sex}
            onChange={onChangeHandler}>
            <option label='Select sex' value=''>
              Select sex
            </option>
            <option value={t("Male")}>{t("Male")}</option>
            <option value={t("Female")}>{t("Female")}</option>
          </Form.Select>
          <Form.Control.Feedback type='invalid' className='text-danger'>
            {t("validation")}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className='col-md-4 mb-3' controlId='formBasicColor'>
          <Form.Label>{t("forms.Color")}</Form.Label>
          <Form.Select
            required
            size='sm'
            name={RegisterFormKeys.Color}
            onChange={onChangeHandler}>
            <option label={t("SelectColor")} value=''>
              {t("SelectColor")}
            </option>
            <option value={t("Brindle")}>{t("Brindle")}</option>
            <option value={t("Fawn")}>{t("Fawn")}</option>
            <option value={t("White")}>{t("White")}</option>
          </Form.Select>
          <Form.Control.Feedback type='invalid' className='text-danger'>
            {t("validation")}
          </Form.Control.Feedback>
        </Form.Group>

        <Form.Group className='col-md-4  mb-3' controlId='formBasicDate'>
          <Form.Label>{t("forms.Birthday")} </Form.Label>
          <Form.Control
            required
            name={RegisterFormKeys.Birthday}
            value={formValues[RegisterFormKeys.Birthday]}
            onChange={onChangeHandler}
            type='date'
          />
          <Form.Control.Feedback type='invalid' className='text-danger'>
            {t("validation")}
          </Form.Control.Feedback>
        </Form.Group>

        <Form.Group className='col-md-4 mb-3' controlId='formBasicHealthStatus'>
          <Form.Label>{t("forms.HealthStatus")}</Form.Label>
          <Form.Control
            name={RegisterFormKeys.HealthStatus}
            value={formValues[RegisterFormKeys.HealthStatus]}
            onChange={onChangeHandler}
            type='text'
          />
        </Form.Group>
        <Form.Group className='col-md-4 mb-3' controlId='formKennel'>
          <Form.Label>{t("forms.Kennel")}</Form.Label>
          <Form.Control
            required
            name={RegisterFormKeys.Kennel}
            value={formValues[RegisterFormKeys.Kennel]}
            onChange={onChangeHandler}
            type='text'
          />
          <Form.Control.Feedback type='invalid' className='text-danger'>
            {t("validation")}
          </Form.Control.Feedback>
        </Form.Group>

        <Button className='col-md-4  mb-3' variant='secondary' type='submit'>
          {t("forms.Button.RegisterDog")}
        </Button>
      </Form>
    </Container>
  );
};
