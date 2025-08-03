import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import useAuthStore from "../../store/authStore";
import Swal from "sweetalert2";

const useHeader = () => {
  const { isLoggedIn, user, logout } = useAuthStore();
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);
  const [isSignupModalOpen, setIsSignupModalOpen] = useState(false);
  const [isTransparent, setIsTransparent] = useState(false);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout(); 
    navigate("/");
    localStorage.removeItem("token");
    localStorage.removeItem("refreshToken");
    
      Swal.fire({
        title: "로그아웃 성공!",
        text: `로그아웃 되었습니다.`,
        icon: "success",
        confirmButtonColor: "#3085d6",
        confirmButtonText: "확인",
      })
  };

  const handleIconClick = () => {
    if (isLoggedIn) navigate("/mypage");
  };

  const openLoginModal = () => setIsLoginModalOpen(true);
  const closeLoginModal = () => setIsLoginModalOpen(false);
  const openSignupModal = () => setIsSignupModalOpen(true);
  const closeSignupModal = () => setIsSignupModalOpen(false);

  useEffect(() => {
    const handleScroll = () => {
      setIsTransparent(window.scrollY > 50);
    };

    window.addEventListener("scroll", handleScroll);
    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  return {
    isLoggedIn,
    user, 
    isLoginModalOpen,
    isSignupModalOpen,
    isTransparent,
    handleIconClick,
    openLoginModal,
    closeLoginModal,
    openSignupModal,
    closeSignupModal,
    handleLogout,
  };
};

export default useHeader;
