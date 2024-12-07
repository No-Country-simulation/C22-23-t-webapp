import "./Header.css"
import { BASENAME } from '../../config.js'
import { Link, NavLink } from 'react-router-dom'

export function Header() {
    return (
        <header id="Header">
            <nav id="HeaderNavbar">
                <Link to={"/"} id="HeaderLogo">
                    <img id="HeaderLogoImage" src={`${BASENAME}/vite.svg`} alt="Logo de Vite.js" />
                    <h1 id="HeaderLogoTitle">SegundasHuellas</h1>
                </Link>
                <div id="HeaderSearchContainer">
                    <img id="HeaderSearchIcon" src={`${BASENAME}/search-logo.svg`} alt="" />
                    <input id="HeaderSearchInput" type="text" placeholder="Buscar perros, gatos y más..." />
                </div>
            </nav>
            <div id="HeaderUserMenu">
                <NavLink to={"/login"} id="HeaderLoginButton" className="HeaderUserMenuLink">Iniciar Sesión</NavLink>
                <NavLink to={"/register"} id="HeaderRegisterButton" className="HeaderUserMenuLink">Únete</NavLink>
            </div>
        </header>
    )
}
