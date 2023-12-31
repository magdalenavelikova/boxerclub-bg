import { Container, Row, Col, Tab, Tabs } from "react-bootstrap";

import { useTranslation } from "react-i18next";
import { useState } from "react";
import { Links } from "../Link/Links";
export const LinksTab = ({ eventLink }) => {
  const { t } = useTranslation();
  const [key, setKey] = useState(eventLink);

  return (
    <Container fullscreen='true' fluid className=' m-auto mt-5 p-5'>
      <Row xl={12} xs={1} md={2}>
        <Col className=' m-auto border-secondary'>
          <Tabs justify activeKey={key} onSelect={(k) => setKey(k)}>
            <Tab
              className='border border-top-0'
              eventKey='CANINE ORGANISATIONS'
              title={t("nav.Links.CanineOrganizations")}>
              <Links linkType={key} />
            </Tab>
            <Tab
              className='border border-top-0'
              eventKey='BOXER CLUBS'
              title={t("nav.Links.BoxerClub")}>
              <Links linkType={key} />
            </Tab>
          </Tabs>
        </Col>
      </Row>
    </Container>
  );
};
