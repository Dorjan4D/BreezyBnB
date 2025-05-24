import React from "react";
import { Link } from "react-router-dom";

function NotFound() {
  return (
    <div>
      <h1>Page not found!</h1>
      <p>
        Got to <Link to="/">Home page</Link>
      </p>
    </div>
  );
}
export default NotFound;
