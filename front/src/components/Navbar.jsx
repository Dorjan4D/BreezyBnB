import { Link, useNavigate } from "react-router";
import "./Navbar.css";
import { AuthContext } from "./AuthenticationContext";
import { useContext } from "react";
function Navbar() {
  const { user, updateUser } = useContext(AuthContext);
  const navigate = useNavigate();
  const eventHandler = () => {
    fetch("http://localhost:8080/logout", {
      method: "POST",
      credentials: "include",
    })
      .then(() => {
        updateUser({
          isAuth: false,
          isCustomer: false,
          isHost: false,
          isAdmin: false,
        });
        navigate("/");
      })
      .catch((err) => {
        console.error("Logout failed", err);
      });
  };
  return (
    <>
      <div className="navbar-container">
        <div className="navbar-inner-container">
          <Link to="/">
            <div className="element-container">POČETNA</div>
          </Link>
          {!user.isAuth && (
            <Link to="/login">
              <div className="element-container">PRIJAVA</div>
            </Link>
          )}
          {!user.isAuth && (
            <Link to="/registration">
              <div className="element-container">REGISTRACIJA</div>
            </Link>
          )}
          {user.isHost && (
            <Link to="/MyAds">
              <div className="element-container">MOJI SMJEŠTAJI</div>
            </Link>
          )}
          {user.isAdmin && (
            <Link to="/postavke">
              <div className="element-container">POSTAVKE</div>
            </Link>
          )}
          {user.isAuth && (
            <button className="odjava-button" onClick={eventHandler}>
              ODJAVA
            </button>
          )}
        </div>
      </div>
    </>
  );
}

export default Navbar;
