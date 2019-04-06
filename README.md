ExtensibleCalculator
====================

What is it?
-----------
**ExtensibleCalculator** is a simple and extensible web-style API that performs basic math operations (shipped with addition and multiplication only), but that can be extended to perform pretty much anything you can think of.

Operations are implemented in the form of **`CalculatorService`** implementations, which are meant to be implemented by users.

The **ExtensibleCalculator** enables users to:
- Load jar files at runtime, 
- Load custom **`CalculatorService`** implementations from previously loded jars and,
- Perform operations using the available **`CalculatorService`** implementations

Building & Run
--------------------
To build the extensible calculator, simply run the following maven command at the root of the project:
``` bash
    $ mvn package
```

Once the building process finishes, to run the service use the following command
```
    $ java .jar ExtensibleCalculator/target/ExtensibleCalculator-1.0.0.jar
```
Thats is it! To verify that the service is runnins you can either use your browser or curl to make a GET request to the URL `http://localhost:8080/calculator` and you should get the following output:
```
{
  "message" : "ExtensibleCalculator is a simple and extensible web-style API based on a custom framework used to dinamically load CalculatorService implementations. CalculatorService implementations contain the actual logic to perform operations and are meant to be implemented by users. For more information visit: https://github.com/KrlosWd/ExtensibleCalculatorMaster"
}
```
What's next ?
----------------
* To Checkout the embeded **`CalculatorService`** implementation of the project you can visit the following endpoints:
    *   `http://localhost:8080/calculator/service/multiplication/help`
    *   `http://localhost:8080/calculator/service/addition/help`
* To have a look at the different available endpoints of the API, theirs description and usage examples visit the [API menu](./Documentation/API_menu.md). 