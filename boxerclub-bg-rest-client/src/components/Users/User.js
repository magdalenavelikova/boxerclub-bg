import { Button } from "react-bootstrap";
import { FaRegTrashAlt } from "react-icons/fa";
import { RiEdit2Fill } from "react-icons/ri";
export const User = ({ info, onEditClick, onDeleteClick }) => {
  const { roles, created, id, ...userInfo } = info;

  return (
    <tr>
      {Object.values(userInfo).map((v, i) => (
        <td key={i}>{v}</td>
      ))}
      <td>
        {roles.map((r) => (
          <span key={Object.values(r)}> {Object.values(r)} </span>
        ))}
      </td>
      <td className='text-center'>
        <Button
          className='me-2'
          variant='outline-secondary'
          size='sm'
          title='Edit'
          onClick={() => onEditClick(info.id, roles)}>
          <RiEdit2Fill />
        </Button>
        <Button
          className='me-2'
          variant='outline-secondary'
          size='sm'
          title='Delete'
          onClick={() => onDeleteClick(info.id)}>
          <FaRegTrashAlt />
        </Button>
      </td>
    </tr>
  );
};
