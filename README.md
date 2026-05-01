## Kinal App
KinalApp es una aplicación desarrollada en Java utilizando el framework Spring Boot, orientada a la 
gestión de ventas y administración de productos. El sistema permite manejar entidades como clientes, usuarios, productos, ventas y detalles de venta.

## Tecnologías
* **Java 21**
* **Spring Boot 4.0.3**
* **Maven** (Gestor de dependencias)
* **MySQL** (Sistema Gestor de Base de Datos)

## Requisitos Previos
Antes de ejecutar la aplicación, debe tener instalado:
* JDK 17 o superior
* Maven Instalado
* Una instancia activa en MySQL

## Instalación y Ejecución
1. Clonar el repositorio "gh repo clone jsoloman-2025178/KinalApp_2025178"
2. Abrir el proyecto en IDEA
3. Compilar el proyecto
4. Ejecutar "Kinal App 2025178"

## Estructura del proyecto
src/
└── main/
└── java/
└── com/jeffersonsoloman/kinalapp/
│
├── controller/
│   ├── ClienteController.java
│   ├── DetalleVentaController.java
│   ├── ProductoController.java
│   ├── UsuarioController.java
│   └── VentaController.java
│
├── entity/
│   ├── Cliente.java
│   ├── DetalleVenta.java
│   ├── Producto.java
│   ├── Usuario.java
│   └── Venta.java
│
├── repository/
│   ├── ClienteRepository.java
│   ├── DetalleVentaRepository.java
│   ├── ProductoRepository.java
│   ├── UsuarioRepository.java
│   └── VentaRepository.java
│
├── service/
│   ├── IClienteService.java
│   ├── ClienteService.java
│   ├── IDetalleVentaService.java
│   ├── DetalleVentaService.java
│   ├── IProductoService.java
│   ├── ProductoService.java
│   ├── IUsuarioService.java
│   ├── UsuarioService.java
│   ├── IVentaService.java
│   └── VentaService.java
│
└── KinalAppApplication.java