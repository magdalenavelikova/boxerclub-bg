import * as formatString from "../../utils/StringUtils";
import { useTranslation } from "react-i18next";
export const TableHeaderEvents = ({ title }) => {
  const { t } = useTranslation();

  return (
    <thead className='align-top text-center'>
      <tr>
        <th>
          {formatString.formatStringToUpperCaseWithSpaces(
            `${t(`${title[0]}`)}`
          )}
        </th>
        {title.slice(1, -1).map((header, i) => (
          <th className='d-none d-lg-table-cell' key={i}>
            {formatString.formatStringToUpperCaseWithSpaces(
              `${t(`${header}`)}`
            )}
          </th>
        ))}
        <th>
          {formatString.formatStringToUpperCaseWithSpaces(
            `${t(`${title[title.length - 1]}`)}`
          )}
        </th>
      </tr>
    </thead>
  );
};
