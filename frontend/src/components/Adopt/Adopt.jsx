// import './Adopt.css'
import { useParams } from 'react-router-dom'

export function Adopt() {
    const petId = useParams().petId

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

                {/* <!-- Campo de contraseña --> */}
                <div className="AdoptFormField">
                    <label for="AdoptFormPassword" className="AdoptFormLabel">Contraseña:</label>
                    <input 
                    type="password" 
                    id="AdoptFormPassword" 
                    name="password" 
                    className="AdoptFormInput" 
                    required 
                    aria-required="true" 
                    placeholder="Ingrese una contraseña segura" 
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
