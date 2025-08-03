import axiosInstance from "../config/axiosConfig";

const signinApi = async (email, password) => {
  try {
    const response = await axiosInstance.post("/api/v1/signin", {
      email,
      password,
    });
     // 액세스 토큰과 리프레시 토큰을 로컬 스토리지에 저장
    localStorage.setItem("token", response.data.accessToken);
    localStorage.setItem("refreshToken", response.data.refreshToken);


    return response.data;

  } catch (error) {
    throw new Error("로그인 실패. 다시 시도해 주세요.");
  }
};

export default signinApi;
