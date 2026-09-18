# Calculadora JavaFX

Aplicación de escritorio de una calculadora básica desarrollada con JavaFX y Maven.

## Funciones

- Suma, resta, multiplicación y división.
- Operaciones encadenadas de izquierda a derecha.
- Números decimales, con un único separador por operando.
- Mensaje controlado ante una división por cero.
- Retroceso, cambio de signo y limpieza del cálculo.
- Atajos de teclado para números, operadores, decimal, Enter, Retroceso, Supr y Escape.
- Interfaz adaptable con estilos CSS.

## Requisitos

- JDK 17 o superior.
- Maven 3.9 o superior.
- Acceso a Internet la primera vez que Maven descargue las dependencias de JavaFX y JUnit.

Puedes comprobar las versiones instaladas con:

```powershell
java -version
mvn -version
```

## Ejecutar la aplicación

Desde la raíz del proyecto:

En Windows:

```powershell
.\mvnw.cmd javafx:run
```

En macOS o Linux:

```sh
./mvnw javafx:run
```

## Ejecutar las pruebas

En Windows:

```powershell
.\mvnw.cmd test
```

En macOS o Linux:

```sh
./mvnw test
```

Las pruebas cubren las operaciones básicas, cálculos encadenados, el manejo de errores, la limpieza de estado, el uso del separador decimal y la carga del FXML con su controlador.

## Estructura

```text
src/
├── main/
│   ├── java/com/calculator/
│   │   ├── CalculatorApp.java        # Punto de entrada de JavaFX
│   │   ├── CalculatorController.java # Eventos de la interfaz
│   │   └── CalculatorModel.java      # Estado y operaciones de la calculadora
│   └── resources/
│       ├── calculator.fxml           # Diseño de la interfaz
│       └── calculator.css            # Estilos visuales
└── test/java/com/calculator/
    └── CalculatorModelTest.java      # Pruebas unitarias
```

## Uso

Selecciona los dígitos y las operaciones con los botones o el teclado. `Enter` o `=` muestran el resultado; `⌫` borra el último carácter; `+/-` invierte el signo; y `C`, `Supr` o `Escape` reinician el cálculo. Los resultados enteros se muestran sin decimales innecesarios; por ejemplo, `2 + 2` muestra `4` y `5 / 2` muestra `2.5`.
