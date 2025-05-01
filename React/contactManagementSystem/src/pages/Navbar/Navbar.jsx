import { Avatar, AvatarFallback } from '@/components/ui/avatar'
import { Button } from '@/components/ui/button'
import { Dialog, DialogClose, DialogContent, DialogFooter, DialogHeader, DialogTrigger } from '@/components/ui/dialog'
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from '@/components/ui/dropdown-menu'
import { logout } from '@/Redux/Auth/ActionTypes'
import { store } from '@/Redux/Store'

import { DotsVerticalIcon, PersonIcon } from '@radix-ui/react-icons'
import React, { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import CreateForm from '../CreateForm/CreateForm'
import ChangePassword from './ChangePassword'

function Navbar() {
  const dispatch = useDispatch()
  const {auth} = useSelector(store=>store)
  const [dialogOpen,setDialogOpen] = useState(false)
  const [user,setUser] = useState(null)
 
  function handleLogout(){
    dispatch(logout())
  }

  function handleChangePassword(passeduser){
    setDialogOpen(true)
    setUser(passeduser)
  }

  return (
    <div className='w-full '>
      <div className='w-4/5 mx-auto py-5 flex justify-between items-center'>
        <div className='flex space-x-2'>
          <p className='text-sm md:text-xl font-semibold cursor-pointer'>Contact Management System</p>
          <Dialog>
            <DialogTrigger>
              <Button onClick={()=>setDialogOpen(true)} >Create New Contact</Button>
            </DialogTrigger>

            <DialogContent>
              <DialogHeader className='sm:text-lg xl:text-2xl lg:text-xl'>Contact Information</DialogHeader>
              <CreateForm setDialogOpen={setDialogOpen}></CreateForm>
            </DialogContent>
          </Dialog>
        </div>
        <div className='flex mx-3 lg:gap-5 items-center'>
          <DropdownMenu>
            <DropdownMenuTrigger>
              <Button variant="outline" size="icon" className="rounded-full border-2 border-gray-500">
                <PersonIcon></PersonIcon>
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent>
              <DropdownMenuItem onClick={(auth)=>handleChangePassword(auth.user)}>
                Change Password
              </DropdownMenuItem>
              <DropdownMenuItem onClick={handleLogout}>
                Logout
              </DropdownMenuItem>
              
            </DropdownMenuContent>
          </DropdownMenu>
          <p className='text-base'>{auth.user.username}</p>
        </div>

        
      </div>
      <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
          <DialogContent>
            <DialogHeader>Change Password</DialogHeader>
            <ChangePassword setDialogOpen={setDialogOpen}></ChangePassword>
          </DialogContent>
        </Dialog>
    </div>
  )
}

export default Navbar