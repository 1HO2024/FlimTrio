import { useState } from "react";

const useSearch = () => {
  const [searchTerm, setSearchTerm] = useState("");

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSearchSubmit = (event) => {
    event.preventDefault();
    console.log(searchTerm);
  };

  return {
    searchTerm,
    handleSearchChange,
    handleSearchSubmit,
  };
};

export default useSearch;
