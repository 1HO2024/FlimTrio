import axios from "axios";
import Swal from "sweetalert2";

// axios 인스턴스 생성
const axiosInstance = axios.create({
  baseURL: "http://localhost:8080",  // 백엔드 서버 URL
  headers: {
    "Content-Type": "application/json",  // 기본 Content-Type 설정
  },
  withCredentials: true,  // 크로스 도메인 쿠키 처리
});


// 리프레시 토큰을 사용하여 새로운 accessToken을 가져오는 함수
const refreshAccessToken = async () => {
  try {
    const refreshToken = localStorage.getItem("refreshToken");

    if (!refreshToken) {
      Swal.fire("로그인 정보가 만료되었습니다. 다시 로그인 해주세요.", "", "error");
      
      return;
    }



    const response = await axiosInstance.post(
      "/api/v1/refresh",
      {},
      {
        headers: {
          "Authorization": `Bearer ${refreshToken}`
        },
       authRequired: false 
      }
    );

    const { accessToken, refreshToken: newRefreshToken} = response.data;
    localStorage.setItem("token", accessToken);
    localStorage.setItem("refreshToken", newRefreshToken);



    return accessToken;

  } catch (error) {
    console.error("리프레시 요청 실패:", error.response?.data || error.message);
    Swal.fire("로그인 정보가 만료되었습니다. 다시 로그인 해주세요.", "", "error");
    return;
  }
};



// 요청 인터셉터: 각 요청에 Authorization 헤더 추가
axiosInstance.interceptors.request.use(
  (config) => {
    // 요청에 'authRequired'가 true일 경우에만 Authorization 헤더 추가
    if (config.authRequired !== false) {
      const token = localStorage.getItem("token");  // 로컬 스토리지에서 토큰 가져오기
      if (token) {
        config.headers["Authorization"] = `Bearer ${token}`;  // Authorization 헤더에 토큰 추가
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터: 401 에러 발생 시 토큰 갱신
axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const originalRequest = error.config;
    const status = error.response?.status;
    const refreshToken = localStorage.getItem("refreshToken");

    // 토큰이 없으면 바로 에러 처리 (로그인 페이지로 이동 가능)
    if (!refreshToken) {

      return Promise.reject(error);
    }

     if (status === 401) {
      // 401 Unauthorized: 토큰 만료 또는 인증 실패 - 토큰 삭제 후 로그인 페이지 이동
      localStorage.removeItem("token");
      localStorage.removeItem("refreshToken");
       Swal.fire({
              title: "로그인 정보가 만료되었습니다",
              icon: "warning",
              confirmButtonText: "확인",
              confirmButtonColor: "#3085d6" 
            }).then(() => {
            });
        return;
    }

    if (status === 403 && !originalRequest._retry) {
      // 403 Forbidden: 권한 없음 - 토큰 재발급 시도
      originalRequest._retry = true;

      try {
        const newAccessToken = await refreshAccessToken();
        if (newAccessToken) {
          originalRequest.headers["Authorization"] = `Bearer ${newAccessToken}`;
          return axiosInstance(originalRequest);
        }
      } catch (err) {
         Swal.fire({
                title: "로그인 정보가 만료되었습니다",
                icon: "warning",
                confirmButtonText: "확인",
                confirmButtonColor: "#3085d6" 
              }).then(() => {
              });
         return;
      }
    }

    return Promise.reject(error);
  }
);

export default axiosInstance;


