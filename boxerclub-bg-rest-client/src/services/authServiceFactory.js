import { requestFactory } from "./requester";
const host =
  process.env.NODE_ENV === "development"
    ? "http://localhost:8080"
    : "http://localhost:8080";

const baseUrl = `${host}/users`;


export const authServiceFactory = (token) => {
  const request = requestFactory(token);

  return {
    register: (data) => request.post(`${baseUrl}/register`, data),
    verify: (data) => request.get(`${baseUrl}/registerConfirm?token=${data}`),
    login: (loginData) => request.post(`${baseUrl}/login`, loginData),
    getAll: () => request.get(`${baseUrl}`),
    getAllRoles: () => request.get(`${baseUrl}/roles`),
    remove: (id) => request.remove(`${baseUrl}/${id}`),
    update: (id, data) => request.patch(`${baseUrl}/${id}`, data),
    find: (id) => request.get(`${baseUrl}/${id}`),
  };
};
