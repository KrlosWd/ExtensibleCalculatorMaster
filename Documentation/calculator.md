## Welcome Message

The endpoint  `/calculator` returns a welcome message to the user.

| Method | Path             | Parameters |
| ------ | -----------------| -----------|
| `GET`  | `/calculator`    | none       |

##### Example Request
```bash
    $ curl -X GET http://localhost:8080/calculator
```

##### Example Response

```json
{
  "message" : "ExtensibleCalculator is a simple and extensible web-style API based on a custom framework used
  to dinamically load CalculatorService implementations. CalculatorService implementations contain the actual
  logic to perform operations and are meant to be implemented by users. For more information
  visit: https://github.com/KrlosWd/ExtensibleCalculatorMaster"
}
```

Note: Line breaks were added to the response in this documentation for readability purposes

[ExtensibleCalculator API - Menu](./API_menu.md)