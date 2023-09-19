/* eslint-disable no-unreachable */
const request = async (method, token, url, data) => {
  const options = {};

  if (method !== "GET") {
    options.method = method;
    if (data) {
      options.headers = {
        "Content-type": "application/json",
      };
      options.body = JSON.stringify(data);
    }
  }

  if (token) {
    options.headers = {
      ...options.headers,
      "X-Authorization": token,
    };
  }

  const response = await fetch(url, options);

  if (response.status === 200) {
    return Promise.all([
      response.json(),
      response.headers.get("Authorization"),
    ]);
  }
  if (response.status === 400) {
    return Promise.all([response.json(), {}]);
  } else {
    return Promise.reject("Invalid login attempt");
  }

  const result = await response.json();

  return result;
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
