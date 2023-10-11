import { Routes, Route } from "react-router-dom";
import "./App.css";
import { Home } from "./components/Home/Home";
import { Navigation } from "./components/Navigation/Navigation";
import { FooterComponent } from "./components/Footer/Footer";
import { Regulation } from "./components/Regulation/Regulation";
import { Profile } from "./components/Profile/Profile";
import { AuthTab } from "./components/Tabs/AuthTab";
import { AuthProvider } from "./contexts/AuthContext";
import { useState } from "react";
import { LogoutPage } from "./components/Logout/Logout";
import { Users } from "./components/Users/Users";
import { NewDog } from "./components/Dogs/NewDog";
import { ParentDog } from "./components/Dogs/ParentDog";
import { DogProvider } from "./contexts/DogContext";
import { UploadPedigree } from "./components/Dogs/UploadPedigree";
import { Dogs } from "./components/Dogs/Dogs";
import { EditDog } from "./components/Dogs/EditDog";
import { RegisterConfirm } from "./components/Register/RegisterConfirm";

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
          <Route path='users/login' element={<AuthTab />} />
          <Route
            path='users/registrationConfirm?'
            element={<RegisterConfirm />}
          />

          <Route path='users/profile' showModal={true} element={<Profile />} />
          <Route path='users/logout' element={<LogoutPage />} />
          <Route path='users/all' element={<Users />} />
          <Route path='dogs' element={<Dogs />} />
          <Route path='dogs/register' element={<NewDog />} />
          <Route path='dogs/register/parents' element={<ParentDog />} />
          <Route path='dogs/pedigree/upload' element={<UploadPedigree />} />
          <Route path='dogs/edit' element={<EditDog />} />

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
