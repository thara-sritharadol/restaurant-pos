import { Link, NavLink } from "react-router-dom";
import RestaurantLogo from "../assets/res-pos-logo.png";
import {
  CiHome,
  CiForkAndKnife,
  CiClock1,
  CiSquareMore,
  CiLogout,
} from "react-icons/ci";

const NavBar = () => {
  const activeLink = ({ isActive }) => {
    isActive
      ? "bg-red-700 bg-opacity-60 rounded px-3 py-2"
      : "hover:bg-red-700 hover:bg-opcacity-20 py-2 px-3";
  };
  return (
    <aside
      className="fixed top-0 left-0 z-40 w-64 h-screen transition-transform -translate-x-full sm:translate-x-0"
      aria-label="Sidebar"
    >
      <div className="h-full px-3 py-4 overfolw-y-auto bg-gray-50 dark:bg-gray-800">
        <div className="flex gap-x-4">
          <Link to="/">
            <img
              src={RestaurantLogo}
              alt="Restaurant Logo"
              className="h-28 w-28 object-cover"
            />
          </Link>
        </div>
        <ul className="space-y-2 font-medium">
          <li>
            <NavLink
              to="/"
              className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
            >
              <CiHome fontSize="1.5em" />
              <span className="ms-3 text-1lg">Home</span>
            </NavLink>
            <NavLink
              to="/table-status"
              className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
            >
              <CiSquareMore fontSize="1.5em" />
              <span className="ms-3 text-1lg">Table Status</span>
            </NavLink>
            <NavLink
              to="/order-history"
              className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
            >
              <CiClock1 fontSize="1.5em" />
              <span className="ms-3 text-1lg">Order History</span>
            </NavLink>
          </li>
        </ul>
        <ul className="pt-4 mt-4 space-y-2 font-medium border-t border-gray-200 dark:border-gray-700">
          <li>
            <NavLink
              to="/manage-dish"
              className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
            >
              <CiForkAndKnife fontSize="1.5em" />
              <span className="ms-3 text-1lg">Manage Dish</span>
              <span className="inline-flex items-center justify-center px-2 ms-3 text-sm font-medium text-gray-800 bg-gray-100 rounded-full dark:bg-gray-700 dark:text-gray-300">
                Manager
              </span>
            </NavLink>
            <a
              href="#"
              className="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group"
            >
              <CiLogout fontSize="1.5em" />
              <span className="ms-3 text-1lg">Logout</span>
            </a>
          </li>
        </ul>
      </div>
    </aside>
  );
};

export default NavBar;
