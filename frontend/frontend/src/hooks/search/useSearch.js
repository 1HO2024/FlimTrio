import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import fetchSearchResults from "../../api/search/search"; 

const useSearch = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const searchRef = useRef(null);
  const [isInputVisible, setIsInputVisible] = useState(false);

  const navigate = useNavigate();
  const token = localStorage.getItem("token"); 

  const handleSearchChange = (event) => {
    const value = event.target.value;
    setSearchTerm(value);
    setIsOpen(!!value); 

    if (value) {
      fetchSearchResults(value, token).then((results) => {
        setSearchResults(results); 
      });
    } else {
      setSearchResults([]); 
    }
  };

  const handleSearchSubmit = (event) => {
    event.preventDefault();
  };

  const handleResultClick = () => {
    setSearchTerm(""); 
    setIsOpen(false);
  };

  const handleResultClickWithState = (result) => {
    navigate(`/searchResults/${result.movieId}`, { state: { result } });
    handleResultClick();
  };

  const handleIconClick = () => {
    setIsInputVisible((prev) => !prev);
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
    isInputVisible,
    handleSearchChange,
    handleSearchSubmit,
    handleResultClick,
    handleResultClickWithState,
    handleIconClick,
    searchRef,
  };
};

export default useSearch;
