import './LandingPageFeatures.css'

export function LandingPageFeature({ title, description }) {
    return (
        <>
            <div className="LandingPageFeaturesCard">
                <h3 className="LandingPageFeaturesTitle">{title}</h3>
                <p className="LandingPageFeaturesDescription">{description}</p>
            </div>
        </>
    )
}