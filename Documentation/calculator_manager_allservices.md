## Removing all **`CalculatorService`** implementations

The endpoint  `/calculator/manager/allServices` is used to remove all **`CalculatorService`** implementations 
known by the **ExtensibleCalculator**.

| Method | Path                              | Parameters                  |
| ------ | --------------------------------- | --------------------------- |
| `DELETE`  | `/calculator/manager/allService`  | none         |

##### Example Request
```bash
    $ curl -X DELETE http://localhost:8080/calculator/manager/allServices
```

##### Example Response

```json
{
  "message" : "Removed [2] CalculatorServices successfully!"
}
```

[ExtensibleCalculator API - Menu](./API_menu.md)