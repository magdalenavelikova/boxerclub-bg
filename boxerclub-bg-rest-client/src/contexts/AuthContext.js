import { createContext, useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { authServiceFactory } from "../services/authServiceFactory";
import { useLocalStorage } from "../hooks/useLocalStorage";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useLocalStorage("auth", {});
  const [jwt, setJwt] = useLocalStorage("jwt", {});
  const [errors, setErrors] = useState({});
  const [users, setUsers] = useState([]);
  const [roles, setRoles] = useState([]);
  const authService = authServiceFactory(jwt);
  const navigate = useNavigate();

  const onLoginSubmitHandler = async (data) => {
    try {
      setErrors({});
      const result = await authService.login(data);
      setAuth(result[0]);
      setJwt(result[1]);
      navigate("/");
    } catch (error) {
      setErrors({ error: "Invalid credential" });
    }
  };

  const onRegisterSubmitHandler = async (data) => {
    setErrors({});
    const { confirmPassword, ...registerData } = data;
    if (confirmPassword !== registerData.password) {
      setErrors({ password: "Passwords not match!" });
      setErrors({ confirmPassword: "Passwords not match!" });
      return;
    }

    const result = await authService.register(data);
    setErrors({});
    if (result[0].status === "BAD_REQUEST") {
      setErrors(result[0].fieldErrors);
    } else {
      setAuth(result[0]);
      setJwt(result[1]);
      setErrors({});
      navigate("/dogs");
    }
  };

  const onGetAllUsersHandler = async () => {
    try {
      const result = await authService.getAll();
      setUsers(result);
    } catch (error) {
      setErrors(error);
    }
  };
  const onGetAllRoles = async () => {
    try {
      const result = await authService.getAllRoles();
      let arrRoles = [];
      Object.values(result).forEach((obj) => {
        for (const [key, value] of Object.entries(obj)) {
          arrRoles.push(value);
        }
      });

      setRoles(arrRoles);
    } catch (error) {
      setErrors(error);
    }
  };

  const onUserDelete = async (id) => {
    try {
      await authService.remove(id);
    } catch (error) {
      setErrors(error);
    }
    setUsers((state) => state.filter((x) => x.id !== id));
  };
  const onUserEdit = (data) => {
    console.log(data);
  };

  const onLogoutHandler = () => {
    setAuth({});
    setJwt({});
  };

  const context = {
    onRegisterSubmitHandler,
    onLoginSubmitHandler,
    onLogoutHandler,
    onGetAllUsersHandler,
    onGetAllRoles,
    onUserDelete,
    onUserEdit,
    errors,
    roles,
    users,
    userId: auth._id,
    token: jwt,
    email: auth.email,
    fullName: auth.fullName,
    authorities: auth.authorities,
    isAuthenticated: auth.authorities && Object.keys(jwt).length !== 0,
  };

  return (
    <>
      <AuthContext.Provider value={context}>{children}</AuthContext.Provider>
    </>
  );
};
export const useAuthContext = () => {
  const context = useContext(AuthContext);
  return context;
};
