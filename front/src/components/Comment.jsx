import "./Comment.css";

function Comment(props) {
  return (
    <div>
      <hr />
      <h4 className="comment-username">{props.name + " " + props.surname}</h4>
      <div className="comment-container">
        <div className="comment-info-div">
          <p className="user-info">Komentar:</p>
          <p className="comment-text">{props.comment}</p>
        </div>
        <p className="user-info">Recenzija:</p>
        <p className="comment-text">{props.rating}/5</p>
      </div>
    </div>
  );
}

export default Comment;
