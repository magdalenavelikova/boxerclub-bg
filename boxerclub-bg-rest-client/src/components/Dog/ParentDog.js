import {
  Button,
  Row,
  Col,
  Container,
  Form,
  Alert,
  Spinner,
} from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useMultiPartForm } from "../../hooks/useMultiPartForm";
import { DogContext } from "../../contexts/DogContext";
import { useNavigate } from "react-router-dom";
import { SuccessModal } from "../Modal/SuccessModal";
import { useContext, useEffect, useState } from "react";
import { OnFindParentModal } from "../Modal/OnFindParentModal";
import { AuthContext } from "../../contexts/AuthContext";

export const ParentDog = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const onCloseHandler = () => {
    return navigate("/");
  };

  const {
    onCreateParentDogSubmitHandler,
    onAddParentDogSubmitHandler,
    createdDog,
    parent,
    dogs,
    errors,
    spinner,
  } = useContext(DogContext);
  const { isAuthenticated, authorities } = useContext(AuthContext);
  const isAdminOrModerator =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));
  const [dogsList, setDogsList] = useState([]);
  const [child, setChild] = useState({});
  const [selectedDog, setSelectedDog] = useState({});
  const [mother, setMother] = useState({});
  const [father, setFather] = useState({});
  const [parents, setParents] = useState([]);
  const [registrationNum, setRegistrationNum] = useState({});
  const [birthday, setBirthday] = useState({});
  const [modalShow, setModalShow] = useState(false);
  const [successModalShow, setSuccessModalShow] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(spinner);
  }, [spinner]);

  const onSetParentHandler = () => {
    setModalShow(false);
    if (selectedDog.length !== 0) {
      if (selectedDog[0].sex == "Male") {
        setFather(selectedDog[0]);
        const parent = { ...selectedDog[0], childId: `${child.id}` };
        onAddParentDogSubmitHandler(parent);
        setParents((state) => [...state, parent]);
        setSelectedDog({});
      } else {
        setMother(selectedDog[0]);
        const parent = { ...selectedDog[0], childId: `${child.id}` };
        onAddParentDogSubmitHandler(parent);
        setParents((state) => [...state, parent]);
        setSelectedDog({});
      }
    }
  };
  const onCloseClick = () => {
    setModalShow(false);
    setSelectedDog({});
  };

  useEffect(() => {
    setDogsList(dogs);
    setSelectedDog({});
    setChild(createdDog);
  }, []);

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

  const RegisterMotherFormKeys = {
    RegistrationNum: "registrationNum",
    Name: "name",
    File: "file",
    Pedigree: "pedigree",
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
    Pedigree: "pedigree",
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
    onFileSelectedPedigreeHandler,
    onSubmit,
    validated,
  } = useMultiPartForm(
    {
      [RegisterMotherFormKeys.RegistrationNum]: "",
      [RegisterMotherFormKeys.Name]: "",
      [RegisterMotherFormKeys.MicroChip]: "",
      [RegisterMotherFormKeys.File]: "",
      [RegisterMotherFormKeys.Pedigree]: "",
      [RegisterMotherFormKeys.Sex]: "Female",
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
    onFileSelectedPedigreeHandler: onFileSelectedPedigreeHandler2,
    onSubmit: onSubmit2,
    validated: validated2,
  } = useMultiPartForm(
    {
      [RegisterFatherFormKeys.RegistrationNum]: "",
      [RegisterFatherFormKeys.Name]: "",
      [RegisterFatherFormKeys.MicroChip]: "",
      [RegisterFatherFormKeys.File]: "",
      [RegisterFatherFormKeys.Pedigree]: "",
      [RegisterFatherFormKeys.Sex]: "Male",
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

    const parentIsNotAsChild =
      formValues.registrationNum == child.registrationNum;
    if (parentIsNotAsChild) {
      setModalShow(false);
    } else {
      setModalShow(true);
    }
  }, [formValues.registrationNum]);

  useEffect(() => {
    setDogsList(dogs);
    setSelectedDog(
      dogsList.filter(
        (item) => item.registrationNum === formValues2.registrationNum
      )
    );
    const parentIsNotAsChild =
      formValues.registrationNum === child.registrationNum;
    if (parentIsNotAsChild) {
      setModalShow(false);
    } else {
      setModalShow(true);
    }
  }, [formValues2.registrationNum]);

  useEffect(() => {
    if (Object.keys(parent).length !== 0) {
      if (parent.sex == "Male") {
        setFather(parent);
      } else {
        setMother(parent);
      }
      setParents((state) => [...state, parent]);
      setSuccessModalShow(true);
    } else {
      setSuccessModalShow(false);
    }
  }, [parent]);

  return (
    <>
      {successModalShow && <SuccessModal parent={parent} child={createdDog} />}
      {selectedDog.length !== 0 && modalShow && (
        <OnFindParentModal
          parent={selectedDog[0]}
          onSetParentHandler={onSetParentHandler}
          onCloseClick={onCloseClick}
        />
      )}
      <Container fluid className='m-auto container-fluid-md pt-5 '>
        {Object.keys(registrationNum).length !== 0 && (
          <Row xs={1} md={2} className=' mt-4'>
            <Alert className='col-md-6 m-auto  text-center' variant='danger'>
              {registrationNum}
            </Alert>
          </Row>
        )}
        {Object.keys(birthday).length !== 0 && (
          <Row xs={1} md={2} className=' mt-4'>
            <Alert className='col-md-6 m-auto  text-center' variant='danger'>
              {birthday}
            </Alert>
          </Row>
        )}
        <Row xs={1} md={2}>
          <Col>
            {Object.keys(mother).length === 0 && (
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
                  <Form.Label>{t("registrationNum")}</Form.Label>
                  <Form.Control
                    required={!isAdminOrModerator}
                    name={RegisterMotherFormKeys.RegistrationNum}
                    value={formValues[RegisterMotherFormKeys.RegistrationNum]}
                    onChange={onChangeHandler}
                    type='text'
                    placeholder={t("EnterRegistrationNum")}
                  />
                  <Form.Control.Feedback type='invalid' className='text-danger'>
                    {t("validation")}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group className='col-md-4 mb-2' controlId='formBasicName'>
                  <Form.Label>{t("firstName")}</Form.Label>
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
                  <Form.Label>{t("microChip")}</Form.Label>
                  <Form.Control
                    name={RegisterMotherFormKeys.MicroChip}
                    value={formValues[RegisterMotherFormKeys.MicroChip]}
                    onChange={onChangeHandler}
                    type='text'
                    placeholder={t("EnterMicroChip")}
                  />
                </Form.Group>

                <Form.Group className='col-md-4 mb-3' controlId='formFileSm'>
                  <Form.Label>{t("picture")}</Form.Label>

                  <Form.Control
                    type='file'
                    accept='image/png, image/jpeg'
                    size='sm'
                    label=''
                    onChange={onFileSelectedHandler}
                    placeholder={t("EnterPictureUrl")}
                  />
                </Form.Group>
                {child.hasOwnProperty("registrationNum") &&
                  child.registrationNum.includes("Newborn") && (
                    <Form.Group
                      className='col-md-3 mb-3'
                      controlId='formFileSm'>
                      <Form.Label> {t("UploadPedigree")}</Form.Label>
                      <Form.Control
                        required={!isAdminOrModerator}
                        type='file'
                        className='prevent-validation-styles'
                        accept='image/jpeg,image/gif,image/png,application/pdf'
                        size='sm'
                        title='Choose a pdf or image file please'
                        onChange={onFileSelectedPedigreeHandler}
                      />
                    </Form.Group>
                  )}
                <Form.Group className='col-md-2 mb-2' controlId='formBasicSex'>
                  <Form.Label>{t("sex")}</Form.Label>
                  <Form.Control
                    disabled
                    name={RegisterMotherFormKeys.Sex}
                    value={t("Female")}
                    onChange={onChangeHandler}
                    type='text'
                  />
                </Form.Group>

                <Form.Group
                  className='col-md-3 mb-3'
                  controlId='formBasicColor'>
                  <Form.Label>{t("color")}</Form.Label>
                  <Form.Select
                    required
                    size='sm'
                    name={RegisterMotherFormKeys.Color}
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

                <Form.Group
                  className='col-md-4  mb-3'
                  controlId='formBasicDate'>
                  <Form.Label>{t("birthday")} </Form.Label>
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
                  <Form.Label>{t("healthStatus")}</Form.Label>
                  <Form.Control
                    name={RegisterMotherFormKeys.HealthStatus}
                    value={formValues[RegisterMotherFormKeys.HealthStatus]}
                    onChange={onChangeHandler}
                    type='text'
                  />
                </Form.Group>
                <Form.Group className='col-md-4 mb-3' controlId='formKennel'>
                  <Form.Label>{t("kennel")}</Form.Label>
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
                  {t("forms.Button.Submit")}
                </Button>
              </Form>
            )}
          </Col>
          <Col>
            {Object.keys(father).length === 0 && (
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
                  <Form.Label>{t("registrationNum")}</Form.Label>
                  <Form.Control
                    required={!isAdminOrModerator}
                    name={RegisterFatherFormKeys.RegistrationNum}
                    value={formValues2[RegisterFatherFormKeys.RegistrationNum]}
                    onChange={onChangeHandler2}
                    type='text'
                    placeholder={t("EnterRegistrationNum")}
                  />
                  <Form.Control.Feedback type='invalid' className='text-danger'>
                    {t("validation")}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group className='col-md-4 mb-2' controlId='formBasicName'>
                  <Form.Label>{t("firstName")}</Form.Label>
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
                  <Form.Label>{t("microChip")}</Form.Label>
                  <Form.Control
                    type='text'
                    name={RegisterFatherFormKeys.MicroChip}
                    value={formValues2[RegisterFatherFormKeys.MicroChip]}
                    onChange={onChangeHandler2}
                    placeholder={t("EnterMicroChip")}
                  />
                </Form.Group>
                <Form.Group className='col-md-4 mb-3' controlId='formFileSm'>
                  <Form.Label>{t("picture")}</Form.Label>

                  <Form.Control
                    type='file'
                    accept='image/png, image/jpeg'
                    size='sm'
                    label=''
                    onChange={onFileSelectedHandler2}
                    placeholder={t("EnterPictureUrl")}
                  />
                </Form.Group>
                {child.hasOwnProperty("registrationNum") &&
                  child.registrationNum.includes("Newborn") && (
                    <Form.Group
                      className='col-md-3 mb-3'
                      controlId='formFileSm'>
                      <Form.Label> {t("UploadPedigree")}</Form.Label>
                      <Form.Control
                        required={!isAdminOrModerator}
                        type='file'
                        className='prevent-validation-styles'
                        accept='image/jpeg,image/gif,image/png,application/pdf'
                        size='sm'
                        title='Choose a pdf or image file please'
                        onChange={onFileSelectedPedigreeHandler2}
                      />
                    </Form.Group>
                  )}
                <Form.Group className='col-md-2 mb-2' controlId='formBasicSex'>
                  <Form.Label>{t("sex")}</Form.Label>
                  <Form.Control
                    disabled
                    name={RegisterFatherFormKeys.Sex}
                    value={t("Male")}
                    onChange={onChangeHandler2}
                    type='text'
                  />
                </Form.Group>
                <Form.Group
                  className='col-md-3 mb-3'
                  controlId='formBasicColor'>
                  <Form.Label>{t("color")}</Form.Label>
                  <Form.Select
                    required
                    size='sm'
                    name={RegisterFatherFormKeys.Color}
                    onChange={onChangeHandler2}>
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
                <Form.Group
                  className='col-md-4  mb-3'
                  controlId='formBasicDate'>
                  <Form.Label>{t("birthday")} </Form.Label>
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
                  <Form.Label>{t("healthStatus")}</Form.Label>
                  <Form.Control
                    name={RegisterFatherFormKeys.HealthStatus}
                    value={formValues2[RegisterFatherFormKeys.HealthStatus]}
                    onChange={onChangeHandler2}
                    type='text'
                  />
                </Form.Group>
                <Form.Group className='col-md-4 mb-3' controlId='formKennel'>
                  <Form.Label>{t("kennel")}</Form.Label>
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
                  {t("forms.Button.Submit")}
                </Button>
              </Form>
            )}
          </Col>
        </Row>
        <Row xs={1} md={1} className='p-5'>
          {parents.length < 2 && (
            <Alert className='col-md-6 m-auto text-center' variant='danger'>
              {t("Parent.Danger")}
            </Alert>
          )}
          {parents.length > 1 && (
            <Alert className='col-md-6 m-auto  text-center' variant='success'>
              {t("Parent.Success")}
            </Alert>
          )}

          <Row>
            {parents.length > 1 && (
              <Button
                className='col-md-2  m-auto mt-3 mb-2'
                variant='secondary'
                onClick={onCloseHandler}>
                {t("forms.Button.Close")}
              </Button>
            )}
          </Row>
        </Row>
      </Container>
    </>
  );
};
