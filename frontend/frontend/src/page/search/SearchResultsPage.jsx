import { useState } from "react";
import { useLocation } from "react-router-dom";
import "../../style/search/SearchResults.css";
import DetailModal from "../../components/detail/DetailModal";

const SearchResults = () => {
  const location = useLocation();
  const { result: SRresult } = location.state || {};
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="SRsearchResults">
      <div className="SRsearchHeader">
        <h1>"{SRresult?.name}" 검색 결과</h1>
      </div>
      <div className="SRposterGrid">
        {Array(12)
          .fill(null)
          .map((_, index) => (
            <div className="SRposter" key={index} onClick={openModal}>
              <img src="/images/Avengers.jpeg" alt="Avengers Poster" />
              <p>어벤져스 엔드게임</p>
            </div>
          ))}
      </div>

      {isModalOpen && <DetailModal closeModal={closeModal} />}
    </div>
  );
};

export default SearchResults;
