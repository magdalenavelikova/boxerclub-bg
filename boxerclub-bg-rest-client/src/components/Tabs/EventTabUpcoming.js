import { Container, Row, Col, Tab, Tabs } from "react-bootstrap";

import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { Events } from "../Event/Events";
export const EventsTabUpcoming = ({ eventLink }) => {
  const { t } = useTranslation();
  const [key, setKey] = useState(eventLink);

  useEffect(() => {
    setKey(eventLink);
  }, [eventLink]);

  return (
    <Container fullscreen='true' fluid className=' m-auto mt-5 p-5'>
      <Row xl={1} md={2}>
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
