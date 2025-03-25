import { FaSearch } from "react-icons/fa";
import useSearch from "../../hooks/search/useSearch";
import "../../style/search/Search.css";

const Search = () => {
  const {
    searchTerm,
    searchResults,
    isOpen,
    isInputVisible,
    handleSearchChange,
    handleSearchSubmit,
    handleIconClick,
    searchRef,
    handleResultClickWithState,
  } = useSearch();

  return (
    <form className="searchForm" onSubmit={handleSearchSubmit} ref={searchRef}>
      <div
        className={`searchInputWrapper ${
          isInputVisible ? "visible" : "hidden"
        } ${!isInputVisible ? "initial" : ""}`}
      >
        {isInputVisible && (
          <>
            <input
              type="text"
              value={searchTerm}
              onChange={handleSearchChange}
              placeholder="영화를 검색해주세요."
              className="searchInput"
            />
            <FaSearch className="searchIcon" onClick={handleIconClick} />
          </>
        )}
        {!isInputVisible && (
          <FaSearch
            className="searchIcon"
            style={{ fontSize: "24px" }}
            onClick={handleIconClick}
          />
        )}
      </div>

      {isOpen && (
        <ul className="searchResults">
          {searchResults.length > 0 ? (
            searchResults.map((result, index) => (
              <li key={index} className="searchResultItem">
                <span
                  className="searchLink"
                  onClick={() => handleResultClickWithState(result)}
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
