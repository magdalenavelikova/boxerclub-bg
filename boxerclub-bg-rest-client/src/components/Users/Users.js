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
    console.log("selectedUserby delete");
    console.log(selectedUser);
    setDeleteUserShow(userId);
  };
  const onEditClick = (userId) => {
    setSelectedUser(usersList.filter((u) => u.id === userId));
    let arr = [];
    console.log("selectedUser by edit");
    console.log(selectedUser);
    selectedUser[0].length !== 0 &&
      Object.values(selectedUser[0].roles).forEach((obj) => {
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
