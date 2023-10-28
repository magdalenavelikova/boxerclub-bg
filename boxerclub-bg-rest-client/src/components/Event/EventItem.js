import { Button } from "react-bootstrap";
import { useAuthContext } from "../../contexts/AuthContext";
import { Link } from "react-router-dom";

export const EventItem = ({ info, onEditClick, onDeleteClick }) => {
  const { isAuthenticated, authorities } = useAuthContext();
  const { id, location, urlLink, ...eventInfo } = info;
  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));
  return (
    <tr className='m-auto'>
      {Object.values(eventInfo).map((v, i) => (
        <td key={i}>{v}</td>
      ))}
      <td className='text-center'>
        <Link
          key={eventInfo.title}
          target='_blank'
          to={urlLink}
          className='link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover'>
          {eventInfo.title}
        </Link>
      </td>
      <td className='text-center'>
        {isAuthorized && (
          <>
            <Button
              className='me-2 mb-2'
              variant='outline-secondary'
              size='sm'
              title='Edit'
              onClick={() => onEditClick(id)}>
              <i className='fas fa-edit'></i>
            </Button>

            <Button
              className='me-2 mb-2'
              variant='outline-secondary'
              size='sm'
              title='Delete'
              onClick={() => onDeleteClick(id)}>
              <i className='fas fa-trash'></i>
            </Button>
          </>
        )}
      </td>
    </tr>
  );
};
