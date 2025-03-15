import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/common/Header";
import AuthPage from "./page/auth/AuthPage";
import MyPagePage from "./page/mypage/MyPagePage";
import DetailPage from "./page/detail/DetailPage";

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/auth" element={<AuthPage />} />
        <Route path="/mypage" element={<MyPagePage />} />
        <Route path="/detail/:id" element={<DetailPage />} />
      </Routes>
    </Router>
  );
}

export default App;
