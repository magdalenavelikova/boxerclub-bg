import { Badge, Button, Container, Form, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { DogContext } from "../../contexts/DogContext";
import { useContext, useEffect, useState } from "react";

import { useMultiPartForm } from "../../hooks/useMultiPartForm";
import { AuthContext } from "../../contexts/AuthContext";

export const EditDog = () => {
  const [registrationNum, setRegistrationNum] = useState({});
  const { t } = useTranslation();
  const { isAuthenticated, authorities } = useContext(AuthContext);

  const {
    onEditDogSubmitHandler,
    error,
    selectedDog,
    approveDog,
    onAddParentToCreatedDog,
  } = useContext(DogContext);
  const onApproveClick = (dogId) => {
    approveDog(dogId);
  };
  const [approved, setApproved] = useState(selectedDog.approved);
  const isAdminOrModerator =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));

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
    setApproved(selectedDog.approved);
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
  const onAddParentsClick = (selectedDog) => {
    onAddParentToCreatedDog(selectedDog);
  };
  return (
    <Container className='m-auto container-fluid-md pt-5'>
      <Form
        noValidate
        validated={validated}
        method='POST'
        onSubmit={onSubmit}
        className='row g-3 m-auto mt-5 border border-secondary rounded p-3'>
        <Form.Label className='d-inline-block pb-2'>
          {t("nav.MembersArea.Edit")}
          {approved && <Badge bg='success'>{t("Approved")}</Badge>}
          {!approved && <Badge bg='danger'>{t("Unapproved")}</Badge>}
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
        <Form.Group
          className='col-md-4 mb-2'
          controlId='formBasicRegistrationNum'>
          <Form.Label>{t("registrationNum")}</Form.Label>
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
          <Form.Label>{t("sex")}</Form.Label>
          <Form.Select
            required
            size='sm'
            name={RegisterFormKeys.Sex}
            value={formValues[RegisterFormKeys.Sex]}
            onChange={onChangeHandler}>
            <option label='Select sex'>Select sex</option>
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
            onChange={onChangeHandler}
            value={formValues[RegisterFormKeys.Color]}>
            <option label={t("SelectColor")}>{t("SelectColor")}</option>
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

        {isAdminOrModerator && (
          <>
            <Form.Group className='col-md-4 mb-3' controlId='formOwner'>
              <Form.Label>{t("forms.Owner")}</Form.Label>
              <Form.Control
                name={RegisterFormKeys.Owner}
                value={formValues[RegisterFormKeys.Owner]}
                onChange={onChangeHandler}
                type='text'
              />
            </Form.Group>

            <Form.Group className='col-md-4 mb-3' controlId='formMother'>
              <Form.Label>{t("forms.Mother")}</Form.Label>
              <Form.Control
                name={RegisterFormKeys.Mother}
                value={formValues[RegisterFormKeys.Mother]}
                onChange={onChangeHandler}
                type='text'
              />
            </Form.Group>
            <Form.Group className='col-md-4 mb-3' controlId='formFather'>
              <Form.Label>{t("forms.Father")}</Form.Label>
              <Form.Control
                name={RegisterFormKeys.Father}
                value={formValues[RegisterFormKeys.Father]}
                onChange={onChangeHandler}
                type='text'
              />
            </Form.Group>
          </>
        )}

        <Row xs={1} md={1} className=' mt-3'>
          <Button
            className='col-md-3 m-auto mt-3  mb-3'
            variant='secondary'
            type='submit'>
            {t("forms.Button.EditDog")}
          </Button>
          <Button
            className='col-md-3 m-auto mt-3  mb-3'
            variant='secondary'
            onClick={() => window.history.back()}>
            {t("forms.Button.Close")}
          </Button>
          {!approved && isAdminOrModerator && (
            <Button
              className='col-md-3 m-auto mt-3  mb-3'
              variant='success'
              onClick={() => onApproveClick(selectedDog.id)}>
              {t("forms.Button.Approve")}
            </Button>
          )}
          {selectedDog.fatherRegistrationNum !== "" &&
            selectedDog.motherRegistrationNum !== "" &&
            isAdminOrModerator && (
              <Button
                className='col-md-3 m-auto mt-3  mb-3'
                variant='success'
                onClick={() => onAddParentsClick(selectedDog)}>
                {t("Add Parents")}
              </Button>
            )}
        </Row>
      </Form>
    </Container>
  );
};
