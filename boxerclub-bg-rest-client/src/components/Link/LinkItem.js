import { Button } from "react-bootstrap";
import { useAuthContext } from "../../contexts/AuthContext";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { FaRegTrashAlt } from "react-icons/fa";
import { RiEdit2Fill } from "react-icons/ri";
export const LinkItem = ({ info, onEditClick, onDeleteClick }) => {
  const { t } = useTranslation();
  const { isAuthenticated, authorities } = useAuthContext();
  const { id, type, description, urlLink, ...linkInfo } = info;
  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));
  return (
    <tr className='m-auto'>
      {Object.values(linkInfo).map((v, i) => (
        <td className='pb-3 pt-3' key={i}>
          {v}
        </td>
      ))}
      <td className='text-center pb-3 pt-3'>
        <Link
          className='link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover'
          key={info.id}
          target='_blank'
          to={urlLink}>
          {urlLink}
        </Link>
      </td>
      <td className='text-center'>
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
              className='me-2 mb-2'
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
