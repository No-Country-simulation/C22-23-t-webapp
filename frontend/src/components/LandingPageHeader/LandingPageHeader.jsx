import { useState } from 'react';
import './LandingPageHeader.css'
import { BASENAME } from '../../config.js'
import { Link, NavLink } from 'react-router-dom'

// export function LandingPageHeader() {
//     return (
//         <header id="LandingPageHeader">
//             <div id="LandingPageContainer">
//                 <Link to={"/"} className='header-logo-title'>
//                     <img src="sh-logo.svg" alt="Logo de Segundas Huellas" />
//                     <h1 id="LandingPageTitle">Segundas Huellas</h1>
//                 </Link>
//                 <nav>
//                     <ul id="LandingPageNavList">
//                         <li className="LandingPageNavItem">
//                             <NavLink to={"/search"} className="LandingPageNavLink">Buscar Mascotas</NavLink>
//                         </li>
//                         <li className="LandingPageNavItem">
//                             <NavLink to={"/about"} className="LandingPageNavLink">Sobre Nosotros</NavLink>
//                         </li>
//                         <li className="LandingPageNavItem">
//                             <NavLink to={"/login"} className="LandingPageNavLink">Adoptar</NavLink>
//                         </li>
//                         <li className="LandingPageNavItem">
//                             <NavLink to={"/contact"} className="LandingPageNavLink">Contacto</NavLink>
//                         </li>
//                     </ul>
//                 </nav>
//             </div>
//         </header>
//     )
// }

export function LandingPageHeader() {
    const [menuOpen, setMenuOpen] = useState(false);

    const toggleMenu = () => {
        setMenuOpen(!menuOpen);
    };

    return (
        <header id="LandingPageHeader">
            <div id="LandingPageContainer">
                <Link to={"/"} className="header-logo-title">
                    <img src={`${BASENAME}/sh-logo.svg`} alt="Logo de Segundas Huellas" />
                    <h1 id="LandingPageTitle">Segundas Huellas</h1>
                </Link>
                <nav>
                    <ul id="LandingPageNavList" className={menuOpen ? 'open' : ''}>
                        <li className="LandingPageNavItem">
                            <NavLink to={"/search"} className="LandingPageNavLink">Buscar Mascotas</NavLink>
                        </li>
                        <li className="LandingPageNavItem">
                            <NavLink to={"/about"} className="LandingPageNavLink">Sobre Nosotros</NavLink>
                        </li>
                        <li className="LandingPageNavItem">
                            <NavLink to={"/login"} className="LandingPageNavLink">Adoptar</NavLink>
                        </li>
                        <li className="LandingPageNavItem">
                            <NavLink to={"/contact"} className="LandingPageNavLink">Contacto</NavLink>
                        </li>
                    </ul>
                </nav>
                <div className="menu-icons">
                    <img
                        src={`${BASENAME}/sh-icon-menu.svg`}
                        alt="Menu"
                        className={`menu-icon ${menuOpen ? 'hidden' : ''}`}
                        onClick={toggleMenu}
                    />
                    <img
                        src={`${BASENAME}/sh-icon-close.svg`}
                        alt="Close"
                        className={`close-icon ${menuOpen ? '' : 'hidden'}`}
                        onClick={toggleMenu}
                    />
                </div>
            </div>
        </header>
    );
}
