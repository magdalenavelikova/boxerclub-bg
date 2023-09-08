import { Button, Container, Form } from "react-bootstrap";
import { Link } from "react-router-dom";
export const LoginPage = () => {
  return (
    <Container className='m-auto container-sm'>
      <Form className='m-auto mt-5 mb-5 border border-secondary rounded p-5'>
        <Form.Label className='d-inline-block pb-3'>Login</Form.Label>
        <Form.Group className='mb-3' controlId='formBasicEmail'>
          <Form.Label>Email address</Form.Label>
          <Form.Control type='email' placeholder='Enter email' />
          <Form.Text className='text-muted'>
            We'll never share your email with anyone else.
          </Form.Text>
        </Form.Group>

        <Form.Group className='mb-3' controlId='formBasicPassword'>
          <Form.Label>Password</Form.Label>
          <Form.Control type='password' placeholder='Password' />
        </Form.Group>
        <Form.Group className='mb-3' controlId='formBasicCheckbox'>
          <Form.Check type='checkbox' label='Check me out' />
        </Form.Group>
        <Button variant='secondary' type='submit'>
          Submit
        </Button>
      </Form>
      <Container className='m-auto container-sm'>
        <Link
          className={"link-secondary"}
          to={"/register"}
          style={{ textDecoration: "none" }}>
          Click here if you want to register
        </Link>
      </Container>
    </Container>
  );
};
