import './Pet.css'
import { NavLink } from 'react-router-dom'


export function Pet({ image, name, age, location}) {
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

    return (
    <li className='card-container'>
        <img className='image-pet' src={ `./pet-${image}.jpeg` } alt={ `Foto de la mascota ${image}` } />
        <div className='text-container'>
            <h2 className='title-pet'>{ name }</h2>
            <h3 className='age-pet'>{ convertAgeInDays(age) }</h3>
            <h3 className='location-pet'>{ location }</h3>
        </div>
        <NavLink to={"/pet"} className='btn-pet'>Ver más detalles</NavLink>
    </li>
    )
}
