import "../../style/home/HomePage.css";
import TopHome from "../../components/home/TopHome";
import ActionTopGenre from "../../components/home/ActionTopGenre";
import FantasyTopGenre from "../../components/home/FantasyTopGenre";
const HomePage = () => {
  return (
    <div className="homePage">
      <TopHome />
      <ActionTopGenre />
      <FantasyTopGenre />
    </div>
  );
};

export default HomePage;
