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
import { NewDog } from "./components/Dog/NewDog";
import { ParentDog } from "./components/Dog/ParentDog";
import { DogProvider } from "./contexts/DogContext";
import { UploadPedigree } from "./components/Dog/UploadPedigree";
import { Dogs } from "./components/Dog/Dogs";
import { EditDog } from "./components/Dog/EditDog";
import { RegisterConfirm } from "./components/Register/RegisterConfirm";
import { UploadParentsPedigree } from "./components/Dog/UploadParentsPedigree";
import { LinksTab } from "./components/Tabs/LinksTab";
import { LinkProvider } from "./contexts/LinksContext";

function App() {
  const [regulation, setRegulation] = useState([]);
  const [eventLink, setEventLink] = useState([]);

  const onRegulationClick = (regulation) => {
    setRegulation(regulation);
  };
  const onLinkClick = (eventLink) => {
    setEventLink(eventLink);
  };

  return (
    <AuthProvider>
      <DogProvider>
        <LinkProvider>
          <Navigation
            onRegulationClick={onRegulationClick}
            onLinkClick={onLinkClick}
          />

          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='users/login' element={<AuthTab />} />
            <Route
              path='users/registrationConfirm?'
              element={<RegisterConfirm />}
            />

            <Route
              path='users/profile'
              showModal={true}
              element={<Profile />}
            />
            <Route path='users/logout' element={<LogoutPage />} />
            <Route path='users/all' element={<Users />} />
            <Route path='dogs' element={<Dogs />} />
            <Route path='dogs/register' element={<NewDog nb={false} />} />
            <Route path='dogs/register/nb' element={<NewDog nb={true} />} />
            <Route path='dogs/register/parents' element={<ParentDog />} />
            <Route path='dogs/pedigree/upload' element={<UploadPedigree />} />
            <Route
              path='dogs/pedigree/parents/upload'
              element={<UploadParentsPedigree />}
            />
            <Route path='dogs/edit' element={<EditDog />} />

            <Route
              path='/regulations'
              element={<Regulation regulation={regulation} />}
            />

            <Route path='/links' element={<LinksTab eventLink={eventLink} />} />
          </Routes>

          <FooterComponent />
        </LinkProvider>
      </DogProvider>
    </AuthProvider>
  );
}

export default App;
