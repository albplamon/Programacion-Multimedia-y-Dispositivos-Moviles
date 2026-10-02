package procesos;

public class TrabajadorPrimo {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java procesos.TrabajadorPrimo 2 200000");
            System.exit(1);
        }

        int inicio = 0;
        int fin = 0;
        try {
            inicio = Integer.parseInt(args[0]);
            fin = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Los números deben ser  enteros.");
            System.exit(1);
        }
        if (inicio > fin) {
            System.err.println("Error: El inicio no puede ser mayor que el fin.");
            System.exit(1);
        }

        int contador = 0;
        for (int i = inicio; i <= fin; i++) {
            if (esPrimo(i)) {
                contador++;
            }
        }

        System.out.println(contador);
        System.exit(0);
    }

    private static boolean esPrimo(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
