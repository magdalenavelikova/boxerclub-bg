import { Button } from "react-bootstrap";
import { useAuthContext } from "../../contexts/AuthContext";
import { Link } from "react-router-dom";
import * as formatDate from "../../utils/DateUtils";
import { useTranslation } from "react-i18next";
import { FaRegTrashAlt } from "react-icons/fa";
import { RiEdit2Fill } from "react-icons/ri";
export const EventItem = ({ info, onEditClick, onDeleteClick }) => {
  const { t } = useTranslation();
  const { isAuthenticated, authorities } = useAuthContext();
  const { id, location, urlLink, ...eventInfo } = info;
  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));
  return (
    <tr className='m-auto text-center '>
      <td>{eventInfo.title}</td>

      <td className='d-none d-lg-table-cell pb-3 pt-3'>
        {eventInfo.description}
      </td>
      <td className='d-none d-lg-table-cell pb-3 pt-3'>
        {formatDate.formatDateForUserDetails(eventInfo.startDate)}
      </td>
      <td className='d-none d-lg-table-cell pb-3 pt-3'>
        {formatDate.formatDateForUserDetails(eventInfo.expiryDate)}
      </td>

      <td className='pb-3 pt-3'>
        <Link
          style={{ display: "inline" }}
          key={eventInfo.title}
          target='_blank'
          to={urlLink}
          className='link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover  '>
          {eventInfo.title}
        </Link>
      </td>
      <td className='text-center  pb-3 pt-3'>
        {isAuthorized && (
          <>
            <Button
              className='me-2 mb-2'
              variant='outline-secondary'
              size='sm'
              title={t("nav.Edit")}
              onClick={() => onEditClick(id)}>
              <RiEdit2Fill />
            </Button>

            <Button
              className='me-2 mb-2 '
              variant='outline-secondary'
              size='sm'
              title={t("nav.Delete")}
              onClick={() => onDeleteClick(id)}>
              {" "}
              <FaRegTrashAlt />
            </Button>
          </>
        )}
      </td>
    </tr>
  );
};
