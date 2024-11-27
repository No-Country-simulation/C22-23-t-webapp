import './LandingPageHeader.css'

export function LandingPageHeader() {
    return (
        <header id="LandingPageHeader">
            <div id="LandingPageContainer">

                <h1 id="LandingPageTitle">Segundas Huellas</h1>

                <nav>
                    <ul id="LandingPageNavList">

                        <li className="LandingPageNavItem">
                            <a href="#" className="LandingPageNavLink">Home</a>
                        </li>

                        <li className="LandingPageNavItem">
                            <a href="#" className="LandingPageNavLink">Sobre Nosotros</a>
                        </li>

                        <li className="LandingPageNavItem">
                            <a href="#" className="LandingPageNavLink">Adoptar</a>
                        </li>

                        <li className="LandingPageNavItem">
                            <a href="#" className="LandingPageNavLink">Contacto</a>
                        </li>

                    </ul>
                </nav>

            </div>
        </header>
    )
}
