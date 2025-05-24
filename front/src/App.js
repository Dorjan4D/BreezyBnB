import "./App.css";
import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider,
} from "react-router-dom";
import Home from "./components/Home";
import RootLayout from "../src/Layouts/RootLayout.jsx";
import NotFound from "./components/NotFound.jsx";
import Login from "./components/Login.jsx";
import {
  AuthContext,
  AuthProvider,
} from "./components/AuthenticationContext.jsx";
import { useState } from "react";
import Registration from "./components/Registration.jsx";

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<RootLayout />}>
      <Route index element={<Home />} />
      <Route path="login" element={<Login />} />
      <Route path="registration" element={<Registration />} />
      <Route path="*" element={<NotFound />} /> {/*error page */}
    </Route>
  )
);

function App() {
  const [user, setUser] = useState({
    isAuth: false,
    isAdmin: false,
    isHost: false,
    isCustomer: false,
    userID: null,
    firstName: "",
    lastName: "",
  });

  const updateUser = (newUserData) => {
    setUser((prevUser) => ({ ...prevUser, ...newUserData }));
  };
  return (
    <AuthContext.Provider value={{ user, updateUser }}>
      <div className="App">
        <RouterProvider router={router} />
      </div>
    </AuthContext.Provider>
  );
}

export default App;
