import React from "react";
import "./Footer.css";

export function Footer() {
  return (
    
      <footer className="footer-container">
          <p className="text-footer">
            © 2024 PetAdopt. All rights reserved. Contact us: info@petadopt.com
          </p>
          <div className="social-container">
            <div className="social">
              <img src={"./face-logo.svg"} alt="Logo de Facebook" />
              <img src={"./twitter-logo.svg"} alt="Logo de Twitter" />
              <img src={"./ig-logo.svg"} alt="logo de Instagram" />
            </div>
            <div className="links">
              <a href="#">Sobre Nosotros</a>
              <a href="#">Adoptar es conciencia</a>
              <a href="#">Contacto</a>
            </div>
          </div>
      </footer>
    
  );
}
