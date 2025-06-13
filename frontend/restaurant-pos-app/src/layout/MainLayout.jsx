import NavBar from "../components/NavBar";
import Sidebar from "../components/sideBar";
import { Outlet } from "react-router-dom";

const MainLayout = () => {
  return (
    <>
      <NavBar />
      <Sidebar />
      <main>
        <div className="">
          <Outlet />
        </div>
      </main>
    </>
  );
};

export default MainLayout;
