import React from "react";
import "./Header.css";

function Header() {
  return (
    <header className="header-container">
      <nav className="nav-container">
        <div className="title-logo">
          <img src={"vite.svg"} alt="" />
          <p className="nav-title">SegundasHuellas</p>
        </div>
        <div className="input-container">
          <img className="search" src={"search-logo.svg"} alt="" />
          <input className="input" type="text" placeholder="Que desea buscar?" />
        </div>
      </nav>
      <div className="btn-header">
        <button className="btn-login">Iniciar Sesión</button>
        <button className="btn-join">Únete</button>
      </div>
    </header>
  );
}

export default Header;
