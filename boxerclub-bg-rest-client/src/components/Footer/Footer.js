import { Container } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { Link } from "react-router-dom";
import { FaSquareFacebook, FaLinkedin } from "react-icons/fa6";

export const FooterComponent = () => {
  const { t } = useTranslation();
  return (
    <footer className='text-center text-lg-start bg-white text-muted mt-5'>
      <section className='d-flex justify-content-center justify-content-lg-between p-4 border-bottom'>
        <Container className='me-5 d-none d-lg-block'>
          <span>Get connected with us on social networks:</span>
        </Container>

        <Container>
          <a
            href='https://www.facebook.com/groups/boxerclub/'
            className='me-4 link-secondary'>
            <FaSquareFacebook />
          </a>

          <a
            href='https://bg.linkedin.com/in/bozhidar-velikov-3aab573b'
            className='me-4 link-secondary'>
            <FaLinkedin />
          </a>
        </Container>
      </section>
      <section className='d-flex justify-content-center justify-content-lg-between'>
        <Container className='container text-center text-md-start mt-5'>
          <Container className='row mt-3'>
            <Container className='col-md-3 col-lg-4 col-xl-3 mx-auto mb-4'>
              <h6 className='text-uppercase fw-bold mb-4'>
                <i className='fa-solid fa-shield-dog'></i>
                {t("brand")}
              </h6>
            </Container>

            <Container className='col-md-2 col-lg-2 col-xl-2 mx-auto mb-4'>
              <h6 className='text-uppercase fw-bold mb-4'>{t("nav.Events")}</h6>
              <p>
                <Link to={"/events/upcoming"} className='text-reset'>
                  {t("nav.Events.International")}
                </Link>
              </p>
              <p>
                <Link to={"/events/upcoming"} className='text-reset'>
                  {t("nav.Events.Bulgarian")}
                </Link>
              </p>
            </Container>

            <Container className='col-md-3 col-lg-2 col-xl-2 mx-auto mb-4'>
              <h6 className='text-uppercase fw-bold mb-4'> {t("nav.Links")}</h6>
              <p>
                <a href='https://www.fci.be/en/' className='text-reset'>
                  FCI
                </a>
              </p>
              <p>
                <a href='https://kennelunion.bg/' className='text-reset'>
                  KENNEL UNION BULGARIA
                </a>
              </p>
              <p>
                <a href='https://www.atibox.dog/' className='text-reset'>
                  ATIBOX
                </a>
              </p>
            </Container>

            <Container className='col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4'>
              <h6 className='text-uppercase fw-bold mb-4 '>
                {t("nav.Contacts")}
              </h6>

              <p className='text-secondary'>
                <i className='fas fa-home font-size-15 align-middle pe-2 text-success'></i>
                Varna, 9002, Bulgaria
              </p>
              <p>
                <Link
                  className='myLinks text-secondary'
                  to='#'
                  onClick={(e) => {
                    window.location.href = `mailto:  office@boxerclub-bg.org`;
                    e.preventDefault();
                  }}>
                  <i className='fas fa-envelope font-size-15 align-middle pe-2 text-success'></i>{" "}
                  office@boxerclub-bg.org
                </Link>
              </p>

              <p>
                <Link
                  className='myLinks text-secondary'
                  to='#'
                  onClick={(e) => {
                    window.location.href = `tel:  +359 885 162 864`;
                    e.preventDefault();
                  }}>
                  <i className='fas fa-solid fa-mobile font-size-15 align-middle pe-2 text-success'></i>{" "}
                  +359 885 162 864
                </Link>
              </p>
            </Container>
          </Container>
        </Container>
      </section>
      <section className='d-flex justify-content-center justify-content-lg-between m-auto'>
        <Container className='text-center'>
          © 2023 Copyright:{" "}
          <a
            className='text-reset fw-bold'
            href='https://github.com/magdalenavelikova'>
            Magdalena Velikova
          </a>
        </Container>
      </section>
    </footer>
  );
};
