# Nubank challenge

1. [About the Project](#about-the-project)
2. [Getting Started](#getting-started)
   1. [Prerequisites](#prerequisites)
   2. [Installation](#installation)
   3. [Usage](#usage)

#about-the-project
## About the Project

#getting-started
## Getting Started
#prerequisites
### Prerequisites
- Install version 11 of the JDK
    - [Manual](https://adoptium.net/?variant=openjdk11)
    - [IntelliJ](https://www.jetbrains.com/help/idea/sdk.html#jdk)
    - Homebrew
      ```bash
      brew install openjdk@11
      ```

- Set the `JAVA_HOME` environment variable to the path where your JDK is located
    - [Windows](https://confluence.atlassian.com/doc/setting-the-java_home-variable-in-windows-8895.html)
    - macOS/Linux/WSL
      ```bash
      echo 'export JAVA_HOME=/path/to/jdk' >> ~/.bashrc 
      ```

#installation
### Installation
- Run the following to install dependencies and compile the project:
    - Windows
      ```bash
      mvnw.bat compile
      ```
    - macOS/Linux/WSL
      ```bash
      ./mvnw compile
      ```

#usage
### Usage
- Start the compiled application:
    - Windows/macOS/Linux/WSL
      ```
      java -jar ./target/nubank-challenge.jar
      ```

- Insert the desired operations (example bellow):
    - Windows/macOS/Linux/WSL
      ```
      [{"operation":"buy", "unit-cost":10.00, "quantity": 10000}, {"operation":"sell", "unit-cost":20.00, "quantity": 5000}]
      ```

- To stop the application just send two empty lines in sequence