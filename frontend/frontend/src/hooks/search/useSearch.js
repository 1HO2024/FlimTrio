import { useState } from "react";

const useSearch = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [searchResults, setSearchResults] = useState([]);

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);

    const dummyResults = [
      { id: 1, name: "범죄도시" },
      { id: 2, name: "국가대표" },
      { id: 3, name: "미키17" },
      { id: 4, name: "어벤져스" },
      { id: 5, name: "기생충" },
      { id: 6, name: "괴물" },
    ];
    
    const filteredResults = dummyResults.filter((item) =>
      item.name.includes(event.target.value)
    );
    setSearchResults(filteredResults);
  };

  const handleSearchSubmit = (event) => {
    event.preventDefault();
    console.log(searchTerm);
  };

  return {
    searchTerm,
    searchResults,
    handleSearchChange,
    handleSearchSubmit,
  };
};

export default useSearch;
