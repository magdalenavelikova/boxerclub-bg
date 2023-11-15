export const formatStringToUpperCaseWithSpaces = (input) => {
  let upper = input && input[0].toUpperCase() + input.slice(1);
  let output = upper.replace(/([A-Z])/g, " $1").trim();
  return `${output}`;
};
export const formatStringToUpperCase = (input) => {
  let upper = input.toUpperCase();
  return `${upper}`;
};
export const formatStringToWrap = (input) => {
  let wrapInput = input;

  if (wrapInput.length > 12) {
    const index = input.lastIndexOf(" ", 12);
    if (index !== -1) {
      wrapInput = (
        <>
          <tspan x='-40' dy='40'>
            {input.slice(0, index)}
          </tspan>
          <tspan x='-40' dy='1.2em'>
            {input.slice(index + 1)}
          </tspan>
        </>
      );
    }
  }

  return wrapInput;
};
