import { Container, Form, Button } from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { Link } from "react-router-dom";
import { LanguageSwitcher } from "./LanguageSwitcher";
import { useTranslation } from "react-i18next";

export const Navigation = ({ onClickRegulation }) => {
  const logo = require("../../assets/logo.png");
  const { t } = useTranslation();

  return (
    <header>
      <Navbar expand='lg' className='bg-body-tertiary'>
        <Container fluid>
          <Navbar.Brand className='nav-item px-lg-5 me-2 text-secondary'>
            <Link
              className={"link-dark"}
              to={"/"}
              style={{ textDecoration: "none" }}>
              <img
                src={logo}
                width='30'
                height='30'
                className='d-inline-block align-top me-3'
                alt='Boxer Club Bulgaria'
              />
              {t("brand")}
            </Link>
          </Navbar.Brand>

          <Navbar.Toggle aria-controls='navbarScroll' />
          <Navbar.Collapse id='navbarScroll'>
            <Nav
              className='me-auto my-2 my-lg-0'
              style={{ maxHeight: "100px" }}
              navbarScroll>
              {" "}
              <Nav.Link className='me-2'>
                <Link
                  className={"link-dark"}
                  to={"/dogs"}
                  style={{ textDecoration: "none" }}>
                  {t("nav.Dogs")}{" "}
                </Link>
              </Nav.Link>
              <NavDropdown
                className='me-2'
                title={t("nav.Events")}
                id='basic-nav-dropdown'>
                <NavDropdown.Header>
                  {t("nav.Events.Upcoming")}
                </NavDropdown.Header>

                <NavDropdown.Item href='#action/3.3'>
                  <Link
                    className={"link-dark"}
                    to={"/events/bg"}
                    style={{ textDecoration: "none" }}>
                    {t("nav.Events.Bulgarian")}
                  </Link>
                </NavDropdown.Item>

                <Link
                  className={"link-dark"}
                  to={"/events/bg"}
                  style={{ textDecoration: "none" }}>
                  <NavDropdown.Item>
                    {t("nav.Events.International")}
                  </NavDropdown.Item>
                </Link>
                <NavDropdown.Divider />
                <NavDropdown.Header>
                  {t("nav.Events.Passed")}
                </NavDropdown.Header>
                <NavDropdown.Item href='#action/3.3'>
                  {t("nav.Events.Bulgarian")}
                </NavDropdown.Item>
                <NavDropdown.Item href='#action/3.4'>
                  {t("nav.Events.International")}
                </NavDropdown.Item>
              </NavDropdown>
              <NavDropdown
                className='me-2'
                title={t("nav.Regulations")}
                id='basic-nav-dropdown'>
                <NavDropdown.Item>
                  <Link
                    onClick={() => onClickRegulation("standard")}
                    className={"link-dark"}
                    to={"/regulations"}
                    style={{ textDecoration: "none" }}>
                    {t("nav.Regulations.Standard")}
                  </Link>
                </NavDropdown.Item>

                <NavDropdown.Divider />
                <NavDropdown.Header>
                  {t("nav.Regulations.WorkingExams")}
                </NavDropdown.Header>

                <NavDropdown.Item>
                  <Link
                    onClick={() => onClickRegulation("AD")}
                    className={"link-dark"}
                    to={"/regulations"}
                    style={{ textDecoration: "none" }}>
                    {t("nav.Regulations.EnduranceTest")}
                  </Link>
                </NavDropdown.Item>

                <NavDropdown.Item>
                  <Link
                    onClick={() => onClickRegulation("BH")}
                    className={"link-dark"}
                    to={"/regulations"}
                    style={{ textDecoration: "none" }}>
                    {t("nav.Regulations.CompanionDog")}
                  </Link>
                </NavDropdown.Item>

                <NavDropdown.Item>
                  <Link
                    onClick={() => onClickRegulation("IPO1")}
                    className={"link-dark"}
                    to={"/regulations"}
                    style={{ textDecoration: "none" }}>
                    IPO I
                  </Link>{" "}
                </NavDropdown.Item>

                <NavDropdown.Item>
                  <Link
                    onClick={() => onClickRegulation("IPO2")}
                    className={"link-dark"}
                    to={"/regulations"}
                    style={{ textDecoration: "none" }}>
                    IPO II
                  </Link>
                </NavDropdown.Item>

                <NavDropdown.Item>
                  <Link
                    onClick={() => onClickRegulation("IPO3")}
                    className={"link-dark"}
                    to={"/regulations"}
                    style={{ textDecoration: "none" }}>
                    IPO III
                  </Link>
                </NavDropdown.Item>
              </NavDropdown>
              <NavDropdown
                className='me-2'
                title={t("nav.Links")}
                id='basic-nav-dropdown'>
                <NavDropdown.Item href='#action/3.1'>
                  {t("nav.Links.CanineOrganizations")}
                </NavDropdown.Item>
                <NavDropdown.Item href='#action/3.3'>
                  {t("nav.Links.BoxerClub")}
                </NavDropdown.Item>
              </NavDropdown>
              <Nav.Link className='me-2' href='#link'>
                {t("nav.Contacts")}
              </Nav.Link>
              <Nav.Link className='me-2' href='#link'>
                {t("nav.Gallery")}
              </Nav.Link>
              <NavDropdown title={t("nav.MembersArea")} id='basic-nav-dropdown'>
                <NavDropdown.Item>
                  <Link
                    className={"link-dark"}
                    to={"/login"}
                    style={{ textDecoration: "none" }}>
                    {t("nav.MembersArea.Login")}
                  </Link>
                </NavDropdown.Item>
                <NavDropdown.Item>
                  <Link
                    className={"link-dark"}
                    to={"/register"}
                    style={{ textDecoration: "none" }}>
                    {t("nav.MembersArea.Register")}
                  </Link>
                </NavDropdown.Item>
                <NavDropdown.Divider />

                <NavDropdown.Item>
                  <Link
                    className={"link-dark"}
                    to={"/logout"}
                    style={{ textDecoration: "none" }}>
                    {" "}
                    {t("nav.MembersArea.Logout")}
                  </Link>
                </NavDropdown.Item>
              </NavDropdown>
            </Nav>
            {/*    <Nav className='d-inline-block align-top me-2'>
            <Link
          className={"link-dark"}
              to={"/login"}
              style={{ textDecoration: "none" }}
              className='d-flex bg-body-tertiary link-dark me-3 '>
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
    </header>
  );
};
