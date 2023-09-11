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
function App() {
  const [regulation, setRegulation] = useState([]);
  const onClickRegulation = (regulation) => {
    setRegulation(regulation);
  };

  return (
    <AuthProvider>
      <Navigation onClickRegulation={onClickRegulation} />

      <Routes>
        <Route path='/' element={<Home />} />
        <Route path='/login' element={<LoginPage />} />
        <Route path='/register' element={<RegisterPage />} />

        <Route
          path='/regulations'
          element={<Regulation regulation={regulation} />}
        />
      </Routes>

      <FooterComponent />
    </AuthProvider>
  );
}

export default App;
