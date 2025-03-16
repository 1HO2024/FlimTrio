import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/common/Header";
import MyPagePage from "./page/mypage/MyPagePage";
import DetailPage from "./page/detail/DetailPage";
import HomePage from "./page/home/HomePage";

import "./App.css";
function App() {
  return (
    <Router>
      <Header />
      <div style={{ marginTop: "50px" }}>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/mypage" element={<MyPagePage />} />
          <Route path="/detail/:id" element={<DetailPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
