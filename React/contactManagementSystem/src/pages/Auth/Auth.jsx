import { Button } from '@/components/ui/button'
import React, { useState } from 'react'
import Signup from './Signup'
import Login from './Login'

function Auth() {
  const [active, setActive] = useState(true)

  
  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="w-full max-w-md bg-white shadow-md rounded-2xl p-8 space-y-6">
        <h2 className="text-2xl font-bold text-center text-gray-800">
          {active ? 'Login to your account' : 'Create a new account'}
        </h2>

        <div>
          {active ? <Login /> : <Signup />}
        </div>

        <div className="text-center space-y-2">
          {active ? (
            <>
              <p className="text-sm text-gray-600">Don't have an account?</p>
              <Button
                className="w-full"
                onClick={() => setActive(false)}
              >
                Register
              </Button>
            </>
          ) : (
            <>
              <p className="text-sm text-gray-600">Already have an account?</p>
              <Button
                className="w-full"
                onClick={() => setActive(true)}
              >
                Login
              </Button>
            </>
          )}
        </div>
      </div>
    </div>
  )
}

export default Auth
