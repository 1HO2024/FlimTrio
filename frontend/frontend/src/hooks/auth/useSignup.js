import { useState } from "react";
import signupApi from "../../api/auth/signup";
import Swal from "sweetalert2";  

const useSignup = (closeModal) => {  

  // 회원가입 관련된 함수 로직들
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [nickname, setNickname] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
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

  const handlePhoneNumberChange = (e) => {
    const value = e.target.value;
    // 전화번호 자동 포맷
    const formattedPhone = value
      .replace(/[^0-9]/g, "")
      .replace(/^(\d{3})(\d{3,4})(\d{4})$/, "$1-$2-$3");
    setPhoneNumber(formattedPhone);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // 유효성 검사
    if (!email || !password || !confirmPassword || !nickname || !phoneNumber) {
      setError("모든 입력창을 입력해주세요.");
      return;
    }

    if (!/^[a-zA-Z0-9]{8,15}$/.test(password)) {
      setError("비밀번호는 8자 이상 15자 이하의 영문과 숫자 조합이어야 합니다.");
      return;
    }

    if (password !== confirmPassword) {
      setError("비밀번호가 일치하지 않습니다.");
      return;
    }

    if (!/\S+@\S+\.\S+/.test(email)) {
      setError("이메일 형식으로 입력해주세요.");
      return;
    }

    setError(""); 

    try {
      await signupApi(nickname, email, password, phoneNumber);
      closeModal();

      Swal.fire({
        icon: "success",
        title: "회원가입 완료",
        text: "회원가입이 완료되었습니다!",
        confirmButtonColor: "#3085d6",
        confirmButtonText: "확인",
      });

    } catch (err) {
      setError(err.message);  
    }
  };

  return {
    email,
    password,
    confirmPassword,
    nickname,
    phoneNumber,
    error,
    handleEmailChange,
    handlePasswordChange,
    handleConfirmPasswordChange,
    handleNicknameChange,
    handlePhoneNumberChange,
    handleSubmit,
  };
};

export default useSignup;
