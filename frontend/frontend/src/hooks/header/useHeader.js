import { useState } from "react";
import { useNavigate } from "react-router-dom";

const useHeader = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  const [isLoginModalOpen, setIsLoginModalOpen] = useState(false);
  const [isSignupModalOpen, setIsSignupModalOpen] = useState(false);
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

  return {
    isLoggedIn,
    isLoginModalOpen,
    isSignupModalOpen,
    handleIconClick,
    openLoginModal,
    closeLoginModal,
    openSignupModal,
    closeSignupModal,
    setIsLoggedIn,
  };
};

export default useHeader;
