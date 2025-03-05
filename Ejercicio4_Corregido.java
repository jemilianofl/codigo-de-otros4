package org.generation;

// Importación de las librerias util
import java.util.*;

public class Codigo4 {
	
	// Número de rondas que se deben ganar para ser el vencedor
    private static final int RONDAS_PARA_GANAR = 3;
    
    /**
     * Método principal que inicia la aplicación
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Instanciamos el juego y ejecutamos
        Codigo4 juego = new Codigo4();
        juego.iniciarPrograma();
    }
    
    /**
     * Método que inicia el programa y maneja la recursividad para volver a jugar
     */
    public void iniciarPrograma() {
        // Creamos un solo Scanner para toda la aplicación (optimización)
        try (Scanner scanner = new Scanner(System.in)) {
            boolean seguirJugando = true;
            
            while (seguirJugando) {
                // Ejecutamos una partida completa
                ejecutarJuego(scanner);
                
                // Preguntamos si desea volver a jugar
                seguirJugando = preguntarSiJugarDeNuevo(scanner);
            }
            
            System.out.println("\n¡Gracias por jugar! ¡Hasta pronto!");
        } // El try-with-resources cierra automáticamente el Scanner
    }
    
    /**
     * Pregunta al usuario si desea jugar otra partida
     * 
     * @param scanner El scanner para leer la entrada
     * @return true si el usuario desea jugar otra partida, false en caso contrario
     */
    private boolean preguntarSiJugarDeNuevo(Scanner scanner) {
        while (true) {
            System.out.print("\n¿Desea jugar otra partida? (s/n): ");
            String respuesta = scanner.nextLine().toLowerCase().trim();
            
            if (respuesta.equals("s")) {
                return true;
            } else if (respuesta.equals("n")) {
                return false;
            } else {
                System.out.println("Respuesta no válida. Por favor, ingrese 's' para sí o 'n' para no.");
            }
        }
    }
    
    /**
     * Método principal que ejecuta el juego completo con múltiples rondas
     * hasta que un jugador alcance el número de victorias definido.
     * 
     * @param scanner El scanner para leer la entrada del usuario
     */
    public void ejecutarJuego(Scanner scanner) {
        System.out.println("\n¡Bienvenido al juego de Piedra, Papel o Tijeras!");
        System.out.println("El primer jugador que gane " + RONDAS_PARA_GANAR + " rondas será el vencedor.");
        
        // Contadores de victorias para cada jugador
        int victoriasJ1 = 0;
        int victoriasJ2 = 0;
        int numeroRonda = 1;
        
        // Continuamos jugando hasta que algún jugador alcance las victorias necesarias
        while (victoriasJ1 < RONDAS_PARA_GANAR && victoriasJ2 < RONDAS_PARA_GANAR) {
            System.out.println("\n--- RONDA " + numeroRonda + " ---");
            
            // Obtenemos y validamos las jugadas
            String jugadaJ1 = obtenerJugada(1, scanner);
            String jugadaJ2 = obtenerJugada(2, scanner);
            
            // Determinamos y mostramos el resultado de la ronda
            int ganadorRonda = mostrarResultadoRonda(jugadaJ1, jugadaJ2);
            
            // Actualizamos los contadores de victorias
            if (ganadorRonda == 1) {
                victoriasJ1++;
            } else if (ganadorRonda == 2) {
                victoriasJ2++;
            }
            
            // Mostramos el marcador actual
            System.out.println("MARCADOR - Jugador 1: " + victoriasJ1 + " | Jugador 2: " + victoriasJ2);
            
            numeroRonda++;
        }
        
        // Mostramos el resultado final
        mostrarGanadorFinal(victoriasJ1, victoriasJ2);
    }
    
