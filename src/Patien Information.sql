CREATE DATABASE hospital_db;

\c hospital_db;

CREATE TABLE patients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    email VARCHAR(100),
    phone VARCHAR(15),
    address TEXT
);
