# IMDb Dataset REST API

A Spring Boot application that imports the IMDb datasets into a relational database and exposes RESTful APIs for
querying movie and person data.

## Features

- Import IMDb datasets into the application database
- Find titles where the **director and writer are the same living person**
- Find titles in which **two actors have both appeared**
- ~~Return the **best title for each year** in a given genre based on rating and vote count~~
- Count the number of HTTP requests received since the application started

---

## Technology Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Hibernate
- Maven

---

## IMDb Dataset

Download the following datasets from the official IMDb website:
[Dataset Link](https://developer.imdb.com/non-commercial-datasets)

When application starts up, dataset automatically will insert into database via [
`FileDataReader.init`](src/main/java/org/lobox/common/data/FileDataReader.java#L49) method

Required files:

| Dataset                | Description             |
|------------------------|-------------------------|
| `title.basics.tsv`     | Titles and genres       |
| `title.ratings.tsv`    | Ratings and vote counts |
| `name.basics.tsv`      | People information      |
| `title.principals.tsv` | Cast and crew           |

---

## Configuration

Configure your database in `application.yml` or `application.properties`.

Example:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/imdb
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

## Config Batch Insertion

```properties
spring.jpa.properties.hibernate.jdbc.batch_size=5000
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
spring.jpa.properties.hibernate.jdbc.batch_versioned_data=true
```

---

## Running the Application

```bash
mvn spring-boot:run
```

or

```bash
java -jar imdb.jar
```

---

# REST APIs

## 1. Titles Where Director and Writer Are the Same Living Person

Returns every title where the director and writer are the same person and that person is still alive.

```
GET /api/v1/imdb/same-write-director?page=1&size=10
```

Response

```json
{
  "page": 1,
  "size": 10,
  "total": 510,
  "data": [
    "Title 1",
    "Title 2"
  ]
}
```

---

## 2. Common Titles Between Two Actors

Returns all titles in which both actors appeared.

```
GET /api/v1/imdb/same-title-for-two-actors?firstActorId=1&secondActorId=2&page=1&size=10
```

Response

```json
{
  "page": 1,
  "size": 10,
  "total": 510,
  "data": [
    "Title 1",
    "Title 2"
  ]
}
```

---

## 5. HTTP Request Counter

Returns the number of HTTP requests received since the application started.

```
GET /api/statistics/requests-count
```

Response

```json
{
  "data": 10
}
```

---

# Performance Considerations

- Batch inserts are used during dataset import.
- Database indexes are created for frequently queried columns.
- BufferedReader is used while reading large TSV files to reduce memory usage.
- Pagination can be added for endpoints returning large result sets.
- Caching may be used for frequently requested queries.

---

# Future Improvements

- Docker support
- Redis cache
- Import progress tracking
- Asynchronous dataset import
- OpenAPI / Swagger documentation
- Integration tests
- Metrics with Spring Boot Actuator