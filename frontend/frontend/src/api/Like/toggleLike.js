import axiosInstance from "../config/axiosConfig";

const ToggleLike = async (movieId) => {
  try {
    const response = await axiosInstance.post(
      "/api/v1/movie-detail/toggle-like",
      { id: movieId },
      {
        authRequired: true,  
      }
    );

    console.log(response);
    return response.data.data; 

  } catch (error) {
    console.error(error);
    return [];
  }
};

export default ToggleLike;
