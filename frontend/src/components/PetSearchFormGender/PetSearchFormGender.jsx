export function PetSearchFormGender({ onInputChange, inputValue }) {
    return (
        <select
            name="gender"
            id="PetSearchFilterSex"
            className="PetSearchFilter"
            onChange={ onInputChange }
            value={ inputValue }
        >
            <option value="" className="PetSearchFilterOption">{ inputValue === "" ? "Sexo" : "Quitar filtro por sexo" }</option>
            <option value="FEMALE" className="PetSearchFilterOption">Hembra</option>
            <option value="MALE" className="PetSearchFilterOption">Macho</option>
            <option value="UNDEFINED" className="PetSearchFilterOption">No definido</option>
        </select>
    )
}
