import { Container, Row, Col, Tab, Tabs } from "react-bootstrap";

import { useTranslation } from "react-i18next";
import { useState } from "react";
import { Events } from "../Event/Events";
export const EventsTabUpcoming = ({ eventEvent }) => {
  const { t } = useTranslation();
  const [key, setKey] = useState(eventEvent);

  return (
    <Container fullscreen={true} fluid className=' m-auto p-5'>
      <Row xs={1} md={2}>
        <Col className=' m-auto border-secondary'>
          <Tabs justify activeKey={key} onSelect={(k) => setKey(k)}>
            <Tab
              className='border border-top-0'
              eventKey='bg'
              title={t("nav.Events.Bulgarian")}>
              <Events location={key} status={"upcoming"} />
            </Tab>
            <Tab
              className='m-auto border border-top-0'
              eventKey='int'
              title={t("nav.Events.International")}>
              <Events location={key} status={"upcoming"} />
            </Tab>
          </Tabs>
        </Col>
      </Row>
    </Container>
  );
};
