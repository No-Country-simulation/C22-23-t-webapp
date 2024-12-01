import './mission.css'

export function Mission() {
    return (
        <main id="MissionContainer">
            <section className="MissionSection" id="MissionHeader">
                <h2 id="MissionTitle">Nuestra Misión</h2>
                <p className="MissionText">
                    En Segundas Huellas, creemos que cada mascota merece una segunda oportunidad. Nuestra misión es resolver la problemática del abandono de animales al conectar a personas, refugios y comunidades a través de una plataforma de adopción responsable. Queremos transformar vidas, una adopción a la vez.
                </p>
            </section>

            <article className="MissionArticle">
                <h3 className="MissionSubtitle">Contexto y Problemática</h3>
                <p className="MissionText">
                    Cada año, millones de animales son abandonados, muchos de ellos en refugios sobrecargados que luchan por brindarles la atención adecuada. El proceso de adopción sigue siendo inaccesible para muchos, lo que perpetúa la situación y deja a innumerables mascotas sin hogar. Es urgente encontrar una solución más eficiente y accesible para resolver este problema.
                </p>
            </article>

            <article className="MissionArticle">
                <h3 className="MissionSubtitle">Nuestra Solución</h3>
                <p className="MissionText">
                    Segundas Huellas no es solo una plataforma de adopción, es un puente que conecta a personas, refugios y comunidades con un objetivo común: salvar vidas. Ofrecemos una plataforma segura y fácil de usar tanto para los adoptantes como para los refugios. A través de nuestra plataforma, cada adopción se convierte en una historia de éxito, donde las mascotas encuentran hogares responsables y amorosos.
                </p>
            </article>

            <section className="MissionSection">
                <h3 className="MissionSubtitle">Modelos de Valor</h3>
                <div className="MissionDivider">
                    <h3>1. Impacto Social</h3>
                    <p className="MissionText">
                        Segundas Huellas aborda el problema de los 70 millones de animales sin hogar en el mundo, ayudando a los refugios a encontrar adoptantes responsables y proporcionando herramientas accesibles para mejorar la calidad de vida de los animales.
                    </p>
                </div>
                <div className="MissionDivider">
                    <h3>2. Problemática Central</h3>
                    <p className="MissionText">
                        Miles de refugios carecen de los recursos necesarios para encontrar hogares adecuados para los animales bajo su cuidado, lo que complica la adopción y compromete el bienestar de estos seres.
                    </p>
                </div>
                <div className="MissionDivider">
                    <h3>3. Nuestra Solución</h3>
                    <p className="MissionText">
                        Nuestra solución se basa en promover la adopción responsable y proporcionar herramientas accesibles a los refugios, para ayudarles a conectar de manera más eficaz con los potenciales adoptantes.
                    </p>
                </div>
            </section>

            <section className="MissionSection">
                <h3 className="MissionSubtitle">Impacto Social y Problemática Central</h3>
                <p className="MissionText">
                    Muchas mascotas se ven abandonadas o entregadas a refugios debido a cambios en las circunstancias de sus dueños, lo que genera angustia e inseguridad tanto para los animales como para los propietarios. Estos cambios no solo afectan a los animales, sino que también generan una gran preocupación en las familias que se ven obligadas a tomar decisiones difíciles.
                </p>
            </section>

            <section className="MissionSection">
                <h3 className="MissionSubtitle">Motivación y Objetivo</h3>
                <div className="MissionDivider">
                    <h4 className="MissionListSubtitle">Motivación</h4>
                    <p className="MissionText">
                        Las personas buscan un nuevo hogar para sus mascotas por diversos motivos, como cambios de circunstancias personales o familiares. En Segundas Huellas, nos aseguramos de que estos animales lleguen a un hogar responsable, brindando tranquilidad a los propietarios que deben tomar esta difícil decisión.
                    </p>
                </div>
                <div className="MissionDivider">
                    <h4 className="MissionListSubtitle">Objetivo</h4>
                    <p className="MissionText">
                        Nuestro objetivo es garantizar que cada mascota sea adoptada por una familia responsable, proporcionando un proceso transparente, accesible y seguro para todos los involucrados.
                    </p>
                </div>
            </section>

            <section className="MissionSection">
                <h3 className="MissionSubtitle">Estrategias de Crecimiento y Sustentabilidad</h3>
                <ul className="MissionUnorderedList">
                    <li className="MissionUnorderedListItem">
                        <strong>Campañas en Redes Sociales:</strong> Utilizaremos plataformas como Instagram, Facebook y Twitter, junto con la colaboración de influencers, para aumentar la visibilidad de Segundas Huellas y fomentar la concienciación sobre la adopción responsable.
                    </li>
                    <li className="MissionUnorderedListItem">
                        <strong>Colaboraciones con Refugios:</strong> Estableceremos alianzas con refugios locales e internacionales, para compartir recursos y aumentar el alcance de nuestras iniciativas en favor de los animales abandonados.
                    </li>
                    <li className="MissionUnorderedListItem">
                        <strong>Crowdfunding y Donaciones:</strong> Impulsaremos campañas de crowdfunding para recaudar fondos destinados a cubrir necesidades específicas de los refugios, mientras fomentamos las donaciones individuales para asegurar la sostenibilidad del proyecto.
                    </li>
                </ul>
            </section>
        </main>
    )
}