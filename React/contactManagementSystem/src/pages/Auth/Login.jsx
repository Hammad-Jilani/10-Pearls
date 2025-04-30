import { Button } from '@/components/ui/button'
import { Form, FormControl, FormField, FormItem } from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { login } from '@/Redux/Auth/ActionTypes'
import { store } from '@/Redux/Store'
import React from 'react'
import { useForm } from 'react-hook-form'
import { useDispatch, useSelector } from 'react-redux'

function Login() {
  const dispatch = useDispatch()

  const {auth} = useSelector(store=>store)
  const form = useForm({
    defaultValues:{
      email:'',
      password:''
    }
  })
  function onSubmit(data){
    dispatch(login(data))  
  }
  return (
    <div className='space-y-5'>
      <h1 className='sm:text-xl lg:text-3xl text-center mb-5'>Login</h1>
      <Form {...form}>

        <form className='space-y-3' onSubmit={form.handleSubmit(onSubmit)}>
          <FormField control={form.control} name="email" render={({field})=>(
            <FormItem>
              <FormControl>
                <Input {...field} type="text" className="w-full py-5 px-5" placeholder="Email"></Input>
              </FormControl>
            </FormItem>
          )}
          >

          </FormField>

          <FormField control={form.control} name="password" render={({field})=>(
            <FormItem>
              <FormControl>
                <Input {...field} type='password' className='py-5 px-5' placeholder="Password"></Input>
              </FormControl>
            </FormItem>
          )}>

          </FormField>


          <Button variant={'secondary'} className='w-full' type="submit" >Submit</Button>

          {
            auth?.error?<p className='text-center text-red-600'>{auth.error}</p>:null
          }
        </form>
      </Form>
    </div>
  )
}

export default Login