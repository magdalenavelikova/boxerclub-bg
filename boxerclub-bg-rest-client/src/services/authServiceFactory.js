import { requestFactory } from "./requester";

const baseUrl = "http://localhost:8080/users";

export const authServiceFactory = (token) => {
  const request = requestFactory(token);

  return {
    register: (data) => request.post(`${baseUrl}/register`, data),
    logout: () => request.get(`${baseUrl}/logout`),
    login: (loginData) => request.post(`${baseUrl}/login`, loginData),
  };
};
