import { requestFactory } from "./requester";

const host =
  process.env.NODE_ENV === "development"
    ? "http://localhost:8080"
    : "http://localhost:8080";

const baseUrl = `${host}/links`;

export const linkServiceFactory = (token) => {
  const request = requestFactory(token);

  const getAll = async () => {
    const result = await request.get(baseUrl);
    const dogs = Object.values(result);
    return dogs;
  };

  const create = async (data) => {
    const result = await request.post(baseUrl, { data });

    return result;
  };

  return {
    create,
    getAll,
  };
};
