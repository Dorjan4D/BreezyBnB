import { Link } from "react-router";
import "./Navbar.css";
function Navbar() {
  return (
    <>
      <div className="navbar-container">
        <div className="navbar-inner-container">
          <Link to="/">
            <div className="element-container">POČETNA</div>
          </Link>
          <Link to="/login">
            <div className="element-container">PRIJAVA</div>
          </Link>
          <Link to="/registration">
            <div className="element-container">REGISTRACIJA</div>
          </Link>
        </div>
      </div>
    </>
  );
}

export default Navbar;
