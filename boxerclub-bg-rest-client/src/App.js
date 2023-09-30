import { Routes, Route } from "react-router-dom";
import "./App.css";
import { Home } from "./components/Home/Home";
import { Navigation } from "./components/Navigation/Navigation";
import { FooterComponent } from "./components/Footer/Footer";
import { Regulation } from "./components/Regulation/Regulation";
import { LoginPage } from "./components/Login/Login";
import { RegisterPage } from "./components/Register/Register.js";
import { AuthProvider } from "./contexts/AuthContext";
import { useState } from "react";
import { LogoutPage } from "./components/Logout/Logout";
import { Users } from "./components/Users/Users";
import { NewDog } from "./components/Dogs/NewDog";
import { ParentDog } from "./components/Dogs/ParentDog";
import { DogProvider } from "./contexts/DogContext";
import { UploadPedigree } from "./components/Dogs/UploadPedigree";

function App() {
  const [regulation, setRegulation] = useState([]);
  const onClickRegulation = (regulation) => {
    setRegulation(regulation);
  };

  return (
    <AuthProvider>
      <DogProvider>
        <Navigation onClickRegulation={onClickRegulation} />

        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='users/login' element={<LoginPage />} />
          <Route path='users/register' element={<RegisterPage />} />
          <Route path='users/logout' element={<LogoutPage />} />
          <Route path='users/all' element={<Users />} />

          <Route path='dogs/register' element={<NewDog />} />
          <Route path='dogs/register/parents' element={<ParentDog />} />
          <Route path='dogs/pedigree/upload' element={<UploadPedigree />} />

          <Route
            path='/regulations'
            element={<Regulation regulation={regulation} />}
          />
        </Routes>

        <FooterComponent />
      </DogProvider>
    </AuthProvider>
  );
}

export default App;
