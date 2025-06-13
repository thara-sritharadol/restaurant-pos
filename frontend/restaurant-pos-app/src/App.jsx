import {
  Route,
  createBrowserRouter,
  createRoutesFromElements,
  RouterProvider,
} from "react-router-dom";
import Homepage from "./pages/Homepage";
import LoginPage from "./pages/LoginPage";
import ManageDishPage from "./pages/ManageDishPage";
import TransactionPage from "./pages/TransactionPage";
import MainLayout from "./layout/MainLayout";
import OrderHistoryPage from "./pages/OrderHistoryPage";
import TableStatusPage from "./pages/TableStatusPage";

function App() {
  const router = createBrowserRouter(
    createRoutesFromElements(
      <Route path="/" element={<MainLayout />}>
        <Route index element={<Homepage />} />
        <Route path="/table-status" element={<TableStatusPage />} />
        <Route path="/manage-dish" element={<ManageDishPage />} />
        <Route path="/order-history" element={<OrderHistoryPage />} />
      </Route>
    )
  );
  return <RouterProvider router={router} />;
}

export default App;
