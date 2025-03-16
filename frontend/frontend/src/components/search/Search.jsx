import { FaSearch } from "react-icons/fa";
import useSearch from "../../hooks/search/useSearch";
import "../../style/search/Search.css";

const Search = () => {
  const {
    searchTerm,
    searchResults,
    isOpen,
    handleSearchChange,
    handleSearchSubmit,
    searchRef,
    handleResultClickWithState,
  } = useSearch();

  return (
    <form className="searchForm" onSubmit={handleSearchSubmit} ref={searchRef}>
      <div className="searchInputWrapper">
        <FaSearch className="searchIcon" />
        <input
          type="text"
          value={searchTerm}
          onChange={handleSearchChange}
          placeholder="영화를 검색해주세요."
          className="searchInput"
        />
      </div>

      {isOpen && (
        <ul className="searchResults">
          {searchResults.length > 0 ? (
            searchResults.map((result, index) => (
              <li key={index} className="searchResultItem">
                <span
                  className="searchLink"
                  onClick={() => handleResultClickWithState(result)} // 클릭 시 상태 전달
                >
                  {result.name}
                </span>
              </li>
            ))
          ) : (
            <li className="noResults">검색 결과가 없습니다.</li>
          )}
        </ul>
      )}
    </form>
  );
};

export default Search;
