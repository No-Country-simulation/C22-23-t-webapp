import './LandingPageFooter.css'

export function LandingPageFooter() {
    return (
        <footer id="LandingPageFooter">
            <div id="LandingPageFooterContainer">

                <div id="LandingPageFooterContent">

                    <div className="footer-column">

                        <h3 className="footer-title">Segundas Huellas</h3>
                        <p className="footer-text">Conectando mascotas con hogares desde 2024.</p>

                    </div>

                    <div className="footer-column">

                        <h4 className="footer-subtitle">Enlaces Rápidos</h4>

                        <ul id="LandingPageFooterLinks">
                            <li><a href="#">Sobre Nosotros</a></li>
                            <li><a href="#">Proceso de Adopción</a></li>
                            <li><a href="#">Tips para el cuidado de mascotas</a></li>
                            <li><a href="#">Contáctate con nosotros</a></li>
                        </ul>

                    </div>

                    <div className="footer-column">

                        <h4 className="footer-subtitle">Mantente Al Tanto</h4>

                        <p className="footer-text">¡Síguenos en redes sociales para novedades y fotos tiernas de mascotas!</p>

                        <div id="LandingPageFooterSocial">
                            <a href="#">Facebook</a>
                            <a href="#">Twitter / X</a>
                            <a href="#">Instagram</a>
                        </div>

                    </div>

                </div>

                <div id="LandingPageFooterBottom">
                    <p>&copy; 2024 Segundas Huellas. Todos los derechos reservados.</p>
                </div>
            </div>
        </footer>
    )
}