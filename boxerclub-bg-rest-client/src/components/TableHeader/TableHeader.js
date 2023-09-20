import * as formatString from "../../utils/StringUtils";
export const TableHeader = ({ title }) => {
  return (
    <thead>
      <tr>
        {title.map((header, i) => (
          <th key={i}>
            {formatString.formatStringToUpperCaseWithSpaces(header)}
          </th>
        ))}
        <th>Actions</th>
      </tr>
    </thead>
  );
};
