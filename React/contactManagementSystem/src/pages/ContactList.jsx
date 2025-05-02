import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Button } from '@/components/ui/button';
import { Card } from '@/components/ui/card';
import { deleteContact, fetchContacts, searchContactList } from '@/Redux/Contact/Action';
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from '@/components/ui/dropdown-menu';
import { DotsVerticalIcon, MagnifyingGlassIcon } from '@radix-ui/react-icons';
import { Dialog, DialogContent, DialogHeader, DialogTrigger } from '@/components/ui/dialog';
import UpdateForm from './UpdateForm';
import { toast } from 'sonner';
import { Input } from '@/components/ui/input';
import { Form, FormControl, FormField, FormItem } from '@/components/ui/form';
import { useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';

function ContactList() {
  const dispatch = useDispatch();
  const { contact } = useSelector(store => store);
  const navigate = useNavigate()

  const [updateContact,setUpdateContact]=useState(null)
  const [dialogOpen,setDialogOpen] = useState(false)
  const [searchContact,setSearchContact] = useState('')
  

  useEffect(() => {
    dispatch(fetchContacts(contact.page));
  }, [dispatch, contact.page]);


  const handlePageChange = (newPage) => {
    if (newPage >= 0 && newPage <= contact.totalPages) {
      dispatch(fetchContacts(newPage));
    }
  };

  const form = useForm({
    defaultValues:{
      search:''
    }
  })

  if (contact.loading) {
    return <p className="text-center text-lg mt-8 text-gray-600">Loading...</p>;
  }

  if (contact.error) {
    return <p className="text-center text-lg mt-8 text-red-500">{contact.error}</p>;
  }

  function handleDelete(id){
    toast.success("Deleted successfully")
    dispatch(deleteContact({contactId:id}))
  }

  function handleUpdateContact(c){
    console.log(c);
    setUpdateContact(c)
    setDialogOpen(true)
  }

  function onSubmit(data){
    console.log(searchContact);
    setSearchContact(data.search)
    dispatch(searchContactList(data.search))
  }

  function handleCard(c){
    navigate(`/details/${c.contactId}`)
    
  }

  return (
    <div className="w-full max-w-4xl mx-auto mt-10 space-y-4 px-4">

      <Form {...form}>
        <form className="w-full flex justify-center space-x-3" onSubmit={form.handleSubmit(onSubmit)}>
          <FormField
            control={form.control}
            name="search"
            render={({ field }) => (
              <FormItem>
                <FormControl>
                  <Input
                    {...field}
                    placeholder="Search Contact"
                    type="text"
                    className="xl:w-96 text-base border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 shadow-sm transition-all duration-200"
                  />
                </FormControl>
              </FormItem>
            )}
          />
          <Button type="submit">
            Search
          </Button>
        </form>
      </Form>

      {
        searchContact && <div className='text-xl font-semibold w-full text-center'>Searched Contact</div>
      }

      { searchContact &&
      contact.searchContact.map((c) => (
          
        <Card key={c.contactId} className="p-4 shadow-md rounded-2xl border w-1/2 my-3 mx-auto border-gray-200" >
          
          <div className='flex items-center'>
            <div className='w-2/3 text-center'>
              <p className="text-lg font-semibold text-gray-800">{c.name}</p>
              <p className="text-gray-600">{c.email}</p>
              <p className="text-gray-600">{c.number}</p>
            </div>
            <div className='w-1/2 text-right'>
              <DropdownMenu>
                <DropdownMenuTrigger>
                    <Button variant={"secondary"}><DotsVerticalIcon></DotsVerticalIcon></Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent>
                  <DropdownMenuItem onClick={()=>handleDelete(c.contactId)}>
                    Delete
                  </DropdownMenuItem>
                  <DropdownMenuItem onClick={()=>handleUpdateContact(c)}>
                    Update
                  </DropdownMenuItem>
                  <DropdownMenuItem onClick={()=>handleCard(c)}>
                    Display
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
          </div>
        </Card>
      
      ))}
      
      <h2 className="text-2xl font-bold text-center text-gray-800 mt-14">Your Contacts</h2>

      {contact.contact.length === 0 ? (
        <p className="text-center text-gray-500">No contacts found.</p>
      ) : (
        <div className='xl:flex xl:gap-2 xl:justify-center w-full'>
        {contact.contact.map((c) => (
          
          <Card key={c.contactId} className="p-4 shadow-md rounded-2xl border w-1/2 my-2 mx-auto border-gray-200">
            
            <div className='flex items-center'>
              <div className='w-2/3 text-center'>
                <p className="text-lg font-semibold text-gray-800">{c.name}</p>
                <p className="text-gray-600">{c.email}</p>
                <p className="text-gray-600">{c.number}</p>
              </div>
              <div className='w-1/2 text-right'>
                <DropdownMenu>
                  <DropdownMenuTrigger>
                      <Button variant={"secondary"}><DotsVerticalIcon></DotsVerticalIcon></Button>
                  </DropdownMenuTrigger>
                  <DropdownMenuContent>
                    <DropdownMenuItem onClick={()=>handleDelete(c.contactId)}>
                      Delete
                    </DropdownMenuItem>
                    <DropdownMenuItem onClick={()=>handleUpdateContact(c)}>
                      Update
                    </DropdownMenuItem>
                    <DropdownMenuItem onClick={()=>handleCard((c))}>
                      Display
                    </DropdownMenuItem>
                  </DropdownMenuContent>
                </DropdownMenu>
              </div>
            </div>
          </Card>
        
        ))}
        </div>
      )}

      <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
        <DialogContent>
          <DialogHeader>
            Edit Profile
          </DialogHeader>
          {
            updateContact && <UpdateForm contact={updateContact} setDialogOpen={setDialogOpen}></UpdateForm>
          }
        </DialogContent>
      </Dialog>

      <div className="flex justify-center items-center space-x-4 mt-6">
        <Button
          variant="outline"
          onClick={() => handlePageChange(contact.page - 1)}
          disabled={contact.page <= 0}
        >
          Previous
        </Button>

        <span className="text-gray-700 font-medium">
          Page {contact.page+1} of {contact.totalPages}
        </span>

        <Button
          variant="outline"
          onClick={() => handlePageChange(contact.page + 1)}
          disabled={contact.page >= contact.totalPages-1}
        >
          Next
        </Button>
      </div>
    </div>
  );
}

export default ContactList;