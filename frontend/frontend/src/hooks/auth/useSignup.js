import { useState } from "react";

const useSignup = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [nickname, setNickname] = useState("");
  const [error, setError] = useState("");

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleConfirmPasswordChange = (e) => {
    setConfirmPassword(e.target.value);
  };

  const handleNicknameChange = (e) => {
    setNickname(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!email || !password || !confirmPassword || !nickname) {
      setError("모든 필드를 입력해주세요.");
      return;
    }

    if (password !== confirmPassword) {
      setError("비밀번호가 일치하지 않습니다.");
      return;
    }

    setError("");
    console.log("회원가입 완료", { email, password, nickname });
  };

  return {
    email,
    password,
    confirmPassword,
    nickname,
    error,
    handleEmailChange,
    handlePasswordChange,
    handleConfirmPasswordChange,
    handleNicknameChange,
    handleSubmit,
  };
};

export default useSignup;
