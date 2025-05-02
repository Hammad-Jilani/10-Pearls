import { Card } from '@/components/ui/card'
import { getContactDetails } from '@/Redux/Contact/Action'
import { store } from '@/Redux/Store'
import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'

function ContactDetail() {
  const {id} = useParams()
  const {contact} = useSelector(store=>store)
  const dispatch = useDispatch()
  console.log('Contact Detail',contact.contactDetail);
  
  useEffect(()=>{
    dispatch(getContactDetails({contactId:id}))
  },[])
  return (
    <div className='w-full mt-5 flex items-center justify-center'>
      <Card className='w-1/2'>
        <div className='space-y-3'>
          <p className='lg:text-2xl text-md font-semibold text-center'>Contact Name : {contact.contactDetail?.name}</p>
          <p className='text-center text-sm lg:text-lg'>Contact Phone Number : {contact.contactDetail?.number}</p>
          <p className='text-center text-sm lg:text-lg'>Contact Email Address : {contact.contactDetail?.email}</p>
        </div>
      </Card>
    </div>
  )
}

export default ContactDetail