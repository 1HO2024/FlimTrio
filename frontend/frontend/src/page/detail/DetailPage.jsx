import { useLocation } from "react-router-dom";

const DetailPage = () => {
  const location = useLocation();
  const { result } = location.state || {};

  return (
    <div>
      <h1>{result.name}</h1>
    </div>
  );
};

export default DetailPage;
