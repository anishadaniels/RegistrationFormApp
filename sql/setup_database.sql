-- Script to create the 'registration_db' database and 'users' table
CREATE DATABASE IF NOT EXISTS registration_db;

USE registration_db;

CREATE TABLE IF NOT EXISTS users (
    ID INT PRIMARY KEY,
    Name VARCHAR(100),
    Gender VARCHAR(10),
    Address TEXT,
    Contact VARCHAR(15)
);
