export const formatStringToUpperCaseWithSpaces = (input) => {
  let upper = input && input[0].toUpperCase() + input.slice(1);
  let output = upper.replace(/([A-Z])/g, " $1").trim();
  return `${output}`;
};
