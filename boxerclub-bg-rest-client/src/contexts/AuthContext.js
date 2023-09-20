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
  const authService = authServiceFactory(auth.accessToken);
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

    try {
      const result = await authService.register(data);

      if (result[0].status === "BAD_REQUEST") {
        setErrors(result[0].fieldErrors);
      } else {
        setAuth(result[0]);
        setJwt(result[1]);
        setErrors({});
        navigate("/dogs");
      }
    } catch (error) {
      console.log("Error");
    }
  };
  const onGetAllHandler = async () => {
    try {
      const result = await authService.getAll();
      setUsers(result[0]);
    } catch (error) {
      console.log("Error");
    }
  };

  const onUserDeleteClick = () => {};
  const onUserEditClick = () => {};

  const onLogoutHandler = () => {
    setAuth({});
    setJwt({});
  };

  const context = {
    onRegisterSubmitHandler,
    onLoginSubmitHandler,
    onLogoutHandler,
    onGetAllHandler,
    onUserDeleteClick,
    onUserEditClick,
    errors,
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
