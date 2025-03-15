import axios from "axios";

// 영화 임의로 조회하는 코드 !
const fetchTrendingMovies = async (accessToken) => {
  try {
    const response = await axios.get(
      "https://api.themoviedb.org/3/trending/movie/day",
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
    );
    return response.data.results.slice(0, 10); 
  } catch (error) {
    console.error(error);
    throw error; 
  }
};

export default fetchTrendingMovies;