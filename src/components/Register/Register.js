import { Button, Container, Form } from "react-bootstrap";
export const RegisterPage = () => {
  return (
    <Container className='m-auto container-sm'>
      <Form className='row g-3 m-auto mt-5 border border-secondary rounded p-5'>
        <Form.Label className='d-inline-block pb-3'>Register</Form.Label>

        <Form.Group className='col-md-6 mb-3' controlId='formBasicFirstName'>
          <Form.Label>First Name</Form.Label>
          <Form.Control type='text' placeholder='Enter your First Name' />
        </Form.Group>
        <Form.Group className='col-md-6 mb-3' controlId='formBasicLastName'>
          <Form.Label>Last Name</Form.Label>
          <Form.Control type='text' placeholder='Enter your Last Name' />
        </Form.Group>
        <Form.Group className='col-md-6 mb-3' controlId='formBasicCountry'>
          <Form.Label>Country</Form.Label>
          <Form.Control type='text' placeholder='Enter your Country' />
        </Form.Group>
        <Form.Group className='col-md-6 mb-3' controlId='formBasicCity'>
          <Form.Label>City</Form.Label>
          <Form.Control type='text' placeholder='Enter your Home City' />
        </Form.Group>

        <Form.Group className='col-md-12  mb-3' controlId='formBasicEmail'>
          <Form.Label>Email address</Form.Label>
          <Form.Control type='email' placeholder='Enter email' />
          <Form.Text className='text-muted'>
            We'll never share your email with anyone else.
          </Form.Text>
        </Form.Group>

        <Form.Group className='col-md-6 mb-3' controlId='formBasicPassword'>
          <Form.Label>Password</Form.Label>
          <Form.Control type='password' placeholder='Password' />
        </Form.Group>
        <Form.Group className='col-md-6 mb-3' controlId='formConfirmPassword'>
          <Form.Label>Confirm Password</Form.Label>
          <Form.Control type='password' placeholder='Confirm Password' />
        </Form.Group>

        <Button className='col-md-2  mb-3' variant='secondary' type='submit'>
          Submit
        </Button>
      </Form>
    </Container>
  );
};
