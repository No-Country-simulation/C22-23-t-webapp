import './LandingPageHero.css'
import { Link } from 'react-router-dom' 

export function LandingPageHero() {
    return (
        <div id="LandingPageHero">
            <div id="LandingPageHeroContainer">

                <h2 id="LandingPageHeroTitle">Encuentra a tu compañero perfecto</h2>

                <p id="LandingPageHeroDescription">
                Dale un hogar a una mascota y cambia su vida y la tuya para siempre.
                </p>

                <button id="LandingPageHeroButton"><Link to={"/home"}>Empieza a adoptar</Link></button>

            </div>
        </div>
    )
}