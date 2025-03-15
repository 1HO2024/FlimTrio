import Search from "../search/Search";
import "../../style/common/Header.css";
import { FaUserCircle } from "react-icons/fa";
import useHeader from "../../hooks/header/useHeader";
import Modal from "react-modal";
import Signin from "../../components/auth/Signin";
import Signup from "../../components/auth/Signup";
import Logo from "../../components/common/Logo";

Modal.setAppElement("#root");

const Header = () => {
  const {
    isLoggedIn,
    isLoginModalOpen,
    isSignupModalOpen,
    handleIconClick,
    openLoginModal,
    closeLoginModal,
    openSignupModal,
    closeSignupModal,
  } = useHeader();

  return (
    <div className="header">
      <div className="headerContent">
        <Logo />

        <div className="rightContainer">
          <div className="searchContainer">
            <Search />
          </div>
          <div className="userContainer">
            {isLoggedIn ? (
              <FaUserCircle className="userIcon" onClick={handleIconClick} />
            ) : (
              <div>
                <div onClick={openLoginModal} className="HeaderTextItem">
                  로그인
                </div>
                <div onClick={openSignupModal} className="HeaderTextSignUpItem">
                  회원가입
                </div>
              </div>
            )}
          </div>
        </div>
      </div>

      <Modal
        isOpen={isLoginModalOpen}
        onRequestClose={closeLoginModal}
        contentLabel="Login Modal"
        className="modalContent"
        overlayClassName="modalOverlay"
      >
        <Signin
          closeModal={closeLoginModal}
          openSignupModal={openSignupModal}
        />
      </Modal>

      <Modal
        isOpen={isSignupModalOpen}
        onRequestClose={closeSignupModal}
        contentLabel="Signup Modal"
        className="modalContent"
        overlayClassName="modalOverlay"
      >
        <Signup closeModal={closeSignupModal} />
      </Modal>
    </div>
  );
};

export default Header;
