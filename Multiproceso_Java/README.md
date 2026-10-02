Práctica: Multiproceso en Java — Cálculo paralelo de primos
Cuenta los números primos en el rango `[1, N]` repartiendo el trabajo entre `M` procesos hijos, y lo compara con una versión secuencial.
Estructura
```
procesos/
├── TrabajadorPrimo.java    # Proceso hijo: cuenta primos en [inicio, fin]
├── MaestroProcesos.java    # Proceso padre: reparte el rango y lanza M hijos
└── PrimosSecuencial.java   # Versión sin procesos, para comparar
README.md
```
Requisitos
JDK 11 o superior (`java -version` y `javac -version` para comprobarlo).
Compilación
Desde la carpeta raíz del proyecto (la que contiene `procesos/`):
```bash
javac procesos/*.java
```
Ejecución
Todos los comandos se lanzan desde la misma carpeta raíz.
Maestro (versión con procesos)
```bash
java procesos.MaestroProcesos              # valores por defecto: N = 200000, M = 4
java procesos.MaestroProcesos <N> <M>      # con parámetros
```
Ejemplo:
```bash
java procesos.MaestroProcesos 5000000 8
```
Muestra los primos de cada subrango, el total y el tiempo empleado.
Versión secuencial
```bash
java procesos.PrimosSecuencial             # valor por defecto: N = 200000
java procesos.PrimosSecuencial <N>
```
Trabajador (programa independiente)
Normalmente lo lanza el maestro, pero también se puede ejecutar a mano:
```bash
java procesos.TrabajadorPrimo <inicio> <fin>
```
Si todo va bien, imprime el número de primos por `stdout` y termina con código `0`.
Si los argumentos son incorrectos, imprime un error por `stderr` y termina con código `1`.
```bash
java procesos.TrabajadorPrimo 1 100     # imprime 25
java procesos.TrabajadorPrimo abc 10    # error, código 1
java procesos.TrabajadorPrimo 50 10     # error (inicio > fin), código 1
```
Para ver el código de salida: `echo $?` (Linux/Mac) o `echo %ERRORLEVEL%` (Windows).
Verificación
Con `N = 200000`, tanto el maestro como el secuencial deben dar 17984 primos:
```bash
java procesos.MaestroProcesos 200000 4
java procesos.PrimosSecuencial 200000
```
Notas
Cada proceso hijo arranca una JVM nueva, lo que añade una sobrecarga inicial considerable. Con rangos pequeños (como `N = 200000`) el multiproceso puede ser más lento que el secuencial; con `N` grande el paralelismo empieza a compensar.
La comunicación entre padre e hijo se hace por `stdout`: el hijo imprime solo el número y el padre lo lee con un `BufferedReader`.
El padre pasa a los hijos su propio classpath, por lo que no hace falta configurarlo aparte.
Los subrangos tienen el mismo tamaño (±1), pero no el mismo coste: comprobar números grandes cuesta más, así que el último proceso suele terminar el último.
