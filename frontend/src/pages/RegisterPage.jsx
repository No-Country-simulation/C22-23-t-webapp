import React from 'react'
import Register from '../components/Register/Register'
import { LandingPageFooter, LandingPageHeader } from '../components'

function RegisterPage() {
  return (
    <>
    <LandingPageHeader />
    <Register />
    <LandingPageFooter />
    </>
  )
}

export default RegisterPage