import { Button, Row, Col, Container, Form } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useMultiPartForm } from "../../hooks/useMultiPartForm";
import { useDogContext } from "../../contexts/DogContext";
import { useNavigate } from "react-router-dom";
import { SuccessModal } from "../Modal/SuccessModal";
import { useEffect, useState } from "react";
import { OnFindParentModal } from "../Modal/OnFindParentModal";

export const ParentDog = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const onCloseHandler = () => {
    return navigate("/");
  };
  const onContinueHandler = () => {
    return navigate("/dogs/pedigree/upload");
  };

  const { onCreateParentDogSubmitHandler, createdDog, parent, dogs } =
    useDogContext();
  const [dogsList, setDogsList] = useState([]);
  const [selectedDog, setSelectedDog] = useState({});
  const [mother, setMother] = useState({});
  const [father, setFather] = useState({});
  const [modalShow, setModalShow] = useState(false);
  const onSetParentHandler = () => {
    setModalShow(false);
    if (selectedDog.length !== 0) {
      if (selectedDog[0].sex == "Мъжки" || selectedDog[0].sex == "Male") {
        setFather(selectedDog[0]);
        setMother({});
      } else {
        setFather({});
        setMother(selectedDog[0]);
      }

      console.log("father");
      console.log(father);
      console.log("mother");
      console.log(mother);
    }
  };
  const onCloseClick = () => {
    setSelectedDog({});
  };
  useEffect(() => {
    setDogsList(dogs);
    setSelectedDog({});
  }, []);
  const RegisterMotherFormKeys = {
    RegistrationNum: "registrationNum",
    Name: "name",
    File: "file",
    Sex: "sex",
    Color: "color",
    Birthday: "birthday",
    MicroChip: "microChip",
    HealthStatus: "healthStatus",
    Kennel: "kennel",
    ChildId: "childId",
  };
  const RegisterFatherFormKeys = {
    RegistrationNum: "registrationNum",
    Name: "name",
    File: "file",
    Sex: "sex",
    Color: "color",
    Birthday: "birthday",
    MicroChip: "microChip",
    HealthStatus: "healthStatus",
    Kennel: "kennel",
    ChildId: "childId",
  };

  const {
    formValues,
    onChangeHandler,
    onFileSelectedHandler,
    onSubmit,
    validated,
  } = useMultiPartForm(
    {
      [RegisterMotherFormKeys.RegistrationNum]: mother
        ? mother.registrationNum
        : "",
      [RegisterMotherFormKeys.Name]: mother ? mother.Name : "",
      [RegisterMotherFormKeys.MicroChip]: mother ? mother.MicroChip : "",
      [RegisterMotherFormKeys.File]: "",
      [RegisterMotherFormKeys.Sex]: mother ? mother.Sex : `${t("Female")}`,
      [RegisterMotherFormKeys.Color]: "",
      [RegisterMotherFormKeys.Birthday]: "",
      [RegisterMotherFormKeys.HealthStatus]: "",
      [RegisterMotherFormKeys.Kennel]: "",
      [RegisterMotherFormKeys.ChildId]: `${createdDog.id}`,
    },
    onCreateParentDogSubmitHandler
  );
  const {
    formValues: formValues2,
    onChangeHandler: onChangeHandler2,
    onFileSelectedHandler: onFileSelectedHandler2,
    onSubmit: onSubmit2,
    validated: validated2,
  } = useMultiPartForm(
    {
      [RegisterFatherFormKeys.RegistrationNum]: father ? father.Name : "",
      [RegisterFatherFormKeys.Name]: father ? father.registrationNum : "",
      [RegisterFatherFormKeys.MicroChip]: father ? father.MicroChip : "",
      [RegisterFatherFormKeys.File]: "",
      [RegisterFatherFormKeys.Sex]: `${t("Male")}`,
      [RegisterFatherFormKeys.Color]: "",
      [RegisterFatherFormKeys.Birthday]: "",
      [RegisterFatherFormKeys.HealthStatus]: "",
      [RegisterFatherFormKeys.Kennel]: "",
      [RegisterFatherFormKeys.ChildId]: `${createdDog.id}`,
    },
    onCreateParentDogSubmitHandler
  );
  useEffect(() => {
    setDogsList(dogs);
    setSelectedDog(
      dogsList.filter(
        (item) => item.registrationNum === formValues.registrationNum
      )
    );
    setModalShow(true);
  }, [formValues.registrationNum]);
  //console.log(formValues);
  return (
    <>
      {Object.keys(parent).length !== 0 && (
        <SuccessModal parent={parent} createdDog={createdDog} />
      )}
      {selectedDog.length !== 0 && modalShow && (
        <OnFindParentModal
          parent={selectedDog[0]}
          onSetParentHandler={onSetParentHandler}
          onCloseClick={onCloseClick}
        />
      )}
      <Container fluid className=' mt-4 p-4 border border-secondary rounded'>
        <Row xs={1} md={2}>
          <Col>
            <Form
              noValidate
              validated={validated}
              method='POST'
              onSubmit={onSubmit}
              className='row g-3 m-auto mt-5 border border-secondary rounded p-3'>
              <Form.Label className='d-inline-block pb-2'>
                {t("RegisterMother")}
              </Form.Label>
              <Form.Group
                className='col-md-4 mb-2'
                controlId='formBasicRegistrationNum'>
                <Form.Label>{t("forms.RegistrationNum")}</Form.Label>
                <Form.Control
                  name={RegisterMotherFormKeys.RegistrationNum}
                  value={formValues[RegisterMotherFormKeys.RegistrationNum]}
                  onChange={onChangeHandler}
                  type='text'
                  placeholder={t("EnterRegistrationNum")}
                />
              </Form.Group>
              <Form.Group className='col-md-4 mb-2' controlId='formBasicName'>
                <Form.Label>{t("forms.FirstName")}</Form.Label>
                <Form.Control
                  required
                  name={RegisterMotherFormKeys.Name}
                  value={formValues[RegisterMotherFormKeys.Name]}
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
                controlId='formBasicMicroChip'>
                <Form.Label>{t("forms.Microchip")}</Form.Label>
                <Form.Control
                  name={RegisterMotherFormKeys.MicroChip}
                  value={formValues[RegisterMotherFormKeys.MicroChip]}
                  onChange={onChangeHandler}
                  type='text'
                  placeholder={t("EnterMicroChip")}
                />
              </Form.Group>

              <Form.Group className='col-md-4 mb-3' controlId='formFileSm'>
                <Form.Label>{t("forms.PictureUrl")}</Form.Label>

                <Form.Control
                  type='file'
                  accept='image/png, image/jpeg'
                  size='sm'
                  label=''
                  onChange={onFileSelectedHandler}
                  placeholder={t("EnterPictureUrl")}
                />
              </Form.Group>

              <Form.Group className='col-md-4 mb-2' controlId='formBasicSex'>
                <Form.Label>{t("forms.Sex")}</Form.Label>
                <Form.Control
                  disabled
                  name={RegisterMotherFormKeys.Sex}
                  value={t("Female")}
                  onChange={onChangeHandler}
                  type='text'
                />
              </Form.Group>

              <Form.Group className='col-md-4 mb-3' controlId='formBasicColor'>
                <Form.Label>{t("forms.Color")}</Form.Label>
                <Form.Select
                  required
                  size='sm'
                  name={RegisterMotherFormKeys.Color}
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
                  name={RegisterMotherFormKeys.Birthday}
                  value={formValues[RegisterMotherFormKeys.Birthday]}
                  onChange={onChangeHandler}
                  type='date'
                />
              </Form.Group>

              <Form.Group
                className='col-md-4 mb-3'
                controlId='formBasicHealthStatus'>
                <Form.Label>{t("forms.HealthStatus")}</Form.Label>
                <Form.Control
                  name={RegisterMotherFormKeys.HealthStatus}
                  value={formValues[RegisterMotherFormKeys.HealthStatus]}
                  onChange={onChangeHandler}
                  type='text'
                />
              </Form.Group>
              <Form.Group className='col-md-4 mb-3' controlId='formKennel'>
                <Form.Label>{t("forms.Kennel")}</Form.Label>
                <Form.Control
                  name={RegisterMotherFormKeys.Kennel}
                  value={formValues[RegisterMotherFormKeys.Kennel]}
                  onChange={onChangeHandler}
                  type='text'
                />
              </Form.Group>

              <Button
                className='col-md-2  mb-3'
                variant='secondary'
                type='submit'>
                {t("forms.Button.Submit")}
              </Button>
            </Form>
          </Col>
          <Col>
            <Form
              noValidate
              validated={validated2}
              method='POST'
              onSubmit={onSubmit2}
              className='row g-3 m-auto mt-5 border border-secondary rounded p-3'>
              <Form.Label className='d-inline-block pb-2'>
                {t("RegisterFather")}
              </Form.Label>
              <Form.Group
                className='col-md-4 mb-2'
                controlId='formBasicRegistrationNum'>
                <Form.Label>{t("forms.RegistrationNum")}</Form.Label>
                <Form.Control
                  name={RegisterFatherFormKeys.RegistrationNum}
                  value={formValues2[RegisterFatherFormKeys.RegistrationNum]}
                  onChange={onChangeHandler2}
                  type='text'
                  placeholder={t("EnterRegistrationNum")}
                />
              </Form.Group>
              <Form.Group className='col-md-4 mb-2' controlId='formBasicName'>
                <Form.Label>{t("forms.FirstName")}</Form.Label>
                <Form.Control
                  required
                  name={RegisterFatherFormKeys.Name}
                  value={formValues2[RegisterFatherFormKeys.Name]}
                  onChange={onChangeHandler2}
                  type='text'
                  placeholder={t("EnterName")}
                />
                <Form.Control.Feedback type='invalid' className='text-danger'>
                  {t("validation")}
                </Form.Control.Feedback>
              </Form.Group>

              <Form.Group
                className='col-md-4 mb-2'
                controlId='formBasicMicroChip'>
                <Form.Label>{t("forms.Microchip")}</Form.Label>
                <Form.Control
                  type='text'
                  name={RegisterFatherFormKeys.MicroChip}
                  value={formValues2[RegisterFatherFormKeys.MicroChip]}
                  onChange={onChangeHandler2}
                  placeholder={t("EnterMicroChip")}
                />
              </Form.Group>

              <Form.Group className='col-md-4 mb-3' controlId='formFileSm'>
                <Form.Label>{t("forms.PictureUrl")}</Form.Label>

                <Form.Control
                  type='file'
                  accept='image/png, image/jpeg'
                  size='sm'
                  label=''
                  onChange={onFileSelectedHandler2}
                  placeholder={t("EnterPictureUrl")}
                />
              </Form.Group>
              <Form.Group className='col-md-4 mb-2' controlId='formBasicSex'>
                <Form.Label>{t("forms.Sex")}</Form.Label>
                <Form.Control
                  disabled
                  name={RegisterFatherFormKeys.Sex}
                  value={t("Male")}
                  onChange={onChangeHandler2}
                  type='text'
                />
              </Form.Group>

              <Form.Group className='col-md-4 mb-3' controlId='formBasicColor'>
                <Form.Label>{t("forms.Color")}</Form.Label>
                <Form.Select
                  required
                  size='sm'
                  name={RegisterFatherFormKeys.Color}
                  onChange={onChangeHandler2}>
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
                  name={RegisterFatherFormKeys.Birthday}
                  value={formValues2[RegisterFatherFormKeys.Birthday]}
                  onChange={onChangeHandler2}
                  type='date'
                />
              </Form.Group>

              <Form.Group
                className='col-md-4 mb-3'
                controlId='formBasicHealthStatus'>
                <Form.Label>{t("forms.HealthStatus")}</Form.Label>
                <Form.Control
                  name={RegisterFatherFormKeys.HealthStatus}
                  value={formValues2[RegisterFatherFormKeys.HealthStatus]}
                  onChange={onChangeHandler2}
                  type='text'
                />
              </Form.Group>
              <Form.Group className='col-md-4 mb-3' controlId='formKennel'>
                <Form.Label>{t("forms.Kennel")}</Form.Label>
                <Form.Control
                  name={RegisterFatherFormKeys.Kennel}
                  value={formValues2[RegisterFatherFormKeys.Kennel]}
                  onChange={onChangeHandler2}
                  type='text'
                />
              </Form.Group>

              <Button
                className='col-md-2  mb-3'
                variant='secondary'
                type='submit'>
                {t("forms.Button.Submit")}
              </Button>
            </Form>
          </Col>
        </Row>
        <Row xs={1} md={1} className=' mt-3'>
          <Button
            className='col-md-2  m-auto mb-2'
            variant='secondary'
            onClick={onCloseHandler}>
            {t("forms.Button.Close")}
          </Button>
          <Button
            className='col-md-2  m-auto mb-2'
            variant='secondary'
            onClick={onContinueHandler}>
            {t("forms.Button.Continue")}
          </Button>
        </Row>
      </Container>
    </>
  );
};
