# Product Catalog API (Spring Boot + Liquibase)

Technical assessment solution for a Support Java Developer role.

## Stack

- Java 21
- Spring Boot 3.3.x
- Spring Web + Spring Data JPA + Bean Validation
- Liquibase
- H2 in-memory database
- Maven

## Domain model

- `Producer` - product manufacturer (`id`, `name`, `country`)
- `Product` - product in catalog (`id`, `name`, `description`, `price`, `producer`, `attributes`)
- `attributes` - dynamic product properties stored as JSON (`Map<String, Object>`) to support 50-200 fields per product

## API

### Products

- `GET /api/products` - list all products with producer details
- `POST /api/products` - create product
- `PUT /api/products/{id}` - update product (full replace style)
- `DELETE /api/products/{id}` - delete product

### Producers (bonus)

- `GET /api/producers` - list producers

## Example request payload

```json
{
  "name": "Galaxy TV",
  "description": "4K Smart TV",
  "price": 1167.78,
  "producerId": 1,
  "attributes": {
    "width": 143,
    "height": 86,
    "energy_rating": "A+",
    "certifications": "CE"
  }
}
```

## Running locally

```bash
./mvnw spring-boot:run
```

App starts on `http://localhost:8080`.

Useful links:
- H2 console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:product_catalog`
- User: `sa`
- Password: *(empty)*

## Run tests

```bash
./mvnw test
```

## Notes

- DB schema and seed data are managed by Liquibase changelogs in `src/main/resources/db/changelog`.
- Three producers are seeded for easier manual testing: Samsung, Apple, Dell.
- Validation is intentionally basic and focused on assessment scope.

