import { useContext, useEffect, useState } from "react";
import { DogContext } from "../../contexts/DogContext";
import { TableHeaderActions } from "../TableHeader/TableHeaderActions";
import { Container, Table } from "react-bootstrap";
import { Dog } from "./Dog";
import { DeleteDog } from "./DeleteDog";
import { useTranslation } from "react-i18next";
import { OnDeleteParentModal } from "../Modal/OnDeleteParentModal";

import Alert from "react-bootstrap/Alert";

export const OwnedDogs = ({ owner }) => {
  const { t } = useTranslation();
  const { dogs, error, onDogEdit, onDogDelete, getDogDetails, getSelectedDog } =
    useContext(DogContext);

  const firstRow = Array.isArray(dogs) && dogs.length ? dogs[0] : {};
  const headerTitle = Object.keys(firstRow);
  const [deleteDogShow, setDeleteDogShow] = useState(false);
  const [selectedDog, setSelectedDog] = useState({});
  const [editDogShow, setEditDogShow] = useState(null);
  const [dogsList, setDogsList] = useState(dogs);

  const [ownedDogsList, setOwnedDogsList] = useState([]);

  let arr = headerTitle.filter(
    (state) =>
      state !== "id" &&
      state !== "pictureUrl" &&
      state !== "ownerId" &&
      state !== "hasPedigree" &&
      state !== "approved"
  );
  arr.unshift("");

  useEffect(() => {
    setDogsList(dogs);
    setOwnedDogsList(dogsList.filter((d) => d.ownerId === String(owner)));
    setSelectedDog({});
  }, []);

  useEffect(() => {
    setDogsList(dogs);
    setOwnedDogsList(dogsList.filter((d) => d.ownerId === String(owner)));
    setSelectedDog({});
  }, [dogs]);

  const onCloseClick = () => {
    setDeleteDogShow(null);
    setEditDogShow(null);
  };

  const onDogDeleteHandler = () => {
    onDogDelete(deleteDogShow);
    setDeleteDogShow(null);
    setSelectedDog({});
  };
  const onDogEditHandler = () => {
    onDogEdit(editDogShow);
    setEditDogShow(null);
  };
  const onInfoClick = (dogId) => {
    getDogDetails(dogId);
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
      {Object.keys(error).length !== 0 && <OnDeleteParentModal />}

      {deleteDogShow && (
        <DeleteDog
          dog={selectedDog[0]}
          onCloseClick={onCloseClick}
          onDelete={onDogDeleteHandler}
        />
      )}
      {ownedDogsList.length === 0 && (
        <Container fluid className='p-5'>
          <Alert variant='success'> {t("No dogs")}</Alert>
        </Container>
      )}
      {ownedDogsList && ownedDogsList.length !== 0 && (
        <Container fluid className=' mb-3 pt-5'>
          <Table
            className='align-middle project-list text-center'
            responsive='md'
            hover>
            <TableHeaderActions title={arr} />
            <tbody>
              {ownedDogsList.map((u) => (
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
