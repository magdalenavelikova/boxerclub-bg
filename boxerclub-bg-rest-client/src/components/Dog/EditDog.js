import { Button, Container, Form, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useDogContext } from "../../contexts/DogContext";
import { useEffect, useState } from "react";

import { useMultiPartForm } from "../../hooks/useMultiPartForm";
import { useAuthContext } from "../../contexts/AuthContext";
export const EditDog = () => {
  const [registrationNum, setRegistrationNum] = useState({});
  const { t } = useTranslation();
  const { isAuthenticated, authorities } = useAuthContext;

  const { onEditDogSubmitHandler, error, selectedDog } = useDogContext();
  const isAdmin =
    isAuthenticated && authorities.some((item) => item === "ROLE_ADMIN");
  const RegisterFormKeys = {
    Id: "id",
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
    Owner: "ownerEmail",
    Mother: "motherRegistrationNum",
    Father: "fatherRegistrationNum",
  };

  useEffect(() => {
    setRegistrationNum({});

    if (error === null) {
      setRegistrationNum({});
    } else {
      setRegistrationNum(error);
    }
  }, [error]);

  useEffect(() => {
    changeValues(selectedDog);
  }, [selectedDog]);

  const {
    formValues,
    validated,
    onChangeHandler,
    onSubmit,
    changeValues,
    onFileSelectedHandler,
    onFileSelectedPedigreeHandler,
  } = useMultiPartForm(
    {
      [RegisterFormKeys.Id]: "",
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
      [RegisterFormKeys.Owner]: "",
      [RegisterFormKeys.Mother]: "",
      [RegisterFormKeys.Father]: "",
    },
    onEditDogSubmitHandler
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
          {t("nav.MembersArea.Edit")}
        </Form.Label>
        {true === false && (
          <Form.Control
            required
            name={RegisterFormKeys.Id}
            value={formValues[RegisterFormKeys.Id]}
            onChange={onChangeHandler}
            type='text'
          />
        )}
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

        <Form.Group className='col-md-3 mb-3' controlId='formFileSm'>
          <Form.Label>{t("forms.PictureUrl")}</Form.Label>

          <Form.Control
            type='file'
            accept='image/png, image/jpeg'
            size='sm'
            onChange={onFileSelectedHandler}
            placeholder={t("EnterPictureUrl")}
          />
        </Form.Group>

        <Form.Group className='col-md-3 mb-3' controlId='formFileSm'>
          <Form.Label> {t("UploadPedigree")}</Form.Label>
          <Form.Control
            type='file'
            className='prevent-validation-styles'
            accept='image/jpeg,image/gif,image/png,application/pdf'
            size='sm'
            title='Choose a pdf or image file please'
            onChange={onFileSelectedPedigreeHandler}
          />
        </Form.Group>

        <Form.Group className='col-md-3 mb-3' controlId='formBasicSex'>
          <Form.Label>{t("forms.Sex")}</Form.Label>
          <Form.Select
            required
            size='sm'
            name={RegisterFormKeys.Sex}
            value={formValues[RegisterFormKeys.Sex]}
            onChange={onChangeHandler}>
            <option label='Select sex'>Select sex</option>
            <option value={t("Male")}>{t("Male")}</option>
            <option value={t("Female")}>{t("Female")}</option>
          </Form.Select>
          <Form.Control.Feedback type='invalid' className='text-danger'>
            {t("validation")}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className='col-md-3 mb-3' controlId='formBasicColor'>
          <Form.Label>{t("forms.Color")}</Form.Label>
          <Form.Select
            required
            size='sm'
            name={RegisterFormKeys.Color}
            onChange={onChangeHandler}
            value={formValues[RegisterFormKeys.Color]}>
            <option label={t("SelectColor")}>{t("SelectColor")}</option>
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

        {{ isAdmin } && (
          <>
            <Form.Group className='col-md-4 mb-3' controlId='formOwner'>
              <Form.Label>{t("forms.Owner")}</Form.Label>
              <Form.Control
                required
                name={RegisterFormKeys.Owner}
                value={formValues[RegisterFormKeys.Owner]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className='col-md-4 mb-3' controlId='formMother'>
              <Form.Label>{t("forms.Mother")}</Form.Label>
              <Form.Control
                required
                name={RegisterFormKeys.Mother}
                value={formValues[RegisterFormKeys.Mother]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className='col-md-4 mb-3' controlId='formFather'>
              <Form.Label>{t("forms.Father")}</Form.Label>
              <Form.Control
                required
                name={RegisterFormKeys.Father}
                value={formValues[RegisterFormKeys.Father]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
          </>
        )}

        <Row xs={1} md={1} className=' mt-3'>
          <Button
            className='col-md-4 m-auto mt-3  mb-3'
            variant='secondary'
            type='submit'>
            {t("forms.Button.EditDog")}
          </Button>
        </Row>
      </Form>
    </Container>
  );
};
