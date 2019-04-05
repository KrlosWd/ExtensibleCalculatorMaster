## Accessing **`CalculatorService`** implementations

The endpoint  `/calculator/service/{path}` is used to gain access to the service provided by any given 
**`CalculatorService`** implementation. For a custom-made **`CalculatorService`** to be available, 
it MUST be previously loaded via the `/calculator/manager/jar` and the `/calculator/manager/service` 
endpoints (see [`/calculator/manager/jar`](./calculator_manager_jar.md) and 
[`/calculator/manager/service`](./calculator_manager_service.md)). 

| Method | Path                          | Parameters                                    |
| ------ | ----------------------------- | --------------------------------------------- |
| `GET`  | `/calculator/service/{path}`  | Those required by the requested service       |

##### Parameters:
- `path (string: <required>)`: Path where the requested **`CalculatorService`** is located. This 
parameter is specified as part of the URL.

Different **`CalculatorService`** implementations may require different GET parameters. A description 
of the parameters required by a **`CalculatorService`** implementation can be obtained via the endpoint
`/calculator/service/{path}/help` (see [`/calculator/service/{path}/help`](./calculator_service_path_help.md)).

By default two **`CalculatorService`** implementations are provided: **AdditionService** at the 
endpoint `/calculator/service/addition`, and **MultiplicationService** at the endpoint 
`/calculator/service/multiplication`. For example, the **AdditionService** requires a list of operands
 to be added up, where one or more operands may be specified with the parameter `operand (int: <required>)`

##### Example Request
```bash
    $ curl \
        -X GET \
        -G http://localhost:8080/calculator/service/addition \
        -d "operand=12&operand=8"
```

##### Example Response

```json
{
  "responseTime" : 200,
  "input" : "{operand=[12, 8]}",
  "output" : "20",
  "serviceProvider" : "myob.technicaltest.calculator.services.AdditionService"
}
```

A successful request to one **`CalculatorService`** always includes the following values:
- **responseTime:** Response time in *nanoseconds*
- **input:** The list of parameters received by the service as input
- **output**: The output given by the requested service
- **serviceProvider**: The canonical name of the **`CalculatorService`**


[ExtensibleCalculator API - Menu](./API_menu.md)