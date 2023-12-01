import { useContext, useEffect, useState } from "react";
import { DogContext } from "../../contexts/DogContext";
import { FaArrowUp } from "react-icons/fa";
import { Container, Row, Col, Table, Navbar, Badge } from "react-bootstrap";
import { Dog } from "./Dog";
import { DeleteDog } from "./DeleteDog";
import { useTranslation } from "react-i18next";
import { OnDeleteParentModal } from "../Modal/OnDeleteParentModal";

import { AuthContext } from "../../contexts/AuthContext";
import { TableHeaderActionsDogs } from "../TableHeader/TableHeadersActionsDogs";

export const Dogs = () => {
  const { t } = useTranslation();
  const {
    dogs,
    error,
    onDogDelete,
    getSelectedDog,
    getDogDetails,
    getDogChart,
  } = useContext(DogContext);
  const { isAuthenticated, authorities } = useContext(AuthContext);
  const firstRow = Array.isArray(dogs) && dogs.length ? dogs[0] : {};
  const headerTitle = Object.keys(firstRow);
  const [deleteDogShow, setDeleteDogShow] = useState(false);
  const [selectedDog, setSelectedDog] = useState({});
  const [showUnapprovedOnly, setShowUnapprovedOnly] = useState(false);
  const [dogsList, setDogsList] = useState([]);
  const [showScrollButton, setShowScrollButton] = useState(false);
  const isAdminOrModerator =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));

  let arr = headerTitle.filter(
    (state) =>
      state !== "id" &&
      state !== "pictureUrl" &&
      state !== "ownerId" &&
      state !== "hasPedigree" &&
      state !== "dateOfDecease" &&
      state !== "approved"
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
  const handleCheckboxChange = () => {
    setShowUnapprovedOnly(!showUnapprovedOnly);
  };
  useEffect(() => {
    isAdminOrModerator
      ? setDogsList(dogs)
      : setDogsList(dogs.filter((d) => d.ownerId !== null));

    setSelectedDog({});
  }, []);

  useEffect(() => {
    let filteredDogs = isAdminOrModerator
      ? dogs
      : dogs.filter((d) => d.ownerId !== null);

    if (showUnapprovedOnly) {
      filteredDogs = filteredDogs.filter((d) => d.approved !== true);
    }

    setDogsList(filteredDogs);
    setSelectedDog({});
  }, [dogs, showUnapprovedOnly]);

  const onCloseClick = () => {
    setDeleteDogShow(null);
  };

  const onDogDeleteHandler = () => {
    onDogDelete(deleteDogShow);
    setDeleteDogShow(null);
    setSelectedDog({});
  };

  const onDeleteClick = (dogId) => {
    setSelectedDog(dogsList.filter((d) => d.id === dogId));
    setDeleteDogShow(dogId);
  };
  const onEditClick = (dogId) => {
    getSelectedDog(dogId);
  };

  const onChartClick = (dogId) => {
    getDogChart(dogId);
  };
  const onInfoClick = (dogId) => {
    getDogDetails(dogId);
  };

  const handleScroll = () => {
    const scrollY = window.scrollY;
    const tableOffsetTop =
      document.querySelector(".custom-table")?.offsetTop || 0;
    setShowScrollButton(scrollY > tableOffsetTop + window.innerHeight);
  };

  useEffect(() => {
    const scrollEventListener = () => {
      handleScroll();
    };

    window.addEventListener("scroll", scrollEventListener);

    return () => {
      window.removeEventListener("scroll", scrollEventListener);
    };
  }, []);

  const scrollToTop = () => {
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  return (
    <>
      <Container fluid className='pt-3 '>
        <Row
          className={`bg-white mt-5 justify-content-center  align-items-center pt-5 ${
            showScrollButton ? "fixed-top" : ""
          }`}>
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
          {isAdminOrModerator && (
            <Col className='col-md-2'>
              <input
                className='me-2'
                type='checkbox'
                id='showApprovedOnly'
                checked={showUnapprovedOnly}
                onChange={handleCheckboxChange}
              />
              <label htmlFor='showApprovedOnly'>
                <Badge bg='danger'>{t("Unapproved")}</Badge>
              </label>
            </Col>
          )}
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
        <Container fluid className='mt-5 mb-3'>
          <Table
            className='align-middle project-list text-center custom-table'
            responsive='md'
            hover>
            <TableHeaderActionsDogs title={arr} />

            <tbody>
              {search(dogsList).map((u) => (
                <Dog
                  key={u.id}
                  info={u}
                  onDeleteClick={onDeleteClick}
                  onEditClick={onEditClick}
                  onInfoClick={onInfoClick}
                  onChartClick={onChartClick}
                />
              ))}
            </tbody>
          </Table>
        </Container>
      )}
      {showScrollButton && (
        <div
          className='scroll-to-top'
          onClick={scrollToTop}
          style={{ position: "fixed", bottom: "20px", right: "60px" }}>
          <FaArrowUp style={{ color: "green", opacity: 0.7 }} />
        </div>
      )}
    </>
  );
};
