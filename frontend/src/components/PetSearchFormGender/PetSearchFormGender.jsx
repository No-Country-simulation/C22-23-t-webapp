export function PetSearchFormGender({ onInputChange, inputValue }) {
    return (
        <select
            name="gender"
            id="PetSearchFilterSex"
            className="PetSearchFilter"
            onChange={ onInputChange }
            value={ inputValue }
        >
            <option value="" className="PetSearchFilterOption" disabled>Sexo</option>
            <option value="" className="PetSearchFilterOption">Hembra</option>
            <option value="" className="PetSearchFilterOption">Macho</option>
            <option value="" className="PetSearchFilterOption">No definido</option>
        </select>
    )
}
