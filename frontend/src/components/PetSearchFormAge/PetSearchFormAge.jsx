export function PetSearchFormAge({ onInputChange, inputValue }) {
    return (
        <select
            name="age"
            id="PetSearchFilterAge"
            className="PetSearchFilter"
            onChange={ onInputChange }
            value={ inputValue }
        >
            <option value="" className="PetSearchFilterOption">{ inputValue === "" ? "Edad" : "Quitar filtro por edad" }</option>
            <option value="0-365" className="PetSearchFilterOption">Menos de 1 año</option>
            <option value="365-1825" className="PetSearchFilterOption">1 a 5 años</option>
            <option value="1825-3650" className="PetSearchFilterOption">5 a 10 años</option>
            <option value="3650-inf" className="PetSearchFilterOption">Más de 10 años</option>
        </select>
    )
}
