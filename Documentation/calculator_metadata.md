## Metadata Message

The endpoint  `/calculator/metadata` returns the metadata of the **`CalculatorService`** project.

| Method | Path                    | Parameters |
| ------ | ------------------------| -----------|
| `GET`  | `/calculator/metadata`  | none       |

##### Example Request
```bash
    $ curl -X GET http://localhost:8080/calculator/metadata
```

##### Example Response

```json
{
  "author" : {
    "name" : "Juan Carlos Fuentes Carranza",
    "email" : "juan.fuentes.carranza@gmail.com"
  },
  "project" : {
    "name" : "ExtensibleCalculator",
    "description" : "Technical Test for MYOB",
    "version" : "1.0.0"
  }
}
```

[ExtensibleCalculator API - Menu](./API_menu.md)