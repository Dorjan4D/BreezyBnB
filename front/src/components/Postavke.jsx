import { useContext, useEffect, useState } from "react";
import "./Login.css";
import { useLocation, useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthenticationContext.jsx";

const Postavke = () => {
  const [files, setFiles] = useState();
  const [preview, setPreview] = useState();
  const { user, updateUser } = useContext(AuthContext);
  const [images, setImages] = useState("");
  const navigate = useNavigate();
  const [error, setError] = useState("");
  const [lat, setLat] = useState();
  const [lng, setLng] = useState();
  const [color, setColor] = useState("");
  const [vrste, setVrste] = useState([]);
  const [inputValues, setInputValues] = useState({});
  const [newType, setNewType] = useState(""); // novo stanje za unos novog tipa

  const handleInputChange = (vrsta, newValue) => {
    setInputValues((prev) => ({
      ...prev,
      [vrsta]: newValue,
    }));
  };

  useEffect(() => {
    if (!user.isAdmin) {
      navigate("/login");
    }
    fetch("http://localhost:8080/acmdtypes", {
      method: "GET",
      credentials: "include",
    })
      .then((res) => {
        if (res.ok) {
          return res.json();
        } else {
          throw Error("Greška pri dohvaćanju");
        }
      })
      .then((data) => {
        setVrste(data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const handleUpdate = (vrsta) => {
    const value = inputValues[vrsta];
    console.log(`Updating ${vrsta} with value: ${value}`);
    // primjer: pozovi PUT API za update vrste smještaja
    fetch(
      `http://localhost:8080/admin/updateAcmdtype?oldType=${vrsta}&newType=${value}`,
      {
        method: "POST",
        credentials: "include",
      }
    )
      .then((res) => {
        if (!res.ok) throw Error("Greška kod updatea");
        // update lokalnog stanja - zamijeni stari naziv novim
        setVrste((prev) => prev.map((v) => (v === vrsta ? value : v)));
        // obriši input polje nakon updatea (opcionalno)
        setInputValues((prev) => ({ ...prev, [vrsta]: "" }));
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const handleDelete = (vrsta) => {
    console.log(`Deleting ${vrsta}`);
    // primjer: pozovi DELETE API za brisanje vrste smještaja
    fetch(`http://localhost:8080/admin/removeAcmdtype`, {
      method: "POST",
      headers: { "Content-Type": "text/plain" },
      credentials: "include",
      body: vrsta,
    })
      .then((res) => {
        if (!res.ok) throw Error("Greška kod brisanja");
        // ukloni iz lokalnog stanja
        setVrste((prev) => prev.filter((v) => v !== vrsta));
        // izbriši inputValue za taj tip
        setInputValues((prev) => {
          const copy = { ...prev };
          delete copy[vrsta];
          return copy;
        });
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const handleAddNewType = () => {
    if (!newType.trim()) return;
    console.log(`Adding new type: ${newType}`);
    // primjer: pozovi POST API za dodavanje nove vrste
    fetch("http://localhost:8080/admin/addAcmdtype", {
      method: "POST",
      headers: { "Content-Type": "text/plain" },
      credentials: "include",
      body: newType,
    })
      .then((res) => {
        if (!res.ok) throw Error("Greška kod dodavanja");
        return res.text();
      })
      .then((addedType) => {
        // dodaj u lokalno stanje
        setVrste((prev) => [...prev, newType]);
        setNewType(""); // resetiraj input
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div className="login-container">
      <section className="login-window">
        <h1 className="login-headline">Vrste smještaja</h1>
        {vrste.length > 0 &&
          vrste.map((vrstSmjestaja) => (
            <div className="form" key={vrstSmjestaja}>
              <p className="lol">{vrstSmjestaja}</p>
              <div className="form-group">
                <input
                  type="text"
                  className="form-control"
                  id={`mjesto-${vrstSmjestaja}`}
                  name={`mjesto-${vrstSmjestaja}`}
                  value={inputValues[vrstSmjestaja] || ""}
                  onChange={(e) =>
                    handleInputChange(vrstSmjestaja, e.target.value)
                  }
                  placeholder="Nova vrijednost"
                  required
                />
              </div>
              <div className="button-container">
                <button
                  id="btn"
                  className="btn"
                  onClick={() => handleUpdate(vrstSmjestaja)}
                >
                  Update
                </button>
                <button
                  id="btn"
                  className="btn"
                  onClick={() => handleDelete(vrstSmjestaja)}
                >
                  Delete
                </button>
              </div>
            </div>
          ))}
        <div className="form-group">
          <input
            type="text"
            className="form-control"
            id="mjesto"
            name="mjesto"
            value={newType}
            onChange={(e) => setNewType(e.target.value)}
            placeholder="Nova vrijednost"
            required
          />
        </div>
        <div className="button-container">
          <button className="btn" id="btn" onClick={handleAddNewType}>
            Dodaj novu vrstu smještaja
          </button>
        </div>
      </section>
    </div>
  );
};

export default Postavke;
