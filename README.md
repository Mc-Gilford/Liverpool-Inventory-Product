# Liverpool Inventory Product

Aplicación backend la búsqueda de productos y consultar su disponibilidad mediante 2 microservicios independientes.

El proyecto fue desarrollado con **Java 17**, **Spring Boot** y **Maven**. Cada microservicio tiene base de datos H2 y se ejecuta de manera independiente.

## Microservicios

### ProyectoA - Product Service

Es el servicio administra y busca los productos. Cuando encuentra coincidencias por nombre o descripción, consulta al **Inventory Service** para agregar el stock y el estado del inventario a la respuesta.

Puerto: `8081`


Abrir en el navegador:

```text
http://localhost:8081/h2-console
```

Utilizar los siguientes datos:

```text
Saved Settings: Generic H2 (Embedded)
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:proyecto_a_db
User Name: sa
Password:
```

La contraseña se debe dejar vacía. Después presionar **Connect**.


### ProyectoB - Inventory Service

Es el servicio encargado de administrar el inventario y consultar la cantidad disponible de un producto mediante su identificador.

Puerto: `8082`


## Acceso a las bases de datos

Las APIs no requieren usuario ni contraseña para consumir sus endpoints. Las siguientes credenciales se utilizan únicamente para acceder a las consolas H2.

Abrir en el navegador:

```text
http://localhost:8082/h2-console
```

Utilizar los siguientes datos:

```text
Saved Settings: Generic H2 (Embedded)
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:proyecto_b_db
User Name: sa
Password:
```

La contraseña se debe dejar vacía. Después presionar **Connect**.

> Las bases de datos se encuentran en memoria. Los datos se cargan cuando inicia cada aplicación y se eliminan cuando esta se detiene.


## Estructura del repositorio

```text
Liverpool-Inventory-Product/
  > ProyectoA/    Product Service
  > ProyectoB/    Inventory Service
```

Los proyectos utilizan una separación por responsabilidades mediante los paquetes `controller`, `service`, `repository`, `entity`, `dto`, `mapper`, `external` y `exception`.


## Ejecución

Se recomienda iniciar primero el **Inventory Service** y después el **Product Service**.


## Datos iniciales

Al iniciar las aplicaciones se cargan automáticamente utilizando un load de los productos y su inventario que se encontrara en una carpeta del service

| ID | Producto | Precio | Stock |
| --- | --- | ---: | ---: |
| EXT-001 | iPhone 13 128GB | 18999.99 | 10 |
| EXT-002 | Samsung Galaxy S22 | 17499.50 | 0 |
| EXT-003 | MacBook Air M1 | 21999.00 | 5 |
| EXT-004 | Dell XPS 13 | 24999.99 | 2 |
| EXT-005 | Audífonos Sony WH-1000XM4 | 5999.00 | 20 |

## Endpoints

### Product Service

#### Buscar productos

Realiza una búsqueda por coincidencias en el nombre o la descripción del producto y agrega la información de inventario.

```http
GET /api/v1/products/search?query={keyword}
```

Ejemplo:

```text
http://localhost:8081/api/v1/products/search?query=laptop
```

Respuesta:

```json
[
  {
    "id": "EXT-003",
    "name": "MacBook Air M1",
    "description": "Laptop ligera",
    "price": 21999.0,
    "stock": 5,
    "inventoryStatus": "AVAILABLE"
  }
]
```

El campo `inventoryStatus` puede contener los siguientes valores:

- `AVAILABLE`: el producto cuenta con existencias.
- `OUT_OF_STOCK`: el producto tiene stock igual a cero.
- `UNAVAILABLE`: no fue posible obtener la información del Inventory Service.

#### Obtener la primera coincidencia

```http
GET /api/v1/products/search/{keyword}
```

#### Registrar un producto

```http
POST /api/v1/products
```

Ejemplo del cuerpo de la petición:

```json
{
  "id": "EXT-006",
  "name": "Teclado mecánico",
  "description": "Teclado para computadora",
  "price": 1499.99
}
```

### Inventory Service

#### Consultar inventario

```http
GET /api/v1/inventory/{productId}
```

Ejemplo:

```text
http://localhost:8082/api/v1/inventory/EXT-001
```

Respuesta:

```json
{
  "id": "EXT-001",
  "stock": 10
}
```

#### Registrar inventario

```http
POST /api/v1/inventory/
```

Ejemplo del cuerpo de la petición:

```json
{
  "id": "EXT-006",
  "stock": 8
}
```

## Comunicación entre servicios

El Product Service utiliza `RestClient` para consultar el inventario de cada producto mediante HTTP.

La dirección del Inventory Service se encuentra configurada en `ProyectoA/src/main/resources/application.properties`:

```properties
inventory.service.url=http://localhost:8082
```

Si el Inventory Service no está disponible o devuelve un error, la búsqueda de productos continúa y responde con:

```json
{
  "stock": null,
  "inventoryStatus": "UNAVAILABLE"
}
```

## Swagger / OpenAPI

La documentación de los endpoints puede consultarse desde Swagger UI.

**Product Service**

```text
http://localhost:8081/swagger-ui/index.html
```

**Inventory Service**

```text
http://localhost:8082/swagger-ui/index.html
```

## Autor

**José de Jesús Rodríguez Aparicio**

[GitHub - Mc-Gilford](https://github.com/Mc-Gilford)
