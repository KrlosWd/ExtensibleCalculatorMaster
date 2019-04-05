## Accessing **`CalculatorService`** help

The endpoint  `/calculator/service/{path}/help` is used to retrieve the help information of 
any given **`CalculatorService`** implementation.

| Method | Path                               | Parameters  |
| ------ | ---------------------------------- | ----------- |
| `GET`  | `/calculator/service/{path}/help`  | none        |

##### Parameters:
- `path (string: <required>)`: Path where the requested **`CalculatorService`** is located. This 
parameter is specified as part of the URL.

For example, to retrieve the help of the **MultiplicationService** with "`path`= multiplication",
we can use the following request:


##### Example Request
```bash
    $ curl -X GET http://localhost:8080/calculator/service/multiplication/help
```

##### Example Response

```json
{
  "description" : "MultiplicationService: Calculates the multiplication of 'n' numbers",
  "parameters" : {
    "operand" : "operand List of one or more numbers to multiply"
  },
  "output" : "The multiplication of all operand values provided"
}
```


[ExtensibleCalculator API - Menu](./API_menu.md)