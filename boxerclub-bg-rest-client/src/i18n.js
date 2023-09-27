import i18n from "i18next";
import Backend from "i18next-xhr-backend";
import { initReactI18next } from "react-i18next";

const lang = localStorage.getItem("lang");
i18n
  .use(Backend)
  .use(initReactI18next)
  .init({
    fallbackLng: lang === null ? "bg" : `${lang}`,
    interpolation: {
      escapeValue: false,
    },
  });

export default i18n;
