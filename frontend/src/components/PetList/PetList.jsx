import './PetList.css'
import { Pet } from '../'

export function PetList({ pets }) {
    return (
        <ul>
            {pets.map((pet)  => <Pet 
                                    key={pet.id}
                                    petId={pet.id}
                                    image={pet.photo.thumbnailUrl}
                                    name={pet.name}
                                    age={pet.ageInDays}
                                    species={pet.species}
                                    gender={pet.gender}
                                    location={pet.location}
                                    status={pet.status}
                                />)}
        </ul>
    )
}
