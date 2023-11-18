import { useContext, useEffect, useState } from "react";
import { TableHeaderActions } from "../TableHeader/TableHeaderActions";
import { Container, Table } from "react-bootstrap";

import { DeleteLink } from "./DeleteLink";

import { LinkContext } from "../../contexts/LinkContext";
import { LinkItem } from "./LinkItem";
import { useAuthContext } from "../../contexts/AuthContext";
import { EditLink } from "./EditLink";
import { Maintenance } from "../Maintenance/Maintenance";

export const Links = ({ linkType }) => {
  const { isAuthenticated, authorities } = useAuthContext();
  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));

  const { links, onLinkDelete, success } = useContext(LinkContext);
  const firstRow = Array.isArray(links) && links.length ? links[0] : {};
  const headerTitle = Object.keys(firstRow);

  const [deleteLinkShow, setDeleteLinkShow] = useState(false);
  const [editLinkShow, setEditLinkShow] = useState(null);
  const [linksList, setLinksList] = useState([]);
  const [selectedLink, setSelectedLink] = useState({});
  const [isRendered, setIsRendered] = useState(false);

  useEffect(() => {
    const timer = setTimeout(() => {
      setIsRendered(true);
    }, 2000);
    return () => clearTimeout(timer);
  }, []);
  let arr =
    headerTitle.length > 0 &&
    headerTitle.filter(
      (state) => state !== "id" && state !== "type" && state !== "description"
    );

  useEffect(() => {
    Array.isArray(links) &&
      setLinksList(links.filter((x) => x.type === linkType));
    if (success) {
      setEditLinkShow(null);
    }
  }, []);
  useEffect(() => {
    if (success) {
      setEditLinkShow(null);
    }
  }, [success]);

  useEffect(() => {
    Array.isArray(links) &&
      setLinksList(links.filter((x) => x.type === linkType));
  }, [links, linkType]);

  const onCloseClick = () => {
    setDeleteLinkShow(null);
    setEditLinkShow(null);
  };

  const onLinkDeleteHandler = () => {
    onLinkDelete(deleteLinkShow);
    setDeleteLinkShow(null);
    setSelectedLink({});
  };

  const onDeleteClick = (linkId) => {
    linksList.length !== 0 &&
      setSelectedLink(linksList.filter((l) => l.id === linkId));
    setDeleteLinkShow(linkId);
  };
  const onEditClick = (linkId) => {
    linksList.length !== 0 &&
      setSelectedLink(linksList.filter((l) => l.id === linkId));
    setEditLinkShow(linkId);
  };

  return (
    <>
      {deleteLinkShow && (
        <DeleteLink
          link={selectedLink[0]}
          onCloseClick={onCloseClick}
          onDelete={onLinkDeleteHandler}
        />
      )}
      {editLinkShow && (
        <EditLink link={selectedLink[0]} onCloseClick={onCloseClick} />
      )}
      {linksList && linksList.length !== 0 && (
        <Container fluid className='mb-3 pt-5'>
          <Table className='align-middle project-list ' responsive='md' hover>
            {isAuthorized && <TableHeaderActions title={arr} />}
            {/*{!isAuthorized.toString && <TableHeader title={arr} />}*/}
            <tbody>
              {linksList.length !== 0 &&
                linksList.map((l) => (
                  <LinkItem
                    className='m-auto'
                    key={l.id}
                    info={l}
                    onDeleteClick={onDeleteClick}
                    onEditClick={onEditClick}
                  />
                ))}
            </tbody>
          </Table>
        </Container>
      )}
      {Array.isArray(linksList) && linksList.length === 0 && isRendered && (
        <Maintenance />
      )}
    </>
  );
};
