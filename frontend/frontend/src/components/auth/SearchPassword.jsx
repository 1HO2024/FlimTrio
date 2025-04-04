import { FiCopy } from "react-icons/fi";
import Logo from "../common/Logo";
import useSearchPassword from "../../hooks/auth/useSearchPassword";
import "../../style/auth/SearchPassword.css";

const SearchPassword = ({ closeModal }) => {
  const {
    email,
    phoneNumber,
    tempPassword,
    handleEmailChange,
    handlePhoneNumberChange,
    handleSearchPassword,
    handleCopyPassword,
  } = useSearchPassword(closeModal);

  return (
    <div>
      <div className="modalLogo">
        <Logo />
      </div>
      <form onSubmit={handleSearchPassword} className="searchPasswordForm">
        <h2 className="searchPasswordh2">비밀번호 찾기</h2>
        <div className="inputField">
          <input
            type="email"
            id="email"
            name="email"
            placeholder="이메일 입력"
            value={email}
            onChange={handleEmailChange}
            required
          />
        </div>
        <div className="inputField">
          <input
            type="text"
            id="phoneNumber"
            name="phoneNumber"
            placeholder="전화번호"
            value={phoneNumber}
            onChange={handlePhoneNumberChange}
            required
          />
        </div>
        {tempPassword && (
          <div
            onClick={handleCopyPassword}
            style={{
              cursor: "pointer",
            }}
          >
            <p>
              임시 비밀번호 :
              <strong>
                <>
                  <FiCopy className="copyIcon" onClick={handleCopyPassword} />
                  {tempPassword}
                </>
              </strong>
            </p>
          </div>
        )}
        <button type="submit" className="modalButton">
          비밀번호 찾기
        </button>
      </form>
    </div>
  );
};

export default SearchPassword;
