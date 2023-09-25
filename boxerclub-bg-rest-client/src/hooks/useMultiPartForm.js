import { useState } from "react";

export const useMultiPartForm = (initialValues, onSubmitHandler) => {
  const [formValues, setFormValues] = useState(initialValues);
  const [selectedFile, setSelectedFile] = useState(null);
  const [validated, setValidated] = useState(false);

  const onChangeHandler = (e) => {
    setFormValues((state) => ({
      ...state,
      [e.target.name]:
        e.target.type === "checkbox" ? e.target.checked : e.target.value,
    }));
  };
  const onFileSelectedHandler = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const onSubmit = (e) => {
    const form = e.currentTarget;
    if (form.checkValidity() === false) {
      e.preventDefault();
      e.stopPropagation();
    }
    setValidated(true);
    e.preventDefault();

    const { picture, ...dtoValues } = formValues;
    const formData = new FormData();
    formData.append("file", selectedFile);
    formData.append("dto", JSON.stringify(dtoValues));
    /* console.log("New form Data");

    for (var key of formData.entries()) {
      console.log(key[0] + ", " + key[1]);
    }*/
    onSubmitHandler(formData, dtoValues);
  };
  const changeValues = (newValues) => {
    setFormValues(newValues);
  };

  return {
    formValues,
    onChangeHandler,
    onFileSelectedHandler,
    validated,
    onSubmit,
    changeValues,
  };
};
