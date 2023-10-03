import { useContext, useEffect, useState } from "react";
import { DogContext } from "../../contexts/DogContext";
import { TableHeader } from "../TableHeader/TableHeader";
import { Container, Row, Col, Table } from "react-bootstrap";
import { Dog } from "./Dog";

//import { DeleteDog } from "./DeleteDog";
//import { EditDog } from "./EditDog";

export const Dogs = () => {
  const { dogs, onDogEdit, onDogDelete } = useContext(DogContext);
  const firstRow = Array.isArray(dogs) && dogs.length ? dogs[0] : {};
  const headerTitle = Object.keys(firstRow);
  const [deleteDogShow, setDeleteDogShow] = useState(false);
  const [selectedDog, setSelectedDog] = useState({});
  const [editDogShow, setEditDogShow] = useState(null);
  const [dogsList, setDogsList] = useState([]);

  let arr = headerTitle.filter(
    (state) => state !== "id" && state !== "pictureUrl"
  );
  arr.unshift("");
  const [q, setQ] = useState("");

  const [searchParam] = useState(["name", "registrationNum"]);

  function search(dogsList) {
    return dogsList.filter((item) => {
      return searchParam.some((newItem) => {
        return (
          item[newItem].toString().toLowerCase().indexOf(q.toLowerCase()) > -1
        );
      });
    });
  }

  useEffect(() => {
    setDogsList(dogs);
    setSelectedDog({});
  }, []);

  useEffect(() => {
    setDogsList(dogs);
  }, [dogs]);

  const onCloseClick = () => {
    setDeleteDogShow(null);
    setEditDogShow(null);
  };

  const onDogDeleteHandler = () => {
    onDogDelete(deleteDogShow);
    setDeleteDogShow(null);
  };
  const onDogEditHandler = () => {
    onDogEdit(editDogShow);
    setEditDogShow(null);
  };
  const onInfoClick = (dogId) => {
    setSelectedDog(dogsList.filter((d) => d.id === dogId));
  };
  const onDeleteClick = (dogId) => {
    setSelectedDog(dogsList.filter((d) => d.id === dogId));

    setDeleteDogShow(dogId);
  };
  const onEditClick = (dogId) => {
    setSelectedDog(dogsList.filter((d) => d.id === dogId));

    setEditDogShow(dogId);
  };

  return (
    <>
      <Container>
        <Row className='height d-flex justify-content-center align-items-center'>
          <Col class='col-md-6'>
            <div class='form'>
              <i class='fa fa-search'></i>

              <input
                type='text'
                className='form-control form-input'
                placeholder='Search anything...'
                value={q}
                onChange={(e) => setQ(e.target.value)}
              />
            </div>
          </Col>
        </Row>
      </Container>

      {/*  {deleteDogShow && (
        <DeleteDog
          dog={selectedDog[0]}
          onCloseClick={onCloseClick}
          onDelete={onDogDeleteHandler}
        />
      )}
      {editDogShow && (
        <EditDog
          dog={selectedDog[0]}
          userRoles={userRoles}
          onCloseClick={onCloseClick}
          onDogEdit={onDogEditHandler}
        />
      )}*/}
      {dogsList && dogsList.length !== 0 && (
        <Container fluid className='mt-3 mb-3'>
          <Table className='align-middle project-list' responsive='md' hover>
            <TableHeader title={arr} />
            <tbody>
              {search(dogsList).map((u) => (
                <Dog
                  key={u.id}
                  info={u}
                  onDeleteClick={onDeleteClick}
                  onEditClick={onEditClick}
                  onInfoClick={onInfoClick}
                />
              ))}
            </tbody>
          </Table>
        </Container>
      )}
    </>
  );
};
