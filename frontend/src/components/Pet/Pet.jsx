import './Pet.css'
import { NavLink } from 'react-router-dom'


export function Pet({ image, name, age, location}) {
    return (
    <li className='card-container'>
        <img className='image-pet' src={ `./pet-${image}.jpeg` } alt={ `Foto de la mascota ${image}` } />
        <div className='text-container'>
            <h2 className='title-pet'>{ name }</h2>
            <h3 className='age-pet'>{ age } aprox.</h3>
            <h3 className='location-pet'>{ location }</h3>
        </div>
        <NavLink to={"/pet"} className='btn-pet'>Ver más detalles</NavLink>
    </li>
    )
}
