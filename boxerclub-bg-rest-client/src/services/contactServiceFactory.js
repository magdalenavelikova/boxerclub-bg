import { requestFactory } from "./requester";

const host =
  process.env.NODE_ENV === "development"
    ? "https://restapi.boxerclub-bg.org"
    : "https://restapi.boxerclub-bg.org";

const baseUrl = `${host}/contacts`;

export const contactServiceFactory = (token) => {
  const request = requestFactory(token);

  return {
    getAll: () => request.get(`${baseUrl}`),
    create: (data) => request.post(`${baseUrl}/add`, data),
    remove: (id) => request.remove(`${baseUrl}/${id}`),
    update: (id, data) => request.patch(`${baseUrl}/${id}`, data),
  };
};
