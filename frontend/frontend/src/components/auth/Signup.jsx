import React from "react";
import Logo from "../../components/common/Logo";
import "../../style/auth/Signup.css";
import useSignup from "../../hooks/auth/useSignup";

const Signup = ({ closeModal, openLoginModal }) => {
  const {
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
  } = useSignup(closeModal);

  return (
    <div>
      <div className="modalLogo">
        <Logo />
      </div>

      <form onSubmit={handleSubmit} className="signupForm">
        <h2 className="signuph2">회원가입</h2>

        <div className="inputField">
          <input
            type="email"
            id="email"
            name="email"
            value={email}
            onChange={handleEmailChange}
            required
            placeholder="이메일"
          />
        </div>

        <div className="inputField">
          <input
            type="password"
            id="password"
            name="password"
            value={password}
            onChange={handlePasswordChange}
            required
            placeholder="비밀번호"
          />
        </div>

        <div className="inputField">
          <input
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            value={confirmPassword}
            onChange={handleConfirmPasswordChange}
            required
            placeholder="비밀번호 확인"
          />
        </div>

        <div className="inputField">
          <input
            type="text"
            id="nickname"
            name="nickname"
            value={nickname}
            onChange={handleNicknameChange}
            required
            placeholder="닉네임"
          />
        </div>

        <div className="inputField">
          <input
            type="text"
            id="phoneNumber"
            name="phoneNumber"
            value={phoneNumber}
            onChange={handlePhoneNumberChange}
            required
            placeholder="전화번호"
          />
        </div>

        <button type="submit" className="modalButton">
          회원가입
        </button>
      </form>

      {error && <div className="error">{error}</div>}

      <div className="loginPrompt">
        <span>이미 계정이 있으신가요? </span>
        <button
          onClick={() => {
            closeModal();
            openLoginModal();
          }}
          className="loginButton"
        >
          로그인
        </button>
      </div>
    </div>
  );
};

export default Signup;
