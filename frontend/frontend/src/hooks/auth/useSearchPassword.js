import { useState } from "react";
import passwordApi from "../../api/auth/passwordSearch";

const useSearchPassword = (closeModal) => {
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");

  const handleEmailChange = (e) => setEmail(e.target.value);
  
  const handlePhoneNumberChange = (e) => {
    const value = e.target.value;
    // 전화번호 자동 포맷
    const formattedPhone = value
      .replace(/[^0-9]/g, "")
      .replace(/^(\d{3})(\d{3,4})(\d{4})$/, "$1-$2-$3");
    setPhoneNumber(formattedPhone);
  };

  const handleSearchPassword = async (e) => {
    e.preventDefault();
    try {
      const response = await passwordApi(email, phoneNumber);
      console.log(response);
      closeModal();
    } catch (error) {
      console.error(error);
    }
  };

  return {
    email,
    phoneNumber,
    handleEmailChange,
    handlePhoneNumberChange,
    handleSearchPassword,
  };
};

export default useSearchPassword;