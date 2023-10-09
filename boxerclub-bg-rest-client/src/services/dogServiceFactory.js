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

  /*const getLatest = async () => {
    const query = encodeURIComponent("_createdOn desc");
    ///data/games?sortBy=_createdOn%20desc&distinct=category
    const result = await request.get(
      `${baseUrl}?sortBy=${query}&distinct=category`
    );
    const latestDogs = Object.values(result);
    return latestDogs;
  };*/

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

  const uploadPedigree = async (dogData) => {
    const response = await fetch(`${baseUrl}/pedigree/upload`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      body: dogData,
    });
    return await response.json();
  };

  /* const createWithoutFile = async (dogData) => {
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

  /*const addComment = async (gameId, data) => {
    const result = await request.post(`${baseUrl}/${gameId}/comments/`, data);
    return result;
  };*/

  const remove = (dogId) => {
    return request.remove(`${baseUrl}/${dogId}`);
  };

  const edit = (dogId, data) => {
    const result = request.put(`${baseUrl}/${dogId}`, data);
    return result;
  };

  return {
    getAll,
    remove,
    edit,
    create,
    addParent,
    createParent,
    uploadPedigree,
    getById,
  };
};
