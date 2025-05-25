import React, { useContext, useEffect, useState } from "react";
import { Form, Navigate, redirect, useNavigate } from "react-router-dom";
import "./NewAd.css";
import { AuthContext } from "./AuthenticationContext";
export const NewAd = () => {
  const [files, setFiles] = useState();
  const [preview, setPreview] = useState();
  const { user, updateUser } = useContext(AuthContext);
  const [images, setImages] = useState("");
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const [lat, setLat] = useState();
  const [lng, setLng] = useState();
  const [color, setColor] = useState("");
  const [vrste, setVrste] = useState(["ONEnight", "vila", "priroda", "indoor"]);
  console.log(vrste);

  useEffect(() => {
    if (!user.isAuth && !user.isHost) {
      navigate("/");
    }
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

  useEffect(() => {
    fetch("http://localhost:8080/acmdtypes", {
      method: "GET",
      credentials: "include",
    })
      .then((res) => {
        if (res.ok) {
          return res.json();
        } else {
          throw Error("lol");
        }
      })
      .then((data) => {
        setVrste(data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

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

  const createAdSubmit = async (event) => {
    event.preventDefault();
    //getting form data and turning it into object
    const data = new FormData(event.target);
    let images2 = "";
    setError("");
    if (!files || files.length == 0) {
      setError("Stavite barem jednu sliku");
      return;
    } else if (files.length > 3) {
      setError("Maksimalno 3 slike su dopuštene");
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
    images2 = images2.split(",");
    let jsonimages = images2.map((image) => {
      return { photo: image };
    });

    const jsonData = {
      photos: jsonimages,
      name: data.get("naziv"),
      place: data.get("mjesto"),
      address: data.get("adresa"),
      areaSquareMeters: parseFloat(data.get("kvadrati")),
      costPerNight: parseFloat(data.get("nightCost")),
      description: data.get("opis"),
      numOfBedrooms: parseInt(data.get("bedrooms")),
      numOfBeds: parseInt(data.get("kreveti")),
      numOfBathrooms: parseInt(data.get("bathrooms")),
      maxNumOfGuests: parseInt(data.get("guests")),
      acmdtype: { type: data.get("vrsta") },
    };
    console.log(jsonData);

    if (
      !/^\d+$/.test(jsonData.areaSquareMeters) ||
      !/^\d+$/.test(jsonData.costPerNight) ||
      !/^\d+$/.test(jsonData.maxNumOfGuests) ||
      !/^\d+$/.test(jsonData.numOfBathrooms) ||
      !/^\d+$/.test(jsonData.numOfBedrooms) ||
      !/^\d+$/.test(jsonData.numOfBeds)
    ) {
      setError("Niste dobro ispunili");
      return;
    }
    fetch("http://localhost:8080/host/addAccommodation", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(jsonData),
    })
      .then((res) => {
        //setIsPending(false)
        if (res.ok) {
          navigate("/MyAds");
        } else {
          const errorData = res.json();
          throw new Error(errorData.message || "Unknown error");
        }
      })
      .catch((err) => {
        setError("Pokušajte ponovno");
        console.log(err);
      });
  };

  return (
    <div className="createAd-container">
      <h1 className="createAd-headline">Novi Oglas</h1>
      <Form className="createAd-window" onSubmit={createAdSubmit}>
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
          <label htmlFor="naziv">Naziv smještaja</label>
          <input
            type="naziv"
            className="form-control"
            id="naziv"
            name="naziv"
            required
            placeholder="Naziv smještaja"
          />
        </div>

        <div className="form-group">
          <label htmlFor="mjesto">Mjesto smještaja</label>
          <input
            type="naziv"
            className="form-control"
            id="mjesto"
            name="mjesto"
            required
            placeholder="Mjesto smještaja"
          />
        </div>

        <div className="form-group">
          <label htmlFor="adresa">Adresa smještaja</label>
          <input
            type="naziv"
            className="form-control"
            id="adresa"
            name="adresa"
            required
            placeholder="Adresa smještaja"
          />
        </div>

        <div className="form-group">
          <label htmlFor="kvadrati">Broj kvadrata</label>
          <input
            type="naziv"
            className="form-control"
            id="kvadrati"
            name="kvadrati"
            required
            placeholder="Broj kvadrata"
          />
        </div>

        <div className="form-group">
          <label htmlFor="nightCost">Cijena noćenja</label>
          <input
            type="naziv"
            className="form-control"
            id="nightCost"
            name="nightCost"
            required
            placeholder="Cijena noćenja"
          />
        </div>

        <div className="form-group">
          <label htmlFor="opis">Opis</label>
          <textarea
            className="form-control"
            id="opis"
            rows="2"
            name="opis"
            required
          ></textarea>
        </div>

        <div className="form-group">
          <label htmlFor="bedrooms">Broj spavaćih soba</label>
          <input
            type="naziv"
            className="form-control"
            id="bedrooms"
            name="bedrooms"
            required
            placeholder="Broj spavaćih soba"
          />
        </div>

        <div className="form-group">
          <label htmlFor="kreveti">Broj kreveta</label>
          <input
            type="naziv"
            className="form-control"
            id="kreveti"
            name="kreveti"
            required
            placeholder="Broj kreveta"
          />
        </div>

        <div className="form-group">
          <label htmlFor="bathrooms">Broj kupaona</label>
          <input
            type="naziv"
            className="form-control"
            id="bathrooms"
            name="bathrooms"
            required
            placeholder="Broj kupaona"
          />
        </div>

        <div className="form-group">
          <label htmlFor="guests">Maksimalan broj gosti</label>
          <input
            type="naziv"
            className="form-control"
            id="guests"
            name="guests"
            required
            placeholder="Maksimalan broj gosti"
          />
        </div>

        <div className="form-group">
          <label htmlFor="vrsta">Vrsta smještaja</label>
          <select className="form-control" id="vrsta" name="vrsta">
            {vrste.map((vrsta) => (
              <option key={vrsta} value={vrsta}>
                {vrsta}
              </option>
            ))}
          </select>
        </div>

        <div className="text-center">
          <button className="btn btn-primary" id="btn-createAd">
            Dodaj oglas
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
};
