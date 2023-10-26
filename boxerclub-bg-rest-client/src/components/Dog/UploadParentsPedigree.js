import { Button, Row, Col, Container, Form, Alert } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useMultiPartForm } from "../../hooks/useMultiPartForm";
import { useDogContext } from "../../contexts/DogContext";
import { useNavigate } from "react-router-dom";
import { SuccessModal } from "../Modal/SuccessModal";
import { useEffect, useState } from "react";
import { OnFindParentModal } from "../Modal/OnFindParentModal";
import { use } from "i18next";

export const UploadParentsPedigree = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const onCloseHandler = () => {
    return navigate("/");
  };
  const onContinueHandler = () => {
    return navigate("/dogs/pedigree/upload");
  };

  const {
    onCreateParentDogSubmitHandler,
    onAddParentDogSubmitHandler,
    createdDog,
    parent,
    dogs,
  } = useDogContext();

  const [dogsList, setDogsList] = useState([]);
  const [selectedDog, setSelectedDog] = useState({});
  const [mother, setMother] = useState({});
  const [father, setFather] = useState({});
  const [parents, setParents] = useState([]);
  const [modalShow, setModalShow] = useState(false);
  const [successModalShow, setSuccessModalShow] = useState(false);
  const onSetParentHandler = () => {
    setModalShow(false);
    if (selectedDog.length !== 0) {
      if (selectedDog[0].sex == "Мъжки" || selectedDog[0].sex == "Male") {
        setFather(selectedDog[0]);
        const parent = { ...selectedDog[0], childId: `${createdDog.id}` };
        onAddParentDogSubmitHandler(parent);
        setParents((state) => [...state, parent]);
        setSelectedDog({});
      } else {
        setMother(selectedDog[0]);
        const parent = { ...selectedDog[0], childId: `${createdDog.id}` };
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
  }, []);

  const RegisterMotherFormKeys = {
    Id: "id",
    File: "file",
  };
  const RegisterFatherFormKeys = {
    Id: "id",
    File: "file",
  };

  const {
    formValues,
    onChangeHandler,
    onFileSelectedHandler,
    onSubmit,
    validated,
  } = useMultiPartForm(
    {
      [RegisterMotherFormKeys.Id]: "",
      [RegisterMotherFormKeys.File]: "",
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
      [RegisterFatherFormKeys.Id]: "",
      [RegisterFatherFormKeys.File]: "",
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
      formValues.registrationNum === createdDog.registrationNum;
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
      formValues.registrationNum === createdDog.registrationNum;
    if (parentIsNotAsChild) {
      setModalShow(false);
    } else {
      setModalShow(true);
    }
  }, [formValues2.registrationNum]);
  useEffect(() => {
    if (Object.keys(parent).length !== 0) {
      if (parent.sex == "Мъжки" || parent.sex == "Male") {
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
                {t("UploadPedigree")} на майката
              </Form.Label>
              <Row>
                <Form.Group className='col-md-4 mb-3' controlId='formFileSm'>
                  <Form.Label>{t("forms.File")}</Form.Label>
                  <Form.Control
                    required
                    type='file'
                    className='prevent-validation-styles'
                    accept='image/jpeg,image/gif,image/png,application/pdf'
                    size='sm'
                    title='Choose a pdf or image file please'
                    onChange={onFileSelectedHandler}
                  />
                </Form.Group>
              </Row>
              <Row>
                <Button
                  className='col-md-2 m-auto mt-3 mb-3'
                  variant='secondary'
                  type='submit'>
                  {t("forms.Button.Submit")}
                </Button>
              </Row>
            </Form>
          </Col>
          <Col>
            <Form
              noValidate
              validated={validated}
              method='POST'
              onSubmit={onSubmit}
              className='row g-3 m-auto mt-5 border border-secondary rounded p-3'>
              <Form.Label className='d-inline-block pb-2'>
                {t("UploadPedigree")} на бащата
              </Form.Label>
              <Row>
                <Form.Group className='col-md-4 mb-3' controlId='formFileSm'>
                  <Form.Label>{t("forms.File")}</Form.Label>
                  <Form.Control
                    required
                    type='file'
                    className='prevent-validation-styles'
                    accept='image/jpeg,image/gif,image/png,application/pdf'
                    size='sm'
                    title='Choose a pdf or image file please'
                    onChange={onFileSelectedHandler}
                  />
                </Form.Group>
              </Row>
              <Row>
                <Button
                  className='col-md-2 m-auto mt-3 mb-3'
                  variant='secondary'
                  type='submit'>
                  {t("forms.Button.Submit")}
                </Button>
              </Row>
            </Form>
          </Col>
        </Row>
        <Row xs={1} md={1} className=' mt-3'>
          {parents.length < 2 && (
            <Alert variant='danger'>
              За да бъде успешна регистрацията на Вашето куче трябва да качите
              родословията и на двамата родители{" "}
            </Alert>
          )}
          {/* <Button
            className='col-md-2  m-auto mb-2'
            variant='secondary'
            onClick={onCloseHandler}>
            {t("forms.Button.Close")}
          </Button>*/}
          {parents.length > 1 && (
            <Button
              className='col-md-2  m-auto mt-3 mb-2'
              variant='secondary'
              onClick={onContinueHandler}>
              {t("forms.Button.Continue")}
            </Button>
          )}
        </Row>
      </Container>
    </>
  );
};
