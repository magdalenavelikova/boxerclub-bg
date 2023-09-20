export const formatDateForUser = (input) => {
  const date = new Date(input);
  let day = date.getDate();
  let month = date.toLocaleString("default", { month: "long" });
  let year = date.getFullYear();

  return `${month} ${day}, ${year}`;
};

export const formatDateForUserDetails = (input) => {
  let options = { weekday: "long" };
  let date = new Date(input);
  let dayOfWeek = date.toLocaleDateString("en-US", options);
  let day = date.getDate();
  let month = date.toLocaleString("default", { month: "long" });
  let year = date.getFullYear();
  return `${dayOfWeek}, ${month} ${day}, ${year}`;
};

export const formatDate = (input) => {
  let date = new Date(input);
  return date.toLocaleString("en-Us", {
    month: "long",
    day: "numeric",
    year: "numeric",
  });
};
