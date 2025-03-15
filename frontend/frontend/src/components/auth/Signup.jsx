import React from "react";
import Logo from "../../components/common/Logo";
import "../../style/auth/Signup.css";
import useSignup from "../../hooks/auth/useSignup";

const Signup = ({ closeSignupModal, openLoginModal }) => {
  const {
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
  } = useSignup();

  return (
    <div>
      <div className="modalLogo">
        <Logo />
      </div>

      {error && <div className="error">{error}</div>}

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

        <button type="submit" className="modalButton">
          회원가입
        </button>
      </form>

      <div className="loginPrompt">
        <span>이미 계정이 있으신가요? </span>
        <button
          onClick={() => {
            closeSignupModal();
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
