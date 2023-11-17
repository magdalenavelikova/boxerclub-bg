import { useTranslation } from "react-i18next";
import { Form } from "react-bootstrap";

export const LanguageSwitcher = () => {
  const { i18n } = useTranslation();

  const handleLangChange = (evt) => {
    const lang = evt.target.value;
    localStorage.setItem("lang", lang);
    i18n.changeLanguage(lang);
  };

  return (
    <Form.Select
      style={{ width: "4rem" }}
      size='sm'
      aria-label='Default select example'
      onChange={handleLangChange}
      value={localStorage.getItem("lang")}>
      <option label='Select language'>Select language</option>
      <option value='bg'>BG</option>
      <option value='en'>EN</option>
    </Form.Select>
  );
};
