export function PetSearchFormAge({ onInputChange, inputValue }) {
    return (
        <select
            name="age"
            id="PetSearchFilterAge"
            className="PetSearchFilter"
            onChange={ onInputChange }
            value={ inputValue }
        >
            <option value="" className="PetSearchFilterOption" disabled>Edad</option>
            <option value="" className="PetSearchFilterOption">Menos de 1 año</option>
            <option value="" className="PetSearchFilterOption">1 a 5 años</option>
            <option value="" className="PetSearchFilterOption">5 a 10 años</option>
            <option value="" className="PetSearchFilterOption">Más de 10 años</option>
        </select>
    )
}
