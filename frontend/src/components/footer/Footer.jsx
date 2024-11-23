import React from "react";
import "./Footer.css";

function Footer() {
  return (
    <div className="footer-container">
      <div className="fixed">
        <div className="text-footer">
          © 2024 PetAdopt. All rights reserved. Contact us: info@petadopt.com
        </div>
        <div className="footer-social">
          <div className="social">
            <img src={"./public/face-logo.svg"} alt="" />
            <img src={"./public/twitter-logo.svg"} alt="" />
            <img src={"./public/ig-logo.svg"} alt="" />
          </div>
          <div className="links">
            <a href="#">Sobre Nosotros</a>
            <a href="#">Adoptar es conciencia</a>
            <a href="#">Contacto</a>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Footer;
