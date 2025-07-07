# HealthCareAppointment API

A Spring Boot RESTful web service for managing healthcare appointments. Features include: patients, doctors, specialties, schedules, and appointments management.

## Table of Contents
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Setup & Configuration](#setup--configuration)
- [Building & Running](#building--running)
- [API Endpoints](#api-endpoints)
  - [Base URL](#base-url)
  - [Patients](#patients)
  - [Doctors](#doctors)
  - [Specialties](#specialties)
  - [Schedules](#schedules)
  - [Appointments](#appointments)
- [Testing](#testing)
- [License](#license)

## Technologies
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL
- Lombok

## Prerequisites
- Java 17 (or higher)
- Maven 3.x
- MySQL database

## Setup & Configuration
1. Clone the repository:
   ```bash
   git clone <repo_url>
   cd HealthCareAppointment
   ```
2. Create a MySQL database:
   ```sql
   CREATE DATABASE healthcare_appointment;
   ```
3. Update `src/main/resources/application.properties` with your DB credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/healthcare_appointment
   spring.datasource.username=<your_username>
   spring.datasource.password=<your_password>
   ```
4. (Optional) Customize other settings in `application.properties` (e.g., JWT, mail, etc.).

## Building & Running
Build the project:
```bash
mvn clean package
```
Run the application:
```bash
mvn spring-boot:run
```
The API will start on `http://localhost:8080`.

## API Endpoints
### Base URL
```
${apiPrefix} = /api/v1
```

### Patients
- `GET /api/v1/patients/patient/all` – List all patients
- `POST /api/v1/patients/patient/add` – Add a new patient
- `GET /api/v1/patients/patient/{id}` – Get patient by ID
- `PUT /api/v1/patients/patient/update/{id}` – Update patient
- `DELETE /api/v1/patients/patient/delete/{id}` – Delete patient

### Doctors
- `GET /api/v1/doctors/doctor/all` – List all doctors
- `POST /api/v1/doctors/doctor/add` – Add a new doctor
- `PUT /api/v1/doctors/doctor/update/{id}` – Update doctor
- `DELETE /api/v1/doctors/doctor/delete/{id}` – Delete doctor
- `GET /api/v1/doctors/doctor/{id}` – Get doctor by ID
- `GET /api/v1/doctors/filter?specialty={name}` – Filter by specialty

### Specialties
- `GET /api/v1/specialties/specialty/all` – List all specialties
- `POST /api/v1/specialties/specialty/add` – Add a new specialty

### Schedules
- `GET /api/v1/schedules/schedule/all` – List all schedules
- `POST /api/v1/schedules/schedule/add` – Add a new schedule (use JSON body)

### Appointments
- `GET /api/v1/appointments/appointment/all` – List all appointments
- `GET /api/v1/appointments/appointment/{id}` – Get by ID
- `POST /api/v1/appointments/appointment/add` – Create appointment
- `PUT /api/v1/appointments/appointment/confirm/{id}` – Confirm appointment
- `PUT /api/v1/appointments/appointment/cancel/{id}` – Cancel appointment

## Testing
Run unit and integration tests:
```bash
mvn test
```

## License
This project is licensed under the MIT License.
