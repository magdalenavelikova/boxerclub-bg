import { Container, Row, Col, Tab, Tabs } from "react-bootstrap";

import { useTranslation } from "react-i18next";
import { LoginPage } from "../Login/Login";
import { RegisterPage } from "../Register/Register";
import { useEffect, useState } from "react";
import { EditActiveUser } from "../Users/EditActiveUser";
export const UserTab = () => {
  const { t } = useTranslation();
  const [key, setKey] = useState("login");
  const onSelectHandler = (k) => {
    setKey(k);
  };

  return (
   /* <Container fluid className='m-auto p-3'>
      <Row xs={1} md={2}>
        <Col className=' m-auto border-secondary'>
          <Tabs justify activeKey={key} onSelect={(k) => setKey(k)}>
            <Tab
              className='m-auto border border-top-0'
              eventKey='login'
              title={t("nav.MembersArea.Login")}>
              <EditActiveUser
                showModal={true}
                onSelectHandler={onSelectHandler}
              />
            </Tab>
            <Tab
              className='m-auto border border-top-0'
              eventKey='register'
              title={t("nav.MembersArea.Register")}>
              <RegisterPage />
            </Tab>
          </Tabs>
        </Col>
      </Row>
    </Container>*/
    <></>
  );
};
