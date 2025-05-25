import { useEffect, useState, useContext } from "react";
import "./Ad_detail.css";
import { useParams } from "react-router-dom";
import { AuthContext } from "./AuthenticationContext";
import { useNavigate } from "react-router-dom";
import Comment from "./Comment.jsx";

function Ad_detail() {
  let keyCounter = 0;

  const { id } = useParams();

  const navigate = useNavigate();

  const { user, updateUser } = useContext(AuthContext);

  const [isPending, setPending] = useState(true);
  const [addButton, setAddButton] = useState(true);
  const [addComment, setAddComment] = useState(false);
  const [colors, setColors] = useState("");
  const [images, setImages] = useState([]);
  const [firstImage, setFirstImage] = useState("");

  const [comments, setComments] = useState(false);

  const [the_ad, setTheAd] = useState({
    id: null,
    verified: "",
    name: "",
    place: "",
    address: "",
    areaSquareMeters: 3.0,
    costPerNight: 3.0,
    description: "",
    numOfBedrooms: 3,
    numOfBeds: 3,
    numOfBathrooms: 3,
    maxNumOfGuests: 3,
    acmdtype: {
      id: 2,
      type: "House",
    },
    host: {
      id: 3,
      username: "",
      name: "",
      surname: "",
      email: "",
      registered: "",
      gender: "FEMALE",
      dateOfBirth: "",
      verified: "",
      contactPhone: "",
    },
    photos: [
      {
        id: null,
        photo: "",
      },
    ],
  });
  const [bezprvog, setBezPrvog] = useState([]);

  useEffect(() => {
    const jsonData = { id: id };
    console.log(jsonData);
    fetch("http://localhost:8080/accommodations/" + String(id), {
      method: "GET",
      credentials: "include",
    })
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        setTheAd(data);

        let podaci = data.photos;
        podaci = podaci.filter((photo, index) => {
          return index != 0;
        });

        setBezPrvog(podaci);
        setFirstImage(data.photos[0]);

        console.log(data);
        console.log(podaci);
        console.log(bezprvog);
      })
      .catch((err) => {
        console.log(err);
        return;
      })
      .then(() =>
        fetch(
          "http://localhost:8080/accommodations/" + String(id) + "/reviews",
          {
            method: "GET",
            credentials: "include",
          }
        )
      )
      .then((res) => {
        return res.json();
      })
      .then((data) => {
        if (data.length != 0) {
          setComments(data);
          setPending(false);
        } else {
          setPending(false);
        }
      })
      .catch(() => {
        console.log("error");
      });
  }, []);

  function changeCommentState() {
    setAddButton(true);
    setAddComment(false);
  }
  function checkUserAuth() {
    if (!user.isAuth) {
      navigate("/login");
    }
  }
  console.log(bezprvog);
  return (
    <div className="home-detail-container">
      <div className="ads-detail-container">
        <div className="pet-image-container">
          <div id="carouselExample" className="carousel slide">
            <div className="carousel-inner">
              <div className="carousel-item active" key={firstImage.id}>
                <img
                  src={"data:image/png;base64," + firstImage.photo}
                  className="d-block w-100"
                  alt="..."
                />
              </div>
              {the_ad &&
                bezprvog.map((photo) => (
                  <div className="carousel-item" key={photo.id}>
                    <img
                      src={"data:image/png;base64," + photo.photo}
                      className="d-block w-100"
                      alt="..."
                    />
                  </div>
                ))}
            </div>
            <button
              className="carousel-control-prev"
              type="button"
              data-bs-target="#carouselExample"
              data-bs-slide="prev"
            >
              <span
                className="carousel-control-prev-icon"
                aria-hidden="true"
              ></span>
              <span className="visually-hidden">Previous</span>
            </button>
            <button
              className="carousel-control-next"
              type="button"
              data-bs-target="#carouselExample"
              data-bs-slide="next"
            >
              <span
                className="carousel-control-next-icon"
                aria-hidden="true"
              ></span>
              <span className="visually-hidden">Next</span>
            </button>
          </div>
        </div>
        <div className="pet-info-container">
          <div className="pet-info-container-left">
            <h2>{the_ad.name}</h2>
            <p>
              <i className="category-style">Vrsta smještaja: </i>
              {the_ad.acmdtype.type}
            </p>
            <p>
              <i className="category-style">Mjesto: </i>
              {the_ad.place}
            </p>
            <p>
              <i className="category-style">Adresa: </i>
              {the_ad.address}
            </p>
            <p>
              <i className="category-style">Broj kvadrata: </i>
              {the_ad.areaSquareMeters}
            </p>
            <p>
              <i className="category-style">Noćenje: </i>
              {the_ad.costPerNight + "€"}
            </p>
            <p>
              <i className="category-style">Opis: </i>
              {the_ad.description}
            </p>
          </div>
          <div className="pet-info-container-right">
            <p>
              <i className="category-style">Broj spavaćih soba:</i>
              {the_ad.numOfBedrooms}
            </p>
            <p>
              <i className="category-style">Broj kreveta:</i>
              {the_ad.numOfBeds}
            </p>
            <p>
              <i className="category-style">Broj kupaona:</i>
              {the_ad.numOfBathrooms}
            </p>
            <p>
              <i className="category-style">Maksimalan broj gosti:</i>
              {the_ad.maxNumOfGuests}
            </p>
            <p>
              <i className="category-style">Vlasnik:</i>
              {the_ad.host.name + " " + the_ad.host.surname}
            </p>
            <p>
              <i className="category-style">Kontakt:</i>
              {the_ad.host.contactPhone}
            </p>
          </div>
        </div>
        <div className="comment-section-container">
          <h1>Komentari</h1>
          <hr />
          {isPending && <p>Učitavanje komentara...</p>}
          {!comments && !isPending && <p>Nema komentara</p>}
          {addButton && (
            <button
              className="btn btn-light"
              id="add-button"
              onClick={() => {
                setAddComment(true);
                setAddButton(false);
                checkUserAuth();
              }}
            >
              Dodaj komentar <i className="bi bi-plus-lg"></i>
            </button>
          )}
          {/*addComment && user.isAuth && (
            <NewComment
              username={user.firstName + " " + user.lastName}
              change={changeCommentState}
            />
          )*/}
          {comments &&
            comments.map((comment) => (
              <Comment
                name={comment.name}
                surname={comment.surname}
                rating={comment.rating}
                comment={comment.comment}
                key={comment.id}
              />
            ))}
        </div>
      </div>
    </div>
  );
}

export default Ad_detail;
