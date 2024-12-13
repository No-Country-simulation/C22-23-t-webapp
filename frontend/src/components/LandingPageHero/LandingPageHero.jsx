import './LandingPageHero.css'
import { BASENAME } from '../../config.js'
import { Link } from 'react-router-dom' 

export function LandingPageHero() {
    const BACKGROUND_STYLE = {
        backgroundImage: `url(${BASENAME}/sh-image-hero-designedByFreepik.jpg)`
    }
    
    return (
        <div id="LandingPageHero" style={ BACKGROUND_STYLE }>
            <div id="LandingPageHeroContainer">

                <h2 id="LandingPageHeroTitle">Encuentra a tu compañero perfecto</h2>

                <p id="LandingPageHeroDescription">
                Dale un hogar a una mascota y cambia su vida y la tuya para siempre.
                </p>

                <button id="LandingPageHeroButton"><Link to={"/search"}>Empieza a adoptar</Link></button>

            </div>
        </div>
    )
}