import { Header, PetDetailsContainer, LandingPageFooter } from '../components/'
import { useParams } from 'react-router-dom'

export function PetDetailsPage() {
    return (
        <>
            <Header />
            <PetDetailsContainer petId={useParams().petId} />
            <LandingPageFooter />
        </>
    )
}
