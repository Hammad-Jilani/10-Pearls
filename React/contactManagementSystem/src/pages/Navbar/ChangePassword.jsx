import { Button } from '@/components/ui/button'
import { Form, FormControl, FormField, FormItem } from '@/components/ui/form'
import { Input } from '@/components/ui/input'
import { changePassword } from '@/Redux/Auth/ActionTypes'
import { store } from '@/Redux/Store'
import React from 'react'
import { useForm } from 'react-hook-form'
import { useDispatch, useSelector } from 'react-redux'
import { toast } from 'sonner'

function ChangePassword({setDialogOpen}) {

  const dispatch = useDispatch()
  const {auth} = useSelector(store=>store)

  const form = useForm({
    defaultValues:{
      oldPassword:'',
      newPassword:''
    }
  })

  function onSubmit(data){
    console.log(data,'auth ',auth);
    setDialogOpen(false)
    dispatch(changePassword(data))
    
  }

  return (
    <div>

      
      <Form {...form}>
        <form className="space-y-5" onSubmit={form.handleSubmit(onSubmit)}>
          <FormField
            control={form.control}
            name="oldPassword"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input
                    {...field}
                    placeholder="Enter Old Password"
                    type="text"
                  />
                </FormControl>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="newPassword"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input
                    {...field}
                    placeholder="Enter New Password"
                    type="text"
                  />
                </FormControl>
              </FormItem>
            )}
          />
          <Button type="submit">
            Save
          </Button>
        </form>
      </Form>
    </div>
  )
}

export default  ChangePassword