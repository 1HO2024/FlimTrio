import { Link } from "react-router-dom";
import "../../style/common/Logo.css";

const Logo = () => {
  return (
    <Link to="/" className="logo">
      <span>Flim</span>
      <span>Trio</span>
    </Link>
  );
};

export default Logo;
