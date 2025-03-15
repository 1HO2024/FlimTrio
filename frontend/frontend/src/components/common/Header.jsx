import { Link } from "react-router-dom";
import Search from "../search/Search";
import "../../style/common/Header.css";

const Header = () => {
  return (
    <div className="header">
      <div className="headerContent">
        <Link to="/" className="logo">
          FlimTrio
        </Link>

        <nav className="nav">
          <Link to="/auth" className="navLink">
            로그인
          </Link>
          <Link to="/mypage" className="navLink">
            마이페이지
          </Link>
        </nav>

        <div className="searchContainer">
          <Search />
        </div>
      </div>
    </div>
  );
};

export default Header;
