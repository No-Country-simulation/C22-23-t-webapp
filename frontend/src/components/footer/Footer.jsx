import "./Footer.css"
import { BASENAME } from '../../config.js'
import { Link } from 'react-router-dom'

export function Footer() {
    return (
        <footer className="footer-container">
            <p className="text-footer">© 2024 Segundas Huellas<br />Todos los derechos reservados.</p>
            <div className="social-container">
                <div className="social">
                    <Link to={"https://www.facebook.com/"}>
                        <img src={`${BASENAME}/face-logo.svg`} alt="Logo de Facebook" />
                    </Link>
                    <Link to={"https://www.twitter.com/"}>
                        <img src={`${BASENAME}/twitter-logo.svg`} alt="Logo de Twitter" />
                    </Link>
                    <Link to={"https://www.instagram.com/"}>
                        <img src={`${BASENAME}/ig-logo.svg`} alt="logo de Instagram" />
                    </Link>
                </div>
                <div className="links">
                    <Link to={"/about"}>Sobre Nosotros</Link>
                    <Link to={"/mission"}>Adoptar es conciencia</Link>
                    <Link to={"/contact"}>Contacto</Link>
                </div>
            </div>
        </footer>
    )
}
