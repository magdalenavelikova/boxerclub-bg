import { useState } from "react";
import { useTranslation } from "react-i18next";
import { Form } from "react-bootstrap";
export const LanguageSwitcher = () => {
  const { i18n } = useTranslation();
  const [language, setLanguage] = useState("id");

  const handleLangChange = (evt) => {
    const lang = evt.target.value;
    console.log(lang);
    setLanguage(lang);
    i18n.changeLanguage(lang);
  };

  return (
    <Form.Select
      style={{ width: "5rem" }}
      size='sm'
      aria-label='Default select example'
      onChange={handleLangChange}
      value={language}>
      <option label='Select language'>Select language</option>
      <option value='bg'>BG</option>
      <option value='en'>EN</option>
    </Form.Select>
  );
};
