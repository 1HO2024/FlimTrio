import useSearch from "../../hooks/search/useSearch";
import "../../style/search/Search.css";
const Search = () => {
  const { searchTerm, handleSearchChange, handleSearchSubmit } = useSearch();

  return (
    <form className="searchForm" onSubmit={handleSearchSubmit}>
      <input
        type="text"
        value={searchTerm}
        onChange={handleSearchChange}
        placeholder="영화를 검색해주세요."
        className="searchInput"
      />
    </form>
  );
};

export default Search;
