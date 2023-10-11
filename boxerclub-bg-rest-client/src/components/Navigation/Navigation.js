import { Container, Form, Button } from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { Link } from "react-router-dom";
import { LanguageSwitcher } from "./LanguageSwitcher";
import { useTranslation } from "react-i18next";
import { useContext } from "react";
import { AuthContext } from "../../contexts/AuthContext";

export const Navigation = ({ onClickRegulation }) => {
  const logo = require("../../assets/logo.png");
  const { t } = useTranslation();
  const { isAuthenticated, fullName, authorities } = useContext(AuthContext);
  const isAdmin =
    isAuthenticated &&
    authorities.some((item) => item.authority === "ROLE_ADMIN");
  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item.authority === "ROLE_ADMIN") ||
      authorities.some((item) => item.authority === "ROLE_MODERATOR") ||
      authorities.some((item) => item.authority === "ROLE_MEMBER"));

  return (
    <header>
      <Navbar expand='lg' className='bg-body-tertiary' sticky='top'>
        <Container fluid>
          <Navbar.Brand className='nav-item px-lg-2 me-2 text-secondary'>
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
              className='m-auto my-2 my-lg-0'
              style={{ maxHeight: "100px" }}
              navbarScroll>
              <Nav.Link as={Link} className='me-2' to={"/dogs"}>
                {t("nav.Dogs")}
              </Nav.Link>
              {isAuthorized && (
                <NavDropdown
                  className='me-2'
                  title={t("nav.Dog")}
                  id='basic-nav-dropdown'>
                  <NavDropdown.Item as={Link} to={"dogs/register"}>
                    {t("nav.DogPedigree")}
                  </NavDropdown.Item>
                  <NavDropdown.Item as={Link} to={"dogs/register"}>
                    {t("nav.DogNewBorn")}
                  </NavDropdown.Item>
                  <NavDropdown.Item as={Link} to={"dogs/register"}>
                    {t("nav.DogChangeOwner")}
                  </NavDropdown.Item>
                </NavDropdown>
              )}

              <NavDropdown
                className='me-2'
                title={t("nav.Events")}
                id='basic-nav-dropdown'>
                <NavDropdown.Header>
                  {t("nav.Events.Upcoming")}
                </NavDropdown.Header>

                <NavDropdown.Item as={Link} to={"/events/bg"}>
                  {t("nav.Events.Bulgarian")}
                </NavDropdown.Item>

                <NavDropdown.Item as={Link} to={"/events/international"}>
                  {t("nav.Events.International")}
                </NavDropdown.Item>

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
                <NavDropdown.Item
                  as={Link}
                  onClick={() => onClickRegulation("standard")}
                  to={"/regulations"}>
                  {t("nav.Regulations.Standard")}
                </NavDropdown.Item>

                <NavDropdown.Divider />
                <NavDropdown.Header>
                  {t("nav.Regulations.WorkingExams")}
                </NavDropdown.Header>

                <NavDropdown.Item
                  as={Link}
                  onClick={() => onClickRegulation("AD")}
                  to={"/regulations"}>
                  {t("nav.Regulations.EnduranceTest")}
                </NavDropdown.Item>

                <NavDropdown.Item
                  as={Link}
                  onClick={() => onClickRegulation("BH")}
                  to={"/regulations"}>
                  {t("nav.Regulations.CompanionDog")}
                </NavDropdown.Item>

                <NavDropdown.Item
                  onClick={() => onClickRegulation("IPO1")}
                  to={"/regulations"}>
                  IPO I
                </NavDropdown.Item>

                <NavDropdown.Item
                  as={Link}
                  onClick={() => onClickRegulation("IPO2")}
                  to={"/regulations"}>
                  IPO II
                </NavDropdown.Item>

                <NavDropdown.Item
                  as={Link}
                  onClick={() => onClickRegulation("IPO3")}
                  to={"/regulations"}>
                  IPO III
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
              {!isAuthenticated && (
                <Nav.Link as={Link} className='me-2' to={"/users/login"}>
                  {t("nav.MembersArea.Login")}
                </Nav.Link>
              )}
              {isAuthenticated && (
                <NavDropdown
                  title={t("nav.MembersArea.Profile")}
                  id='basic-nav-dropdown'>
                  <NavDropdown.Item as={Link} to={"users/profile"}>
                    {fullName}
                  </NavDropdown.Item>
                  <NavDropdown.Divider />

                  <NavDropdown.Item as={Link} to={"users/logout"}>
                    {t("nav.MembersArea.Logout")}
                  </NavDropdown.Item>
                  {isAdmin && (
                    <>
                      <NavDropdown.Divider />
                      <NavDropdown.Item as={Link} to={"users/all"}>
                        {t("nav.MembersArea.AllUsers")}
                      </NavDropdown.Item>
                    </>
                  )}
                </NavDropdown>
              )}
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
