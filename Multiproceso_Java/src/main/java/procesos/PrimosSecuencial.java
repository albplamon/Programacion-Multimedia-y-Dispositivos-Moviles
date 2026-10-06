package procesos;

public class PrimosSecuencial {

    public static void main(String[] args) {

        long n = 200000;

        if (args.length == 1) {
            try {
                n = Long.parseLong(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("N debe ser un numero");
                System.exit(1);
            }
        }

        long inicioTiempo = System.nanoTime();

        int contador = 0;

        for (int i = 1; i <= n; i++) {
            if (esPrimo(i)) {
                contador++;
            }
        }

        long tiempo = (System.nanoTime() - inicioTiempo) / 1000000;

        System.out.println("Total de primos: " + contador);
        System.out.println("Tiempo total: " + tiempo + " ms");
    }

    public static boolean esPrimo(int numero) {

        if (numero < 2) {
            return false;
        }

        for (int i = 2; i < numero; i++) {
            if (numero % i == 0) {
                return false;
            }
        }

        return true;
    }
}

