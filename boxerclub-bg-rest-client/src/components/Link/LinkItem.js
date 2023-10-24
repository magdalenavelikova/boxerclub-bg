import { Button } from "react-bootstrap";
import { useAuthContext } from "../../contexts/AuthContext";
import { Link } from "react-router-dom";

export const LinkItem = ({ info, onEditClick, onDeleteClick }) => {
  const { isAuthenticated, authorities } = useAuthContext();
  const { id, type, description, urlLink, ...linkInfo } = info;
  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));
  return (
    <tr className='m-auto'>
      {Object.values(linkInfo).map((v, i) => (
        <td key={i}>{v}</td>
      ))}
      <td className='text-center'>
        <Link target='_blank' to={urlLink}>
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
