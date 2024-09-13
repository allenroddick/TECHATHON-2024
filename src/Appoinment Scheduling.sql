CREATE DATABASE appointment_db;

\c appointment_db;

CREATE TABLE appointments (
    id SERIAL PRIMARY KEY,
    patient_name VARCHAR(100),
    doctor_name VARCHAR(100),
    appointment_date DATE,
    appointment_time TIME,
    contact_number VARCHAR(15),
    reason TEXT
);
