import { requestFactory } from "./requester";
const lang = localStorage.getItem("lang");

const host =
  process.env.NODE_ENV === "development"
    ? "http://localhost:8080"
    : "http://localhost:8080";

const baseUrl = `${host}/dogs`;
const pedigreeUrl = `${host}/pedigree`;
export const dogServiceFactory = (token) => {
  const request = requestFactory(token);

  const getAll = async (token) => {
    const result = await request.get(baseUrl, token);
    const dogs = Object.values(result);
    return dogs;
  };

  const getAllApproved = async () => {
    const result = await request.get(`${baseUrl}/approved`);
    const dogs = Object.values(result);
    return dogs;
  };
  const create = async (dogData) => {
    const response = await fetch(`${baseUrl}/register`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Accept-Language": lang,
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

  const getById = async (id) => {
    return await request.get(`${baseUrl}/${id}`);
  };

  const getDetailsById = async (id) => {
    return await request.get(`${baseUrl}/details/${id}`);
  };

  const remove = async (dogId) => {
    return await request.remove(`${baseUrl}/${dogId}`);
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
  };

  const getPedigreeById = async (id) => {
    await fetch(`${pedigreeUrl}/download/${id}`, {
      method: "GET",
      headers: new Headers({
        Authorization: "Bearer " + token,
      }),
    }).then((response) => {
      const filename = response.headers
        .get("Content-Disposition")
        .split("filename=")[1];

      response.blob().then((blob) => {
        let url = window.URL.createObjectURL(blob);
        let a = document.createElement("a");
        a.href = url;
        a.download = filename;
        a.click();
      });
    });
  };

  return {
    getAllApproved,
    getAll,
    remove,
    update,
    create,
    addParent,
    createParent,
    getDetailsById,
    getPedigreeById,
    getById,
  };
};
