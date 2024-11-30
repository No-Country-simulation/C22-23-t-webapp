import './PetListContainer.css'
import { PetList } from '../'

export function PetListContainer() {
    const PETS = [
        {
            image: "1",
            name: "Buddy",
            age: "2 años",
            location: "Lugar donde se encuentra"
        },
        {
            image: "2",
            name: "Raza",
            age: "1 año",
            location: "Lugar donde se encuentra"
        },
        {
            image: "3",
            name: "Raza",
            age: "5 años",
            location: "Lugar donde se encuentra"
        },
        {
            image: "4",
            name: "Raza",
            age: "2 años",
            location: "Lugar donde se encuentra"
        },
        {
            image: "5",
            name: "Raza",
            age: "6 años",
            location: "Lugar donde se encuentra"
        },
        {
            image: "6",
            name: "Raza",
            age: "9 meses",
            location: "Lugar donde se encuentra"
        },
        {
            image: "7",
            name: "Raza",
            age: "6 años",
            location: "Lugar donde se encuentra"
        },
        {
            image: "8",
            name: "Raza",
            age: "2 años",
            location: "Lugar donde se encuentra"
        }
    ]      

    return (
        <main id="PetListContainer">
            <PetList pets={PETS} />
        </main>
    )
}
