export function PetSearchFormSpecies({ onInputChange, inputValue, speciesValue }) {
    const handleInputChange = (event) => {
        speciesValue(event.target.value)
        onInputChange(event)
    }

    return (
        <select
            name="species"
            id="PetSearchFilterSpecies"
            className="PetSearchFilter"
            onChange={ handleInputChange }
            value={ inputValue }
        >
            <option value="" className="PetSearchFilterOption">{ inputValue === "" ? "Especie" : "Quitar filtro por especie" }</option>
            <option value="DOG" className="PetSearchFilterOption">Perro</option>
            <option value="CAT" className="PetSearchFilterOption">Gato</option>
            <option value="OTHER" className="PetSearchFilterOption">Otros</option>
        </select>
    )
}
