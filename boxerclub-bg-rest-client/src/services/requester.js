/* eslint-disable no-unreachable */
const request = async (method, token, url, data) => {
  const options = {};
  const authURL = "http://localhost:8080/users";
  const lang = localStorage.getItem("lang");

  if (Object.keys(token).length !== 0) {
    options.headers = {
      ...options.headers,
      Authorization: `Bearer ${token}`,
    };
  }

  if (method !== "GET") {
    options.method = method;
    if (data) {
      options.headers = {
        "Content-type": "application/json",
        "Accept-Language": lang,
      };
      options.body = JSON.stringify(data);
    }
  }

  if (
    method !== "GET" &&
    url !== `${authURL}/register` &&
    url !== `${authURL}/login`
  ) {
    options.method = method;
    if (data) {
      options.headers = {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      };
      options.body = JSON.stringify(data);
    }
  }

  const response = await fetch(url, options);
  if (response.status === 401) {
    return Promise.all(["401", response.text()]);
  }

  if (url !== `${authURL}/register` && url !== `${authURL}/login`) {
    return await response.json();
  }

  if (
    response.status === 200 &&
    (url === `${authURL}/register` || url === `${authURL}/login`)
  ) {
    return Promise.all([
      response.json(),
      response.headers.get("Authorization"),
    ]);
  }

  if (
    response.status === 400 &&
    (url === `${authURL}/register` || url === `${authURL}/login`)
  ) {
    return Promise.all([response.json(), {}]);
  } else {
    return Promise.reject("Invalid login attempt");
  }
};

export const requestFactory = (token) => {
  /*if (!token) {
    const persistedAuthSerialized = localStorage.getItem("auth");

    if (persistedAuthSerialized) {
      const auth = JSON.parse(persistedAuthSerialized);
      token = auth.accessToken;
    }
  }*/

  return {
    get: request.bind(null, "GET", token),
    post: request.bind(null, "POST", token),
    put: request.bind(null, "PUT", token),
    patch: request.bind(null, "PATCH", token),
    remove: request.bind(null, "DELETE", token),
  };
};
