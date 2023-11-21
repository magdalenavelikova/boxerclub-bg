import { Button, Container, Form, Row, Spinner } from "react-bootstrap";
import { useTranslation } from "react-i18next";

import { useMultiPartForm } from "../../hooks/useMultiPartForm";

import { DogContext, useDogContext } from "../../contexts/DogContext";
import { useAuthContext } from "../../contexts/AuthContext";
import { useContext, useEffect, useState } from "react";
export const NewDog = ({ nb }) => {
  const [isLoading, setIsLoading] = useState(false);
  const [registrationNum, setRegistrationNum] = useState({});
  const [birthday, setBirthday] = useState({});
  const { t } = useTranslation();
  const { userId } = useAuthContext();
  const { onCreateDogSubmitHandler, errors, spinner } = useContext(DogContext);

  const RegisterFormKeys = {
    Name: "name",
    RegistrationNum: "registrationNum",
    File: "file",
    Pedigree: "pedigree",
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
    setIsLoading(spinner);
  }, [spinner]);

  useEffect(() => {
    setRegistrationNum({});
    setBirthday({});
    if (errors === null) {
      setRegistrationNum({});
      setBirthday({});
    } else {
      for (const [key, value] of Object.entries(errors)) {
        switch (key) {
          case "birthday":
            setBirthday(value);
            break;
          case "registrationNum":
            setRegistrationNum(value);
            break;

          default:
            break;
        }
      }
    }
  }, [errors]);

  const {
    formValues,
    onChangeHandler,
    onFileSelectedHandler,
    onFileSelectedPedigreeHandler,
    onSubmit,
    validated,
  } = useMultiPartForm(
    {
      [RegisterFormKeys.Name]: "",
      [RegisterFormKeys.RegistrationNum]: "",
      [RegisterFormKeys.MicroChip]: "",
      [RegisterFormKeys.File]: "",
      [RegisterFormKeys.Pedigree]: "",
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
    <Container className='m-auto container-fluid-md pt-5'>
      <Form
        noValidate
        validated={validated}
        method='POST'
        onSubmit={onSubmit}
        className='row g-3 m-auto mt-5 border border-secondary rounded p-3'>
        <Form.Label className='d-inline-block pb-2'>
          {t("nav.MembersArea.Register")}
        </Form.Label>
        {nb !== true && (
          <Form.Group
            className='col-md-4 mb-2'
            controlId='formBasicRegistrationNum'>
            <Form.Label>{t("registrationNum")}</Form.Label>
            <Form.Control
              required
              name={RegisterFormKeys.RegistrationNum}
              value={
                nb !== true
                  ? formValues[RegisterFormKeys.RegistrationNum]
                  : "nb"
              }
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
        )}
        <Form.Group className='col-md-4 mb-2' controlId='formBasicName'>
          <Form.Label>{t("firstName")}</Form.Label>
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

        <Form.Group className='col-md-4 mb-2' controlId='formBasicMicroChip'>
          <Form.Label>{t("microChip")}</Form.Label>
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

        <Form.Group className='col-md-3 mb-3' controlId='formFileSm'>
          <Form.Label>{t("pictureUrl")}</Form.Label>

          <Form.Control
            type='file'
            accept='image/png, image/jpeg'
            size='sm'
            onChange={onFileSelectedHandler}
            placeholder={t("EnterPictureUrl")}
          />
        </Form.Group>
        {nb !== true && (
          <>
            <Form.Group className='col-md-3 mb-3' controlId='formFileSm'>
              <Form.Label> {t("UploadPedigree")}</Form.Label>
              <Form.Control
                required
                type='file'
                className='prevent-validation-styles'
                accept='image/jpeg,image/gif,image/png,application/pdf'
                size='sm'
                title='Choose a pdf or image file please'
                onChange={onFileSelectedPedigreeHandler}
              />
            </Form.Group>
          </>
        )}
        <Form.Group className='col-md-3 mb-3' controlId='formBasicSex'>
          <Form.Label>{t("sex")}</Form.Label>
          <Form.Select
            required
            size='sm'
            name={RegisterFormKeys.Sex}
            onChange={onChangeHandler}>
            <option label='Select sex' value=''>
              Select sex
            </option>
            <option value='Male'>{t("Male")}</option>
            <option value='Female'>{t("Female")}</option>
          </Form.Select>
          <Form.Control.Feedback type='invalid' className='text-danger'>
            {t("validation")}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className='col-md-3 mb-3' controlId='formBasicColor'>
          <Form.Label>{t("color")}</Form.Label>
          <Form.Select
            required
            size='sm'
            name={RegisterFormKeys.Color}
            onChange={onChangeHandler}>
            <option label={t("SelectColor")} value=''>
              {t("SelectColor")}
            </option>
            <option value='Brindle'>{t("Brindle")}</option>
            <option value='Fawn'>{t("Fawn")}</option>
            <option value='White'>{t("White")}</option>
          </Form.Select>
          <Form.Control.Feedback type='invalid' className='text-danger'>
            {t("validation")}
          </Form.Control.Feedback>
        </Form.Group>

        <Form.Group className='col-md-4  mb-3' controlId='formBasicDate'>
          <Form.Label>{t("birthday")} </Form.Label>
          <Form.Control
            required
            name={RegisterFormKeys.Birthday}
            value={formValues[RegisterFormKeys.Birthday]}
            onChange={onChangeHandler}
            type='date'
          />
          {Object.keys(birthday).length !== 0 && (
            <Form.Control.Feedback className='text-danger'>
              {birthday}
            </Form.Control.Feedback>
          )}
          <Form.Control.Feedback type='invalid' className='text-danger'>
            {t("validation")}
          </Form.Control.Feedback>
        </Form.Group>

        <Form.Group className='col-md-4 mb-3' controlId='formBasicHealthStatus'>
          <Form.Label>{t("healthStatus")}</Form.Label>
          <Form.Control
            name={RegisterFormKeys.HealthStatus}
            value={formValues[RegisterFormKeys.HealthStatus]}
            onChange={onChangeHandler}
            type='text'
          />
        </Form.Group>
        <Form.Group className='col-md-4 mb-3' controlId='formKennel'>
          <Form.Label>{t("kennel")}</Form.Label>
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
        <Row xs={1} md={1} className=' mt-3'>
          <Button
            className='col-md-4 m-auto mt-3  mb-3'
            variant='secondary'
            type='submit'>
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
            {t("forms.Button.RegisterDog")}
          </Button>
        </Row>
      </Form>
    </Container>
  );
};
