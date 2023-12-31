import { useTranslation } from "react-i18next";
import * as formatString from "../../utils/StringUtils";
export const TableHeader = ({ title }) => {
  const { t } = useTranslation();
  return (
    <thead className='align-top text-center'>
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
      </tr>
    </thead>
  );
};
