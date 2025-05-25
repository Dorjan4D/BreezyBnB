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
import { useEffect, useState } from "react";
import Registration from "./components/Registration.jsx";
import Ad_detail from "./components/Ad_detail.jsx";
import MyAds from "./components/MyAds.jsx";
import { NewAd } from "./components/NewAd.jsx";
import { EditAd } from "./components/EditAd.jsx";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import Postavke from "./components/Postavke.jsx";

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
      <Route path="/postavke" element={<Postavke />} />
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
  useEffect(() => {
    if (!user.isAuth) {
      fetch("http://localhost:8080/checkUserType", {
        method: "GET",
        credentials: "include",
      })
        .then((res) => {
          if (!res.ok) {
            updateUser({
              isAuth: false,
              isAdmin: false,
              isHost: false,
              isCustomer: false,
            });
            throw Error("lol");
          }
          return res.text();
        })
        .then((text) => {
          if (text == "customer") {
            updateUser({
              isCustomer: true,
              isAuth: true,
            });
          } else if (text == "host") {
            updateUser({
              isHost: true,
              isAuth: true,
            });
          } else if (text == "admin") {
            updateUser({
              isAdmin: true,
              isAuth: true,
            });
          }
        })
        .catch((error) => {
          console.error("Login fetch failed:", error);
        });
    }
  }, []);
  return (
    <AuthContext.Provider value={{ user, updateUser }}>
      <div className="App">
        <RouterProvider router={router} />
      </div>
    </AuthContext.Provider>
  );
}

export default App;
