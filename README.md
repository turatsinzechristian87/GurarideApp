GurarideApp
WebTech Final Project

GuraRide Bike Rental Management System

Name: Turatsinze Christian ID: 24029

Table of Content 0. Final Deployment link

Project Description
Problem Statement
Project Requirement (Functional and Non-Functional requirement )
Project Plan
Database Schema
User Documentation
Technical Documentation
Source Code
 

Project Description The GuraRide Bike Rental Management System is an innovative web application specifically created to assist GuraRide in effectively managing their daily bike rentals in Kigali City. With the aim of streamlining the rental process and improving customer service, this system provides a comprehensive solution for both customers and GuraRide staff. Customers can easily rent bikes online and conveniently track their reservations, ensuring a seamless experience. Meanwhile, GuraRide benefits from an intuitive and user-friendly interface that allows them to efficiently manage their bike inventory and keep track of bike locations and statuses. By leveraging the GuraRide Bike Rental Management System, customers gain the ability to effortlessly browse through the available bike options, select their desired bike, and make reservations with just a few clicks. This online platform eliminates the need for customers to physically visit the rental store, saving them time and effort. Moreover, customers can conveniently monitor their reservations, ensuring they have access to the bike when they need it. The system's integration with GuraRide's inventory management enables real-time availability updates, ensuring accurate information is provided to customers during the reservation process. From GuraRide's perspective, the management system offers a range of powerful features to streamline their operations. Staff members can log into the system, view reservations, and efficiently handle the confirmation or rejection of reservations. This functionality provides GuraRide with centralized control and a comprehensive overview of their rental activities. Additionally, the system assists GuraRide in the check-in and check-out processes. When a customer arrives to pick up their bike, GuraRide staff can easily confirm the reservation and complete the bike handover process. The system also aids in tracking the return of bikes, enabling staff to efficiently manage the availability and location of each bike in real-time. Overall, the GuraRide Bike Rental Management System serves as a valuable tool in enhancing the efficiency and customer experience of GuraRide's bike rental services. With its user-friendly interface, customers can conveniently rent bikes online and monitor their reservations, while GuraRide gains the ability to efficiently manage their inventory, track bike locations, and ensure seamless operations. By leveraging this digital solution, GuraRide can elevate their rental business, optimize resource allocation, and deliver an exceptional customer service experience in Kigali City.

Problem Statement GuraRide is currently managing their bike rentals using paper-based systems, which can be time-consuming and prone to errors. They also do not have a way to track bike reservations or customer information, which makes it difficult for them to provide a high level of customer service. The GuraRide Bike Rental Management System aims to solve these problems by providing a digital solution that streamlines the rental process and allows for easy tracking of inventory and customer information.

Project Requirement (Functional and Non-Functional requirement ) 3.1 Functional Requirement of the system ○ All users should be allowed to login and create an account to the system. ○ Customers [renter] should be able to login into the system and browse and explore the system functionalities; including viewing the available bikes; reserving the bikes they want at a given time and monitoring the progress. ○ The customer browses the available bikes, selects the desired bike, and makes a reservation. ○ Guraride staff [ worker ] should be able to browse customers' info and have to confirm the reservation for the customer [renter] or reject the reservation. ○ GuraRide staff [worker] can log into the system to view reservations, track inventory, and manage bike rentals. ○ Guraride staff should be able to confirm the bikes return form the rented bikes. ○ When a customer arrives to pick up their bike, GuraRide staff can use the system to confirm the reservation and check out the bike to the customer. ○ GuraRide staff can also use the system to track the return of the bike. ○ The admin of the system has control over all the information involved in the whole application. He can edit and delete users of the system, he can register new bikes, edit and delete bike information. He can also generate appropriate reports for the managerial functions. ○ The system can generate reports to both admin, the usual staff and the customer accordingly, as specified by their system level to help GuraRide track rental revenue, inventory levels, and customer information. ○ The system should allow the logged in user to logout as they wish. ○ The system should implement authentication and authorization using spring security for all the users who want to access the privileged system resources. 3.2 Non-Functional Requirement of the system ● User data privacy and security, by ensuring that all users data are kept safe and only authenticated, authorized and people with right privileges are the one to access the dedicated resources accordingly. ● System performance and availability. ● User-friendly interface design. The System, the interface should be intuitive, visually appealing, and easy to navigate. ● Compatibility with different devices and browsers. Ensure that the GuraRide Bike Rental Management System is compatible and functions correctly across different platforms.

Project Plan This project was done in a period of two weeks, starting from the 3rd December, 2023 to 16th December, 2023. The overall timeline involved combining both the design and the implementation part interchangeably.

Database Schema The GuraRide Bike Rental Management System consists of 3 tables described below. ● ‘Users’ Contains all the information of the user of the application. ● ‘Bikes’ Also contains the information of the Bikes. As seen in the picture below we shall be recording the bike brand, bike model and the status, + the id, createdAt and the updateAt info. ● ‘Rentals’ Finally the rentals table will be used to record the daily transaction of the rentals operation. It contains the ids foreign keys from the users and bikes table, where at each transaction we shall be saving the user_id to indicate the person who rented or reserved the bike and the bike id to indicate the bikes which s/he rents or reserve. In the below ERD the rentals bike_id and the user_id are mapped from the bikes and users tables respectively.

User Documentation ➔ This is the ‘/’ path accessible when you run the project for the first time.

➔ Login page for the users to enter in the system.

➔ Signup page for the first time users to use throughout the system.

The application uses 3 personnel; which include the admin, worker and renter. User should first provide their credentials [email and password ] for authentication so that they can access the privileged resources and each user will be redirected to a respective page according to his/her role in the application. ➔ The Admin Dashboard View, where he/she has access to view/edit/delete all the user information, and bikes information.

➔ Admin User can also view the bike information and also register a new bike to the system.

The Guraride Staff described in the project as the ‘worker’ will have all these functionalities ➔ The Guraride Staff dashboard view

➔ The Staff can view all the users who have rented or reserved the bikes, with their information. ➔ The Guraride Staff can also view all bike information and be able to delete/edit the bikes and also register new bikes.

➔ The GuraRide Staff will be the one to check and approve if bikes have been returned by the users, and record them here.for all the bike which have not been returned there will be that button for the staff to approve and the time the users returns the bike.

The User will be able to view the dashboard, view all the available bikes, reserve a bike and view information about his/her history of the bikes rental. ➔ The User View of all the bikes available for rent and reservation

➔ User Viewing his/her reserved bikes status.

➔ The User view of the rented bikes, and the status whether their returned them or not.

For the addition all the user should be able to logout to the system and I have applied authorization rules to privileged paths so that only privileged user can view the page. ➔ Here is the example with the error message on the login page after redirection.

➔ Also I have applied validation on the form submission to ensure that inputs from the user side are well validated, both when the password is not correct or user don’t have an account.

Technical Documentation This is a web application project and was built using Spring Boot, an open-source Java framework that is designed to simplify the development of Java applications, particularly web applications and microservices. 7.1 Languages that were used in this project For the backend I have used Java and I have used HTML, CSS for 7.2 Frameworks/modules and dependencies that were used in the project are: ● Spring Boot ● Spring Web ● Spring Security ● Thymeleaf ● Maven ● Lombook ● PostgreSQL ● Docker \etc…

Source Code I have this application on github and it can be accessed here ● https://github.com/turatsinzechristian87/GurarideApp

Thanks.
