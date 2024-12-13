import './App.css'
import { BASENAME } from './config.js'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { LoginPage, AdoptPage, LandingPage, SearchPage, AboutPage, ContactPage, MissionPage, TipsPage, ProcessPage, PetDetailsPage, RegisterPage } from './pages/'

function App() {
    const WIP_MESSAGE = "Página aún en construcción..."
    const ERROR_MESSAGE = "¡UPS! Esa página no existe..."

    return (
        <BrowserRouter basename={BASENAME}>
            <Routes>
                <Route path="/"           element={ <LandingPage />            } />
                <Route path="/search"     element={ <SearchPage />             } />
                <Route path="/pet/:petId" element={ <PetDetailsPage />         } />
                <Route path="/adopt/:petId" element={ <AdoptPage />   } />
                <Route path="/login"      element={ <LoginPage />              } />
                <Route path="/register"   element={ <RegisterPage />           } />
                <Route path="/about"      element={ <AboutPage />              } />
                <Route path="/recovery"   element={ <h2>{ WIP_MESSAGE }</h2>   } />
                <Route path="/mission"    element={ <MissionPage />            } />
                <Route path="/contact"    element={ <ContactPage />            } />
                <Route path="/tips"       element={ <TipsPage />               } />
                <Route path="/process"    element={ <ProcessPage />            } />
                <Route path="*"           element={ <h2>{ ERROR_MESSAGE }</h2> } />
            </Routes>
        </BrowserRouter>
    )
}

export default App
