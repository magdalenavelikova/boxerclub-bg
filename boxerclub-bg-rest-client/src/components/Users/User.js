export const User = ({ info }) => {
  const { roles, ...userInfo } = info;
  return (
    <tr>
      {Object.values(userInfo).map((v, i) => (
        <td key={i}>{v}</td>
      ))}
      <td>
        {roles.map((r) => (
          <span key={roles.indexOf(r)}> {Object.values(r)} </span>
        ))}
      </td>
      <td>actions</td>
    </tr>
  );
};
