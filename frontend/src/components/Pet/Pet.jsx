import './Pet.css'
import { BASENAME } from '../../config.js'
import { NavLink } from 'react-router-dom'

export function Pet({ petId, image, name, age, species, gender, location, size}) {
    const convertAgeInDays = (age_in_days) => {
        if (age_in_days < 28)
            return `${age_in_days} día${age_in_days === 1 ? '' : 's'}`

        if (age_in_days < 365) {
            const MONTHS = Math.floor(age_in_days / 28)
            return `${MONTHS} mes${MONTHS === 1 ? '' : 'es'}`
        }

        const YEARS = Math.floor(age_in_days / 365)
        return `${YEARS} año${YEARS === 1 ? '' : 's'}`
    }

    const showGender = (gender) => {
        switch (gender) {
            case "Masculino": return "male"
        
            case "Femenino": return "female"
            
            default:
                // "No definido"
                return "question-mark"
        }
    }

    return (
        <li className='card-container'>
            <img className='image-pet' src={ image } alt={ `Foto de la mascota llamada ${name}` } />
            <div className='text-container'>
                <div id="cardTitleContainer">
                    <h2 className='title-pet'>{ name }</h2>
                    <img src={`${BASENAME}/sh-icon-${showGender(gender)}.svg`} alt={`Símbolo sexo ${gender}`} />
                </div>
                <p className='age-pet'>{ `Edad: ${convertAgeInDays(age)}` }</p>
                <p className='species-pet'>{ `Especie: ${species}` }</p>
                <p className='location-pet'>{ location }</p>
                <p className='location-pet'>{ `Tamaño: ${size}` }</p>
            </div>
            <NavLink to={`/pet/${petId}`} className='btn-pet'>Ver más detalles</NavLink>
        </li>
    )
}
