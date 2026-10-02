package procesos;

public class PrimosSecuencial {

    public static void main(String[] args) {
        long n = 200_000;

        if (args.length == 1) {
            try {
                n = Long.parseLong(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("N debe ser un número entero.");
                System.exit(1);
            }
        } else if (args.length > 1) {
            System.err.println("Uso: java procesos.PrimosSecuencial [N]");
            System.exit(1);
        }

        long inicio = System.nanoTime();

        long contador = 0;
        for (long i = 1; i <= n; i++) {
            if (esPrimo(i)) {
                contador++;
            }
        }

        long ms = (System.nanoTime() - inicio) / 1_000_000;

        System.out.println("Total de primos en [1, " + n + "]: " + contador);
        System.out.println("Tiempo total: " + ms + " ms");
    }

    private static boolean esPrimo(long n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}