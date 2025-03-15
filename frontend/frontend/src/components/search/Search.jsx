import { Link } from "react-router-dom";
import useSearch from "../../hooks/search/useSearch";
import "../../style/search/Search.css";

const Search = () => {
  const { searchTerm, searchResults, handleSearchChange, handleSearchSubmit } =
    useSearch();

  return (
    <form className="searchForm" onSubmit={handleSearchSubmit}>
      <input
        type="text"
        value={searchTerm}
        onChange={handleSearchChange}
        placeholder="영화를 검색해주세요."
        className="searchInput"
      />

      {searchTerm && (
        <ul className="searchResults">
          {searchResults.length > 0 ? (
            searchResults.map((result, index) => (
              <li key={index} className="searchResultItem">
                <Link to={`/detail/${result.id}`} className="searchLink">
                  {result.name}
                </Link>
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
