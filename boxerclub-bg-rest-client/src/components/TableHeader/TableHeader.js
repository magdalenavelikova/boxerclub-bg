import * as formatString from "../../utils/StringUtils";
export const TableHeader = ({ title }) => {
  return (
    <thead className='align-top'>
      <tr>
        {title.map((header, i) => (
          <th key={i} className='text-center'>
            {formatString.formatStringToUpperCaseWithSpaces(header)}
          </th>
        ))}
      </tr>
    </thead>
  );
};
