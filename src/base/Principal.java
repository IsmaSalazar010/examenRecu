package base;

import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class Principal {
    private static final Logger LOGGER = Logger.getLogger(Principal.class.getName());
    private static Scanner teclado = new Scanner(System.in);
    private static boolean tienePermiso = false;
    private static boolean compuertasVerificadas = false;

    static {
        try {
            // Crear un manejador de archivo que registre en "opcionesMenu.log"
            FileHandler fileHandler = new FileHandler("opcionesMenu.log", true);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return "Se ha seleccionado la opción: " + record.getMessage() + "\n";
                }

                @Override
                public String getHead(Handler h) {
                    return "COMIENZO DEL LOG\n";
                }

                @Override
                public String getTail(Handler h) {
                    return "FIN DEL LOG\n";
                }
            });
            LOGGER.addHandler(fileHandler);

            // Establecer el nivel de logging del logger
            LOGGER.setLevel(Level.FINE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Este programa lee el nivel de agua de una presa y permite abrir compuertas si tenemos permiso y el nivel es superior a 50.");

        int nivel = leerNivelAgua();

        int opcion = 0;
        do {
            opcion = mostrarMenu(nivel);

            // Log de la opción seleccionada
            LOGGER.fine(String.valueOf(opcion));

            switch (opcion) {
                case 1:
                    nivel = leerNivelAgua();
                    tienePermiso = false;
                    compuertasVerificadas = false;
                    break;
                case 2:
                    boolean abiertas = intentarAbrirCompuertas(tienePermiso, compuertasVerificadas);
                    if (abiertas) {
                        mostrarMensajeCompuertasAbiertas();
                    } else {
                        mostrarMensajeNoSeCumplenCondiciones();
                    }
                    break;
                case 3:
                    tienePermiso = solicitarPermiso(nivel);
                    if (!tienePermiso) {
                        System.out.println("El permiso solamente se concede si el nivel del agua es superior a 75.");
                    }
                    break;
                case 4:
                    compuertasVerificadas = verificarCompuertas();
                    if (compuertasVerificadas) {
                        System.out.println("¡Compuertas verificadas!");
                    }
                    break;
                default:
                    break;
            }
        } while (opcion != 5);
        
        // Añadir "FIN DEL LOG" al final del archivo
        for (Handler handler : LOGGER.getHandlers()) {
            handler.close();
        }
    }

    private static int mostrarMenu(int nivel) {
        int opcion;
        System.out.println();
        System.out.println("Nivel del agua: " + nivel);
        System.out.println();
        System.out.println("ACCIONES: ");
        System.out.println();
        System.out.println("1. Nueva lectura del nivel de agua.");
        System.out.println("2. Abrir compuertas. Requiere:");
        System.out.println("    3. Solicitar permiso. Estado: " + (tienePermiso ? "CONCEDIDO" : "NO CONCEDIDO"));
        System.out.println("    4. Verificar compuertas. Estado: " + (compuertasVerificadas ? "VERIFICADAS" : "NO VERIFICADAS"));
        System.out.println("5. Salir");
        System.out.println();
        System.out.print("Introduce opción: ");
        opcion = teclado.nextInt();
        return opcion;
    }

    static int leerNivelAgua() {
        tienePermiso = false;
        return (int) Math.round(Math.random() * 100);
    }

    static boolean intentarAbrirCompuertas(boolean tienePermiso, boolean compuertasVerificadas) {
        return tienePermiso && compuertasVerificadas;
    }

    private static void mostrarMensajeNoSeCumplenCondiciones() {
        System.out.println();
        System.out.print("No se cumplen las condiciones para abrir compuertas.");
    }

    private static void mostrarMensajeCompuertasAbiertas() {
        System.out.println();
        System.out.print("¡Compuertas abiertas!");
    }

    public static boolean solicitarPermiso(int nivel) {
        return nivel > 75;
    }

    static boolean verificarCompuertas() {
        return true;
    }
}

