# Práctica de procesos en Java

En esta práctica contamos cuántos números primos hay entre `1` y `N`.

El trabajo se puede hacer de dos formas:

* Usando varios procesos para repartir el trabajo.
* De forma normal, sin procesos, para comparar el tiempo.

## Archivos

Dentro de la carpeta `procesos` están las tres clases:

* `TrabajadorPrimo.java`: cuenta los primos de un rango.
* `MaestroProcesos.java`: divide el trabajo y crea los procesos.
* `PrimosSecuencial.java`: cuenta los primos sin usar procesos.

## Compilar

Desde la carpeta principal del proyecto:

```bash
javac procesos/*.java
```

## Ejecutar

### Maestro

Si no ponemos nada, usa `N = 200000` y `M = 4`.

```bash
java procesos.MaestroProcesos
```

También podemos poner nuestros propios valores:

```bash
java procesos.MaestroProcesos 5000000 8
```

El programa divide el rango entre los procesos, suma los resultados y muestra el tiempo que ha tardado.

### Secuencial

Para hacerlo sin procesos:

```bash
java procesos.PrimosSecuencial
```

También podemos indicar el valor de `N`:

```bash
java procesos.PrimosSecuencial 5000000
```

### Trabajador

El trabajador normalmente lo ejecuta el maestro, pero también podemos probarlo directamente:

```bash
java procesos.TrabajadorPrimo 1 100
```

En este caso debería mostrar:

```text
25
```

Si ponemos mal los números, muestra un error.

## Comprobar el resultado

Con `N = 200000`, los dos programas deberían dar el mismo resultado:

```bash
java procesos.MaestroProcesos 200000 4
java procesos.PrimosSecuencial 200000
```

El resultado debe ser:

```text
17984
```

## Cosas a tener en cuenta

El programa con procesos no siempre tiene que ser más rápido.

Cada proceso tiene que arrancar una JVM nueva, y eso tarda un tiempo. Por eso, con números pequeños puede ser incluso más lento que hacerlo de forma secuencial.

Con números más grandes se puede notar más la ventaja de repartir el trabajo entre varios procesos.

El trabajador manda el resultado por `stdout` y el maestro lo recoge usando `BufferedReader`.

Los rangos se reparten de forma bastante igual entre los procesos, aunque algunos pueden tardar más porque comprobar números más grandes cuesta más.
