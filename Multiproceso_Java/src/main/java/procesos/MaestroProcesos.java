package procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MaestroProcesos {

    public static void main(String[] args) {
        long n = 200_000;
        int m = 4;

        if (args.length != 0 && args.length != 2) {
            System.err.println("Uso: java procesos.MaestroProcesos [N M]");
            System.exit(1);
        }

        if (args.length == 2) {
            try {
                n = Long.parseLong(args[0]);
                m = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("N y M deben ser números enteros.");
                System.exit(1);
            }
        }

        if (n < 1 || m < 1) {
            System.err.println("N y M deben ser mayores que 0.");
            System.exit(1);
        }
        if (m > n) {
            m = (int) n; // no tiene sentido tener más procesos que números
        }

        long inicioTiempo = System.nanoTime();

        String java = System.getProperty("java.home") + "/bin/java";
        String classpath = System.getProperty("java.class.path");

        // División equitativa: todos reciben n/m y los primeros n%m reciben uno más
        long base = n / m;
        long resto = n % m;

        List<Process> procesos = new ArrayList<>();
        List<String> rangos = new ArrayList<>();

        try {
            // 1. Lanzar los M procesos sin esperar (así corren en paralelo)
            long ini = 1;
            for (int i = 0; i < m; i++) {
                long tam = base + (i < resto ? 1 : 0);
                long fin = ini + tam - 1;

                ProcessBuilder pb = new ProcessBuilder(
                        java, "-cp", classpath,
                        "procesos.TrabajadorPrimo",
                        String.valueOf(ini), String.valueOf(fin));

                procesos.add(pb.start());
                rangos.add("[" + ini + ", " + fin + "]");
                ini = fin + 1;
            }

            // 2. Recoger resultados
            long total = 0;
            boolean hayErrores = false;

            for (int i = 0; i < procesos.size(); i++) {
                Process p = procesos.get(i);

                try (BufferedReader out = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));
                     BufferedReader err = new BufferedReader(
                             new InputStreamReader(p.getErrorStream()))) {

                    String linea = out.readLine();   // stdout del hijo
                    int codigo = p.waitFor();        // espera y obtiene el código de salida

                    if (codigo == 0 && linea != null) {
                        long parcial = Long.parseLong(linea.trim());
                        System.out.println("Proceso " + i + " " + rangos.get(i)
                                + ": " + parcial + " primos");
                        total += parcial;
                    } else {
                        hayErrores = true;
                        System.err.println("Proceso " + i + " " + rangos.get(i)
                                + " falló (código " + codigo + "): " + err.readLine());
                    }
                }
            }

            long ms = (System.nanoTime() - inicioTiempo) / 1_000_000;

            if (hayErrores) {
                System.err.println("Algún proceso falló; el total puede ser incorrecto.");
            }
            System.out.println("Total de primos en [1, " + n + "]: " + total);
            System.out.println("Tiempo total: " + ms + " ms");

        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}