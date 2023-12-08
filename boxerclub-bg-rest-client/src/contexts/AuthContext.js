import { createContext, useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { authServiceFactory } from "../services/authServiceFactory";
import { useLocalStorage } from "../hooks/useLocalStorage";
import jwt_decode from "jwt-decode";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  // const [auth, setAuth] = useLocalStorage("auth", {});
  const [jwt, setJwt] = useLocalStorage("jwt", {});
  const [errors, setErrors] = useState({});
  const [success, setSuccess] = useState({});
  const [users, setUsers] = useState([]);
  const [activeUser, setActiveUser] = useState({});
  const [roles, setRoles] = useState([]);
  const [spinner, setSpinner] = useState(false);
  const decodeJwt = Object.keys(jwt).length !== 0 ? jwt_decode(jwt) : "";
  const authService = authServiceFactory(jwt);
  const navigate = useNavigate();
  const authorities = decodeJwt.authorities;
  const isAuthenticated =
    decodeJwt.authorities && Object.keys(jwt).length !== 0;

  useEffect(() => {
    // eslint-disable-next-line no-lone-blocks
    {
      Object.keys(jwt).length !== 0 &&
        Promise.all([getById(decodeJwt.jti)]).then(([user]) => {
          setActiveUser(user);
        });
    }
  }, [decodeJwt.jti]);

  const onLoginSubmitHandler = async (data) => {
    try {
      setErrors({});
      setSpinner(true);
      const result = await authService.login(data);

      if (result[0] === "401") {
        setErrors({ error: "Invalid credential" });
        setSuccess({});
        setSpinner(false);
      } else {
        setJwt(result[1]);
        setActiveUser(result[0]);
        setSpinner(false);
        navigate("/");
      }
    } catch (error) {
      setErrors({ error: "Invalid credential" });
    }
  };

  const onRegisterSubmitHandler = async (data) => {
    setErrors({});
    setSuccess({});

    const { confirmPassword, ...registerData } = data;
    if (confirmPassword !== registerData.password) {
      setErrors({ password: "Passwords not match!" });
      setErrors({ confirmPassword: "Passwords not match!" });
      return;
    }
    setSpinner(true);
    const result = await authService.register(data);
    setErrors({});
    setSuccess({});

    if (result[0].status === "CONFLICT") {
      setErrors(result[0].fieldErrors);
      setSuccess({});
      setSpinner(false);
    } else {
      setSpinner(false);
      setErrors({});
      setSuccess({
        message:
          "You have registered successfully, please check your email for activation",
      });
    }
  };

  const onRegisterVerifyHandler = async (data) => {
    const result = await authService.verify(data);

    if (result[0] === "401") {
      setErrors({ message: result[1] });
      setSuccess({});
    } else {
      setErrors({});
      setSuccess({
        message: "You have activated your account successfully, please login",
      });
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
      setUsers((state) => state.filter((x) => x.id !== id));
    } catch (error) {
      setErrors(error);
    }
  };

  const onUserEdit = async (data) => {
    setErrors({});
    setSuccess({});
    const { ROLE_Admin, ROLE_Member, ROLE_Moderator, ROLE_User, ...formData } =
      data;
    const roles = [];
    ROLE_Admin && roles.push({ role: "ADMIN" });
    ROLE_Moderator && roles.push({ role: "MODERATOR" });
    ROLE_Member && roles.push({ role: "MEMBER" });
    ROLE_User && roles.push({ role: "USER" });
    formData["roles"] = roles;

    try {
      setSpinner(true);
      const result = await authService.update(data.id, formData);

      if (result.description == "Username is already exist!") {
        setErrors({ email: result.description });
        setSuccess({});
        setSpinner(false);
      }
      if (result.status === "CONFLICT") {
        setErrors(result.fieldErrors);
        setSuccess({});
        setSpinner(false);
      }
      if (result.id) {
        setErrors({});
        setSpinner(false);
        setSuccess({ message: "Successfully changed details" });

        result &&
          setUsers((state) =>
            state.map((x) => (x.id === result.id ? result : x))
          );

        result.email == activeUser.email && setActiveUser(result);
      }
    } catch (error) {
      setErrors(error);
      setSpinner(false);
    }
  };
  const onForgottenPasswordSubmitHandler = async (data) => {
    setErrors({});

    const result = await authService.forgottenPassword(data);

    if (result.message == "Email was send") {
      setErrors({});
      setSpinner(false);
      setSuccess({
        message: "success",
      });
      navigate("/");
    }
    if (result.message == "Invalid email address") {
      setErrors({ email: "Invalid email address" });
      setSuccess({});
      setSpinner(false);
    }
  };

  const onForgottenPasswordNewPasswordSubmitHandler = async (data) => {
    setSuccess({});
    setErrors({});
    setSpinner(true);
    const result = await authService.forgottenPasswordNewPassword(data);

    if (result.status === "CONFLICT") {
      setErrors(result.fieldErrors);
      setSuccess({});
      setSpinner(false);
    }
    if (result[0] === "401") {
      setErrors({ message: result[1] });
      setSuccess({});
      setSpinner(false);
    }

    if (result.message === "Successfully changed password") {
      setErrors({});
      setSpinner(false);
      setSuccess({
        message: "Successfully changed password",
      });
    }
  };
  const onChangePasswordSubmitHandler = async (data) => {
    setSuccess({});
    setErrors({});
    setSpinner(true);
    const result = await authService.changePassword(data);

    if (result.status === "CONFLICT") {
      setErrors(result.fieldErrors);
      setSuccess({});
      setSpinner(false);
    }
    if (result.message === "Old password does not match!") {
      setErrors({ oldPasswordNotMatch: result.message });
      setSuccess({});
      setSpinner(false);
    }

    if (result.message === "Successfully changed password") {
      setErrors({});
      setSpinner(false);
      setSuccess({
        message: "Successfully changed password",
      });
    }
  };
  const onMembershipRequestSubmitHandler = async (data) => {
    setSuccess({});
    setErrors({});
    setSpinner(true);
    const result = await authService.onMembershipRequest(data);
    if (result[0] === "404") {
      setErrors({ email: "Invalid request" });
      setSpinner(false);
    } else {
      setSpinner(false);
    }
  };

  const onLogoutHandler = () => {
    setSuccess({});
    setErrors({});
    //  setAuth({});
    setActiveUser({});
    setJwt({});
  };
  const clear = () => {
    setErrors({});
    setSuccess({});
  };

  const getById = async (id) => {
    const result = await authService.find(id);
    return result;
  };

  const context = {
    onRegisterSubmitHandler,
    onRegisterVerifyHandler,
    onLoginSubmitHandler,
    onForgottenPasswordSubmitHandler,
    onForgottenPasswordNewPasswordSubmitHandler,
    onChangePasswordSubmitHandler,
    onMembershipRequestSubmitHandler,
    onLogoutHandler,
    onGetAllUsersHandler,
    onGetAllRoles,
    onUserDelete,
    onUserEdit,
    getById,
    clear,
    errors,
    spinner,
    activeUser,
    success,
    roles,
    users,
    userId: decodeJwt.jti,
    token: jwt,
    email: decodeJwt.sub,
    authorities,
    isAuthenticated,
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
