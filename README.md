# APP LITERATURA
## Spring Boot, PostgreSQL, JPA, Hibernate

Build application using Spring Boot, Postgres, JPA and Hibernate.

## Requirements

1. Java - 21

2. Maven - 3.x.x

3. PostgreSql - 16.x.x

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/kevinCerrinos/literatura.git
```

**2. Create PostgreSql database**
```bash
create database literatura
```

**3. Change PostgreSQL username and password as per your installation**

+ open `src/main/resources/application.properties`

+ change `spring.datasource.username` and `spring.datasource.password` as per your PostgreSql installation

**4. Build and run the app using maven**


you can run the app without packaging it using -

```bash
mvn spring-boot:run
```

The app will start running

## Explore APP

The app defines following functions.

    Listar Libros Registrados
    
    Listar Autores Registrados
    
    Listar Autores Vivos En Un Determinado Año
    
    Listar Libros por idioma
    
    Ver estadisticas sobre la descarga de los libros registrados
