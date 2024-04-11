# Project360 Clinic System

The Project360 Clinic System is an advanced JavaFX application designed to digitalize and streamline clinic operations, enhancing the interaction between patients, doctors, and nurses. It offers a secure, intuitive platform for managing medical records, appointments, and communications across different user roles.

## Key Features

- **User Authentication**: Secure login and registration processes with password hashing for patients, doctors, and nurses.
- **Role-Based Access Control**: Tailored interfaces and functionalities for each user role, ensuring relevant data accessibility and operations.
- **Medical Records Management**: Comprehensive management of patient medical records, including test results and doctor's notes.
- **Appointment Scheduling**: Facilitates the booking, viewing, and management of appointments.
- **Communication Platform**: Enables secure messaging between patients and healthcare providers.

## Getting Started

### Prerequisites

Ensure you have the following installed:
- Java Development Kit (JDK) 11 or newer.
- JavaFX SDK (version corresponding to the JDK).
- SQLite for local database management.
- BCrypt library for secure password hashing.

### Installation

1. **Clone the Repository**:
2. **Configure JavaFX**:
Set up JavaFX in your IDE and ensure it's correctly configured to run the application.
3. **Database Setup**:
Create the SQLite database using the schema provided in `schema.sql`. Place `identifier.sqlite` in the project's root directory.

### Running the Application

- Open the project in your IDE.
- Run the `MainApplication.java` file to start the system.
- Use the login screen to access the application. New users can register via the registration option.

## Usage

### For Patients
- **Profile Management**: View and update personal and medical information.
- **Appointment Scheduling**: Book new appointments and view upcoming ones.
- **Medical Records**: Access test results and doctor's diagnoses.

### For Doctors
- **Patient Records**: View and update patient medical histories and current health data.
- **Appointment Management**: Review and manage scheduled appointments.
- **Prescription and Notes**: Add prescriptions and notes to patient records.

### For Nurses
- **Patient Vitals**: Record and update patient vital signs.
- **Appointment Assistance**: Help in managing and organizing patient appointments.
- **Patient Management**: Assist in patient record updates and communications.

## Development

This project welcomes contributions. Please refer to the CONTRIBUTING.md file for more information on how to submit pull requests, report issues, and contribute to the development.



