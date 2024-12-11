import { useState, useEffect, useRef } from 'react'

export function PetSearchFormBreed({ onInputChange, inputValue, speciesValue }) {
    const isFirstRender = useRef(true)
    const [ breedsList, setBreedsList ] = useState([])

    const fetchBreeds = async (speciesValue) => {
        try {
            if (speciesValue === "") return

            const BASE_URL = "http://localhost:8080/api/pets/reference-data/breeds"
 
            const breedsResponse = await fetch(`${BASE_URL}?species=${speciesValue}`)

            const breedsData = await breedsResponse.json()

            setBreedsList(breedsData)
        } catch (error) {
            console.error(error)
        }
    }

    useEffect(() => {
        if (isFirstRender.current) {
            isFirstRender.current = false
            return
        }
        
        fetchBreeds(speciesValue)
    }, [speciesValue])

    return (
        <select
            name="breed"
            id="PetSearchFilterBreed"
            className="PetSearchFilter"
            onChange={ onInputChange }
            value={ inputValue }
            disabled={!speciesValue || speciesValue === "OTHER"}
        >
            <option value="" className="PetSearchFilterOption" disabled>Raza</option>
            {/* <option value="" className="PetSearchFilterOption">Raza1</option> */}
            {breedsList.map((breed) =>  <option 
                                            key={breed.id}
                                            value={breed.name}
                                            className="PetSearchFilterOption"
                                        >
                                            {breed.name}
                                        </option>)}
        </select>
    )
}
