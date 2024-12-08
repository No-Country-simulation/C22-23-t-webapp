import "./Header.css"
import { BASENAME } from '../../config.js'
import { Link, NavLink } from 'react-router-dom'
import { useState } from 'react'

export function Header() {
    const [ username, setUsername ] = useState()
    
    return (
        <header id="Header">
            <nav id="HeaderNavbar">

                <Link to={"/"} id="HeaderLogo">

                    <img id="HeaderLogoImage" src={`${BASENAME}/vite.svg`} alt="Logo de Vite.js" />

                    <h1 id="HeaderLogoTitle">SegundasHuellas</h1>

                </Link>

                <form id="HeaderSearchContainer">

                    <input id="HeaderSearchInput" type="text" placeholder="Estoy buscando..." />

                    <button id="HeaderSearchSubmit" type="submit">
                        <img id="HeaderSearchIcon" src={`${BASENAME}/search-logo.svg`} alt="" />
                    </button>

                </form>

                <button id="HeaderMenuButton">
                    <img id="HeaderMenuButtonIcon" src={`${BASENAME}/sh-icon-menu.svg`} alt="" />
                </button>
            </nav>
            <ul id="HeaderUserMenu">
                <li className="HeaderUserMenuItem">
                    <h2>{`${username ? username : "Tu nuevo amigo te espera..."}`}</h2>
                </li>
                <li className="HeaderUserMenuItem">
                    <NavLink to={"/login"} id="HeaderLoginButton" className="HeaderUserMenuLink">Iniciar Sesión</NavLink>
                </li>
                <li className="HeaderUserMenuItem">
                    <NavLink to={"/register"} id="HeaderRegisterButton" className="HeaderUserMenuLink">Únete</NavLink>
                </li>
            </ul>
        </header>
    )
}
