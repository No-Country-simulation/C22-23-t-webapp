import { Header, PetDetailsContainer, Footer } from '../components/'
import { useParams } from 'react-router-dom'

export function PetDetailsPage() {
    return (
        <>
            <Header />
            <PetDetailsContainer petId={useParams().petId} />
            <Footer />
        </>
    )
}
