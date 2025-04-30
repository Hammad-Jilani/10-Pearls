import { Avatar, AvatarFallback } from '@/components/ui/avatar'
import { Button } from '@/components/ui/button'
import { Dialog, DialogContent, DialogFooter, DialogHeader, DialogTrigger } from '@/components/ui/dialog'
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from '@/components/ui/dropdown-menu'
import { logout } from '@/Redux/Auth/ActionTypes'
import { store } from '@/Redux/Store'

import { DotsVerticalIcon, PersonIcon } from '@radix-ui/react-icons'
import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import CreateForm from '../CreateForm/CreateForm'

function Navbar() {
  const dispatch = useDispatch()
  const {auth} = useSelector(store=>store)

  function handleLogout(){
    dispatch(logout())
  }
  return (
    <div className='w-full '>
      <div className='xl:w-4/5 sm:w-[9/10] mx-auto py-5 flex justify-between'>
        <div className='flex space-x-5'>
          <p className='sm:text-sm xl:text-xl font-semibold'>Contact Management System</p>
          <Dialog>
            <DialogTrigger>
              <Button variant={"secondary"}>Create New Contact</Button>
            </DialogTrigger>

            <DialogContent>
              <DialogHeader className='sm:text-lg xl:text-2xl lg:text-xl'>Contact Information</DialogHeader>
              <CreateForm></CreateForm>
            </DialogContent>
          </Dialog>
        </div>
        <div className='flex space-x-5'>
          <DropdownMenu>
            <DropdownMenuTrigger>
              <Button variant="outline" size="icon" className="rounded-full border-2 border-gray-500">
                <PersonIcon></PersonIcon>
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent>
              <DropdownMenuItem onClick={handleLogout}>
                Logout
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
          <p>{auth.user.username}</p>
        </div>
      </div>
    </div>
  )
}

export default Navbar