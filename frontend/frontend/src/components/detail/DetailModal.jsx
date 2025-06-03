import { useLocation } from "react-router-dom";
import { FaUserCircle } from "react-icons/fa";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; 
import DetailMoviePoster from "../../components/detail/DetailMoviePoster";
import DetailRecommendMoviePoster from "./DetailRecommendMoviePoster ";
import DetailMovieInfo from "../../components/detail/DetailMovieInfo";
import DetailMovieTrailer from "../../components/detail/DetailMovieTrailer";
import DetailMovieCastPoster from "./DetailMovieCastPoster";
import fetchMovieDetails from "../../api/movie/detailMovie";
import fetchMovieReviews from "../../api/movie/detailMovieReviews"; 
import DetailReviewModal from "./DetailReviewModal";
import "../../style/detail/DetailModal.css";

const DetailModal = ({ closeModal }) => {
  const location = useLocation();
  const { result } = location.state || {};
  const navigate = useNavigate();
  const [movieData, setMovieData] = useState(null);
  const [movieReviews, setMovieReviews] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [reviewsError, setReviewsError] = useState(null); 
  const [showAllCast, setShowAllCast] = useState(false);
  const [showAllReviews, setShowAllReviews] = useState(false); // 리뷰 더보기 상태 추가
  const [showAllRecommendations, setShowAllRecommendations] = useState(false);
  const [showReviewModal, setShowReviewModal] = useState(false);

  const handleReviewSubmit = (review_comment) => {
    // 여기서 처리한 리뷰를 부모 컴포넌트로 전달
    console.log("작성된 리뷰:", review_comment);
  };

  // 영화 데이터 
  useEffect(() => {
    const loadMovieDetails = async () => {
      if (!result?.movieId) {
        setError("movieId가 없습니다.");
        setLoading(false);
        return;
      }

      try {
        const data = await fetchMovieDetails(result.movieId);
        setMovieData(data);
      } catch (e) {
        setError("영화 정보를 불러오는 데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };

    loadMovieDetails();
  }, [result]);

  // 영화 리뷰 
  useEffect(() => {
    const loadMovieReviews = async () => {
      if (!result?.movieId) return;

      const reviews = await fetchMovieReviews(result.movieId);
      if (reviews?.error) {
        setReviewsError(reviews.error); 
      } else {
        setMovieReviews(reviews); 
        setReviewsError(null); 
      }
    };

    loadMovieReviews();
  }, [result]);

  if (loading) return <div>Loading...</div>;
  if (error || !movieData) return <div>{error || "데이터 없음"}</div>;

  const { movie, cast, crew, recommendedMovies } = movieData;

  const displayedCast = showAllCast ? cast : cast.slice(0, 6);
  const displayedReviews = showAllReviews ? movieReviews : movieReviews.slice(0, 3); 
  const displayedRecommendations = showAllRecommendations
    ? recommendedMovies
    : recommendedMovies.slice(0, 10);

    
  return (
  
    <div className="DetailModalOverlay" onClick={closeModal}>
      <div className="DetailModal" onClick={(e) => e.stopPropagation()}>
        <div className="DetailPageMainContent">
          <div className="DetailPageMoviePoster">
            <DetailMoviePoster posterUrl={movie.posterPath} />
          </div>
          <div className="DetailPageTrailer">
            <DetailMovieTrailer movieName={movie.title} />
          </div>
        </div>

        <button className="DetailLikeButton" >
        <span className="Canclelike-icon">❤️<a style={{fontSize:'16px'}}>좋아요 등록 취소하기</a></span> 
        {/*<span className="like-icon">♡<a style={{fontSize:'16px'}}>좋아요 등록된 영화</a></span>*/}
        </button>
        

        <div className="DetailPageMovieInfoSection">
          <h3>줄거리</h3>

          <div className="DetailPageMovieInfoGrid">
            <strong>
            <DetailMovieInfo description={movie.overview || "저장된 줄거리가 없습니다."} />
            </strong>       
          </div> 
        </div>

        <div className="DetailCastSection">
          <div className="DetailCastSectionHeader">
            <h3>출연진</h3>
            {cast.length > 6 && (
              <button
                className="ShowMoreButton"
                onClick={() => setShowAllCast(!showAllCast)}
              >
                {showAllCast ? "접기" : "+ 더보기"}
              </button>
            )}
          </div>

          <div className="DetailCastGrid">
            {displayedCast.map((actor, index) => (
              <div key={index} className="CastCard">
                <DetailMovieCastPoster posterUrl={actor.profilePath} />
                <strong style={{fontSize:'14px'}}>{actor.name} ({actor.character})</strong>
              </div>
            ))}
          </div>
        </div>

        <div className="DetailPageReviews">
          <div className="DetailPageReviewsHeader">
            <h3>리뷰</h3>
            <div className="ReviewButton">
              <button
                className="ReviewWriteButton"
                onClick={() =>  setShowReviewModal(true)}
              >
                <p>리뷰 쓰기</p>
              </button>
               {showReviewModal && (
                <DetailReviewModal
                  onClose={() => setShowReviewModal(false)}
                  onSubmit={handleReviewSubmit}
                  movieId={movie.id}       // movieId 전달
                  movieTitle={movie.title}
                />
              )}

            {movieReviews.length > 3 && (
              <button
                className="ShowMoreReviewButton"
                onClick={() => setShowAllReviews(!showAllReviews)}
              >
                
                {showAllReviews ? "접기" : "+ 더보기"}
              </button>
            )}
            </div>
          </div>

          <div className="DetailPageReviewsGrid">
            {movieReviews.length > 0 ? (
              displayedReviews.map((review, index) => (
                <div key={index} className="ReviewCard">
                  <p style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }} >
                    <FaUserCircle style={{ fontSize: "32px", marginRight: "10px" }} />
                    <strong>{review.nickname}</strong>
                    <span style={{ color: '#ffcc00' }}>★</span>
                    <strong>{review.review_rating}</strong>
                  </p>
                  <hr />
                  <strong>{review.review_comment}</strong>
                </div>
              ))
            ) : (
              <strong style={{ marginLeft: "33%" }}>현재 작성된 리뷰가 없습니다.</strong>
            )}
          </div>
        </div>

        <div className="Recommend">
          <div className="DetailRecommendHeader">
            <h3>비슷한 영화</h3>
          </div>
          <div className="RecommendGrid">
            {displayedRecommendations.map((movie,index) => (
              <div 
              key={index} 
              className="RecommendCard"
              onClick={() =>  navigate("/detail", { state: { result:{...movie, movieId: movie.id}}})}
              >
                <DetailRecommendMoviePoster posterUrl={movie.poster_path} />
                <strong>{movie.title}</strong>
              </div>
            ))}
          </div>
        </div>

      </div>
    </div>
  );
};

export default DetailModal;
