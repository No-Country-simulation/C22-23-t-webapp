import React from 'react'
import './Card.css'

function Card(props) {
  return (
    <div className='cards-container'>
        <img className='image-pet' src={`./public/pet-${props.image}.jpeg`} alt='Foto de la pet' />
        <div className='text-container'>
            <div className='title-pet'>{props.name}</div>
            <div className='age-pet'>{props.age} aprox.</div>
            <div className='location-pet'>{props.location}</div>
        </div>
        <button className='btn-pet'>Ver más detalles</button>
    </div>
  )
}

export default Card