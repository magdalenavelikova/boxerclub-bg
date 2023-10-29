export const FooterComponent = () => {
  return (
    <footer className='text-center text-lg-start bg-white text-muted mt-5'>
      <section className='d-flex justify-content-center justify-content-lg-between p-4 border-bottom'>
        <div className='me-5 d-none d-lg-block'>
          <span>Get connected with us on social networks:</span>
        </div>

        <div>
          <a
            href='https://www.facebook.com/groups/boxerclub/'
            className='me-4 link-secondary'>
            <i className='fab fa-facebook-f'></i>
          </a>

          <a
            href='https://bg.linkedin.com/in/bozhidar-velikov-3aab573b'
            className='me-4 link-secondary'>
            <i className='fab fa-linkedin'></i>
          </a>
        </div>
      </section>
      <section className=''>
        <div className='container text-center text-md-start mt-5'>
          <div className='row mt-3'>
            <div className='col-md-3 col-lg-4 col-xl-3 mx-auto mb-4'>
              <h6 className='text-uppercase fw-bold mb-4'>
                <i className='fa-solid fa-shield-dog'></i>Boxer Club Bulgaria
              </h6>
              <p>We are the best</p>
            </div>

            <div className='col-md-2 col-lg-2 col-xl-2 mx-auto mb-4'>
              <h6 className='text-uppercase fw-bold mb-4'>Events</h6>
              <p>
                <a href='#!' className='text-reset'>
                  International
                </a>
              </p>
              <p>
                <a href='#!' className='text-reset'>
                  Bulgarian
                </a>
              </p>
            </div>

            <div className='col-md-3 col-lg-2 col-xl-2 mx-auto mb-4'>
              <h6 className='text-uppercase fw-bold mb-4'>Useful links</h6>
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
            </div>

            <div className='col-md-4 col-lg-3 col-xl-3 mx-auto mb-md-0 mb-4'>
              <h6 className='text-uppercase fw-bold mb-4'>Contact</h6>

              <p>
                <i className='fas fa-home me-3 text-secondary'></i>Varna, 9002,
                Bulgaria
              </p>
              <p>
                <i className='fas fa-envelope me-3 text-secondary'></i>
                bozhidar.velikov@gmail.com
              </p>

              <p>
                <i className='fas fa-solid fa-mobile me-3 text-secondary'></i>{" "}
                +359 885 162 864
              </p>
            </div>
          </div>
        </div>
      </section>

      <div
        className='text-center p-4 me-3'
        style={{ backgroundColor: "rgba(0, 0, 0, 0.025)" }}>
        © 2023 Copyright:
        <a
          className='text-reset fw-bold'
          href='https://github.com/magdalenavelikova'>
          Magdalena Velikova
        </a>
      </div>
    </footer>
  );
};
