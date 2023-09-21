import { useContext } from "react";
import { useEffect, useState } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import { TableHeader } from "../TableHeader/TableHeader";
import { Container, Table } from "react-bootstrap";
import { User } from "./User";
import { DeleteUser } from "./DeleteUser";
import { EditUser } from "./EditUser";

export const Users = () => {
  const {
    onGetAllUsersHandler,
    onGetAllRoles,
    users,
    onUserEdit,
    onUserDelete,
  } = useContext(AuthContext);

  const firstRow = Array.isArray(users) && users.length ? users[0] : {};
  const headersTitle = Object.keys(firstRow);
  const [deleteUserShow, setDeleteUserShow] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);
  const [editUserShow, setEditUserShow] = useState(null);
  const [usersList, setUsersList] = useState([]);

  useEffect(() => {
    setUsersList(users);
  }, [users]);

  useEffect(() => {
    onGetAllUsersHandler();
    onGetAllRoles();
  }, []);

  const onDeleteClick = (userId) => {
    setSelectedUser(usersList.filter((u) => u.id === userId));
    setDeleteUserShow(userId);
  };
  const onCloseClick = () => {
    setSelectedUser(null);
    setDeleteUserShow(null);
    setEditUserShow(null);
  };
  const onUserDeleteHandler = () => {
    onUserDelete(deleteUserShow);
    setDeleteUserShow(null);
  };
  const onUserEditHandler = (e) => {
    onUserEdit(selectedUser.id, e);
    setEditUserShow(null);
  };
  const onEditClick = (userId) => {
    setSelectedUser(users.filter((u) => u.id === userId));
    setEditUserShow(userId);
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
      {editUserShow && (
        <EditUser
          user={selectedUser[0]}
          onCloseClick={onCloseClick}
          onUserEdit={onUserEditHandler}
        />
      )}
      {usersList.length !== 0 && (
        <Container className='mt-5'>
          <Table responsive='md' striped bordered hover variant='light'>
            <TableHeader title={headersTitle} />
            <tbody>
              {usersList.length !== 0 &&
                usersList.map((u) => (
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
      )}
    </>
  );
};
