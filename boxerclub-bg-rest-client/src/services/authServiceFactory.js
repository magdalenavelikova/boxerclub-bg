import { requestFactory } from "./requester";

const baseUrl = "http://localhost:8080/users";

export const authServiceFactory = (token) => {
  const request = requestFactory(token);

  return {
    register: (data) => request.post(`${baseUrl}/register`, data),
    verify: (data) =>
      request.get(`${baseUrl}/registrationConfirm?token=${data}`),
    login: (loginData) => request.post(`${baseUrl}/login`, loginData),
    getAll: () => request.get(`${baseUrl}`),
    getAllRoles: () => request.get(`${baseUrl}/roles`),
    remove: (id) => request.remove(`${baseUrl}/${id}`),
    update: (id, data) => request.patch(`${baseUrl}/${id}`, data),
    find: (id) => request.get(`${baseUrl}/${id}`),
  };
};
