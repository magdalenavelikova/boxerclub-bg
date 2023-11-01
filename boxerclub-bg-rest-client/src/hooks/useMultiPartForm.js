import { use } from "i18next";
import { useState } from "react";

export const useMultiPartForm = (initialValues, onSubmitHandler) => {
  const [formValues, setFormValues] = useState(initialValues);
  const [selectedFile, setSelectedFile] = useState();
  const [selectedFilePedigree, setSelectedFilePedigree] = useState();
  const [isEmptyFile, setIsEmptyFile] = useState(true);
  const [validated, setValidated] = useState(false);

  const onChangeHandler = (e) => {
    setFormValues((state) => ({
      ...state,
      [e.target.name]:
        e.target.type === "checkbox" ? e.target.checked : e.target.value,
    }));
  };

  const onFileSelectedHandler = (e) => {
    if (e.target.files[0] && e.target.files[0].name !== "") {
      setSelectedFile(e.target.files[0]);
      setIsEmptyFile(false);
    }
  };
  const onFileSelectedPedigreeHandler = (e) => {
    if (e.target.files[0] && e.target.files[0].name !== "") {
      setSelectedFilePedigree(e.target.files[0]);
      setIsEmptyFile(false);
    } else {
      setIsEmptyFile(true);
      setValidated(false);
    }
  };

  const onSubmit = (e) => {
    const form = e.currentTarget;
    if (form.checkValidity() === false) {
      e.preventDefault();
      e.stopPropagation();
      e.nativeEvent.stopImmediatePropagation();
    }
    setValidated(true);
    e.preventDefault();

    setFormValues((state) => ({
      ...state,
      [e.target.name]:
        e.target.type === "checkbox" ? e.target.checked : e.target.value,
    }));

    const { file, pedigree, ...dtoValues } = formValues;

    /* if (this.state.pedigree.size >= 5000000) {
      this.setState({ error: "File size exceeds limit of 2MB." });
      return;
    }*/
    const formData = new FormData();
    formData.append("file", selectedFile);
    formData.append("pedigree", selectedFilePedigree);
    formData.append(
      "dto",
      new Blob([JSON.stringify(dtoValues)], {
        type: "application/json",
      })
    );

    // formData.append("dto", JSON.stringify(dtoValues));
    /* console.log("New form Data");

    for (var key of formData.entries()) {
      console.log(key[0] + ", " + key[1]);
    }*/
    if (form.checkValidity() === true) {
      onSubmitHandler(
        formData,
        isEmptyFile,
        dtoValues.id,
        dtoValues.registrationNum,
        dtoValues.childId
      );
    }
  };
  const changeValues = (newValues) => {
    setFormValues(newValues);
  };

  return {
    formValues,
    selectedFile,
    validated,
    onChangeHandler,
    onFileSelectedHandler,
    onFileSelectedPedigreeHandler,
    onSubmit,
    changeValues,
  };
};
