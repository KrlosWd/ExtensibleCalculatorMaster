### Loading a Jar File

The endpoint `/calculator/manager/jar` reads a (local) jar file in the system at the specified path.
After this step, **`CalculatorService`** classes in the jar are ready to be loaded via the
 `/calculator/manager/service` endpoint (see [`/calculator/manager/service`](./calculator_manager_service.md)).

| Method | Path                       | Parameters         |
| ------ | -------------------------- | ------------------ |
| `POST`  | `/calculator/manager/jar`  | `filepath`        |

##### Parameters:
* `filepath (string: <required>)`: The absolute path to the jar file to be loaded by the service. This
 is a path local to the machine running the **ExtensibleCalculator** service.

##### Example Request
  
```bash
    $ curl \
        -X POST http://localhost:8080/calculator/manager/jar \
        -d "filepath=D:\Carlos\Repos\Spring\workspace\ExtensibleCalculatorMaster\ExponentialServices\target\ExponentialServices-1.0.0.jar"
```

##### Example Response
  
```json
{
  "message" : "Jarfile [D:\\Carlos\\Repos\\Spring\\workspace\\ExtensibleCalculatorMaster\\ExponentialServices\\target\\ExponentialServices-1.0.0.jar] loaded successfully!"
}
```

[ExtensibleCalculator API - Menu](./API_menu.md)