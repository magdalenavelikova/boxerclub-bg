import { requestFactory } from "./requester";

//console.log(process.env.NODE_ENV);
const host =
  process.env.NODE_ENV === "development"
    ? "http://localhost:8080"
    : "http://localhost:8080";

const baseUrl = `${host}/dogs`;

export const dogServiceFactory = (token) => {
  const request = requestFactory(token);

  const getAll = async () => {
    const result = await request.get(baseUrl);
    const dogs = Object.values(result);
    return dogs;
  };

  const create = async (dogData) => {
    const response = await fetch(`${baseUrl}/register`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: dogData,
    });

    return [response.status, await response.json()];
  };

  const createParent = async (dogData) => {
    const response = await fetch(`${baseUrl}/register/parent`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: dogData,
    });
    return [response.status, await response.json()];
  };

  const addParent = async (data) => {
    return await request.post(`${baseUrl}/add/parent`, data);
  };

  /*  const uploadPedigree = async (dogData) => {
    const response = await fetch(`${baseUrl}/pedigree/upload`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: dogData,
    });
    return await response.json();
  };

  const createWithoutFile = async (dogData) => {
    const response = await fetch(`${baseUrl}/register`, {
      method: "POST",
      headers: {
        "Content-type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify(dogData),
    });
    const result = await response.json();

    console.log(result);

    //const result = await request.post(`${baseUrl}/register`, dogData);
    // return result;
  };*/
  const getById = async (id) => {
    const result = await request.get(`${baseUrl}/${id}`);
    return result;
  };

  const remove = (dogId) => {
    return request.remove(`${baseUrl}/${dogId}`);
  };

  const update = async (dogId, data) => {
    const response = await fetch(`${baseUrl}/edit/${dogId}`, {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: data,
    });

    return [response.status, await response.json()];
    // return request.put(`${baseUrl}/edit/${dogId}`, data);
  };

  return {
    getAll,
    remove,
    update,
    create,
    addParent,
    createParent,

    getById,
  };
};
