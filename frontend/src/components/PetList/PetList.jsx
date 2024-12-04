import './PetList.css'
import { Pet } from '../'

export function PetList({ pets }) {
    return (
        <ul>
            {pets.map((pet, index)  =>  <Pet 
                                            key={index}
                                            image={pet.image}
                                            name={pet.name}
                                            age={pet.age}
                                            location={pet.location}
                                        />)}
        </ul>
    )
}
