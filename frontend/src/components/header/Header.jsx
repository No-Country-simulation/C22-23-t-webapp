import "./Header.css"
import { Link, NavLink } from 'react-router-dom'

export function Header() {
  return (
    <header className="header-container">
      <nav className="nav-container">
        <Link to={"/"} className="title-logo">
          <img src={"./vite.svg"} alt="Logo de Vite.js" />
          <h1 className="nav-title">SegundasHuellas</h1>
        </Link>
        <div className="input-container">
          <img className="search" src={"search-logo.svg"} alt="" />
          <input className="input" type="text" placeholder="Que desea buscar?" />
        </div>
      </nav>
      <div className="btn-header">
        <NavLink to={"/login"} className="btn-login">Iniciar Sesión</NavLink>
        <NavLink to={"/register"} className="btn-join">Únete</NavLink>
      </div>
    </header>
  )
}
