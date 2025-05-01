import { Button } from '@/components/ui/button'
import { Form, FormControl, FormField, FormItem } from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { updateContact } from '@/Redux/Contact/Action'
import { store } from '@/Redux/Store'
import React from 'react'
import { useForm } from 'react-hook-form'
import { useDispatch, useSelector } from 'react-redux'
import { toast } from 'sonner'

function UpdateForm({contact,setDialogOpen}) {

  const dispatch = useDispatch()
  const {auth}=useSelector(store=>store)

  const form = useForm({
    defaultValues:{
      name:contact.name,
      number:contact.number,
      email:contact.email,
      contactId:contact.contactId,
      user:auth.user
    }
  })

  function onSubmit(data){
    dispatch(updateContact({contactId:contact.contactId,dataUpdate:data}))
    toast.success("Contact Updated Successfuly")
    setDialogOpen(false)
  }

  return (
    <div>
      <Form {...form}>
        <form className='space-y-3' onSubmit={form.handleSubmit(onSubmit)}>
          <FormField control={form.control} name='name' render={({field})=>(
            <FormItem>
              <FormControl>
                <Input {...field} type='text' placeholder='Name'></Input>
              </FormControl>
            </FormItem>
          )}>

          </FormField>
          <FormField control={form.control} name='email' render={({field})=>(
            <FormItem>
              <FormControl>
                <Input {...field} type='text' placeholder='Email'></Input>
              </FormControl>
            </FormItem>
          )}>

          </FormField>
          <FormField control={form.control} name='number' render={({field})=>(
            <FormItem>
              <FormControl>
                <Input {...field} type='text' placeholder='Number'></Input>
              </FormControl>
            </FormItem>
          )}>

          </FormField>
          <Button variant={'secondary'} type="submit">Edit</Button>
        </form>
      </Form>
    </div>
  )
}

export default UpdateForm