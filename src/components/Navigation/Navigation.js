import { Container, Form, Button } from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { Link } from "react-router-dom";
import { LanguageSwitcher } from "./LanguageSwitcher";
import { useTranslation } from "react-i18next";

export const Navigation = () => {
  const logo = require("../../assets/logo.png");
  const { t } = useTranslation();
  return (
    <Navbar expand='lg' className='bg-body-tertiary'>
      <Container fluid>
        <Navbar.Brand href='/' className='nav-item px-lg-5 me-5 text-secondary'>
          <img
            src={logo}
            width='30'
            height='30'
            className='d-inline-block align-top me-3'
            alt='Boxer Club Bulgaria'
          />
          {t("brand")}
        </Navbar.Brand>
        <Navbar.Toggle aria-controls='navbarScroll' />
        <Navbar.Collapse id='navbarScroll'>
          <Nav
            className='me-auto my-2 my-lg-0'
            style={{ maxHeight: "100px" }}
            navbarScroll>
            {" "}
            <Nav.Link className='me-5' href='#link'>
              {" "}
              {t("nav.Dogs")}
            </Nav.Link>
            <NavDropdown
              className='me-5'
              title={t("nav.Events")}
              id='basic-nav-dropdown'>
              <NavDropdown.Header>
                {t("nav.Events.Upcoming")}
              </NavDropdown.Header>
              <NavDropdown.Item href='#action/3.3'>
                {t("nav.Events.Bulgarian")}
              </NavDropdown.Item>
              <NavDropdown.Item href='#action/3.4'>
                {t("nav.Events.International")}
              </NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Header>{t("nav.Events.Passed")}</NavDropdown.Header>
              <NavDropdown.Item href='#action/3.3'>
                {t("nav.Events.Bulgarian")}
              </NavDropdown.Item>
              <NavDropdown.Item href='#action/3.4'>
                {t("nav.Events.International")}
              </NavDropdown.Item>
            </NavDropdown>
            <NavDropdown
              className='me-5'
              title={t("nav.Regulations")}
              id='basic-nav-dropdown'>
              <NavDropdown.Item href='/regulations/standard'>
                {t("nav.Regulations.Standard")}
              </NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Header>
                {t("nav.Regulations.WorkingExams")}
              </NavDropdown.Header>
              <NavDropdown.Item href='/regulations/AD'>
                {t("nav.Regulations.EnduranceTest")}
              </NavDropdown.Item>
              <NavDropdown.Item href='/regulations/BH'>
                {t("nav.Regulations.CompanionDog")}
              </NavDropdown.Item>
              <NavDropdown.Item href='/regulations/IPO1'>
                IPO I
              </NavDropdown.Item>
              <NavDropdown.Item href='/regulations/IPO2'>
                IPO II
              </NavDropdown.Item>
              <NavDropdown.Item href='/regulations/IPO3'>
                IPO III
              </NavDropdown.Item>
            </NavDropdown>
            <NavDropdown
              className='me-5'
              title={t("nav.Links")}
              id='basic-nav-dropdown'>
              <NavDropdown.Item href='#action/3.1'>
                {t("nav.Links.CanineOrganizations")}
              </NavDropdown.Item>
              <NavDropdown.Item href='#action/3.3'>
                {t("nav.Links.BoxerClub")}
              </NavDropdown.Item>
            </NavDropdown>
            <Nav.Link className='me-5' href='#link'>
              {t("nav.Contacts")}
            </Nav.Link>
            <Nav.Link className='me-5' href='#link'>
              {t("nav.Gallery")}
            </Nav.Link>
            <NavDropdown title={t("nav.MembersArea")} id='basic-nav-dropdown'>
              <NavDropdown.Item href='/login'>
                {t("nav.MembersArea.Login")}
              </NavDropdown.Item>
              <NavDropdown.Item href='/register'>
                {t("nav.MembersArea.Register")}
              </NavDropdown.Item>
              <NavDropdown.Divider />

              <NavDropdown.Item href='/logout'>
                {t("nav.MembersArea.Logout")}
              </NavDropdown.Item>
            </NavDropdown>
          </Nav>
          {/*    <Nav className='d-inline-block align-top me-5'>
            <Link
              to={"/login"}
              style={{ textDecoration: "none" }}
              className='d-flex bg-body-tertiary link-secondary me-3 '>
              Login
            </Link>
</Nav>*/}
          <LanguageSwitcher className='d-flex' />
          {/*    <Form className='d-flex'>
            <Form.Control
              type='search'
              placeholder='Search'
              className='me-2'
              aria-label='Search'
            />
            <Button variant='secondary'>Search</Button>
  </Form>*/}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};
