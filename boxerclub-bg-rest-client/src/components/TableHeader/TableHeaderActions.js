import * as formatString from "../../utils/StringUtils";
export const TableHeaderActions = ({ title }) => {
  return (
    <thead className='align-top'>
      <tr>
        {title.map((header, i) => (
          <th key={i} className='text-center'>
            {formatString.formatStringToUpperCaseWithSpaces(header)}
          </th>
        ))}
        <th>Actions</th>
      </tr>
    </thead>
  );
};
