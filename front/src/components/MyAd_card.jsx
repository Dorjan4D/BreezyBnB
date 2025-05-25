import "./MyAd_card.css";
import { useNavigate, Link } from "react-router-dom";

function MyAd_card(props) {
  const navigate = useNavigate();

  const handleDelete = (event, id) => {
    fetch("http://localhost:8080/removeAccommodation/" + String(id), {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
    })
      .then((res) => {
        if (res.ok) {
          event.preventDefault();
          window.location.reload(false);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  function handleEdit() {
    console.log("HandleEdit");
    navigate("/updateAd", { state: props.data });
  }

  function handleClick() {
    navigate("/" + props.id);
  }

  return (
    <div className="myad-card-container">
      <div className="myad-img" onClick={handleClick}>
        <img src={"data:image/png;base64," + props.image} />
      </div>
      <div className="myad-info">
        <div className="name-link">
          <h1 onClick={handleClick}>{props.name}</h1>
        </div>

        <p>
          <span className="text_style">Mjesto: </span>
          {props.place}
        </p>
        <p>
          <span className="text_style">Vrsta smještaja: </span>
          {props.acmdtype}
        </p>
      </div>
      <div className="myad-commands">
        <button type="button" onClick={handleEdit}>
          <i className="bi bi-pencil-fill" title="Uredi oglas"></i>
        </button>
        <button
          type="button"
          onClick={(event) => handleDelete(event, props.id)}
        >
          <i className="bi bi-trash3-fill" title="Obriši oglas"></i>
        </button>
      </div>
    </div>
  );
}

export default MyAd_card;
