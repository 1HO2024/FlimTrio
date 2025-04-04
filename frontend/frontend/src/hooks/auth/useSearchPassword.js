import { useState } from "react";
import passwordApi from "../../api/auth/passwordSearch";

const useSearchPassword = (closeModal) => {
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [tempPassword, setTempPassword] = useState(""); 

  const handleEmailChange = (e) => setEmail(e.target.value);
  
  const handlePhoneNumberChange = (e) => {
    const value = e.target.value;
    const formattedPhone = value
      .replace(/[^0-9]/g, "")
      .replace(/^(\d{3})(\d{3,4})(\d{4})$/, "$1-$2-$3");
    setPhoneNumber(formattedPhone);
  };

  const handleSearchPassword = async (e) => {
    e.preventDefault();
    try {
      const response = await passwordApi(email, phoneNumber);
      console.log(response.tempPassword);
      setTempPassword(response.tempPassword); 
    } catch (error) {
      console.error(error);
    }
  };


  // 복사로직
  const handleCopyPassword = () => {
    if (tempPassword) {
      navigator.clipboard.writeText(tempPassword);
      alert("복사되었습니다.");
    }
  };



  return {
    email,
    phoneNumber,
    tempPassword, 
    handleEmailChange,
    handlePhoneNumberChange,
    handleSearchPassword,
    handleCopyPassword,
  };
};

export default useSearchPassword;
