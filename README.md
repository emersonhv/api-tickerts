# API de Gestión de Tickets

Este es un proyecto de una API RESTful desarrollada en **Java** con **Spring Boot** para la gestión de tickets. Permite crear, leer, actualizar y eliminar tickets, así como recuperar tickets paginados y filtrados por estatus.

## Características Principales

- **CRUD de Tickets**: Crear, leer, actualizar y eliminar tickets.
- **Paginación y Filtrado**: Recuperar tickets paginados y filtrados por estatus.
- **Validación de Datos**: Validación de entradas usando anotaciones de Java Bean Validation.
- **Manejo de Excepciones**: Manejo centralizado de excepciones con respuestas claras y útiles.
- **Pruebas Unitarias**: Pruebas unitarias con JUnit y Mockito para garantizar la calidad del código.
- **Migraciones con Liquibase**: Gestión de cambios en la base de datos usando Liquibase.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **MapStruct**
- **Liquibase**
- **JUnit 5**
- **Mockito**

## Requisitos Previos

- **Java 17**: Asegúrate de tener Java 17 instalado.
- **PostgreSQL**: Necesitarás una instancia de PostgreSQL en ejecución.
- **Maven**: Para gestionar las dependencias y construir el proyecto.

## Instalación y Configuración

1. **Genera el .jar en IntelliJ**:

2. **Crea imagen del proyecto spring boot**:
    ```bash
    docker build -t api-tickets:1.0 .
3. **Monta las imagenes del proyecto y la base de datos**
    ```bash
    docker-compose build
4. **Crea los contenedores correspondientes**
    ```bash
    docker-compose up