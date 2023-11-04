import { useTranslation } from "react-i18next";
import * as formatString from "../../utils/StringUtils";
export const TableHeader = ({ title }) => {
  const { t } = useTranslation();
  return (
    <thead className='align-top text-center'>
      <tr>
        {title.map((header, i) => (
          <th key={i}>
            {formatString.formatStringToUpperCaseWithSpaces(
              `${t(`${header}`)}`
            )}
          </th>
        ))}
      </tr>
    </thead>
  );
};
