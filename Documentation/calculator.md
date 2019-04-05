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
  "message" : "Hello World"
}
```

[ExtensibleCalculator API - Menu](./API_menu.md)