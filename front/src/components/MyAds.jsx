import MyAd_card from "./MyAd_card";
import "./MyAds.css";
import { Link, Navigate, useNavigate } from "react-router-dom";
import { useEffect, useState, useContext } from "react";
import { AuthContext } from "./AuthenticationContext";

function MyAds() {
  const { user, updateUser } = useContext(AuthContext);
  const [ads, setAds] = useState([]);
  const [isPending, setPending] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    if (!user.isHost) {
      navigate("/");
    }
    fetch("http://localhost:8080/myAds", {
      method: "GET",
      credentials: "include",
    })
      .then((res) => {
        if (res.ok) {
          return res.json();
        } else {
          throw Error("error");
        }
      })
      .then((data) => {
        setAds(data);
        setPending(false);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div className="myads-container">
      <br />
      {isPending && <p className="loading">Loading...</p>}
      {!ads && !isPending && <h1>Nemate postavljenih oglasa</h1>}
      {ads &&
        ads.map((ad) => (
          <MyAd_card
            key={ad.id}
            image={ad.photo[0].photo}
            name={ad.name}
            place={ad.place}
            acmdtype={ad.acmdtype}
            id={ad.id}
            data={ad}
          />
        ))}
      <Link to="/newAd">
        <button className="btn btn-light" id="add-button">
          Dodaj oglas <i className="bi bi-plus-lg"></i>
        </button>
      </Link>
    </div>
  );
}

export default MyAds;