    /**
     * Obtiene y valida la jugada de un jugador.
     * Utiliza un bucle do-while para garantizar que la entrada sea válida.
     * 
     * @param numeroJugador El número del jugador (1 o 2)
     * @param scanner El scanner para leer la entrada
     * @return La jugada validada
     */
    private String obtenerJugada(int numeroJugador, Scanner scanner) {
        String jugada;
        boolean jugadaValida = false;
        
        do {
            System.out.print("Turno del jugador " + numeroJugador + 
                " (introduzca piedra, papel o tijeras): ");
            jugada = scanner.nextLine().toLowerCase().trim();
            
            // Validamos que la jugada sea correcta
            jugadaValida = esJugadaValida(jugada);
            
            if (!jugadaValida) {
                System.out.println("Jugada no válida. Intente nuevamente.");
            }
        } while (!jugadaValida);
        
        return jugada;
    }
    
    /**
     * Valida si una jugada es correcta.
     * Comprueba si la entrada del usuario corresponde a una jugada válida.
     * 
     * @param jugada La jugada a validar
     * @return true si es válida, false en caso contrario
     */
    private boolean esJugadaValida(String jugada) {
        return jugada.equals("piedra") || 
               jugada.equals("papel") || 
               jugada.equals("tijeras");
    }
    
    /**
     * Determina y muestra el resultado de una ronda.
     * Separa la lógica de presentación de la lógica de negocio.
     * 
     * @param jugadaJ1 La jugada del primer jugador
     * @param jugadaJ2 La jugada del segundo jugador
     * @return El número del jugador ganador (1 o 2), o 0 en caso de empate
     */
    private int mostrarResultadoRonda(String jugadaJ1, String jugadaJ2) {
        System.out.println("Jugador 1: " + jugadaJ1);
        System.out.println("Jugador 2: " + jugadaJ2);
        
        // Si ambas jugadas son iguales, es empate
        if (jugadaJ1.equals(jugadaJ2)) {
            System.out.println("Resultado: Empate en esta ronda");
            return 0; // Empate, ningún jugador suma puntos
        } else {
            // Determinamos el ganador y mostramos el resultado
            int ganador = determinarGanador(jugadaJ1, jugadaJ2);
            System.out.println("Resultado: Gana el jugador " + ganador + " en esta ronda");
            return ganador;
        }
    }
    
    /**
     * Determina el ganador según las reglas del juego.
     * Aplica la lógica del juego piedra, papel o tijeras.
     * 
     * @param jugadaJ1 La jugada del primer jugador
     * @param jugadaJ2 La jugada del segundo jugador
     * @return El número del jugador ganador (1 o 2)
     */
    private int determinarGanador(String jugadaJ1, String jugadaJ2) {
        // Por defecto gana el jugador 2
        int ganador = 2;
        
        // Verificamos las condiciones para que gane el jugador 1
        if ((jugadaJ1.equals("piedra") && jugadaJ2.equals("tijeras")) ||
            (jugadaJ1.equals("papel") && jugadaJ2.equals("piedra")) ||
            (jugadaJ1.equals("tijeras") && jugadaJ2.equals("papel"))) {
            ganador = 1;
        }
        
        return ganador;
    }
    
    /**
     * Muestra el resultado final del juego y declara al ganador.
     * Se llama cuando un jugador alcanza el número necesario de victorias.
     * 
     * @param victoriasJ1 Número de victorias del jugador 1
     * @param victoriasJ2 Número de victorias del jugador 2
     */
    private void mostrarGanadorFinal(int victoriasJ1, int victoriasJ2) {
        System.out.println("\n==========================================");
        System.out.println("¡FIN DEL JUEGO!");
        System.out.println("Resultado final - Jugador 1: " + victoriasJ1 + " | Jugador 2: " + victoriasJ2);
        
        if (victoriasJ1 > victoriasJ2) {
            System.out.println("¡El jugador 1 es el GANADOR!");
        } else {
            System.out.println("¡El jugador 2 es el GANADOR!");
        }
        System.out.println("==========================================");
    }

}
