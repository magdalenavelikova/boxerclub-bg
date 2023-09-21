/* eslint-disable no-unreachable */
const request = async (method, token, url, data) => {
  const options = {};
  const authURL = "http://localhost:8080/users";

  /*  if (!token) {
    const persistedAuthSerialized = localStorage.getItem("jwt");

    if (persistedAuthSerialized) {
      const jwt = JSON.parse(persistedAuthSerialized);
      token = jwt;
    }
    options.headers = {
      "Content-type": "application/json",
      Authorization: `Bearer ${token}`,
    };
  }*/
  if (token) {
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
      };
      options.body = JSON.stringify(data);
    }
  }
  const response = await fetch(url, options);

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
