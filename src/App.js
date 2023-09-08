import { Routes, Route } from "react-router-dom";
import "./App.css";
import { Home } from "./components/Home/Home";
import { Navigation } from "./components/Navigation/Navigation";
import { FooterComponent } from "./components/Footer/Footer";
import { Regulation } from "./components/Regulation/Regulation";
import { LoginPage } from "./components/Login/Login";
import { RegisterPage } from "./components/Register/Register.js";
import { AuthProvider } from "./contexts/AuthContext";

function App() {
  return (
    <AuthProvider>
      <div id='box'>
        <Navigation />
        <main id='main-content'>
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/login' element={<LoginPage />} />
            <Route path='/register' element={<RegisterPage />} />
            <Route
              path='/regulations/standard'
              element={<Regulation anchor={"standard"} />}
            />
            <Route
              path='/regulations/IPO1'
              element={<Regulation anchor={"IPO1"} />}
            />
            <Route
              path='/regulations/IPO2'
              element={<Regulation anchor={"IPO2"} />}
            />
            <Route
              path='/regulations/IPO3'
              element={<Regulation anchor={"IPO3"} />}
            />
            <Route
              path='/regulations/BH'
              element={<Regulation anchor={"BH"} />}
            />
            <Route
              path='/regulations/AD'
              element={<Regulation anchor={"AD"} />}
            />
          </Routes>
        </main>
        <FooterComponent />
      </div>
    </AuthProvider>
  );
}

export default App;
