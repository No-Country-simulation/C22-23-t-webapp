import './LandingPageFooter.css'
import { Link } from 'react-router-dom'

export function LandingPageFooter() {
    return (
        <footer id="LandingPageFooterContainer">
            <div id="LandingPageFooterContent">
                <div className="footer-column">
                    <h2 className="footer-title">Segundas Huellas</h2>                        
                    <p className="footer-text">Conectando mascotas con hogares desde 2024.</p>
                </div>
                <div className="footer-column">
                    <h3 className="footer-subtitle">Enlaces rápidos</h3>

                    <ul id="LandingPageFooterLinks">
                        <li><Link to={"/about"}>Sobre Nosotros</Link></li>
                        <li><Link to={"/process"}>Proceso de Adopción</Link></li>
                        <li><Link to={"/tips"}>Tips para el cuidado de mascotas</Link></li>
                        <li><Link to={"/contact"}>Contáctate con nosotros</Link></li>
                    </ul>
                </div>
                <div className="footer-column">
                    <h3 className="footer-subtitle">Mantente al tanto</h3>

                    <p className="footer-text">¡Síguenos en redes sociales para novedades y fotos tiernas de mascotas!</p>

                    <ul id="LandingPageFooterSocial">
                        <li><Link to={"https://www.twitter.com/"}>Twitter / X</Link></li>
                        <li><Link to={"https://www.facebook.com/"}>Facebook</Link></li>
                        <li><Link to={"https://www.instagram.com/"}>Instagram</Link></li>
                    </ul>
                </div>
            </div>
            <div id="LandingPageFooterBottom">
                <p>&copy; 2024 Segundas Huellas. Todos los derechos reservados.</p>
            </div>
        </footer>
    )
}