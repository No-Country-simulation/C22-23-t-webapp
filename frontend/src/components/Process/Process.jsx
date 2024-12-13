import './Process.css'
import { useEffect } from 'react'
import { useLocation } from 'react-router-dom'


export function Process() {
    const { pathname } = useLocation()
    useEffect(() => { window.scrollTo(0, 0) }, [ pathname ])

    return (
        <main id="ProcessContainer">
            <section id="ProcessHeader">
                <h2 id="ProcessTitle">Proceso de Adopción</h2>
                <p className="ProcessText">
                    En <strong>Segundas Huellas</strong>, creemos que cada mascota merece un hogar lleno de amor y cuidado. Por eso, hemos diseñado un proceso de adopción simple y transparente, enfocado en garantizar el bienestar tanto de las mascotas como de sus futuros adoptantes.
                </p>
            </section>

            <article className="ProcessArticle">
                <h3 className="ProcessSubtitle">Paso 1: Exploración</h3>
                <p className="ProcessText">
                    Navega por nuestra plataforma para conocer a las mascotas disponibles para adopción. Cada perfil incluye información detallada sobre su personalidad, necesidades especiales y cuidados recomendados. 
                </p>
            </article>

            <article className="ProcessArticle">
                <h3 className="ProcessSubtitle">Paso 2: Solicitud de Adopción</h3>
                <p className="ProcessText">
                Una vez que encuentres a tu compañero ideal, completa el formulario de solicitud en línea. Este nos ayudará a conocerte mejor y evaluar si eres el mejor match para la mascota.
                </p>
            </article>

            <article className="ProcessArticle">
                <h3 className="ProcessSubtitle">Paso 3: Entrevista y Evaluación</h3>
                <p className="ProcessText">
                Nuestro equipo revisará tu solicitud y se pondrá en contacto para programar una entrevista. Durante esta etapa, discutiremos tus expectativas, resolveremos dudas y verificaremos que el entorno sea adecuado para la mascota.
                </p>
            </article>

            <article className="ProcessArticle">
                <h3 className="ProcessSubtitle">Paso 4: Encuentro</h3>
                <p className="ProcessText">
                Organizaremos un encuentro con la mascota para que ambos puedan conocerse en persona. Este paso es clave para asegurarnos de que hay una conexión especial entre ustedes.
                </p>
            </article>

            <article className="ProcessArticle">
                <h3 className="ProcessSubtitle">Paso 5: Adopción Final</h3>
                <p className="ProcessText">
                Si todo está en orden, se procederá a la firma del acuerdo de adopción. También recibirás una guía con consejos para el cuidado de tu nueva mascota, y nuestro equipo estará disponible para cualquier consulta futura.
                </p>
            </article>

            <section id="ProcessFooter">
                <p className="ProcessText">
                ¡Gracias por considerar dar una segunda oportunidad a una mascota! Tu decisión no solo cambiará su vida, sino también la tuya.
                </p>
            </section>
        </main>
    )
}
