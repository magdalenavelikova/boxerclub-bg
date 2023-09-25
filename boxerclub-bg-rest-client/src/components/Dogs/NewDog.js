import { Button, Container, Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";

import { useForm } from "../../hooks/useForm";
import { useEffect, useState } from "react";
import { useDogContext } from "../../contexts/DogContext";
import { useAuthContext } from "../../contexts/AuthContext";
export const NewDog = () => {
  const { t } = useTranslation();
  const { userId } = useAuthContext();
  const { onCreateDogSubmitHandler } = useDogContext();
  const RegisterFormKeys = {
    Name: "name",
    RegistrationNum: "registrationNum",
    // Picture: "picture",
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

  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [RegisterFormKeys.Name]: "",
      [RegisterFormKeys.RegistrationNum]: "",
      //[RegisterFormKeys.Picture]: "",
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
    <Container className='m-auto container-sm'>
      <Form
        noValidate
        validated={validated}
        method='POST'
        // enctype='multipart/form-data'
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
        </Form.Group>
        {/*} <Form.Group className='col-md-4 mb-3' controlId='formFileSm'>
          <Form.Label>{t("forms.PictureUrl")}</Form.Label>
          <Form.Control
            type='file'
            accept='image/png, image/gif, image/jpeg'
            size='sm'
            name={RegisterFormKeys.Picture}
            value={formValues[RegisterFormKeys.Picture]}
            onChange={onChangeHandler}
            placeholder={t("EnterPictureUrl")}
          />
        </Form.Group>*/}

        <Form.Group className='col-md-4 mb-3' controlId='formBasicSex'>
          <Form.Label>{t("forms.Sex")}</Form.Label>
          <Form.Select
            required
            size='sm'
            name={RegisterFormKeys.Sex}
            onChange={onChangeHandler}>
            <option value={t("Male")}>{t("Male")}</option>
            <option value={t("Female")}>{t("Female")}</option>
          </Form.Select>
        </Form.Group>
        <Form.Group className='col-md-4 mb-3' controlId='formBasicColor'>
          <Form.Label>{t("forms.Color")}</Form.Label>
          <Form.Select
            required
            size='sm'
            name={RegisterFormKeys.Color}
            onChange={onChangeHandler}>
            <option value={t("Brindle")}>{t("Brindle")}</option>
            <option value={t("Fawn")}>{t("Fawn")}</option>
            <option value={t("White")}>{t("White")}</option>
          </Form.Select>
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
        </Form.Group>

        <Button className='col-md-2  mb-3' variant='secondary' type='submit'>
          {t("forms.Button.RegisterDog")}
        </Button>
      </Form>
    </Container>
  );
};
