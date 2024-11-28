import "./Footer.css"
import { Link } from 'react-router-dom'

export function Footer() {
  return (
    
      <footer className="footer-container">
          <p className="text-footer">
            © 2024 PetAdopt. All rights reserved. Contact us: info@petadopt.com
          </p>
          <div className="social-container">
            <div className="social">
              <Link to={"https://www.facebook.com/"}>
                <img src={"./face-logo.svg"} alt="Logo de Facebook" />
              </Link>
              <Link to={"https://www.twitter.com/"}>
                <img src={"./twitter-logo.svg"} alt="Logo de Twitter" />
              </Link>
              <Link to={"https://www.instagram.com/"}>
                <img src={"./ig-logo.svg"} alt="logo de Instagram" />
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
