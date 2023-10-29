import { Button, Container, Form, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useForm } from "../../hooks/useForm";
import { useContactContext } from "../../contexts/ContactContext";

export const NewContact = () => {
  const { t } = useTranslation();
  const { onCreateContactSubmitHandler } = useContactContext();

  const ContactFormKeys = {
    Name: "name",
    NameBg: "nameBG",
    Sex: "sex",
    Picture: "picture",
    Position: "position",
    PositionBg: "positionBG",
    Country: "country",
    CountryBg: "countryBG",
    City: "city",
    CityBg: "cityBG",
    Zip: "zip",
    Address: "address",
    AddressBg: "addressBG",
    Email: "email",
    Phone: "phone",
  };

  const { formValues, onChangeHandler, onSubmit, validated } = useForm(
    {
      [ContactFormKeys.Name]: "",
      [ContactFormKeys.NameBg]: "",
      [ContactFormKeys.Sex]: "",
      [ContactFormKeys.Picture]: "",
      [ContactFormKeys.Position]: "",
      [ContactFormKeys.PositionBG]: "",
      [ContactFormKeys.Country]: "",
      [ContactFormKeys.CountryBg]: "",
      [ContactFormKeys.City]: "",
      [ContactFormKeys.CityBg]: "",
      [ContactFormKeys.Zip]: "",
      [ContactFormKeys.Address]: "",
      [ContactFormKeys.AddressBg]: "",
      [ContactFormKeys.Email]: "",
      [ContactFormKeys.Phone]: "",
    },
    onCreateContactSubmitHandler
  );

  return (
    <>
      <Container className='m-auto container-fluid-md pt-5  mt-5'>
        <Form
          noValidate
          validated={validated}
          method='POST'
          onSubmit={onSubmit}
          className='row g-3 m-auto mt-5 mb-5 border border-secondary rounded p-4'>
          <p className='mb-3'>{t("nav.Contacts.AddContact")}</p>
          <Row className='col-md-12 m-auto'>
            <Form.Group className='col-md-5 mb-3' controlId='formBasicName'>
              <Form.Label>Name EN</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.Name}
                value={formValues[ContactFormKeys.Name]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className='col-md-5 mb-3' controlId='formBasicNameBG'>
              <Form.Label>Име BG</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.NameBg}
                value={formValues[ContactFormKeys.NameBg]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className='col-md-2 mb-3' controlId='formBasicSex'>
              <Form.Label>{t("forms.Sex")}</Form.Label>
              <Form.Select
                required
                size='sm'
                name={ContactFormKeys.Sex}
                onChange={onChangeHandler}>
                <option label='Select sex' value=''>
                  Select type
                </option>
                <option value='M'>M</option>
                <option value='F'>F</option>
              </Form.Select>
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
          </Row>
          <Row className='col-md-12 m-auto'>
            <Form.Group className='col-md-4 mb-3' controlId='formBasicPicture'>
              <Form.Label>URL адрес за снимка</Form.Label>
              <Form.Control
                name={ContactFormKeys.Picture}
                value={formValues[ContactFormKeys.Picture]}
                onChange={onChangeHandler}
                type='text'
              />
            </Form.Group>

            <Form.Group className='col-md-4 mb-3' controlId='formBasicEmail'>
              <Form.Label>Имейл адрес</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.Email}
                value={formValues[ContactFormKeys.Email]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className='col-md-4 mb-3' controlId='formBasicPhone'>
              <Form.Label>Телефон</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.Phone}
                value={formValues[ContactFormKeys.Phone]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
          </Row>
          <Row className='col-md-12 m-auto'>
            <Form.Group className='col-md-3 mb-3' controlId='formBasicPosition'>
              <Form.Label>Position EN</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.Position}
                value={formValues[ContactFormKeys.Position]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group
              className='col-md-3 mb-3'
              controlId='formBasicPositionBG'>
              <Form.Label>Позиция BG</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.PositionBg}
                value={formValues[ContactFormKeys.PositionBg]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className='col-md-3 mb-3' controlId='formBasicCountry'>
              <Form.Label>Country EN</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.Country}
                value={formValues[ContactFormKeys.Country]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group
              className='col-md-3 mb-3'
              controlId='formBasicCountryBg'>
              <Form.Label>Държава BG</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.CountryBg}
                value={formValues[ContactFormKeys.CountryBg]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
          </Row>
          <Row className='col-md-12 m-auto'>
            <Form.Group className='col-md-2 mb-3' controlId='formBasicZip'>
              <Form.Label>Пощенски код</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.Zip}
                value={formValues[ContactFormKeys.Zip]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className='col-md-2 mb-3' controlId='formBasicCity'>
              <Form.Label>City EN</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.City}
                value={formValues[ContactFormKeys.City]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className='col-md-2 mb-3' controlId='formBasicCityBG'>
              <Form.Label>Град BG</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.CityBg}
                value={formValues[ContactFormKeys.CityBg]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className='col-md-3 mb-3' controlId='formBasicAddress'>
              <Form.Label>Address EN</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.Address}
                value={formValues[ContactFormKeys.Address]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group
              className='col-md-3 mb-3'
              controlId='formBasicCountryBg'>
              <Form.Label>Адрес BG</Form.Label>
              <Form.Control
                required
                name={ContactFormKeys.AddressBg}
                value={formValues[ContactFormKeys.AddressBg]}
                onChange={onChangeHandler}
                type='text'
              />
              <Form.Control.Feedback type='invalid' className='text-danger'>
                {t("validation")}
              </Form.Control.Feedback>
            </Form.Group>
          </Row>

          <Row className='col-md-12 m-auto'>
            <Button
              className='col-md-4  m-auto mt-4 mb-3'
              variant='secondary'
              type='submit'>
              {t("forms.Button.Submit")}
            </Button>
          </Row>
        </Form>
      </Container>
    </>
  );
};
