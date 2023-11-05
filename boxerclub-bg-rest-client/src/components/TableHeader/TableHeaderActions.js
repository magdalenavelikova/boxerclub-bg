import * as formatString from "../../utils/StringUtils";
import { useTranslation } from "react-i18next";
export const TableHeaderActions = ({ title }) => {
  const { t } = useTranslation();

  return (
    <thead className='align-top'>
      <tr>
        <th>
          {formatString.formatStringToUpperCaseWithSpaces(
            `${t(`${title[0]}`)}`
          )}
        </th>
        <th>
          {formatString.formatStringToUpperCaseWithSpaces(
            `${t(`${title[1]}`)}`
          )}
        </th>
        {title.slice(2).map((header, i) => (
          <th className='d-none d-lg-table-cell' key={i}>
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
