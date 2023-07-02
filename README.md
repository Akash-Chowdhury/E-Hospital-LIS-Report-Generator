
 # E-Hospital LIS (Laboratory Information System) Report Generator


E-Hospital LIS Report Generator is a microservice API written in Java using the Spring Boot framework. This API allows you to convert HL7 (Health Level 7) messages into PDF reports. It provides an efficient and scalable solution for generating reports from HL7 messages, which are commonly used for exchanging medical information between different healthcare systems.

## Features

- Convert HL7 messages to PDF reports
- Support for various HL7 message versions (e.g., HL7 v2.x)
- Customizable report templates
- Generate visually appealing reports with rich formatting options
- Scalable and efficient processing of large volumes of HL7 messages
- Easy integration with existing healthcare systems
- Extensible architecture for adding new features or customizations

## Background on HL7 Messages
Health Level Seven (HL7) is the standard that ensures data consistency across disparate systems, and plays a major role in healthcare interoperability. Health Level Seven (HL7®) is a healthcare-specific standards organization whose primary focus is creating a defined set of international messaging standards used to support interoperability and communication between applications and devices.

Sample HL7 Message:

`MSH|^~\&|SendingApp|SendingFacility|ReceivingApp|ReceivingFacility|20230620120000||ORU^R01|MSG001|P|2.3
PID|1|54321|12345^^^HospitalID||Akash^Chowdhury||20010507|M|||Agartala^799002||(555)555-5555||S||123456789|98765432
OBR|1|1234567^^^HospitalID|9876543^^^LabID|CBC^Complete Blood Count|R||20230620110000||||PhysicianID^Doe^John^^Dr.^^^|||20230620120000
OBX|1|NM|WBC^White Blood Cell Count^LN||6.7|10.3/uL|4.5-11.0|N|||F
OBX|2|NM|RBC^Red Blood Cell Count^LN||5.2|10.6 mg/dL|4.7-6.1|N|||F
OBX|3|NM|HGB^Hemoglobin^LN||15.0|15 g/dL|14.0-18.0|N|||F
OBX|4|NM|HCT^Hematocrit^LN||45.0|%|42.0-52.0|N|||F
OBX|5|NM|PLT^Platelet Count^LN||250|10^3/uL|150-450|N|||F
NTE|1||Additional comments or notes regarding the test results.
`

## Prerequisites

To run the eHospital LIS Report Generator API, ensure that you have the following installed:

- Java Development Kit (JDK) 8 or later
- Maven build tool
- A compatible IDE (e.g., IntelliJ IDEA, Eclipse)

## Getting Started

Follow the steps below to set up and run the eHospital LIS Report Generator API:

1. Clone the repository:
`https://github.com/Akash-Chowdhury/HL7-TO-PDF`
2. Navigate to the project directory:
`cd E-Hospital-LIS-Report-Generator`
3. Build the project using Maven:
`mvn clean install`
4. Run the API:
`mvn spring-boot:run`
5. The API will be accessible at `http://localhost:8080` by default.
## Usage

To convert an HL7 message to a PDF report, send a POST request to the `/pdf/report` endpoint with the HL7 message as the request body. The API will return the generated PDF report.

Example using cURL:
`curl -X POST -H "Content-Type: application/json" -d '{"hl7Message": "your-hl7-message"}' http://localhost:8080/api/convert --output report.pdf`

## Configuration

The eHospital LIS Report Generator API provides various configuration options. These can be modified in the `application.properties` file located in the `src/main/resources` directory. Some of the key configuration properties include:

- `server.port`: The port on which the API should run (default: 8080).
- `report.template.directory`: The directory path where the report templates are located.
- `report.output.directory`: The directory path where the generated reports should be saved.

## Customization

To customize the report templates, locate the template files in the specified `report.template.directory`. These templates can be modified to suit your specific requirements, such as adding additional fields, changing the layout, or applying different styles.

## Contributing

Contributions to the eHospital LIS Report Generator API are welcome! If you have any bug reports, feature requests, or suggestions, please open an issue on the [GitHub repository](https://github.com/your-username/ehospital-lis-report-generator/issues).

## License

This project is licensed under the [MIT License](LICENSE).

## Resources

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [HL7](https://www.hl7.org/)

## Acknowledgements

Special thanks to the contributors and maintainers of the libraries and frameworks used in this project. Your work is greatly appreciated!

## Contact

For any inquiries or additional information, please contact:

Akash Chowdhury.


I hope that eHospital LIS Report Generator helps simplify the process of converting HL7 messages to PDF reports in your healthcare system. If you have any questions or need further assistance, feel free to reach out.
