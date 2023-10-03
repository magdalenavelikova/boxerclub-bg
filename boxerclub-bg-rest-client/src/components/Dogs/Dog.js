import { Button } from "react-bootstrap";
import { useAuthContext } from "../../contexts/AuthContext";
export const Dog = ({ info, onEditClick, onInfoClick, onDeleteClick }) => {
  const { userId, isAuthenticated, authorities } = useAuthContext();
  const { id, pictureUrl, ownerId, ...dogInfo } = info;
  const boxer = require("../../assets/dogs/boxer-vector.png");

  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item.authority === "ROLE_ADMIN") ||
      authorities.some((item) => item.authority === "ROLE_MODERATOR"));

  console.log(ownerId, userId);
  console.log(userId == ownerId);
  console.log(userId == ownerId || isAuthorized);

  return (
    <tr>
      <td className='text-center'>
        <img
          src={pictureUrl !== "" && pictureUrl ? pictureUrl : boxer}
          className='rounded-circle  avatar-xs'
          alt=''
        />
      </td>

      {Object.values(dogInfo).map((v, i) => (
        <td key={i} className='text-center'>
          {v}
        </td>
      ))}
      <td className='text-center'>
        <Button
          className='me-2 mb-2'
          variant='outline-secondary'
          size='sm'
          title='Info'
          onClick={() => onInfoClick(info.id)}>
          <i className='fas fa-info'></i>
        </Button>

        {(userId == ownerId || isAuthorized) && (
          <Button
            className='me-2 mb-2'
            variant='outline-secondary'
            size='sm'
            title='Edit'
            onClick={() => onEditClick(info.id)}>
            <i className='fas fa-edit'></i>
          </Button>
        )}
        {isAuthorized && (
          <Button
            className='me-2 mb-2'
            variant='outline-secondary'
            size='sm'
            title='Delete'
            onClick={() => onDeleteClick(dogInfo.id)}>
            <i className='fas fa-trash'></i>
          </Button>
        )}
      </td>
    </tr>
  );
};
