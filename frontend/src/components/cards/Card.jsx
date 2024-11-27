import React from 'react'
import './Card.css'

function Card(props) {
  return (
    <article className='cards-container'>
        <img className='image-pet' src={`pet-${props.image}.jpeg`} alt='Foto de la pet' />
        <div className='text-container'>
            <h2 className='title-pet'>{props.name}</h2>
            <div className='age-pet'>{props.age} aprox.</div>
            <div className='location-pet'>{props.location}</div>
        </div>
        <button className='btn-pet'>Ver más detalles</button>
    </article>
  )
}

export default Card