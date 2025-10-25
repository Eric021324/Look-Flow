# Look Flow - Beauty Salon Management System

Look Flow es un sistema de gestión para salones de belleza construido con arquitectura hexagonal y principios de Domain-Driven Design (DDD).

## Arquitectura

El proyecto sigue una arquitectura hexagonal (Ports and Adapters) con las siguientes capas:

- **Domain**: Entidades, value objects, y reglas de negocio
- **Application**: Casos de uso y servicios de aplicación
- **Infrastructure**: Implementaciones de puertos, controladores REST, y persistencia

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **PostgreSQL**
- **MapStruct** (para mapeo de objetos)
- **Swagger/OpenAPI** (para documentación de API)
- **Maven** (gestión de dependencias)

## Estructura del Proyecto

```
src/main/java/com/lookflow/
├── application/           # Capa de aplicación
│   ├── port/
│   │   ├── input/        # Puertos de entrada (casos de uso)
│   │   └── output/       # Puertos de salida (repositorios)
│   └── service/          # Servicios de aplicación
├── domain/               # Capa de dominio
│   ├── exception/         # Excepciones del dominio
│   └── model/
│       ├── entity/        # Entidades
│       └── valueobject/   # Value objects
└── infrastructure/       # Capa de infraestructura
    ├── config/           # Configuraciones
    ├── controller/        # Controladores REST
    ├── dto/              # DTOs de request/response
    ├── mapper/           # Mappers MapStruct
    └── persistence/      # Persistencia (JPA)
```

## Entidades del Dominio

### Customer (Cliente)
- Información personal del cliente
- Email, teléfono, fecha de registro

### Employee (Empleado)
- Información del empleado
- Dirección, horarios de trabajo, servicios que puede realizar

### Service (Servicio)
- Servicios ofrecidos por el salón
- Precio, duración, categoría, estado activo/inactivo

### Appointment (Cita)
- Citas entre clientes y empleados
- Estados: PENDING, CONFIRMED, CANCELLED, COMPLETED
- Servicios incluidos en la cita

### Payment (Pago)
- Pagos realizados por las citas
- Vinculado a cliente y cita

## API REST

La API está documentada con Swagger/OpenAPI y disponible en:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

### Endpoints Principales

#### Customers
- `POST /api/v1/customers` - Registrar cliente
- `GET /api/v1/customers/{id}` - Obtener cliente por ID
- `GET /api/v1/customers` - Obtener todos los clientes
- `PUT /api/v1/customers/{id}` - Actualizar cliente

#### Employees
- `POST /api/v1/employees` - Registrar empleado
- `GET /api/v1/employees/{id}` - Obtener empleado por ID
- `GET /api/v1/employees` - Obtener todos los empleados
- `PUT /api/v1/employees/{id}` - Actualizar empleado
- `PUT /api/v1/employees/{id}/work-shifts` - Gestionar horarios

#### Services
- `POST /api/v1/services` - Crear servicio
- `GET /api/v1/services/{id}` - Obtener servicio por ID
- `GET /api/v1/services` - Obtener todos los servicios
- `GET /api/v1/services/active` - Obtener servicios activos
- `PUT /api/v1/services/{id}` - Actualizar servicio
- `PUT /api/v1/services/{id}/activate` - Activar/desactivar servicio

#### Appointments
- `POST /api/v1/appointments` - Crear cita
- `GET /api/v1/appointments/{id}` - Obtener cita por ID
- `GET /api/v1/appointments` - Obtener todas las citas
- `PUT /api/v1/appointments/{id}/confirm` - Confirmar cita
- `PUT /api/v1/appointments/{id}/cancel` - Cancelar cita
- `PUT /api/v1/appointments/{id}/complete` - Completar cita

#### Payments
- `POST /api/v1/payments` - Procesar pago
- `GET /api/v1/payments/{id}` - Obtener pago por ID
- `GET /api/v1/payments` - Obtener todos los pagos
- `GET /api/v1/payments/appointment/{appointmentId}` - Pagos por cita
- `GET /api/v1/payments/customer/{customerId}` - Pagos por cliente

#### Health Check
- `GET /api/v1/health` - Estado de la API

## Configuración

### Base de Datos
El proyecto está configurado para usar PostgreSQL. Asegúrate de tener:
- PostgreSQL instalado y ejecutándose
- Base de datos `lookflow` creada
- Usuario `lookflow` con contraseña `lookflow`

### Variables de Entorno
Puedes sobrescribir la configuración de base de datos usando variables de entorno:
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/lookflow
export SPRING_DATASOURCE_USERNAME=lookflow
export SPRING_DATASOURCE_PASSWORD=lookflow
```

## Ejecución

### Prerrequisitos
- Java 21
- Maven 3.6+
- PostgreSQL 12+

### Pasos para ejecutar

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd Look-Flow
```

2. **Configurar la base de datos**
```sql
CREATE DATABASE lookflow;
CREATE USER lookflow WITH PASSWORD 'lookflow';
GRANT ALL PRIVILEGES ON DATABASE lookflow TO lookflow;
```

3. **Compilar el proyecto**
```bash
mvn clean compile
```

4. **Ejecutar la aplicación**
```bash
mvn spring-boot:run
```

5. **Acceder a la documentación**
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

## Validaciones

La API incluye validaciones robustas:
- Validación de campos requeridos
- Validación de formato de email
- Validación de números de teléfono
- Validación de fechas futuras para citas
- Validación de montos positivos para pagos
- Validación de horarios de trabajo sin solapamiento

## Manejo de Errores

El sistema incluye un manejo global de errores que:
- Captura excepciones del dominio
- Valida datos de entrada
- Retorna respuestas consistentes con códigos HTTP apropiados
- Proporciona mensajes de error descriptivos

## Patrones de Diseño Implementados

- **Arquitectura Hexagonal**: Separación clara entre dominio, aplicación e infraestructura
- **Domain-Driven Design**: Modelado basado en el dominio del negocio
- **Repository Pattern**: Abstracción de acceso a datos
- **Mapper Pattern**: Conversión entre DTOs y entidades del dominio
- **State Pattern**: Estados de citas (Pending, Confirmed, Cancelled, Completed)
- **Value Objects**: Objetos inmutables para representar conceptos del dominio

## Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.
