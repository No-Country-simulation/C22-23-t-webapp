import './Contact.css'

export function Contact() {
    return (
        <main id="ContactContainer" aria-labelledby="contact-title">
            <div className="ContactFormSection" id="ContactTitleContainer">
                <h2 id="ContactTitle">Contáctanos</h2>
                <p id="ContactText">Si tienes alguna pregunta, no dudes en enviarnos un mensaje. ¡Estaremos encantados de ayudarte a encontrar una nueva mascota!</p>
            </div>

            <form
            action="#"
            method="POST"
            aria-labelledby="contact-form"
            id="ContactFormContainer"
            >
                <fieldset id="ContactFormFieldset">
                    <legend id="ContactFormLegend">Formulario de contacto</legend>

                    <div className="ContactFormSection">
                        <label className="ContactFormLabel" htmlFor="ContactFormName">Nombre</label>
                        <input className="ContactFormInput"
                        type="text"
                        id="ContactFormName"
                        name="ContactFormName"
                        placeholder="Tu nombre"
                        required
                        aria-required="true"
                        />
                    </div>

                    <div className="ContactFormSection">
                        <label className="ContactFormLabel" htmlFor="ContactFormEmail">Correo electrónico</label>
                        <input className="ContactFormInput"
                        type="email"
                        id="ContactFormEmail"
                        name="ContactFormEmail"
                        placeholder="Tu correo electrónico"
                        required
                        aria-required="true"
                        />
                    </div>

                    <div className="ContactFormSection">
                        <label className="ContactFormLabel" htmlFor="ContactFormMessage">Mensaje</label>
                        <textarea
                        id="ContactFormMessage"
                        name="ContactFormMessage"
                        rows="4"
                        placeholder="Escribe tu mensaje aquí"
                        required
                        aria-required="true"
                        ></textarea>
                    </div>

                    <div className="ContactFormSection">
                        <button id="ContactFormSubmit" type="submit">Enviar mensaje</button>
                    </div>
                </fieldset>
            </form>
        </main>
    )
}