// import './Adopt.css'
import { useParams } from 'react-router-dom'

export function Adopt() {
    const petId = useParams().petId

    const validateEmail = () => {
        const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
        return re.test(String(email).toLowerCase())
    }

    const validatePhoneNumber = (phoneNumber) => {
        // Definición del regex:
        // 1. ^\+? - El número puede comenzar opcionalmente con un "+".
        // 2. [1-9][0-9]{0,2} - Después del "+" puede haber un código de país de 1 a 3 dígitos.
        // 3. \s? - Espacio opcional después del prefijo del código de país.
        // 4. 9? - El número puede tener un "9" opcional (móviles en Argentina).
        // 5. [0-9]{2,4} - Acepta de 2 a 4 dígitos como código de área/localidad.
        // 6. \s? - Espacio opcional entre el código de área y el número principal.
        // 7. [0-9]{6,8} - Acepta de 6 a 8 dígitos como cuerpo principal del número.
        // 8. (\-[0-9]{1,4})? - Puede tener un guion seguido de 1 a 4 dígitos adicionales (opcional).
        // 9. $ - Fin de la cadena.

        // Números válidos aceptados:
        // - "+54 9 123 456-7890" (con prefijo internacional, código de operador y separadores).
        // - "+5491234567890" (prefijo internacional y número concatenado).
        // - "1234567890" (número local sin prefijo internacional).
        // - "54111234567890" (prefijo internacional sin símbolo '+' y número concatenado).
        // - "123456789" (cualquier secuencia de al menos 9 dígitos).
        const phoneRegex = /^\+?[1-9][0-9]{0,2}\s?9?[0-9]{2,4}\s?[0-9]{6,8}(\-[0-9]{1,4})?$/
      
        return phoneRegex.test(phoneNumber)
    }

    return (
        <main id="AdoptFormContainer">
            <form id="AdoptForm" className="AdoptForm" aria-labelledby="AdoptFormTitle">
                <h2 id="AdoptFormTitle" className="AdoptFormTitle">Formulario de Solicitud de Adopción</h2>
                <p className="AdoptFormDescription">Por favor, complete la información necesaria para procesar su solicitud.</p>

                {/* <!-- Campo de email --> */}
                <div className="AdoptFormField">
                    <label for="AdoptFormEmail" className="AdoptFormLabel">Correo Electrónico:</label>
                    <input 
                        type="email" 
                        id="AdoptFormEmail" 
                        name="email" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="ejemplo@correo.com" 
                    />
                </div>

                {/* <!-- Campo de nombre completo --> */}
                <div className="AdoptFormField">
                    <label for="AdoptFormFullName" className="AdoptFormLabel">Nombre Completo:</label>
                    <input 
                        type="text" 
                        id="AdoptFormFullName" 
                        name="fullName" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su nombre completo" 
                    />
                </div>

                {/* <!-- Campo de edad --> */}
                <div className="AdoptFormField">
                    <label for="AdoptFormAge" className="AdoptFormLabel">Edad:</label>
                    <input 
                        type="number" 
                        id="AdoptFormAge" 
                        name="age" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        min="18" 
                        placeholder="Ingrese su edad" 
                    />
                </div>

                {/* <!-- Campo de dirección --> */}
                <div className="AdoptFormField">
                    <label for="AdoptFormAddress" className="AdoptFormLabel">Dirección de Residencia:</label>
                    <input 
                        type="text" 
                        id="AdoptFormAddress" 
                        name="address" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su dirección" 
                    />
                </div>

                {/* <!-- Campo de ciudad --> */}
                <div className="AdoptFormField">
                    <label for="AdoptFormCity" className="AdoptFormLabel">Ciudad de Residencia:</label>
                    <input 
                        type="text" 
                        id="AdoptFormCity" 
                        name="city" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su ciudad" 
                    />
                </div>

                {/* <!-- Campo de provincia/estado --> */}
                <div className="AdoptFormField">
                    <label for="AdoptFormProvince" className="AdoptFormLabel">Provincia/Estado de Residencia:</label>
                    <input 
                        type="text" 
                        id="AdoptFormProvince" 
                        name="province" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su provincia o estado" 
                    />
                </div>

                {/* <!-- Campo de país --> */}
                <div className="AdoptFormField">
                    <label for="AdoptFormCountry" className="AdoptFormLabel">País de Residencia:</label>
                    <input 
                        type="text" 
                        id="AdoptFormCountry" 
                        name="country" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su país" 
                    />
                </div>

                {/* <!-- Campo de teléfono --> */}
                <div className="AdoptFormField">
                    <label for="AdoptFormPhone" className="AdoptFormLabel">Teléfono:</label>
                    <input 
                        type="tel" 
                        id="AdoptFormPhone" 
                        name="phone" 
                        className="AdoptFormInput" 
                        required 
                        aria-required="true" 
                        placeholder="Ingrese su número de teléfono" 
                    />
                </div>

                {/* <!-- Botón de enviar --> */}
                <div className="AdoptFormActions">
                    <button 
                        type="submit" 
                        className="AdoptFormButton" 
                        aria-label="Enviar solicitud de adopción"
                    >
                    Enviar Solicitud
                    </button>
                </div>
            </form>
        </main>
    )
}
