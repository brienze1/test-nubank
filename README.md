<div id="top"></div>

# Nubank challenge

1. [About the Project](#about-the-project)
    1. [Input](#input)
    2. [Output](#output)
    3. [Rules](#rules)
    4. [Built With](#built-with)
        1. [Dependencies](#dependencies)
        2. [Test Dependencies](#test-dependencies)
        3. [Plugins](#plugins)
    5. [Roadmap](#roadmap)
2. [Getting Started](#getting-started)
    1. [Prerequisites](#prerequisites)
    2. [Installation](#installation)
    3. [Usage](#usage)
        1. [Manual Input](#manual-input)
        2. [File Input](#file-input)
        3. [Docker Input](#docker-input)
    4. [Testing](#testing)

## About the Project

The objective of this challenge is to implement a command-line program (CLI) that calculates the tax to be paid on
profits or losses from operations in the financial stock market.

### Input

The program should receive lists, one per line, of stock market operations in json format through standard input (
stdin ). Each operation in the list must contain the following fields:

- operation: Whether the operation is a buy (buy) or sell (sell) operation
- unit-cost: Unit price of the share in a currency to two decimal places
- quantity: Number of shares traded

Example of how the line should look like:

```
[{"operation":"buy", "unit-cost":10.00, "quantity": 10000}, {"operation":"sell", "unit-cost":20.00, "quantity": 5000}]
```

The operations will be in the order in which they occurred, that is, the second operation in the list happened after the
first one, and so on.
Each line is an independent simulation, your program should not keep the state obtained in one line for the others.
The last line of input will be an empty line.

### Output

For each line of the entry, the program must return a list containing the tax paid for each received operation. The
elements of this list must be encoded in json format and the output must be returned via standard output ( stdout ). The
return consists of the following field:

- tax: The amount of tax paid on an operation

Example of how the line should look like:

```
[{"tax":0.00}, {"tax":10000.00}]
```

The list returned by the program must be the same size as the list of operations processed in the input. For example, if
three operations were processed (buy, buy, sell), the program's return should be a list with three values that
represent the tax paid on each operation.

### Rules

The program must handle two types of operations ( buy and sell ) and it must follow the following rules:

* The tax percentage paid is 20% on the profit obtained in the operation. That is, the tax will be paid when there is a
  sale transaction whose price is higher than the weighted average purchase price.
* To determine whether the trade resulted in a profit or loss, you can calculate the weighted average price, so when you
  buy shares you must recalculate the weighted average price using this formula: `new-weighted-average = ((
  current-amount-of-stocks * weighted-average-current) + (amount-of-stocks * purchase-value)) / (
  amount-of-stocks-current + quantity-of-stocks-purchased)`. For example, if you bought 10 shares for R$20.00, sold 5,
  then bought another 5 for R$10.00, the weighted average is  `((5 x 20.00) + (5 x 10.00)) / (5 + 5) = 15.00`
* You must use past loss to deduct multiple future profits until all losses are deducted.
* Losses happen when you sell shares at less than the weighted average purchase price. In this case, no tax must be paid
  and you must subtract the loss from subsequent profits before calculating the tax.
* You do not pay any tax if the total value of the transaction `(unit cost of the share x quantity)` is less than or
  equal to BRL 20000.00. Use the total amount of the transaction and not the profit made to determine whether tax
  should be paid. And don't forget to deduct the loss from the following profits.
* No tax is paid on purchase transactions.
* No trade will sell more shares than you have at that time.

### Built With

Because the challenge asked to use the least frameworks and dependencies possible I retained myself to use dependencies
only for testing purposes, the only dependency that was used in the actual code was `jackson-databind`, used to map
the command string into the application contract.

#### Dependencies

* [Jackson Databind](https://github.com/FasterXML/jackson-databind)

#### Test Dependencies

* [Junit4](https://github.com/junit-team/junit4): Used to run unit tests
* [Mockito](https://github.com/mockito/mockito): Used to generate mocks used in unit tests
* [Cucumber (Junit)](https://cucumber.io/docs/cucumber/api/#junit): Used to integrate cucumber with Junit4
* [Cucumber (Java)](https://cucumber.io/docs/installation/java/): Used to integrate Gherkin with Java

#### Plugins

* [Maven Compiler Plugin](https://maven.apache.org/plugins/maven-compiler-plugin/): Used to compile the app
* [Maven Assembly Plugin](https://maven.apache.org/plugins/maven-assembly-plugin/): Used to generate an executable jar
* [Maven Wrapper](https://maven.apache.org/wrapper/): Used to run mvn commands

### Roadmap

* [x] Implement Behaviour tests (BDD) with cucumber
* [x] Implement Unit tests
* [x] Implement application logic
* [x] Add maven wrapper to run mvn commands locally
* [ ] Create Dockerfile
* [ ] Create Docker compose
* [ ] Document everything in Readme

<p align="right">(<a href="#top">back to top</a>)</p>

## Getting Started

### Prerequisites

- Install version 11 of the JDK
    - [Manual](https://adoptium.net/?variant=openjdk11)
    - [IntelliJ](https://www.jetbrains.com/help/idea/sdk.html#jdk)
    - [Homebrew](https://docs.brew.sh/Installation)
      ```bash
      brew install openjdk@11
      ```

- Set the `JAVA_HOME` environment variable to the path where your JDK is located
    - [Windows](https://confluence.atlassian.com/doc/setting-the-java_home-variable-in-windows-8895.html)
    - macOS/Linux/WSL
      ```bash
      echo 'export JAVA_HOME=/path/to/jdk' >> ~/.bashrc 
      ```

- Install Docker (Optional)
    - [Windows/macOS/Linux/WSL](https://www.docker.com/get-started/)

### Installation

- Run the following to install dependencies and compile the project:
    - Windows
      ```bash
      ./mvnw.cmd clean install
      ```
    - macOS/Linux/WSL
      ```bash
      ./mvnw clean install
      ```

### Usage

#### Manual Input

- Start the compiled application:
    - Windows/macOS/Linux/WSL
      ```bash
      java -jar ./target/nubank-challenge.jar
      ```

- Insert the desired operations (example bellow):
    - Windows/macOS/Linux/WSL
      ```
      [{"operation":"buy", "unit-cost":10.00, "quantity": 10000}, {"operation":"sell", "unit-cost":20.00, "quantity": 5000}]
      ```

- To stop the application just send an empty line command

#### File Input

- Else you can run the application using an input file:
    - Windows
      ```bash
      type ./src/main/resources/input.txt | java -jar ./target/nubank-challenge.jar 
      ```
    - macOS/Linux/WSL
      ```bash
      java -jar ./target/nubank-challenge.jar < ./src/main/resources/input.txt
      ```

#### Docker Input

- In case you want to use a Docker container to run the application first you need to build the Docker image from
  Dockerfile (the first time you run this may take a few minutes to complete):
    - Windows/macOS/Linux/WSL
      ```bash
      docker build -t nubank-challenge .
      ```

- And then run the new created image:
    - Windows/macOS/Linux/WSL
      ```bash
      docker run --rm -it nubank-challenge:latest
      ```

- Insert the desired operations (example bellow):
    - Windows/macOS/Linux/WSL
      ```
      [{"operation":"buy", "unit-cost":10.00, "quantity": 10000}, {"operation":"sell", "unit-cost":20.00, "quantity": 5000}]
      ```

### Testing

- To run the tests just type the command bellow in terminal:
    - Windows
      ```bash
      ./mvnw.cmd test
      ```
    - macOS/Linux/WSL
      ```bash
      ./mvnw test
      ```

<p align="right">(<a href="#top">back to top</a>)</p>