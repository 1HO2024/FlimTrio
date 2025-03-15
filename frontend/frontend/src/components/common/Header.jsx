import { Link, useNavigate } from "react-router-dom";
import Search from "../search/Search";
import "../../style/common/Header.css";
import { FaUserCircle } from "react-icons/fa";
import { useState } from "react";

const Header = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  const handleIconClick = () => {
    if (isLoggedIn) {
      navigate("/mypage");
    }
  };

  return (
    <div className="header">
      <div className="headerContent">
        <Link to="/" className="logo">
          FlimTrio
        </Link>

        <div className="rightContainer">
          <div className="searchContainer">
            <Search />
          </div>
          <div className="userContainer">
            {isLoggedIn ? (
              <FaUserCircle className="userIcon" onClick={handleIconClick} />
            ) : (
              <div>
                <Link to="/auth" className="HeaderTextItem">
                  로그인
                </Link>
                <Link to="/signup" className="HeaderTextItem">
                  회원가입
                </Link>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Header;
