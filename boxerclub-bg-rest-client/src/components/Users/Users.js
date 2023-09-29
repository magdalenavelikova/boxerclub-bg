import { useContext } from "react";
import { useEffect, useState } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import { TableHeader } from "../TableHeader/TableHeader";
import { Container, Table } from "react-bootstrap";
import { User } from "./User";
import { DeleteUser } from "./DeleteUser";
import { EditUser } from "./EditUser";

export const Users = () => {
  const { onGetAllUsersHandler, users, onUserEdit, onUserDelete } =
    useContext(AuthContext);

  const firstRow = Array.isArray(users) && users.length ? users[0] : {};
  const headersTitle = Object.keys(firstRow);
  const [deleteUserShow, setDeleteUserShow] = useState(false);
  const [selectedUser, setSelectedUser] = useState({});
  const [editUserShow, setEditUserShow] = useState(null);
  const [usersList, setUsersList] = useState([]);
  const [userRoles, setUserRoles] = useState([]);

  useEffect(() => {
    setUsersList(users);
    setSelectedUser({});
    onGetAllUsersHandler();
  }, []);

  useEffect(() => {
    setUsersList(users);
  }, [users]);

  const onCloseClick = () => {
    setDeleteUserShow(null);
    setEditUserShow(null);
  };

  const onUserDeleteHandler = () => {
    onUserDelete(deleteUserShow);
    setDeleteUserShow(null);
  };
  const onUserEditHandler = () => {
    onUserEdit(editUserShow);
    setEditUserShow(null);
  };
  const onDeleteClick = (userId) => {
    setSelectedUser(usersList.filter((u) => u.id === userId));

    setDeleteUserShow(userId);
  };
  const onEditClick = (userId, roles) => {
    setSelectedUser(usersList.filter((u) => u.id === userId));
    let arr = [];

    Object.values(roles).forEach((obj) => {
      for (const [key, value] of Object.entries(obj)) {
        arr.push(value);
      }
    });
    setUserRoles(arr);

    setEditUserShow(userId);
  };

  return (
    <>
      {deleteUserShow && (
        <DeleteUser
          user={selectedUser[0]}
          onCloseClick={onCloseClick}
          onDelete={onUserDeleteHandler}
        />
      )}
      {editUserShow && (
        <EditUser
          user={selectedUser[0]}
          userRoles={userRoles}
          onCloseClick={onCloseClick}
          onUserEdit={onUserEditHandler}
        />
      )}
      {usersList && usersList.length !== 0 && (
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
