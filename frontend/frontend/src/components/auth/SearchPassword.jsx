import Logo from "../common/Logo";
import useSearchPassword from "../../hooks/auth/useSearchPassword";
const SearchPassword = ({ closeModal }) => {
  const {
    email,
    phoneNumber,
    handleEmailChange,
    handlePhoneNumberChange,
    handleSearchPassword,
  } = useSearchPassword(closeModal);

  return (
    <div>
      <div className="modalLogo">
        <Logo />
      </div>
      <form onSubmit={handleSearchPassword}>
        <input
          type="email"
          placeholder="이메일 입력"
          value={email}
          onChange={handleEmailChange}
          required
        />
        <input
          type="tel"
          placeholder="전화번호 입력"
          value={phoneNumber}
          onChange={handlePhoneNumberChange}
          required
        />
        <button type="submit">비밀번호 찾기</button>
      </form>
    </div>
  );
};

export default SearchPassword;
