import { useContext } from "react";
import { useEffect } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import { TableHeader } from "../TableHeader/TableHeader";
import { Container } from "react-bootstrap";
import { User } from "./User";
import { Table } from "react-bootstrap";
export const Users = () => {
  const { onGetAllHandler, users, onUserEditClick, onUserDeleteClick } =
    useContext(AuthContext);

  const firstRow = Array.isArray(users) && users.length ? users[0] : {};
  const headersTitle = Object.keys(firstRow);
  useEffect(() => {
    onGetAllHandler();
  }, []);

  return (
    <Container className='mt-5'>
      <Table striped bordered hover variant='light'>
        <TableHeader title={headersTitle} />
        <tbody>
          {users.length !== 0 &&
            users.map((u) => (
              <User
                key={u.id}
                info={u}
                onDeleteClick={onUserDeleteClick}
                onEditClick={onUserEditClick}
              />
            ))}
        </tbody>
      </Table>
    </Container>
  );
};
