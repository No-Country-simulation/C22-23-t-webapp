import './App.css'
import { BASENAME } from './config.js'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import { LandingPage, SearchPage, AboutPage, ContactPage, MissionPage, TipsPage, ProcessPage, PetDetailsPage } from './pages/'

function App() {
    const WIP_MESSAGE = "Página aún en construcción..."
    const ERROR_MESSAGE = "¡UPS! Esa página no existe..."

    return (
        <BrowserRouter basename={BASENAME}>
            <Routes>
                <Route path="/"           element={ <LandingPage />            } />
                <Route path="/home"       element={ <SearchPage />             } />
                <Route path="/pet/:petId" element={ <PetDetailsPage />         } />
                <Route path="/login"      element={ <LoginPage />              } />
                <Route path="/register"   element={ <RegisterPage />           } />
                <Route path="/about"      element={ <AboutPage />              } />
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