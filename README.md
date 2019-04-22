## Title

Hair_Salon Java Application


## Author

Abigail Murugi


## Description

It is a Java Application where the owner should be able to add a list of the stylists,

and for each stylist, add clients who see that stylist. The stylists work independently,

so each client only belongs to a single stylist.


## Project SetUp

If you want to use this as your template, here's how to go about it:

Install Git on you machine

Maneuver to 'clone or download' button and get the link

--Linux Users-- open your terminal and run the 'git clone ...' command in a directory of your choice

--for Windows Users-- Navigate to the location you'd want the repository located, right click and select "git bash here"

Clone the repository

Upon completion, navigate to the cloned repository Feel free to edit the files to your prefered taste

Now build the Database to enable storing of created instances

Install Postgres SQL

Run psql command to access DataBase

then run the following commands in your terminal

In PSQL:

CREATE DATABASE hair-salon;

CREATE TABLE Stylists (id serial PRIMARY KEY, firstname varchar, middlename varchar, lastname varchar, Age varchar, Address varchar);

CREATE TABLE Clients (id serial PRIMARY KEY, firstname varchar, middlename varchar, lastname varchar, Age varchar, Address varchar);

CREATE DATABASE hair_salon_test WITH TEMPLATE hair_salon;

## Technologies Used

* IntelliJ IDEA version 8

* Terminal

* Gradle build

* DataBase

# License

MIT license
