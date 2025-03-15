import { useState } from "react";

const useSignin = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleLogin = (e) => {
    e.preventDefault();
    // 로그인 처리 로직 추가
  };

  return {
    email,
    password,
    handleEmailChange,
    handlePasswordChange,
    handleLogin,
  };
};

export default useSignin;
