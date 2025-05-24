import { useContext, useState } from "react";
import "./Login.css";
import {
  Form,
  Link,
  Navigate,
  useActionData,
  useNavigate,
} from "react-router-dom";
import { redirect } from "react-router-dom";
import { AuthContext } from "./AuthenticationContext.jsx";

let isPending = false;
function setIsPending(value) {
  isPending = value;
}

function Login() {
  const { user, updateUser } = useContext(AuthContext);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const loginAction = async (event) => {
    event.preventDefault();

    //getting form data and turning it into object
    const data = new FormData(event.target);

    const submission = {
      username: data.get("username"),
      password: data.get("password"),
    };
    setIsPending(true);
    const loginData = new URLSearchParams();
    loginData.append("username", submission.username);
    loginData.append("password", submission.password);

    const jsonData = {
      username: submission.username,
      password: submission.password,
    };
    //send post request with fetch
    //TODO fix route to one that exists
    fetch("http://localhost:8080/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      credentials: "include",
      body: loginData,
    })
      .then((res) => {
        console.log(submission);
        setIsPending(false);
        if (!res.ok) {
          setError("Krivi username ili password");
          //return { error: "Krivi username ili password" };
        }
        return res.text();
      })
      .then((text) => {
        console.log(text);
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
        setError(text);
        //dodati za tip korisnika true/false -----------------------------------------------------------------
      })
      .catch((ex) => {
        console.log(ex);
        setError("Nevaljani username ili password");
        return;
      });
  };

  if (user.isAuth) {
    return <Navigate to="/" />;
  }

  return (
    <div className="login-container">
      <section className="login-window">
        <h1 className="login-headline">Prijava</h1>
        <Form onSubmit={loginAction}>
          <div className="form-floating ">
            <input
              type="text"
              className="form-control"
              id="floatingInput"
              placeholder="Username"
              name="username"
              required
            />
            <label htmlFor="floatingInput">Username</label>
          </div>
          <div className="form-floating">
            <input
              type="password"
              className="form-control"
              id="floatingPassword"
              placeholder="Password"
              name="password"
              required
            />
            <label htmlFor="floatingPassword">Password</label>
          </div>
          <div className="button-container">
            {!isPending && (
              <button className="btn" id="btn">
                Prijavi se
              </button>
            )}
            {isPending && (
              <button className="btn" id="btn" disabled>
                Prijava...
              </button>
            )}
          </div>

          <p className="nisiregistriran">
            {" "}
            Nisi registriran?{" "}
            <Link to="/registration" id="link">
              Registriraj se
            </Link>
          </p>

          <p id="login-error">{error}</p>
        </Form>
      </section>
    </div>
  );
}

export default Login;
