import React, { useContext, useEffect, useState } from "react";
import { Form, Navigate, redirect, useNavigate } from "react-router-dom";
import "./Registration.css";
import { AuthContext } from "./AuthenticationContext.jsx";

export default function Registration() {
  const [files, setFiles] = useState();
  const [preview, setPreview] = useState();
  const { user, updateUser } = useContext(AuthContext);
  const [images, setImages] = useState("");
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const [lat, setLat] = useState();
  const [lng, setLng] = useState();
  const [color, setColor] = useState("");
  const [endpoint, setEndpoint] = useState("");

  const [typeOfuser, setTypeOfUser] = useState("Kupac");

  useEffect(() => {
    //if (!user.isAuth) {
    //  navigate("/");
    //}
    if (!files) return;

    let tmp = [];
    for (let i = 0; i < files.length; i++) {
      tmp.push(URL.createObjectURL(files[i]));
    }
    const objectUrls = tmp;
    setPreview(objectUrls);
    for (let i = 0; i < objectUrls.length; i++) {
      return () => {
        URL.revokeObjectURL(objectUrls[i]);
      };
    }
  }, [files]);

  const getBase64 = (file) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        resolve(reader.result);
      };
      reader.onerror = reject;
    });
  };

  // usage

  const createUser = async (event) => {
    event.preventDefault();
    //getting form data and turning it into object
    const data = new FormData(event.target);
    let images2 = "";
    setError("");
    if (!files) {
      setError("Stavite barem jednu sliku");
      return;
    } else if (files.length > 1) {
      setError("Maksimalno 1 slika");
      return;
    }

    const base64 = await getBase64(files[0]); // `file` your img file
    images2 = base64.split(",").pop();

    if (files.length >= 2) {
      const base6423 = await getBase64(files[1]); // `file` your img file
      images2 = images2 + "," + base6423.split(",").pop();
    }

    if (files.length == 3) {
      const base6424 = await getBase64(files[2]); // `file` your img file
      images2 = images2 + "," + base6424.split(",").pop();
    }

    const submission = {
      username: data.get("username"),
      password: data.get("password"),
      email: data.get("email"),
      broj: data.get("broj"),
      ime: data.get("ime"),
      prezime: data.get("prezime"),
      typeOfUser: data.get("typeOfUser"),
      spol: data.get("spol"),
      DOB: data.get("DOB"),
      slika: images2,
    };
    console.log(submission);

    if (submission.username.length < 4) {
      setError("Username mora sadržavati barem 4 znaka");
      return;
    } else if (submission.password.length < 6) {
      setError("Password mora imati barem 6 znaka");
      return;
    } else if (
      !/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/.test(submission.email)
    ) {
      setError("Nevaljan email");
      return;
    } else if (
      !(isNaN(submission.broj) === false) ||
      submission.broj.length != 10
    ) {
      setError("Nevaljan broj telefona");
      return;
    } else if (!/^[a-zA-ZšŠđĐčČćĆžŽ\s]+$/.test(submission.ime)) {
      setError("Nevaljano ime");
      return;
    } else if (!/^[a-zA-ZšŠđĐčČćĆžŽ\s]+$/.test(submission.prezime)) {
      setError("Nevaljano prezime");
      return;
    } else if (!/^\w+$/.test(submission.username)) {
      setError("Nevaljani username");
      return;
    } else if (
      !/^(?=.*[0-9])(?=.*[!@#$%^&*])[\w!@#$%^&*]{8,16}$/.test(
        submission.password
      )
    ) {
      setError("Nevaljani password");
      return;
    }
    setError("");

    const formData = new FormData();
    formData.append("username", submission.username);
    formData.append("name", submission.ime);
    formData.append("surname", submission.prezime);
    formData.append("email", submission.email);
    formData.append("password", submission.password);
    formData.append("gender", submission.spol.toUpperCase());
    formData.append("dateOfBirth", submission.DOB);
    formData.append("photo", submission.slika);
    if (submission.typeOfUser == "Vlasnik") {
      formData.append("contactPhone", String(submission.broj));
    }
    if (submission.typeOfUser == "Vlasnik") {
      setEndpoint("host");
    }
    if (submission.typeOfUser == "Admin") {
      setEndpoint("admin");
    }
    if (submission.typeOfUser == "Kupac") {
      setEndpoint("customer");
    }

    fetch("https://localhost:8080/register/" + endpoint, {
      method: "POST",
      body: formData,
      credentials: "include",
    }).then((res) => {
      if (res.ok) {
        navigate("/login");
      } else {
        setError("Korsinik već postoji");
      }
    });
  };

  return (
    <div className="createAd-container">
      <h1 className="createAd-headline">Registracija</h1>
      <Form className="createAd-window" onSubmit={createUser}>
        <div className="form-group">
          <label htmlFor="img">Slika</label>
          <input
            type="file"
            accept="image/*"
            multiple
            id="img"
            name="img"
            onChange={(e) => {
              setFiles(e.target.files);
            }}
          />
        </div>
        <div className="form-group">
          {preview &&
            preview.map((pic) => (
              <img src={pic} key={pic} className="createAd-img" />
            ))}
        </div>

        <div className="form-group">
          <label htmlFor="ime">Ime</label>
          <input
            type="text"
            className="form-control"
            id="ime"
            name="ime"
            required
            placeholder="Ime"
          />
        </div>
        <div className="form-group">
          <label htmlFor="prezime">Prezime</label>
          <input
            type="text"
            className="form-control"
            id="prezime"
            name="prezime"
            required
            placeholder="Prezime"
          />
        </div>
        <div className="form-group">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            className="form-control"
            id="username"
            name="username"
            required
            placeholder="Username"
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            className="form-control"
            id="password"
            name="password"
            required
            placeholder="Password"
          />
        </div>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="text"
            className="form-control"
            id="email"
            name="email"
            required
            placeholder="Email"
          />
        </div>

        {/* <div className="form-group">
              <label htmlFor="boja">Boja</label>
              <select className="form-control" id="boja" name='boja'>
              {colors.map((specie) => (
              <option key={specie} value={specie}>
                  {specie}
              </option>
              ))}
              </select>
          </div> */}

        <div className="form-group">
          <label htmlFor="spol">Spol</label>
          <select
            className="form-control"
            id="spol"
            name="spol"
            maxmenuheight={2}
            menuplacement="auto"
          >
            <option value="Male">Male</option>
            <option value="Female">Female</option>
            <option value="Other">Other</option>
            {/*age.map((specie) => (
              <option key={specie} value={specie}>
                {specie}
              </option>
            ))*/}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="DOB">Datum rođenja</label>
          <input
            type="date"
            className="form-control"
            id="DOB"
            name="DOB"
            required
          ></input>
        </div>
        <label htmlFor="typeOfUser">Vrsta korisnika </label>

        <select
          id="typeOfUser"
          value={typeOfuser}
          className="form-control"
          onChange={(e) => setTypeOfUser(e.target.value)}
          name="typeOfUser"
          required
        >
          <option value="Kupac">Kupac</option>
          <option value="Vlasnik">Vlasnik</option>
          <option value="Admin">Admin</option>
        </select>

        {typeOfuser == "Kupac" && <div></div>}
        {typeOfuser == "Admin" && <div></div>}
        {typeOfuser == "Vlasnik" && (
          <div className="form-group">
            <label htmlFor="floatingPhoneNumber">Broj telefona</label>
            <input
              type="text"
              className="form-control"
              id="floatingPhoneNumber"
              placeholder="Broj telefona"
              name="broj"
              required
            />
          </div>
        )}

        <div className="text-center">
          <button className="btn btn-primary" id="btn-createAd">
            Registriraj me
          </button>
        </div>

        {error.length != 0 && (
          <div className="form-group">
            <p className="error-message">{error}</p>
          </div>
        )}
      </Form>
    </div>
  );
}
