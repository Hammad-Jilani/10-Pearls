import { Button } from '@/components/ui/button'
import { Form, FormControl, FormField, FormItem } from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { logout, register } from '@/Redux/Auth/ActionTypes'
import { store } from '@/Redux/Store'
import React from 'react'
import { useForm } from 'react-hook-form'
import { useDispatch, useSelector } from 'react-redux'

function Signup() {
  const {auth} = useSelector(store=>store)
  const form = useForm({
    defaultValues:{
      email:'',
      username:'',
      password:''
      
    }
  })
  const dispatch = useDispatch()
  function onSubmit(data) {
    dispatch(register(data))
  }
  return (
    <div>
      <Form {...form}>
        <form className='space-y-3' onSubmit={form.handleSubmit(onSubmit)}>
          <FormField control={form.control} name="email" render={({field})=>(
            <FormItem>
              <FormControl>
                <Input {...field} placeholder="Email" type="text" className="w-full py-5 px-5"></Input>
              </FormControl>
            </FormItem>
          )}>
            
          </FormField>

          <FormField control={form.control} name="username" render={({field})=>(
            <FormItem>
              <FormControl>
                <Input {...field} placeholder="Username" type="text" className="w-full py-5 px-5"></Input>
              </FormControl>
            </FormItem>
          )}>
            
          </FormField>

          <FormField control={form.control} name="password" render={({field})=>(
            <FormItem>
              <FormControl>
                <Input {...field} placeholder="Password" type="password" className="w-full py-5 px-5"></Input>
              </FormControl>
            </FormItem>
          )}>
            
          </FormField>
          {
            auth?.error?<p className='text-center text-red-600'>{auth.error}</p>:null
          }
          <Button variant={'secondary'} className='w-full' type="submit" >Submit</Button>
        </form>
      </Form>
    </div>
  )
}

export default Signup