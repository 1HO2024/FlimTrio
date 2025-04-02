import axios from "axios";

// 영화 임의로 조회하는 코드 !
// 실제 백엔드랑 api 코드는 수정 되어야 함.
const fetchTrendingMovies = async (accessToken) => {
  try {
    const response = await axios.get(
      "https://api.themoviedb.org/3/trending/movie/day",
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
        params: {
          language: 'ko-KR',  
        },
      }
    );
    // console.log(response);
    return response.data.results.slice(0, 10); 
  } catch (error) {
    console.error(error);
    throw error; 
  }
};

export default fetchTrendingMovies;
