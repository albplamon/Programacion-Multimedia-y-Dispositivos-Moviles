package procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MaestroProcesos {

    public static void main(String[] args) {

        long n = 200000;
        int m = 4;

        if (args.length == 2) {
            try {
                n = Long.parseLong(args[0]);
                m = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Los argumentos deben ser numeros");
                System.exit(1);
            }
        }

        if (n < 1 || m < 1) {
            System.err.println("N y M deben ser mayores que 0");
            System.exit(1);
        }

        if (m > n) {
            m = (int) n;
        }

        long tiempoInicio = System.nanoTime();

        String java = System.getProperty("java.home") + "/bin/java.exe";
        String classpath = System.getProperty("java.class.path");

        long parte = n / m;
        long resto = n % m;

        ArrayList<Process> procesos = new ArrayList<>();

        long inicio = 1;

        try {

            for (int i = 0; i < m; i++) {

                long cantidad = parte;

                if (i < resto) {
                    cantidad++;
                }

                long fin = inicio + cantidad - 1;

                ProcessBuilder proceso = new ProcessBuilder(
                        java,
                        "-cp",
                        classpath,
                        "procesos.TrabajadorPrimo",
                        String.valueOf(inicio),
                        String.valueOf(fin)
                );

                procesos.add(proceso.start());

                inicio = fin + 1;
            }

            long total = 0;

            for (int i = 0; i < procesos.size(); i++) {

                Process proceso = procesos.get(i);

                BufferedReader lector = new BufferedReader(
                        new InputStreamReader(proceso.getInputStream())
                );

                BufferedReader error = new BufferedReader(
                        new InputStreamReader(proceso.getErrorStream())
                );

                String resultado = lector.readLine();

                int codigo = proceso.waitFor();

                if (codigo == 0 && resultado != null) {

                    long primos = Long.parseLong(resultado);
                    total += primos;

                    System.out.println(
                            "Proceso " + i + ": " + primos + " primos"
                    );

                } else {

                    System.err.println("El proceso " + i + " ha fallado");

                    String mensaje = error.readLine();

                    if (mensaje != null) {
                        System.err.println(mensaje);
                    }
                }

                lector.close();
                error.close();
            }

            long tiempo = (System.nanoTime() - tiempoInicio) / 1000000;

            System.out.println("Total de primos: " + total);
            System.out.println("Tiempo total: " + tiempo + " ms");

        } catch (IOException e) {
            System.err.println("Error al crear los procesos");
            System.exit(1);

        } catch (InterruptedException e) {
            System.err.println("El proceso fue interrumpido");
            System.exit(1);
        }
    }
}

