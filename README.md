Author: Tommy Jones
Project Name: Hair Salon
Description: Database of one-to-many to organize a hair stylist and all their clients.

SQL Database Set Up
CREATE DATABASE hair_salon;
CREATE TABLE stylists (id serial PRIMARY KEY, name varchar);
CREATE TABLE clients (id serial PRIMARY KEY, name varchar, stylistid int);
