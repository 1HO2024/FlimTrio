import React, { useState } from "react";
import Logo from "../../components/common/Logo";
import "../../style/auth/Signin.css";

const Signin = ({ closeLoginModal, openSignupModal }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = (e) => {
    e.preventDefault();
  };

  return (
    <div>
      <div className="modalLogo">
        <Logo />
      </div>

      <form onSubmit={handleLogin} className="signinForm">
        <h2 className="signinh2">로그인</h2>

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

        <button type="submit" className="modalButton">
          로그인
        </button>

        <div className="forgotPassword">
          <a>비밀번호를 잊어버리셨나요?</a>
        </div>

        <div className="signupPrompt">
          <span>계정이 없으신가요?</span>
          <button
            onClick={() => {
              closeLoginModal();
              openSignupModal();
            }}
            className="signupLink"
          >
            회원가입
          </button>
        </div>
      </form>
    </div>
  );
};

export default Signin;
