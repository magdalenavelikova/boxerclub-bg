import { useContext } from "react";
import { useEffect, useState } from "react";
import { AuthContext } from "../../contexts/AuthContext";
import { TableHeaderActions } from "../TableHeader/TableHeaderActions";
import { Container, Table, Row, Col } from "react-bootstrap";
import { User } from "./User";
import { DeleteUser } from "./DeleteUser";
import { EditUser } from "./EditUser";
import { useTranslation } from "react-i18next";

export const Users = () => {
  const { t } = useTranslation();
  const { onGetAllUsersHandler, users, onUserDelete, success } =
    useContext(AuthContext);

  const firstRow = Array.isArray(users) && users.length ? users[0] : {};

  const headerTitle = Object.keys(firstRow);
  const [deleteUserShow, setDeleteUserShow] = useState(false);
  const [selectedUser, setSelectedUser] = useState({});
  const [editUserShow, setEditUserShow] = useState(null);
  const [usersList, setUsersList] = useState([]);
  const [userRoles, setUserRoles] = useState([]);
  const [q, setQ] = useState("");
  let arr = headerTitle.filter(
    (state) => state !== "id" && state !== "created" && state !== "roles"
  );
  arr.push("roles");
  const [searchParam] = useState(["email", "firstName", "lastName"]);
  useEffect(() => {
    if (Object.keys(success).length !== 0) {
      setEditUserShow(null);
    }
  }, [success]);
  function search(usersList) {
    return usersList.filter((item) => {
      return searchParam.some((newItem) => {
        return (
          item[newItem].toString().toLowerCase().indexOf(q.toLowerCase()) > -1
        );
      });
    });
  }

  useEffect(() => {
    setUsersList(users);
    setSelectedUser({});
    onGetAllUsersHandler();
    if (Object.keys(success).length !== 0) {
      setEditUserShow(null);
    }
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
        />
      )}
      <Container fluid className='pt-5'>
        <Row className='justify-content-center align-items-center pt-5'>
          <Col className='col-md-6'>
            <div className='form'>
              <i className='fa fa-search'></i>

              <input
                type='text'
                className='form-control form-input'
                placeholder={t("Search")}
                value={q}
                onChange={(e) => setQ(e.target.value)}
              />
            </div>
          </Col>
        </Row>
      </Container>
      {usersList && usersList.length !== 0 && (
        <Container className='mt-5'>
          <Table responsive='md' striped bordered hover variant='light'>
            <TableHeaderActions title={arr} />
            <tbody>
              {usersList.length !== 0 &&
                search(usersList).map((u) => (
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
