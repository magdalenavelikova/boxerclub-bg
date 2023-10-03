import { Button } from "react-bootstrap";
export const Dog = ({ info, onEditClick, onInfoClick, onDeleteClick }) => {
  const { id, pictureUrl, ...dogInfo } = info;
  const boxer = require("../../assets/dogs/boxer-vector.png");

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
        <Button
          className='me-2 mb-2'
          variant='outline-secondary'
          size='sm'
          title='Edit'
          onClick={() => onEditClick(info.id)}>
          <i className='fas fa-edit'></i>
        </Button>

        <Button
          className='me-2 mb-2'
          variant='outline-secondary'
          size='sm'
          title='Delete'
          onClick={() => onDeleteClick(dogInfo.id)}>
          <i className='fas fa-trash'></i>
        </Button>
      </td>
    </tr>
  );
};
