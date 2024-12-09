import './LandingPageHeader.css'
import { Link, NavLink } from 'react-router-dom'

export function LandingPageHeader() {
    return (
        <header id="LandingPageHeader">
            <div id="LandingPageContainer">
                <Link to={"/"}>
                    <h1 id="LandingPageTitle">Segundas Huellas</h1>
                </Link>
                <nav>
                    <ul id="LandingPageNavList">
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
            </div>
        </header>
    )
}
