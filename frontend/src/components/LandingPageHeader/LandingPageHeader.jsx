import './LandingPageHeader.css'
import { NavLink } from 'react-router-dom'

export function LandingPageHeader() {
    return (
        <header id="LandingPageHeader">
            <div id="LandingPageContainer">
                <h1 id="LandingPageTitle">Segundas Huellas</h1>
                <nav>
                    <ul id="LandingPageNavList">
                        <li className="LandingPageNavItem">
                            <NavLink to={"/home"} className="LandingPageNavLink">Home</NavLink>
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
            </div>
        </header>
    )
}
