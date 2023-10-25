import { useContext, useEffect, useState } from "react";
import { TableHeaderActions } from "../TableHeader/TableHeaderActions";
import { TableHeader } from "../TableHeader/TableHeader";
import { Container, Table } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { OnDeleteLinkModal } from "../Modal/OnDeleteLinkModal";
import { DeleteLink } from "./DeleteLink";

import { LinkContext } from "../../contexts/LinksContext";
import { LinkItem } from "./LinkItem";
import { useAuthContext } from "../../contexts/AuthContext";

export const Links = ({ linkType }) => {
  const { t } = useTranslation();
  const { isAuthenticated, authorities } = useAuthContext();
  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));
  console.log(isAuthorized);
  const { links, error, onLinkEdit, onLinkDelete, getSelectedLink } =
    useContext(LinkContext);
  const firstRow = Array.isArray(links) && links.length ? links[0] : {};
  const headerTitle = Object.keys(firstRow);

  const [deleteLinkShow, setDeleteLinkShow] = useState(false);
  const [editLinkShow, setEditLinkShow] = useState(null);
  const [linksList, setLinksList] = useState([]);
  const [selectedLink, setSelectedLink] = useState({});
  let arr = headerTitle.filter(
    (state) => state !== "id" && state !== "type" && state !== "description"
  );

  console.log(headerTitle);
  //console.log(arr);
  useEffect(() => {
    setLinksList(links.filter((x) => x.type === linkType));
  }, []);

  useEffect(() => {
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
  const onLinkEditHandler = () => {
    onLinkEdit(editLinkShow);
    setEditLinkShow(null);
  };

  const onDeleteClick = (linkId) => {
    setSelectedLink(linksList.filter((l) => l.id === linkId));
    setDeleteLinkShow(linkId);
  };
  const onEditClick = (linkId) => {
    getSelectedLink(linkId);
  };

  return (
    <>
      {Object.keys(error).length !== 0 && <OnDeleteLinkModal />}

      {deleteLinkShow && (
        <DeleteLink
          link={selectedLink[0]}
          onCloseClick={onCloseClick}
          onDelete={onLinkDeleteHandler}
        />
      )}

      {linksList && linksList.length !== 0 && (
        <Container fluid className=' mb-3 pt-3'>
          <Table
            className='align-middle project-list mt-3'
            responsive='md'
            hover>
            {isAuthorized && <TableHeaderActions title={arr} />}
            {!isAuthorized && <TableHeader title={arr} />}
            <tbody>
              {linksList.map((l) => (
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
    </>
  );
};
