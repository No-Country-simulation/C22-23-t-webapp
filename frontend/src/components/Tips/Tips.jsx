import './Tips.css'
import { useEffect } from 'react'
import { useLocation } from 'react-router-dom'


export function Tips() {
    const { pathname } = useLocation()
    useEffect(() => { window.scrollTo(0, 0) }, [ pathname ])

    return (
        <main id="TipsContainer">
            <section id="TipsHeader">
                <h2 id="TipsTitle">Tips para el Cuidado de Mascotas</h2>
                <p className="TipsText">
                    En <strong>Segundas Huellas</strong>, queremos que tu mascota tenga la mejor calidad de vida. Aquí tienes algunos consejos útiles para asegurarte de que tu compañero esté sano y feliz.
                </p>
            </section>
            
            <article className="TipsArticle">
                <h3 className="TipsSubtitle">1. Alimentación Balanceada</h3>
                <p className="TipsText">
                    Asegúrate de proporcionar una dieta adecuada según la especie, raza, edad y tamaño de tu mascota. Consulta a un veterinario para elegir el mejor alimento y recuerda no ofrecerles comida que pueda ser dañina, como chocolate, uvas o alimentos muy salados.
                </p>
            </article>
            
            <article className="TipsArticle">
                <h3 className="TipsSubtitle">2. Ejercicio Regular</h3>
                <p className="TipsText">
                    Las mascotas necesitan ejercicio para mantenerse activas y prevenir problemas de salud como la obesidad. Los perros, por ejemplo, disfrutan de paseos diarios y juegos al aire libre, mientras que los gatos pueden beneficiarse de juguetes interactivos y zonas para escalar.
                </p>
            </article>
            
            <article className="TipsArticle">
                <h3 className="TipsSubtitle">3. Visitas al Veterinario</h3>
                <p className="TipsText">
                    No olvides llevar a tu mascota al veterinario para revisiones periódicas y vacunaciones. La prevención es clave para evitar enfermedades graves y garantizar una vida larga y saludable.
                </p>
            </article>
            
            <article className="TipsArticle">
                <h3 className="TipsSubtitle">4. Espacios Seguros</h3>
                <p className="TipsText">
                    Crea un entorno seguro para tu mascota en casa. Asegúrate de que no haya objetos peligrosos a su alcance y considera instalar cercos o redes de protección si tienes balcones o jardines.
                </p>
            </article>
            
            <article className="TipsArticle">
                <h3 className="TipsSubtitle">5. Mucho Amor y Atención</h3>
                <p className="TipsText">
                    Más allá de los cuidados físicos, las mascotas necesitan atención, cariño y tiempo de calidad contigo. Esto refuerza su bienestar emocional y fortalece el vínculo que tienen contigo.
                </p>
            </article>

            <section id="TipsFooter">
                <p className="TipsText">
                    Si tienes dudas específicas sobre el cuidado de tu mascota, no dudes en contactarnos o buscar orientación profesional. En <strong>Segundas Huellas</strong>, estamos aquí para ayudarte.
                </p>
            </section>
        </main>
    )
}
