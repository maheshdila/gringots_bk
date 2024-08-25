# Gringots Banking Application Backend

## Overview
Gringots is a backend application designed for a banking system. It focuses on secure and efficient data handling while adhering to Codd's normalization principles to ensure data integrity.

## Features
- **Codd's Normalization:** Implements database normalization to reduce redundancy and improve data integrity.
- **MySQL Database:** Utilizes MySQL for reliable data storage and management.
- **Prepared Statements:** Enhances security by using prepared statements to prevent SQL injection attacks.
- **Stored Procedures:** Employs stored procedures for efficient data processing and complex queries.
- **Scheduled Transaction Queries:** Supports scheduled execution of transaction queries to automate and streamline banking operations.

## Technology Stack
- **Programming Language:** Java
- **Database:** MySQL

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL Server
- Maven (for dependency management)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/maheshdila/gringots_bk.git
   ```
2. Navigate to the project directory:
   ```bash
   cd gringots_bk
   ```
3. Configure your MySQL database and update the database connection settings in the configuration file.
4. Build the project using Maven:
   ```bash
   mvn clean install
   ```

### Running the Application
To run the application, use the following command:
```bash
java -jar target/gringots_bk.jar
```

### Scheduled Transaction Queries
The application includes functionality for scheduling transaction queries. This allows for automated execution of specific banking operations at defined intervals. Configuration for scheduled tasks can be found in the `application.properties` file.

## Contributing
Contributions are welcome! Please open an issue or submit a pull request for any enhancements or bug fixes.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
For any inquiries, please contact [your-email@example.com].
