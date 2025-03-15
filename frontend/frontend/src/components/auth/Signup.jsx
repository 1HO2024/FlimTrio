import React, { useState } from "react";
import Logo from "../../components/common/Logo";
import "../../style/auth/Signup.css";

const Signup = ({ closeSignupModal, openLoginModal }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [nickname, setNickname] = useState("");
  const [error, setError] = useState("");

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
    closeSignupModal();
  };

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
            onChange={(e) => setEmail(e.target.value)}
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
            onChange={(e) => setPassword(e.target.value)}
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
            onChange={(e) => setConfirmPassword(e.target.value)}
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
            onChange={(e) => setNickname(e.target.value)}
            required
            placeholder="닉네임"
          />
        </div>

        <button type="submit" className="modalButton">
          회원가입
        </button>
      </form>

      <div className="loginPrompt">
        <span>계정이 있으신가요? </span>
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
