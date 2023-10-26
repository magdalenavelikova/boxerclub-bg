import { Routes, Route } from "react-router-dom";
import "./App.css";
import { Home } from "./components/Home/Home";
import { Navigation } from "./components/Navigation/Navigation";
import { FooterComponent } from "./components/Footer/Footer";
import { RegulationEN } from "./components/Regulation/RegulationEN";
import { Profile } from "./components/Profile/Profile";
import { AuthTab } from "./components/Tabs/AuthTab";
import { AuthProvider } from "./contexts/AuthContext";
import { useState } from "react";
import { LogoutPage } from "./components/Logout/Logout";
import { Users } from "./components/Users/Users";
import { NewDog } from "./components/Dog/NewDog";
import { ParentDog } from "./components/Dog/ParentDog";
import { DogProvider } from "./contexts/DogContext";

import { Dogs } from "./components/Dog/Dogs";
import { EditDog } from "./components/Dog/EditDog";
import { RegisterConfirm } from "./components/Register/RegisterConfirm";

import { LinksTab } from "./components/Tabs/LinksTab";
import { LinkProvider } from "./contexts/LinkContext";
import { NewLink } from "./components/Link/NewLink";
import { RegulationBG } from "./components/Regulation/RegulationBG";
import { ContactProvider } from "./contexts/ContactContext";
import { Contacts } from "./components/Contact/Contacts";

function App() {
  const lang = localStorage.getItem("lang");
  const [regulation, setRegulation] = useState([]);
  const [eventLink, setEventLink] = useState([]);
  console.log(lang);
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
          <ContactProvider>
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
              <Route path='dogs/edit' element={<EditDog />} />

              <Route
                path='/regulations'
                element={
                  lang === "en" ? (
                    <RegulationEN regulation={regulation} />
                  ) : (
                    <RegulationBG regulation={regulation} />
                  )
                }
              />

              <Route
                path='/links'
                element={<LinksTab eventLink={eventLink} />}
              />
              <Route path='/links/add' element={<NewLink />} />
              <Route path='/contacts' element={<Contacts />} />
            </Routes>

            <FooterComponent />
          </ContactProvider>
        </LinkProvider>
      </DogProvider>
    </AuthProvider>
  );
}

export default App;
