import { useState, useEffect, useRef } from "react";

const useSearch = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const searchRef = useRef(null); 

  const handleSearchChange = (event) => {
    const value = event.target.value;
    setSearchTerm(value);
    setIsOpen(!!value); 

    const dummyResults = [
      { id: 1, name: "범죄도시" },
      { id: 2, name: "국가대표" },
      { id: 3, name: "미키17" },
      { id: 4, name: "어벤져스" },
      { id: 5, name: "기생충" },
      { id: 6, name: "괴물" },
    ];

    const filteredResults = dummyResults.filter((item) =>
      item.name.includes(value)
    );
    setSearchResults(filteredResults);
  };

  const handleSearchSubmit = (event) => {
    event.preventDefault();
    console.log(searchTerm);
  };

  const handleResultClick = () => {
    setSearchTerm(""); 
    setIsOpen(false);
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (searchRef.current && !searchRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };

    document.addEventListener("click", handleClickOutside);
    return () => {
      document.removeEventListener("click", handleClickOutside);
    };
  }, []);

  return {
    searchTerm,
    searchResults,
    isOpen,
    handleSearchChange,
    handleSearchSubmit,
    handleResultClick,
    searchRef,
  };
};

export default useSearch;
