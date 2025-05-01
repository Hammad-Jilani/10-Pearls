import { Button } from '@/components/ui/button';
import { Form, FormControl, FormField, FormItem } from '@/components/ui/form'
import { Input } from '@/components/ui/input';
import { createContact } from '@/Redux/Contact/Action';
import React from 'react'
import { useForm } from 'react-hook-form'
import { useDispatch } from 'react-redux';
import { toast } from 'sonner';

function CreateForm({setDialogOpen}) {

  const dispatch = useDispatch()
  const form = useForm({
    defaultValues:{
      email:'',
      name:'',
      number:''
    }
  })
  function onSubmit(data) {
    console.log(data)
    toast.success("Contact Created successfully")
    setDialogOpen(false)
    dispatch(createContact(data))
  }
  return (

    <div>
      <Form {...form}>
        <form className='space-y-3' onSubmit={form.handleSubmit(onSubmit)}>
          <FormField control={form.control} name="email" render={({field})=>(
            <FormItem>
              <FormControl>
                <Input {...field} type="text" placeholder='Email'></Input>
              </FormControl>
            </FormItem>
          )}>            
          </FormField>

          <FormField control={form.control} name="name" render={({field})=>(
            <FormItem>
              <FormControl>
                <Input {...field} type="text" placeholder='Name'></Input>
              </FormControl>
            </FormItem>
          )}>            
          </FormField>

          <FormField control={form.control} name="number" render={({field})=>(
            <FormItem>
              <FormControl>
                <Input {...field} type="text" placeholder='Contact Number'></Input>
              </FormControl>
            </FormItem>
          )}>            
          </FormField>

          <Button type="submit">Save</Button>
        </form>
      </Form>
    </div>
  )
}

export default CreateForm