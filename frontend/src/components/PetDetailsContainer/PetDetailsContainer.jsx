import './PetDetailsContainer.css'
import { PetDetails } from '../'
import { useEffect, useState } from 'react'

export function PetDetailsContainer({ petId }) {    
    const [pet, setPet] = useState()
    const [loading, setLoading] = useState(true)
    const GREETING = "Espera mientras juntamos los detalles de esta mascota..."
    const ERROR_MESSAGE = "¡UPS! Parece que esta mascota no existe..."

    const fetchPet = async () => {
        try {
            const petResponse = await fetch((`http://localhost:8080/api/pets/${petId}`), { 
                method: 'GET'
            })

            const petData = await petResponse.json()
            setPet(petData)
            setLoading(false)
        } catch (error) {
            console.error(error)
        }
    }

    useEffect(() => {
        fetchPet()
        console.log(pet)
    }, [petId])

    return (
        <main id="PetDetailsContainer">
            {loading && <h2 id="PetDetailsLoadingGreet">{GREETING}</h2>}
            {!loading && pet && <PetDetails pet={pet} />}
            {!loading && !pet && <h2 id="PetDetailsErrorMessage">{ERROR_MESSAGE}</h2>}
        </main>
    )
}
