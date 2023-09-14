import { createContext, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { authServiceFactory } from "../services/authServiceFactory";
import { useLocalStorage } from "../hooks/useLocalStorage";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useLocalStorage("auth", {});
  const [jwt, setJwt] = useLocalStorage("jwt", {});
  const authService = authServiceFactory(auth.accessToken);
  const navigate = useNavigate();

  const onLoginSubmitHandler = async (data) => {
    try {
      const result = await authService.login(data);
      setAuth(result[0]);
      setJwt(result[1]);
      navigate("/");
    } catch (error) {
      console.log("There is no such user!");
    }
    //  console.log(Object.fromEntries(new FormData(e.target)));
  };

  const onRegisterSubmitHandler = async (data) => {
    const { confirmPassword, ...registerData } = data;
    if (confirmPassword !== registerData.password) {
      console.log("Passwords not match!");
      return;
    }
    try {
      const result = await authService.register(registerData);
      setAuth(result[0]);
      setJwt(result[1]);
      navigate("/dogs");
    } catch (error) {
      console.log("Error");
    }
  };

  const onLogoutHandler = async () => {
    try {
      await authService.logout();
      setAuth({});
      setJwt({});
    } catch (error) {
      console.log("Error");
    }
  };

  const context = {
    onRegisterSubmitHandler,
    onLoginSubmitHandler,
    onLogoutHandler,
    userId: auth._id,
    token: jwt,
    email: auth.email,
    fullName: auth.fullName,
    authorities: auth.authorities,
    isAuthenticated: !!jwt,
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
