import { Button } from "react-bootstrap";
import { useAuthContext } from "../../contexts/AuthContext";

export const Dog = ({ info, onEditClick, onInfoClick, onDeleteClick }) => {
  const { userId, isAuthenticated, authorities } = useAuthContext();
  const { id, pictureUrl, ownerId, ...dogInfo } = info;
  const boxer = require("../../assets/dogs/boxer-vector.png");

  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));

  return (
    <tr>
      <td>
        <img
          src={pictureUrl !== "" && pictureUrl ? pictureUrl : boxer}
          className='rounded-circle  avatar-xs'
          alt=''
        />
      </td>

      {Object.values(dogInfo).map((v, i) => (
        <td key={i}>{v}</td>
      ))}
      <td>
        <Button
          className='me-2 mb-2'
          variant='outline-secondary'
          size='sm'
          title='Info'
          onClick={() => onInfoClick(info.id)}>
          <i className='fas fa-info'></i>
        </Button>

        {((userId == ownerId && userId) || isAuthorized) && (
          <Button
            className='me-2 mb-2'
            variant='outline-secondary'
            size='sm'
            title='Edit'
            onClick={() => onEditClick(id)}>
            <i className='fas fa-edit'></i>
          </Button>
        )}
        {isAuthorized && (
          <Button
            className='me-2 mb-2'
            variant='outline-secondary'
            size='sm'
            title='Delete'
            onClick={() => onDeleteClick(id)}>
            <i className='fas fa-trash'></i>
          </Button>
        )}
      </td>
    </tr>
  );
};
