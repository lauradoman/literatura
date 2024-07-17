# APP LITERATURA

## Requerimientos

1. Java - 21

2. Maven - 3.x.x

3. PostgreSql - 16.x.x

## Pasos a seguir

**1. Clonar app**

```bash
git clone https://github.com/kevinCerrinos/literatura.git
```

**2. Crear base de datos**
```bash
create database literatura
```

**3. Cambias datos en PostgreSQL **

+ abrir `src/main/resources/application.properties`

+ cambiar `spring.datasource.username` y `spring.datasource.password` por tus datos en base de datos

    Ver estadisticas sobre la descarga de los libros registrados
