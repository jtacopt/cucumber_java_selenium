#Selenium Test Automation with Maven and JUnit 5

This project is a test automation framework built with Selenium WebDriver, Maven, and JUnit 5. It aims to provide a scalable and maintainable solution for automated testing of web applications.

## Installation

To use this project, you need to have the following software installed on your machine:

- Java Development Kit (JDK) 17 or higher
- Maven
- Selenium 

Once you have installed the necessary software, you can clone this repository and import it into your favorite IDE as a Maven project.

## Usage

To run the tests, you can use the following Maven command:

```
mvn clean test
```

This will run all the tests in the project and generate a report in the `target/surefire-reports` directory.

You can also run individual test classes or methods using the `-Dtest` option. For example, to run a single test class, you can use the following command:

```
mvn clean test -Dtest=MyTest
```

To run a single test method, you can use the following command:

```
mvn clean test -Dtest=MyTest#myTestMethod
```

## Configuration

WIP

## Contributing

If you would like to contribute to this project, please fork the repository and create a pull request. We welcome contributions of all kinds, including bug fixes, new features, and documentation improvements.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more information.