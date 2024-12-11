import { useEffect, useRef } from 'react'

export function PetSearchFormBreed({ onInputChange, inputValue, speciesValue }) {
    const isFirstRender = useRef(true)

    useEffect(() => {
        if (isFirstRender.current) {
            isFirstRender.current = false
            return
        }
        
        console.log(speciesValue)
    }, [speciesValue])

    return (
        <select
            name="breed"
            id="PetSearchFilterBreed"
            className="PetSearchFilter"
            onChange={ onInputChange }
            value={ inputValue }
        >
            <option value="" className="PetSearchFilterOption" disabled>Raza</option>
            <option value="" className="PetSearchFilterOption">Raza1</option>
        </select>
    )
}
