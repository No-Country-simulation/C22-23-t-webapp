import './About.css'
import { Link } from 'react-router-dom' 

export function About() {
    return (
        <main id="AboutContainer">
            <h2 id="AboutTitle">Acerca de Segundas Huellas</h2>
            <section className="AboutSection">
                <p className="AboutText">
                    En <strong>Segundas Huellas</strong>, creemos que cada mascota merece una segunda oportunidad para encontrar un hogar lleno de amor y cuidado. Nuestra plataforma conecta a personas dispuestas a adoptar con animales que necesitan una nueva familia.
                </p>
            </section>
            <section className="AboutSection">
                <h3 className="AboutSubtitle">Nuestra misión</h3>
                <p className="AboutText">
                    Trabajamos para reducir el número de animales sin hogar facilitando el proceso de adopción y promoviendo la tenencia responsable de mascotas. Queremos crear un mundo donde cada mascota tenga un lugar seguro donde vivir.
                </p>
            </section>
            <section className="AboutSection">
                <h3 className="AboutSubtitle">¿Cómo funciona?</h3>
                <ol className="AboutList">
                    <li className="AboutListItem">Explora nuestro catálogo de mascotas disponibles para adopción.</li>
                    <li className="AboutListItem">Consulta los detalles de cada mascota, como su edad, personalidad y necesidades especiales.</li>
                    <li className="AboutListItem">Contacta con las organizaciones o personas responsables para iniciar el proceso de adopción.</li>
                </ol>
            </section>
            <section className="AboutSection">
                <h3 className="AboutSubtitle">¿Por qué elegirnos?</h3>
                <p className="AboutText">
                    <strong>Segundas Huellas</strong> no solo es una plataforma de adopción; es una comunidad comprometida con el bienestar animal. Colaboramos con refugios, organizaciones sin fines de lucro y voluntarios para garantizar que cada adopción sea una experiencia segura y satisfactoria tanto para las mascotas como para sus nuevas familias.
                </p>
            </section>
            <section className="AboutSection">
                <h3 className="AboutSubtitle">Únete a nuestra misión</h3>
                <p className="AboutText">
                    Ya sea adoptando, difundiendo nuestras iniciativas o colaborando como voluntario, tu apoyo es esencial para hacer realidad el sueño de un hogar para cada mascota.
                </p>
            </section>
            <button id="AboutCTAButton"><Link to={"/home"}>Empieza a adoptar</Link></button>
        </main>
    )
}