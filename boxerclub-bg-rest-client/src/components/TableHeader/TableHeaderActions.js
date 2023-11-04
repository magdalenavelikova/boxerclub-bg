import * as formatString from "../../utils/StringUtils";
import { useTranslation } from "react-i18next";
export const TableHeaderActions = ({ title }) => {
  const { t } = useTranslation();
  return (
    <thead className='align-top'>
      <tr>
        {title.map((header, i) => (
          <th key={i}>
            {formatString.formatStringToUpperCaseWithSpaces(
              `${t(`${header}`)}`
            )}
          </th>
        ))}
        <th>{t("Actions")}</th>
      </tr>
    </thead>
  );
};
