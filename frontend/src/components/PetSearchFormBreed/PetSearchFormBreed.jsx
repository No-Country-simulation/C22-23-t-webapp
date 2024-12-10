export function PetSearchFormBreed({ onInputChange, inputValue }) {
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
