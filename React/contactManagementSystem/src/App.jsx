import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
// import './App.css'
import './components/ui/button'
import { Button } from './components/ui/button'
import { DotsVerticalIcon } from '@radix-ui/react-icons'
import { Route, Routes } from 'react-router-dom'
import Navbar from './pages/Navbar/Navbar'
import Auth from './pages/Auth/Auth'
import { useDispatch, useSelector } from 'react-redux'
import { store } from './Redux/Store'
import { getUser } from './Redux/Auth/ActionTypes'

function App() {
  const [count, setCount] = useState(0)

  const dispatch = useDispatch()
  const {auth} = useSelector(store=>store)

  useEffect(()=>{
    dispatch(getUser())
  },[auth.jwt])

  return (
    
    <>
      {
        auth.user?
        <>
          <Navbar/>
          {/* <Routes>
            
            <Route path='/'></Route>
          </Routes> */}
        </>
        :<Auth/>
      }
    </>
  )
}

export default App
