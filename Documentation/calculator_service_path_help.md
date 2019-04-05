## Accessing **`CalculatorService`** help

The endpoint  `/calculator/service/{path}/help` is used to retrieve the help information of 
a **`CalculatorService`** implementation loaded in the given `path` (see [`/calculator/manager/jar`](./calculator_manager_jar.md) and 
[`/calculator/manager/service`](./calculator_manager_service.md)). 

| Method | Path                               | Parameters  |
| ------ | ---------------------------------- | ----------- |
| `GET`  | `/calculator/service/{path}/help`  | none        |

##### Parameters:
- `path (string: <required>)`: Path where the requested **`CalculatorService`** is located. This 
parameter is specified as part of the URL.

By default two **`CalculatorService`** implementations are provided: **AdditionService** at
the endpoint `/calculator/service/addition`, and **MultiplicationService** at the endpoint
`/calculator/service/multiplication`. To retrieve the help of the **MultiplicationService**
we can use this endpoint with "`path`= multiplication".

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