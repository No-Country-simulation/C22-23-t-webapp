import "./Header.css"
import { BASENAME } from '../../config.js'
import { Link, NavLink } from 'react-router-dom'
import { useState } from 'react'

export function Header() {
    const [ username, setUsername ] = useState() // La implementación del login hará uso de setUsername()
    const [ isMenuOpen, setIsMenuOpen ] = useState(false)
    
    const handleMenuClick = () => { setIsMenuOpen(!isMenuOpen) }

    return (
        <header id="Header">
            <nav id="HeaderNavbar">
                <Link to={"/"} id="HeaderLogo">
                    <img id="HeaderLogoImage" src={`${BASENAME}/sh-logo.svg`} alt="Logo de Segundas Huellas" />

                    <h1 id="HeaderLogoTitle">SegundasHuellas</h1>
                </Link>
                <button id="HeaderMenuButton" onClick={ handleMenuClick }>
                    <img id="HeaderMenuButtonIcon" src={`${BASENAME}/sh-icon-${isMenuOpen ? 'close' : 'account-circle'}.svg`} alt={isMenuOpen ? 'Icono cerrar menú' : 'Icono abrir menú'} />
                </button>
            </nav>
            <ul id="HeaderUserMenu" className={ isMenuOpen ? 'open' : '' }>
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
