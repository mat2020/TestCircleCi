# Forward Inc.

## Team Members

| Name | Student Number |
| --- | --- |
| Patrik Kelemen | 300059871 |
| Nolan Belnap | 300059878 |
| Angelica Paynter | 300062844 |
| Zhen Wang | 300057304 |
| Mathieu Bellefeuille | 6501369 |


## Deliverable 1

The purpose of this deliverable was to: 

- create our git repo and assign tasks to all team members
- have all teammates commit at least once
- create a UML class diagram of our domain model
- have an admin account with the username: admin and the password: 5T5ptQ
- be able to create any number of patient and employee accounts
- be able to login on any valid account and have a welcome message display: 
```
Welcome [firstname]!
You are logged in as [role].
```
- validate all fields in both the login screen and the register screen
- passwords are stored as SHA-256
- Bonus: use a database (we used SQLite)

## Deliverable 2

The purpose of this deliverable was to:

- update our [UML diagram](https://github.com/professor-forward/project-lab01-variable/tree/f/deliverable02/UML/uml_delv2.png) to reflect changes to the classes
- submit a working [APK](https://github.com/professor-forward/project-lab01-variable/tree/f/deliverable02/APK/)
- create 5 [Unit tests](https://github.com/professor-forward/project-lab01-variable/tree/f/deliverable02/app/p1-master/app/src/androidTest/java/com/uottawa/project) for our app (not testing instrumentation or Espresso UI testing)
- admin can create services
- admin can remove services
- admin can edit services
- all fields are validated with error messages (implemented as toasts)
- Bonus: integrate CircleCi


Additional Notes:

- Admin can delete Patient and Employee accounts
- Note: Database was changed to Firebase from SQLite in Deliverable 1, google-services.json is not tracked by GitHub for security purposes, the APK will work 
- There is one Admin account. The login is username: admin, password: 5T5ptQ

## Deliverable 3

The purpose of this deliverable was to:

- update our [UML diagram](https://github.com/professor-forward/project-lab01-variable/tree/f/deliverable03/UML/) to reflect changes to the classes
- submit a working [APK](https://github.com/professor-forward/project-lab01-variable/tree/f/deliverable03/APK/)
- create 2 new [Unit tests](https://github.com/professor-forward/project-lab01-variable/tree/f/deliverable03/app/p1-master/app/src/androidTest/java/com/uottawa/project) for our app (not testing instrumentation or Espresso UI testing)
- employee can create clinics with: an address, phone number, name, insurance type and payment methods
- employee can add services to the clinic
- employee can delete services from the clinic
- employee can view and change their working hours
- employee can view and change the clinic's working hours
- Bonus: the employee can edit working hours

Additional Notes:
- Note: Database was changed to Firebase from SQLite in Deliverable 1, google-services.json is not tracked by GitHub for security purposes, the APK will work 
- There is one Admin account. The login is username: admin, password: 5T5ptQ
