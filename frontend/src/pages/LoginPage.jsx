import React from 'react'
import Login from '../components/Login/Login'
import { LandingPageFooter, LandingPageHeader } from '../components'

function LoginPage() {
  return (
    <>
    <LandingPageHeader />
    <Login />
    <LandingPageFooter />
    </>
  )
}

export default LoginPage