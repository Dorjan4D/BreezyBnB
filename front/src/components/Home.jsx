import "./Home.css";
import Ad_card from "./Ad_card";
import { Form, Link } from "react-router-dom";
import { useEffect, useState } from "react";

function Home() {
  const [isPending, setPending] = useState(true);
  const [filter, setFilter] = useState(false);

  const [ads, setAds] = useState();

  const [filtername, setFilterName] = useState("");
  const [filterplace, setFilterPlace] = useState("");
  const [filterguests, setFilterGuests] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);

    setFilterName(formData.get("name"));
    setFilterPlace(formData.get("place"));
    setFilterGuests(formData.get("guests"));
    console.log(filterguests);
  };

  useEffect(() => {
    fetch("http://localhost:8080/frontpagedata", {
      method: "GET",
      credentials: "include",
    })
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch");
        return res.json();
      })
      .then((data) => {
        setAds(data);
        setPending(false);
      })
      .catch((error) => console.error("Error:", error));
  }, []);

  return (
    <div className="home-container">
      <button className="filter-button" onClick={() => setFilter(!filter)}>
        Filter <i className="bi bi-funnel"></i>
      </button>
      <div className="left-categories">
        <h1 className="search-heading">Pretraživanje</h1>
        <div className="categories-container">
          <Form onSubmit={handleSubmit}>
            <div className="form-floating mb-3">
              <input
                type="name"
                className="form-control"
                id="name"
                name="name"
                placeholder="Naziv smještaja"
              />
              <label htmlFor="name">Naziv smještaja</label>
            </div>
            <div className="form-floating mb-3">
              <input
                type="place"
                className="form-control"
                id="place"
                name="place"
                placeholder="Mjesto"
              />
              <label htmlFor="place">Mjesto</label>
            </div>
            <div className="form-floating mb-3">
              <input
                type="place"
                className="form-control"
                id="guests"
                name="guests"
                placeholder="Maksimalan broj gosti"
              />
              <label htmlFor="guests">Maksimalan broj gosti</label>
            </div>
            <div className="btnFilter-container">
              <button type="submit" className="btn btn-light">
                Pretraži
              </button>
            </div>
          </Form>
        </div>
      </div>

      <div className="ads-container">
        <div className="ads-container2">
          {isPending && <h3>Učitavanje oglasa...</h3>}
          {!ads && !isPending && <h2>Nema oglasa</h2>}
          {ads &&
            ads
              .filter((ad) => {
                if (filtername == "") return true;
                return ad.name
                  .toLowerCase()
                  .includes(filtername.toLocaleLowerCase());
              })
              .filter((ad) => {
                if (filterplace == "") return true;
                return ad.place
                  .toLowerCase()
                  .includes(filterplace.toLocaleLowerCase());
              })
              .filter((ad) => {
                if (filterguests == "") return true;
                return ad.maxNumOfGuests == parseInt(filterguests.trim(), 10);
              })
              .map((ad) => (
                <Link to={"/" + ad.id} key={ad.id}>
                  <Ad_card
                    petname={ad.name}
                    image={ad.photo[0].photo}
                    description={ad.place}
                  />
                </Link>
              ))}
        </div>
      </div>
    </div>
  );
}

export default Home;
