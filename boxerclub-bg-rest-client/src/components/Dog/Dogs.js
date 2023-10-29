import { useContext, useEffect, useState } from "react";
import { DogContext } from "../../contexts/DogContext";
import { TableHeaderActions } from "../TableHeader/TableHeaderActions";
import { Container, Row, Col, Table } from "react-bootstrap";
import { Dog } from "./Dog";
import { DeleteDog } from "./DeleteDog";
import { useTranslation } from "react-i18next";
import { OnDeleteParentModal } from "../Modal/OnDeleteParentModal";

//import { DeleteDog } from "./DeleteDog";
//import { EditDog } from "./EditDog";

export const Dogs = () => {
  const { t } = useTranslation();
  const { dogs, error, onDogDelete, getSelectedDog } = useContext(DogContext);
  const firstRow = Array.isArray(dogs) && dogs.length ? dogs[0] : {};
  const headerTitle = Object.keys(firstRow);
  const [deleteDogShow, setDeleteDogShow] = useState(false);
  const [selectedDog, setSelectedDog] = useState({});

  const [dogsList, setDogsList] = useState([]);

  let arr = headerTitle.filter(
    (state) => state !== "id" && state !== "pictureUrl" && state !== "ownerId"
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
  };

  const onDogDeleteHandler = () => {
    onDogDelete(deleteDogShow);
    setDeleteDogShow(null);
    setSelectedDog({});
  };

  const onInfoClick = (dogId) => {
    setSelectedDog(dogsList.filter((d) => d.id === dogId));
  };
  const onDeleteClick = (dogId) => {
    setSelectedDog(dogsList.filter((d) => d.id === dogId));
    setDeleteDogShow(dogId);
  };
  const onEditClick = (dogId) => {
    getSelectedDog(dogId);
  };

  return (
    <>
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

      {Object.keys(error).length !== 0 && <OnDeleteParentModal />}

      {deleteDogShow && (
        <DeleteDog
          dog={selectedDog[0]}
          onCloseClick={onCloseClick}
          onDelete={onDogDeleteHandler}
        />
      )}

      {dogsList && dogsList.length !== 0 && (
        <Container fluid className='mt-3 mb-3'>
          <Table className='align-middle project-list' responsive='md' hover>
            <TableHeaderActions title={arr} />
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
