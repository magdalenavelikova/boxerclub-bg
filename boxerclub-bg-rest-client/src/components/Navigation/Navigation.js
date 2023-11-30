import { Container } from "react-bootstrap";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { Link } from "react-router-dom";
import { LanguageSwitcher } from "./LanguageSwitcher";
import { useTranslation } from "react-i18next";
import { useContext } from "react";
import { AuthContext } from "../../contexts/AuthContext";

export const Navigation = ({
  onRegulationClick,
  onLinkClick,
  onEventClick,
}) => {
  const logo = require("../../assets/logo.png");
  const { t } = useTranslation();
  const { isAuthenticated, activeUser, authorities } = useContext(AuthContext);
  const isAdminOrModerator =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR"));
  const isAdmin =
    isAuthenticated && authorities.some((item) => item === "ROLE_ADMIN");

  const isAuthorized =
    isAuthenticated &&
    (authorities.some((item) => item === "ROLE_ADMIN") ||
      authorities.some((item) => item === "ROLE_MODERATOR") ||
      authorities.some((item) => item === "ROLE_MEMBER"));

  return (
    <header>
      <Navbar
        collapseOnSelect
        expand='lg'
        className='bg-body-tertiary'
        fixed='top'>
        <Container fluid>
          <Navbar.Brand className='nav-item px-lg-2 me-2 text-secondary '>
            <Link
              className={"link-dark "}
              to={"/"}
              style={{ textDecoration: "none" }}>
              <img
                src={logo}
                width='30'
                height='30'
                className='d-inline-block align-top me-3'
                alt='Boxer Club Bulgaria'
              />

              <span className='d-none d-lg-inline-block'>{t("brand")}</span>
            </Link>
          </Navbar.Brand>

          <Navbar.Toggle aria-controls='navbarScroll' />
          <Navbar.Collapse id='navbarScroll'>
            <Nav className='m-auto my-2 my-lg-0' navbarScroll>
              <Nav.Link eventkey='1' as={Link} className='me-2' to={"/dogs"}>
                {t("nav.Dogs")}
              </Nav.Link>
              {isAuthorized && (
                <NavDropdown
                  eventkey='2'
                  className='me-2'
                  title={t("nav.Dog")}
                  id='collapsible-nav-dropdown'>
                  <NavDropdown.Item eventkey='2' as={Link} to={"dogs/register"}>
                    {t("nav.DogPedigree")}
                  </NavDropdown.Item>
                  <NavDropdown.Item
                    eventkey='3'
                    as={Link}
                    to={"dogs/register/nb"}>
                    {t("nav.DogNewborn")}
                  </NavDropdown.Item>
                  <NavDropdown.Item
                    eventkey='4'
                    as={Link}
                    to={"dogs/ownership"}>
                    {t("nav.DogChangeOwner")}
                  </NavDropdown.Item>
                </NavDropdown>
              )}
              <NavDropdown
                className='me-2'
                title={t("nav.Events")}
                id='collapsible-nav-dropdown'>
                <NavDropdown.Header>
                  {t("nav.Events.Upcoming")}
                </NavDropdown.Header>

                <NavDropdown.Item
                  eventkey='5'
                  as={Link}
                  onClick={() => onEventClick("bg")}
                  to={"/events/upcoming"}>
                  {t("nav.Events.Bulgarian")}
                </NavDropdown.Item>

                <NavDropdown.Item
                  eventkey='6'
                  as={Link}
                  onClick={() => onEventClick("int")}
                  to={"/events/upcoming"}>
                  {t("nav.Events.International")}
                </NavDropdown.Item>

                <NavDropdown.Divider />
                <NavDropdown.Header>
                  {t("nav.Events.Passed")}
                </NavDropdown.Header>
                <NavDropdown.Item
                  eventkey='7'
                  as={Link}
                  onClick={() => onEventClick("bg")}
                  to={"/events/passed"}>
                  {t("nav.Events.Bulgarian")}
                </NavDropdown.Item>
                <NavDropdown.Item
                  eventkey='8'
                  as={Link}
                  onClick={() => onEventClick("int")}
                  to={"/events/passed"}>
                  {t("nav.Events.International")}
                </NavDropdown.Item>
                {isAdminOrModerator && (
                  <>
                    <NavDropdown.Divider />
                    <NavDropdown.Item eventkey='9' as={Link} to={"events/add"}>
                      {t("nav.Events.AddEvent")}
                    </NavDropdown.Item>
                  </>
                )}
              </NavDropdown>
              <NavDropdown
                className='me-2'
                title={t("nav.Regulations")}
                id='collapsible-nav-dropdown'>
                {isAuthenticated && (
                  <>
                    <NavDropdown.Item
                      eventkey='10'
                      as={Link}
                      to={"regulations/breeding-rules"}>
                      {t("nav.Regulations.BreedingRules")}
                    </NavDropdown.Item>
                    <NavDropdown.Item
                      eventkey='11'
                      as={Link}
                      to={"regulations/act-of-incorporation"}>
                      {t("nav.Regulations.ActOfIncorporation")}
                    </NavDropdown.Item>

                    <NavDropdown.Divider />
                  </>
                )}

                <NavDropdown.Item
                  eventkey='12'
                  as={Link}
                  onClick={() => onRegulationClick("standard")}
                  to={"/regulations"}>
                  {t("nav.Regulations.Standard")}
                </NavDropdown.Item>

                <NavDropdown.Divider />
                <NavDropdown.Header>
                  {t("nav.Regulations.WorkingExams")}
                </NavDropdown.Header>

                <NavDropdown.Item
                  eventkey='13'
                  as={Link}
                  onClick={() => onRegulationClick("AD")}
                  to={"/regulations"}>
                  {t("nav.Regulations.EnduranceTest")}
                </NavDropdown.Item>

                <NavDropdown.Item
                  eventkey='14'
                  as={Link}
                  onClick={() => onRegulationClick("BH")}
                  to={"/regulations"}>
                  {t("nav.Regulations.CompanionDog")}
                </NavDropdown.Item>

                <NavDropdown.Item
                  eventkey='15'
                  as={Link}
                  onClick={() => onRegulationClick("SchH")}
                  to={"/regulations"}>
                  {t("nav.Regulations.Schutzhund")}
                </NavDropdown.Item>

                <NavDropdown.Item
                  eventkey='16'
                  as={Link}
                  onClick={() => onRegulationClick("IPO1")}
                  to={"/regulations"}>
                  IPO I
                </NavDropdown.Item>

                <NavDropdown.Item
                  eventkey='17'
                  as={Link}
                  onClick={() => onRegulationClick("IPO2")}
                  to={"/regulations"}>
                  IPO II
                </NavDropdown.Item>

                <NavDropdown.Item
                  eventkey='18'
                  as={Link}
                  onClick={() => onRegulationClick("IPO3")}
                  to={"/regulations"}>
                  IPO III
                </NavDropdown.Item>
              </NavDropdown>
              <NavDropdown
                className='me-2'
                title={t("nav.Links")}
                id='collapsible-nav-dropdown'>
                <NavDropdown.Item
                  eventkey='19'
                  as={Link}
                  onClick={() => onLinkClick("CANINE ORGANISATIONS")}
                  to={"/links"}>
                  {t("nav.Links.CanineOrganizations")}
                </NavDropdown.Item>
                <NavDropdown.Item
                  eventkey='20'
                  as={Link}
                  onClick={() => onLinkClick("BOXER CLUBS")}
                  to={"/links"}>
                  {t("nav.Links.BoxerClub")}
                </NavDropdown.Item>
                {isAdminOrModerator && (
                  <>
                    <NavDropdown.Divider />
                    <NavDropdown.Item eventkey='21' as={Link} to={"links/add"}>
                      {t("nav.Links.AddLink")}
                    </NavDropdown.Item>
                  </>
                )}
              </NavDropdown>
              {isAdminOrModerator && (
                <NavDropdown
                  className='me-2'
                  title={t("nav.Contacts")}
                  id='collapsible-nav-dropdown'>
                  <NavDropdown.Item eventkey='22' as={Link} to={"/contacts"}>
                    {t("nav.Contacts")}
                  </NavDropdown.Item>
                  <NavDropdown.Divider />
                  <NavDropdown.Item eventkey='23' as={Link} to={"contacts/add"}>
                    {t("nav.Contacts.AddContact")}
                  </NavDropdown.Item>
                </NavDropdown>
              )}

              {!isAuthenticated && (
                <Nav.Link
                  eventkey='24'
                  as={Link}
                  className='me-2'
                  to={"/contacts"}>
                  {t("nav.Contacts")}
                </Nav.Link>
              )}
              <Nav.Link
                eventkey='25'
                as={Link}
                className='me-2'
                to={"/gallery"}>
                {t("nav.Gallery")}
              </Nav.Link>
              {!isAuthenticated && (
                <Nav.Link
                  eventkey='26'
                  as={Link}
                  className='me-2'
                  to={"/users/login"}>
                  {t("nav.MembersArea.Login")}
                </Nav.Link>
              )}
              {isAuthenticated && (
                <NavDropdown
                  title={t("nav.MembersArea.Profile")}
                  id='collapsible-nav-dropdown'>
                  <NavDropdown.Item
                    eventkey='27'
                    as={Link}
                    to={"users/profile"}>
                    {activeUser.firstName} {activeUser.lastName}
                  </NavDropdown.Item>
                  {!isAuthorized && (
                    <>
                      <NavDropdown.Item
                        eventkey='28'
                        as={Link}
                        to={"users/membership"}>
                        {t("nav.MembersArea")}
                      </NavDropdown.Item>
                    </>
                  )}
                  <NavDropdown.Divider />

                  <NavDropdown.Item eventkey='29' as={Link} to={"users/logout"}>
                    {t("nav.MembersArea.Logout")}
                  </NavDropdown.Item>
                  {isAdmin && (
                    <>
                      <NavDropdown.Divider />
                      <NavDropdown.Item
                        eventkey='30'
                        as={Link}
                        to={"users/all"}>
                        {t("nav.MembersArea.AllUsers")}
                      </NavDropdown.Item>
                    </>
                  )}
                </NavDropdown>
              )}
            </Nav>
            <LanguageSwitcher fluid />
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </header>
  );
};
