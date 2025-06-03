import React, { useState } from 'react';
import "../../style/detail/DetailReviewModal.css";
import WriteMovieReviews from "../../api/movie/detailMovieWriteReview"; 

const StarRating = ({ maxStars = 5, rating, onRatingChange }) => {
  const handleClick = (starIndex) => {
    const newRating = starIndex + 1;
    onRatingChange(newRating);  
  };

  return (
    <div className="StarRating">
      {Array.from({ length: maxStars }, (_, index) => (
        <span
          key={index}
          className={`star ${index < rating ? 'filled' : 'empty'}`}
          onClick={() => handleClick(index)}
        >
          ★
        </span>
      ))}
    </div>
  );
};

const DetailReviewModal = ({ onClose, movieId, movieTitle }) => {
  const [review_comment, setReviewText] = useState('');
  const [review_rating, setRating] = useState(0);  
  const [loading, setLoading] = useState(false);  
  const [error, setError] = useState(null); 
  const [successMessage, setSuccessMessage] = useState(null);  

  const handleSubmit = async () => {
    setLoading(true);
    setError(null);
    setSuccessMessage(null);

    try {

      const result = await WriteMovieReviews(movieId, review_comment, review_rating);

      if (result.error) {
        if (result.error === "이미 작성한 리뷰가 있습니다.") {
          alert("이미 작성한 리뷰가 있습니다."); 
          onClose();  
          return;
        } else {
          setError(result.error);  
        }
      } else {
        alert("리뷰가 성공적으로 작성되었습니다!");  
          onClose();  
        }; 
    } catch (err) {
      setError("알수없는 오류 , 로그인 인증문제");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="ReviewModalBackdrop">
      <div className="ReviewModal">
        <h2 style={{ marginLeft: '100px' }}>리뷰 작성</h2>

        <strong>{movieTitle}</strong>

        <p>
          <strong>별점:</strong>
          <StarRating rating={review_rating} onRatingChange={setRating} />
        </p>

        <textarea
          placeholder="리뷰를 작성해주세요."
          value={review_comment}
          onChange={(e) => setReviewText(e.target.value)}
        />

        {error && <div className="error-message">{error}</div>}
        {successMessage && <div className="success-message">{successMessage}</div>}

        <div className="ModalButtons">
          <button onClick={handleSubmit} disabled={loading}>
            {loading ? "등록 중..." : "등록"}
          </button>
          <button onClick={onClose}>취소</button>
        </div>
      </div>
    </div>
  );
};

export default DetailReviewModal;
