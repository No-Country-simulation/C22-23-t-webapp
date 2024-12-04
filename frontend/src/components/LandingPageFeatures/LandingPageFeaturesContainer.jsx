import { LandingPageFeature } from './LandingPageFeature';
import './LandingPageFeatures.css'

export function LandingPageFeaturesContainer() {
    const FEATURES = [
        { title: 'Amplia Variedad', description: 'Navega a través de cientos de mascotas buscando un hogar.' },
        { title: 'Proceso Sencillo', description: 'Nuestro sencillo proceso de adopción hace que traer una nueva mascota sea algo simple.' },
        { title: 'Red de Ayuda', description: 'Accede a recursos y conecta con otros dueños de mascotas para consejos y ayuda.' },
      ];
    
    return (
        <div id="LandingPageFeatures">
            <div id="LandingPageFeaturesContainer">
                <h2 id="LandingPageFeaturesTitle">Porqué elegirnos</h2>
                <div className="LandingPageFeaturesGrid">
                    {FEATURES.map((feature, index) =>   <LandingPageFeature 
                                                            key={index}
                                                            title={feature.title}
                                                            description={feature.description}
                                                        />)}
                </div>
            </div>
        </div>
    )
}