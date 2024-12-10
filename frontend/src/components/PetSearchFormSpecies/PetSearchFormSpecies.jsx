export function PetSearchFormSpecies({ onInputChange, inputValue }) {
    return (
        <select
            name="species"
            id="PetSearchFilterSpecies"
            className="PetSearchFilter"
            onChange={ onInputChange }
            value={ inputValue }
        >
            <option value="" className="PetSearchFilterOption" disabled>Especie</option>
            <option value="DOG" className="PetSearchFilterOption">Perro</option>
            <option value="CAT" className="PetSearchFilterOption">Gato</option>
            <option value="OTHER" className="PetSearchFilterOption">Otros</option>
        </select>
    )
}
