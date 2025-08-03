import axiosInstance from "../config/axiosConfig";

const fetchLikeStatus = async (movieId) => {
   const token = localStorage.getItem("token");  // 토큰 확인
  
  if (!token) {
    return "UnLike";
  }
  try {
    const response = await axiosInstance.get(`/api/v1/movie-detail/is-like`, {
      params: {
        id: movieId,
      },
    });

    if (response.data.success) {
      // 서버에서 "Like"가 오면, liked 상태로 반환
      if (response.data.message === "Like") {
        return "liked";  // 좋아요 상태
      } else if (response.data.message === "UnLike") {
        return "UnLike"; // 좋아요 취소 상태
      }
      return "UnLike";  // 결과가 null로 올 경우
    } else {
      return "UnLike";  // 실패 시 null 반환
    }
  } catch (error) {
    console.error("좋아요 조회 실패", error);
    return { error: "좋아요 조회 실패" };  // 에러 처리
  }
};

export default fetchLikeStatus;
