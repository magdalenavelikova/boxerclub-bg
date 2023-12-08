import { Badge, Button } from "react-bootstrap";
import { useAuthContext } from "../../contexts/AuthContext";
import { useTranslation } from "react-i18next";
import { FaRegTrashAlt, FaInfo } from "react-icons/fa";

import { TbBinaryTree } from "react-icons/tb";
import { RiEdit2Fill } from "react-icons/ri";
export const Dog = ({
  info,
  onEditClick,
  onChartClick,
  onInfoClick,
  onDeleteClick,
}) => {
  const { userId, isAuthenticated, authorities } = useAuthContext();
  const {
    id,
    pictureUrl,
    ownerId,
    hasPedigree,
    dateOfDecease,
    approved,
    ...dogInfo
  } = info;
  const boxer = require("../../assets/dogs/boxer-vector.png");
  const { t } = useTranslation();
  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));

  return (
    <tr>
      <td
        onClick={() => onInfoClick(info.id)}
        style={{ cursor: "pointer" }}
        className='d-none d-lg-table-cell'>
        <img
          src={pictureUrl !== "" && pictureUrl ? pictureUrl : boxer}
          className=' rounded-circle  avatar-xs'
          alt=''
        />
        {isAuthorized && (
          <>
            {info.approved && <Badge bg='success'>{t("Approved")}</Badge>}
            {!info.approved && <Badge bg='danger'>{t("Unapproved")}</Badge>}
          </>
        )}
      </td>
      <td onClick={() => onInfoClick(info.id)} style={{ cursor: "pointer" }}>
        {dogInfo.name}
      </td>
      <td>{dogInfo.registrationNum}</td>

      {Object.values(dogInfo)
        .slice(2)
        .map((v, i) => (
          <td className='d-none d-lg-table-cell' key={i}>
            {v}
          </td>
        ))}
      <td>
        <Button
          className='me-1 mb-2 custom-sm-button'
          variant='outline-secondary'
          title={t("nav.Info")}
          onClick={() => onInfoClick(info.id)}>
          <FaInfo />
        </Button>
        <Button
          className='me-1 mb-2 custom-sm-button'
          variant='outline-secondary'
          title={t("nav.Pedigree")}
          onClick={() => onChartClick(info.id)}>
          <TbBinaryTree />
        </Button>
        {((userId == ownerId && userId) || isAuthorized) && (
          <Button
            className='me-1 mb-2 custom-sm-button'
            variant='outline-secondary'
            title={t("nav.Edit")}
            onClick={() => onEditClick(id)}>
            <RiEdit2Fill />
          </Button>
        )}
        {isAuthorized && (
          <Button
            className='me-1 mb-2 custom-sm-button'
            variant='outline-secondary'
            title={t("nav.Delete")}
            onClick={() => onDeleteClick(id)}>
            <FaRegTrashAlt />
          </Button>
        )}
      </td>
    </tr>
  );
};
