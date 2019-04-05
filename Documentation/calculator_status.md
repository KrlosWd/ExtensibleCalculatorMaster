## Status Message

The endpoint  `/calculator/status` returns the current status of the **ExtensibleCalculator**, which shows the current **`CalculatorService`** implementation loaded into the system, and their related path. By default two implementations are loaded: **AdditionService** and **MultiplicationService**.

| Method | Path                    | Parameters |
| ------ | ------------------------| -----------|
| `GET`  | `/calculator/status`    | none       |

##### Example Request
```bash
    $ curl -X GET http://localhost:8080/calculator/status
```

##### Example Response

```json
[ {
  "path" : "multiplication",
  "className" : "myob.technicaltest.calculator.services.MultiplicationService"
}, {
  "path" : "addition",
  "className" : "myob.technicaltest.calculator.services.AdditionService"
} ]
```

[ExtensibleCalculator API - Menu](./API_menu.md)