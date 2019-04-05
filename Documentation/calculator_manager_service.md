## Loading and Removing **`CalculatorService`** implementations

The endpoint  `/calculator/manager/service` is used to load **`CalculatorService`** implementations to make
them available in a given path (see [`/calculator/service/{path}`](calculator_service_path.md) endpoint), 
and to remove **`CalculatorService`** implementations from any given path.

### Loading **`CalculatorService`** implementations

| Method | Path                           | Parameters                  |
| ------ | ------------------------------ | --------------------------- |
| `PUT`  | `/calculator/manager/service`  | `path`, `className`         |

##### Parameters:
- `path (string: <required>)`: Path where the **`CalculatorService`** is to be set
- `className (string: <required>`: The canonical name of the class to be loaded
					

##### Example Request
```bash
    $ curl  \
        -X PUT http://localhost:8080/calculator/manager/service \
        -d "path=squareroot&className=myob.technicaltest.calculator.service.exponential.SquareRootService"
```

##### Example Response

```json
{
  "message" : "CalculatorService [myob.technicaltest.calculator.service.exponential.SquareRootService] loaded in path [squareroot] successfully!"
}
```

### Removing **`CalculatorService`** implementations

| Method    | Path                           | Parameters                  |
| --------- | ------------------------------ | --------------------------- |
| `DELETE`  | `/calculator/manager/service`  | `path`                      |

##### Parameters:
- `path (string: <required>)`: Path of the **`CalculatorService`** is to be removed
					



##### Example Request
```bash
    $ curl \
        -X DELETE http://localhost:8080/calculator/manager/service \
        -d "path=squareroot"
```

##### Example Response

```json
{
  "message" : "CalculatorService in path [squareroot] removed successfully!"
}
```

[ExtensibleCalculator API - Menu](./API_menu.md)