import { requestFactory } from "./requester";

const host =
  process.env.NODE_ENV === "development"
    ? "http://localhost:8080"
    : "https://boxerclub-bg-spring-app-boxerclub-bg-server.azuremicroservices.io";

const baseUrl = `${host}/events`;

export const eventServiceFactory = (token) => {
  const request = requestFactory(token);

  return {
    getAll: () => request.get(`${baseUrl}`),
    create: (data) => request.post(`${baseUrl}/add`, data),
    remove: (id) => request.remove(`${baseUrl}/${id}`),
    update: (id, data) => request.patch(`${baseUrl}/${id}`, data),
  };
};
