<h1> Bulgarian Boxer club</h1>

<h3>Welcome to the Bulgarian Boxer club website repository! My first Rest API built using Spring Boot and ReactJS.</h3>

<img src="/github_images/home.png" alt="Project image" style="width: 700px"/>

<h3>Deployed to :</h3>

https://boxer-club.web.app/

<h3> Table of Contents </h3>
<ul>
  <li><a href="#features">Features</a></li>
  <li><a href="#project-requirements">Project Requirements</a></li>
  <li><a href="#getting-started">Getting Started</a></li>
  <li><a href="#technologies-used">Technologies Used</a></li>
  <li><a href="#demo">Demo</a></li>
  <li><a href="#license">License</a></li>
  <li><a href="#documentation">Documentation</a></li>
  <li><a href="#more-pictures">More Pictures</a></li>
</ul>

<h2 id="features"> Features </h2>

<ul>
  <li>User authentication and registration</li>
  <li>Stores authentication details like token information in local storage.</li>
  <li>Email validation link after registration.</li>
  <li>Forgotten password</li>
  <li>Change password</li>
  <li>Multi-language support</li>
  <li>Search bar</li>
  <li>Recursively create the nested tree structure</li>
  <li>User-friendly interface</li>
  <li>Responsiveness support for all devices</li>
  <li>All can view all contacts, regulations, links, events, dogs</li>
  <li>All can view dog details, download pedigree or explore pedigree view</li>
  <li>All can browse and search for all approved dogs</li>
  
  <hr>
  <li>User can view breading rules</li>
  <li>User can view act of incorporation</li>
  <li>User can make request for membership</li>
  <li>User can view and edit profile details</li>
  <hr>
    <li>Member can add newborn dog and its parents</li>
  <li>Member can add dog with pedigree and its parents</li>
  <li>Member can create a query for change ownership. When the request is submitted the system automatically sends an email to the current owner. There is a button in the email with which he can confirm the change of ownership</li>
  <li>Member can view and edit user profile</li>
  <li>Member can edit details for owned dogs</li>
  <hr>
  <li>Admin can see all registered users, change their roles or details and delete them</li>
  <hr>
  <li>Admin or Moderator can add, edit and delete users, dogs, contacts, regulations, links and events</li>
  <li>Admin or Moderator can approve dog registration</li>
  <li>Admin and Moderator receive an email for every new dog registration</li>

</ul>

<h2 id="project-requirements"> Project requirements </h2>
<p>The actual site is bilingual (BG & EN) and consists of:
Public area:
News Section, containing info on upcoming shows and results of past shows
Standard of the breed
Working exam regulations
Links to boxer clubs worldwide, as well as canine organizations, where the BG boxer club is member.
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

</p>

<h2 id="getting-started"> Getting Started </h2>
<ol>
  <li>Clone the repository to your local machine.</li>
  <li>Configure your MySQL database by updating the application.yml file.</li>
  <li>Configure environment variables by updating the application.yml file.</li>
  <li>Build and run the project boxerclub-bg-rest-server using Gradle.</li>
  <li>Build and run the project boxerclub-bg-rest-client using npm. Follow these step first: 1) go to \boxerclub-bg-rest-client 2) npm install  3) npm start.</li>
  <li>Access the web application by visiting http://localhost:3000 in your web browser.</li>
  <li>Create user accounts, manage products, and place orders as needed.</li>
  <li>Enjoy</li>
</ol>

<h2 id="technologies-used"> Technologies Used </h2>
<ul>
  <li>Java 17</li>
  <li>Spring Boot 3.1.3</li>
  <li>Spring Security</li>
  <li>Spring Batch</li>
  <li>Spring Data JPA</li>
  <li>Jwt.io</li>
  <li>React, Bootstrap for React</li>
  <li>HTML, CSS, JavaScript</li>
  <li>MySQL</li>
  <li>Cloudinary for managing images</li>
  <li>Spring Boot Mail  </li>
    <li>MapStruct</li>
  <li>Swagger: API Documentation & Design Tools for Teams</li>
  
</ul>

<h2 id="demo"> Demo </h2>
<p>Deployed to : <a href="https://boxer-club.web.app/">https://boxer-club.web.app/</a></p>

<h2 id="license"> License </h2>
<ul>
  <li>MIT License</li>
</ul>
<h2 id="documentation"> Documentation </h2>
<ul>
  <li>Documentation on : <a href="https://boxerclub-bg-spring-app-boxerclub-bg-server.azuremicroservices.io/swagger-ui/index.html#">https://boxerclub-bg-spring-app-boxerclub-bg-server.azuremicroservices.io/swagger-ui/index.html#</a></li>
  <img src="/github_images/swagger-ui.png" style="width: 700px"/>
</ul>

<h2 id="more-pictures"> More pictures: </h2>
<img src="/github_images/dogs_admin.png" style="width: 700px"/>
<img src="/github_images/pedigree.png" style="width: 700px"/>
<img src="/github_images/details.png" style="width: 700px"/>
<img src="/github_images/edit.png" style="width: 700px"/>
<img src="/github_images/delete.png" style="width: 700px"/>
<img src="/github_images/delete_parent.png" style="width: 700px"/>
<img src="/github_images/register.png" style="width: 700px"/>
<img src="/github_images/add_parent.png" style="width: 700px"/>
<img src="/github_images/events.png" style="width: 700px"/>
<img src="/github_images/links.png" style="width: 700px"/>
<img src="/github_images/regulations.png" style="width: 700px"/>
<img src="/github_images/contacts.png" style="width: 700px"/>
