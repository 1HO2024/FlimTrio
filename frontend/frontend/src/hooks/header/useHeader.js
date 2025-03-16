import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const useHeader = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);
  const [isSignupModalOpen, setIsSignupModalOpen] = useState(false);
  const [isTransparent, setIsTransparent] = useState(false);  
  const navigate = useNavigate();

  const handleIconClick = () => {
    if (isLoggedIn) {
      navigate("/mypage");
    }
  };

  const openLoginModal = () => setIsLoginModalOpen(true);
  const closeLoginModal = () => setIsLoginModalOpen(false);
  const openSignupModal = () => setIsSignupModalOpen(true);
  const closeSignupModal = () => setIsSignupModalOpen(false);
 
  // 스크롤 내리면 투명화 관련된 함수.
  useEffect(() => {
    const handleScroll = () => {
      if (window.scrollY > 50) {
        setIsTransparent(true);
      } else {
        setIsTransparent(false);
      }
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  return {
    isLoggedIn,
    isLoginModalOpen,
    isSignupModalOpen,
    isTransparent,
    handleIconClick,
    openLoginModal,
    closeLoginModal,
    openSignupModal,
    closeSignupModal,
    setIsLoggedIn,
  };
};

export default useHeader;
