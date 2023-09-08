# spring-boot-react-app

My first application based on the microservices architecture built using Spring Boot and ReactJS.
App for the Bulgarian Boxer club

**DEMO**

- Deployed to :
  https://www.boxerclub-bg.org/
  **Note:** It will be depoyed soon

**FEATURES**

- Google and Facebook OAuth 2.0 support for quick login.
- Regular Username/Password authentication.
- Multi-language support
- Search bar.
- Stores data in the MySQL database.
- Stores API data in Redis Cache to minimize network calls.
- Recursively create the nested tree structure
- Stores authentication details like token information in cookies.
- Responsiveness support for all devices.

**PROJECT REQUREMENTS**

The actual site is bilingual (BG & EN) and consists of:
Public area:
News Section, containing info on upcoming shows and results of past shows
Standard of the breed
Working exam regulations
Links to boxer clubs worldwide, as well as canine organizations, to which the BG boxer club belongs
Contacts
Gallery
In the BG Version only there is an additional Library menu containing a numerous boxer related articles about the anatomy, the breeding etc.
Private area for registered users adds to the above content in Bulgarian only:
Articles of association of the Boxer club
Breeding rules
Information on how to become a member
List of actual members (Members with paid fees)
Database of the club

Creating a stud book Database for the Bulgarian boxer Club

The database should be available for:
Viewing only for the registered users of the main site
Adding a record for club members and, or kennels
Review and approval are allowed by Mods and Administrators only
Additional option is the possibility to upload image/pdf of the Pedigree/export Pedigree of the dog in order to verify the data.

**TOOLS USED**

- **ReactJS:** Front-end Javascript framework.
- **Spring Boot 2.0:** Back-end JAVA framework to build microservices using Spring
  Rest Controller and Spring JPA.
- **React Bootstrap:** Complete re-implementation of the Bootstrap components using React.
- **Grommet:** Build responsive and accessible mobile-first projects for the web with an easy to use component library.
- **MySQL:** Stores product and user information.
- **Cloudinary:** CDN server for storing images.
- **Google OAuth:** 3rd Party authentication service for quick login by retrieving user profile information.
- **Facebook OAuth:** 3rd Party authentication service for quick login by retrieving user profile information.
