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
import Ad_detail from "./components/Ad_detail.jsx";
import MyAds from "./components/MyAds.jsx";
import { NewAd } from "./components/NewAd.jsx";
import { EditAd } from "./components/EditAd.jsx";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";

const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<RootLayout />}>
      <Route index element={<Home />} />
      <Route path="login" element={<Login />} />
      <Route path="registration" element={<Registration />} />
      <Route path=":id" element={<Ad_detail />} />
      <Route path="/MyAds" element={<MyAds />} />
      <Route path="newAd" element={<NewAd />} />
      <Route path="/updateAd" element={<EditAd />} />
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
