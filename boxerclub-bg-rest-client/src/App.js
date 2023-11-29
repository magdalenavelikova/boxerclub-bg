import { Routes, Route, useNavigate } from "react-router-dom";
import "./App.css";
import { Home } from "./components/Home/Home";
import { Navigation } from "./components/Navigation/Navigation";
import { FooterComponent } from "./components/Footer/Footer";
import { RegulationEN } from "./components/Regulation/RegulationEN";
import { Profile } from "./components/Profile/Profile";
import { AuthTab } from "./components/Tabs/AuthTab";
import { AuthProvider } from "./contexts/AuthContext";
import { useContext, useEffect, useState } from "react";
import { LogoutPage } from "./components/Logout/Logout";
import { Users } from "./components/Users/Users";
import { NewDog } from "./components/Dog/NewDog";
import { ParentDog } from "./components/Dog/ParentDog";
import { DogContext, DogProvider } from "./contexts/DogContext";

import { Dogs } from "./components/Dog/Dogs";
import { EditDog } from "./components/Dog/EditDog";
import { RegisterConfirm } from "./components/Register/RegisterConfirm";

import { LinksTab } from "./components/Tabs/LinksTab";
import { LinkProvider } from "./contexts/LinkContext";
import { NewLink } from "./components/Link/NewLink";
import { RegulationBG } from "./components/Regulation/RegulationBG";
import { ContactProvider } from "./contexts/ContactContext";
import { Contacts } from "./components/Contact/Contacts";
import { NewContact } from "./components/Contact/NewContact";
import { EventProvider } from "./contexts/EventContext";
import { EventsTabPassed } from "./components/Tabs/EventTabPassed";
import { EventsTabUpcoming } from "./components/Tabs/EventTabUpcoming";
import { NewEvent } from "./components/Event/NewEvent";
import { Gallery } from "./components/Gallery/Gallery";
import { DogDetails } from "./components/Dog/DogDetails";
import { Maintenance } from "./components/Maintenance/Maintenance";
import { ChangeOwner } from "./components/Dog/ChangeOwner";
import { ChangeOwnerConfirm } from "./components/Dog/ChangeOwnerConfirm";
import DogChartTree from "./components/Dog/DogChartTree";

import { ForgottenPasswordModal } from "./components/Modal/ForgottenPasswordModal";
import { NewPasswordForgottenPassword } from "./components/Users/NewPasswordForgottenPassword";
import { ActOfIncorporation } from "./components/Regulation/ActOfIncorporation";
import { BreedingRules } from "./components/Regulation/BreedingRules";
import { Membership } from "./components/Membership/Membership";

function App() {
  const lang = localStorage.getItem("lang");

  const [regulation, setRegulation] = useState([]);
  const [eventLink, setEventLink] = useState([]);

  const onRegulationClick = (regulation) => {
    setRegulation(regulation);
  };
  const onLinkClick = (eventLink) => {
    setEventLink(eventLink);
  };
  const onEventClick = (eventLink) => {
    setEventLink(eventLink);
  };

  return (
    <AuthProvider>
      <DogProvider>
        <LinkProvider>
          <ContactProvider>
            <EventProvider>
              <Navigation
                onRegulationClick={onRegulationClick}
                onLinkClick={onLinkClick}
                onEventClick={onEventClick}
              />

              <Routes>
                <Route path='/maintenance' element={<Maintenance />} />
                <Route path='/' element={<Home />} />
                <Route path='users/login' element={<AuthTab />} />
                <Route
                  path='users/registerConfirm?'
                  element={<RegisterConfirm />}
                />
                <Route
                  path='users/profile'
                  showModal={true}
                  element={<Profile />}
                />
                <Route path='users/logout' element={<LogoutPage />} />
                <Route
                  path='users/forgotten-password'
                  element={<ForgottenPasswordModal />}
                />
                <Route
                  path='users/forgotten-password/new-password'
                  element={<NewPasswordForgottenPassword />}
                />
                <Route path='users/membership' element={<Membership />} />
                <Route path='users/all' element={<Users />} />
                <Route path='dogs' element={<Dogs />} />
                <Route path='dogs/register' element={<NewDog nb={false} />} />
                <Route path='dogs/register/nb' element={<NewDog nb={true} />} />
                <Route path='dogs/ownership' element={<ChangeOwner />} />
                <Route
                  path='dogs/ownershipConfirm?'
                  element={<ChangeOwnerConfirm />}
                />

                <Route path='dogs/register/parents' element={<ParentDog />} />
                <Route path='dogs/edit' element={<EditDog />} />
                <Route path='dogs/details' element={<DogDetails />} />
                <Route path='dogs/chart' element={<DogChartTree />} />

                <Route
                  path='regulations'
                  element={
                    lang === "en" ? (
                      <RegulationEN regulation={regulation} />
                    ) : (
                      <RegulationBG regulation={regulation} />
                    )
                  }
                />
                <Route
                  path='regulations/breeding-rules'
                  element={<BreedingRules />}
                />
                <Route
                  path='regulations/act-of-incorporation'
                  element={<ActOfIncorporation />}
                />
                <Route
                  path='/links'
                  element={<LinksTab eventLink={eventLink} />}
                />
                <Route path='/links/add' element={<NewLink />} />
                <Route path='/contacts' element={<Contacts />} />
                <Route path='/contacts/add' element={<NewContact />} />
                <Route
                  path='/events/upcoming'
                  element={<EventsTabUpcoming eventLink={eventLink} />}
                />
                <Route
                  path='/events/passed'
                  element={<EventsTabPassed eventLink={eventLink} />}
                />
                <Route path='/events/add' element={<NewEvent />} />
                <Route path='/gallery' element={<Gallery />} />
              </Routes>

              <FooterComponent />
            </EventProvider>
          </ContactProvider>
        </LinkProvider>
      </DogProvider>
    </AuthProvider>
  );
}

export default App;
