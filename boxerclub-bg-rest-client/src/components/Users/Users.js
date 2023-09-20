import { useContext } from "react";
import { useEffect, useState } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import { TableHeader } from "../TableHeader/TableHeader";
import { Container, Table } from "react-bootstrap";
import { User } from "./User";
import { DeleteUser } from "./DeleteUser";

export const Users = () => {
  const { onGetAllHandler, users, onUserEdit, onUserDelete } =
    useContext(AuthContext);

  const firstRow = Array.isArray(users) && users.length ? users[0] : {};
  const headersTitle = Object.keys(firstRow);
  const [deleteUserShow, setDeleteUserShow] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);

  useEffect(() => {
    onGetAllHandler();
  }, []);
  const onDeleteClick = (userId) => {
    setSelectedUser(users.filter((u) => u.id === userId));
    setDeleteUserShow(userId);
  };
  const onCloseClick = () => {
    setSelectedUser(null);
    //  setNewUserShow(false);
    setDeleteUserShow(null);
  };
  const onUserDeleteHandler = () => {
    onUserDelete(deleteUserShow);
    setDeleteUserShow(null);
  };

  const onEditClick = (userId) => {
    setSelectedUser(users.filter((u) => u.id === userId));
  };

  return (
    <>
      {deleteUserShow && (
        <DeleteUser
          user={selectedUser}
          onCloseClick={onCloseClick}
          onDelete={onUserDeleteHandler}
        />
      )}
      <Container className='mt-5'>
        <Table responsive='md' striped bordered hover variant='light'>
          <TableHeader title={headersTitle} />
          <tbody>
            {users.length !== 0 &&
              users.map((u) => (
                <User
                  key={u.id}
                  info={u}
                  onDeleteClick={onDeleteClick}
                  onEditClick={onEditClick}
                />
              ))}
          </tbody>
        </Table>
      </Container>
    </>
  );
};
