import { useState } from "react";

export const useForm = (initialValues, onSubmitHandler) => {
  const [formValues, setFormValues] = useState(initialValues);
  const [validated, setValidated] = useState(false);

  const onChangeHandler = (e) => {
    setFormValues((state) => ({
      ...state,

      [e.target.name]:
        e.target.type === "checkbox" ? e.target.checked : e.target.value,
    }));
  };

  const onSubmit = (e) => {
    const form = e.currentTarget;
    if (form.checkValidity() === false) {
      e.preventDefault();
      e.stopPropagation();
    }
    setValidated(true);
    e.preventDefault();
    onSubmitHandler(formValues);
    //setFormValues(initialValues);

    //   console.log(formValues);
  };
  const changeValues = (newValues) => {
    setFormValues(newValues);
  };

  return {
    formValues,
    onChangeHandler,
    validated,
    onSubmit,
    changeValues,
  };
};
