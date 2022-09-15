# Schedule_Application
## Software II - Advanced Java Application Development - C195

### PURPOSE OF THE APPLICATION:
The purpose of the application is to provide a GUI based scheduling application that utilizes mySQL workbench.

### AUTHOR:  Christopher Miller cmil788@wgu.edu

### APPLICATION VERSION:  1.0

### DATE:  September 15, 2022

### IDE:  IntelliJ Community 

### JavaFX:  JavaFX-SDK-17

### JDK VERSION:

### MySQL Connector: 

### HOW TO RUN PROGRAM:
When the  ▶️ run button in IntelliJ to start the application is selected and the program is initialized. The user is directed to the 
login page where they are asked to enter a username and password that match current usernames and password in the mySQL workbench database.

Current usernames and passwords include:  (test, test),  (user, user),  (admin, admin).  If the user logins in as (admin) they can add, update, 
delete the Users, however (test and user) will not have access to the Users button. 

Also, when the login page is initialized the current users ZonedID, is record as well as, Locale language setting of (English or French) and the 
Login Page is converted to the users language setting.

Once a valid username and password are entered, a dialog window will open letting the user know if there are any upcoming appointments within the 
next 15 minutes.  The user is then taken to the Main Page. The user can select from different buttons which view they would like to be redirected 
to. For example: if the user chooses the Appointments button, they will be taken to the Appointments page that displays a list of all upcoming 
appointments, on this page the user can Add new appointments, Update existing appointments or Delete Appointments. 

When the user is ready to exit the program, they can select the 🅇 in the top right corner or select the Exit button in the Main Menu page.  If the 
user wants to log-in as a different user, they can select the Log-Out button in the Main Menu page and are taken back to the Login page.

### Additional View Reports:
For the additional required report, I choose to display the login_activity.txt file in the text area under the tab Login Activity.  It displays 
each login attempt with the entered username, a timestamp of the attempt, and success or failure entry.  I also added a Deleted Appointments 
Report that displays all deleted appointments.





