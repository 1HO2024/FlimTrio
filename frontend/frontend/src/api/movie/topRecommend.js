import axiosInstance from "../config/axiosConfig";

const fetchRecommendTop = async () => {
  const token = localStorage.getItem("token");
  try {
    const response = await axiosInstance.get(
      "/api/v1/movies/recommend",
      {
        headers: {
          Authorization: token,
        },
      }
    );

    console.log(response)
    return response.data.data; 

  } catch (error) {
    console.error(error);
    return [];
  }
};

export default fetchRecommendTop;
