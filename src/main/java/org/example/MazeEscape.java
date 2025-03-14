package org.example;

import java.util.Scanner;

// Eccezione personalizzata per movimenti fuori dai limiti
class OutOfBoundsException extends Exception {

    public OutOfBoundsException(String message) {
        super(message);
    }
}

// Eccezione personalizzata per collisione con muri
class WallCollisionException extends Exception {

    public WallCollisionException(String message) {
        super(message);
    }
}

public class MazeEscape {

    // Dichiarazione della matrice del labirinto
    private static final char[][] LABIRINTO = {
        {'.', '.', '#', '.', '.', '.', '#', '.', '.', '.'},
        {'#', '.', '#', '.', '#', '.', '.', '#', '.', '#'},
        {'.', '.', '.', '.', '.', '#', '.', '.', '.', '.'},
        {'#', '.', '#', '#', '.', '#', '#', '.', '#', '.'},
        {'#', '.', '#', '#', '.', '.', '#', '.', '.', '#'},
        {'.', '.', '#', '.', '.', '.', '.', '#', '.', '.'},
        {'#', '#', '#', '.', '#', '#', '.', '#', '#', '#'},
        {'.', '.', '.', '.', '#', '.', '.', '.', '.', '.'},
        {'#', '.', '#', '#', '#', '.', '#', '#', '.', '#'},
        {'.', '.', '.', '.', '.', '.', '.', '#', '.', 'E'}
    };

    // Coordinate iniziali del giocatore
    private static int playerX = 0;
    private static int playerY = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean escaped = false;

        System.out.println("Benvenuto in Maze Escape! Trova l'uscita ('E').");

        while (!escaped) {
            printMaze();
            System.out.print("Muoviti (W = su, A = sinistra, S = giù, D = destra): ");
            char move = scanner.next().toUpperCase().charAt(0);

            try {
                movePlayer(move);
            } catch (OutOfBoundsException | WallCollisionException e) {
                System.out.println(e.getMessage());
            }

            if(checkwin()){
                System.out.println("Hai trovato l'uscita! Congratulazioni!");
                escaped = true;
            }
        }

        scanner.close();
    }

    /**
     * Metodo per spostare il giocatore all'interno del labirinto Deve
     * controllare: - Se il movimento è fuori dai limiti → lancia
     * OutOfBoundsException - Se il movimento porta su un muro ('#') → lancia
     * WallCollisionException - Se il movimento è valido, aggiornare la
     * posizione
     */
    private static void movePlayer(char direction) throws OutOfBoundsException, WallCollisionException {
        switch (direction) {
            case 'W':
                playerY--;
                if (playerY < 0 ) {
                    playerY++;
                    throw new OutOfBoundsException("Sei uscito dal labirinto!");
                }
                else if (LABIRINTO[playerY][playerX] == '#') {
                    playerY++;
                    throw new WallCollisionException("Hai colpito un muro!");
                }
                break;
            case 'S':
                playerY++;
                if (playerY > LABIRINTO[0].length-1 ) {
                    playerY--;
                    throw new OutOfBoundsException("Sei uscito dal labirinto!");
                }
                else if (LABIRINTO[playerY][playerX] == '#') {
                    playerY--;
                    throw new WallCollisionException("Hai colpito un muro!");
                }
                break;
            case 'A':
                playerX--;
                if (playerX < 0 ) {
                    playerX++;
                    throw new OutOfBoundsException("Sei uscito dal labirinto!");
                }
                else if (LABIRINTO[playerY][playerX] == '#') {
                    playerX++;
                    throw new WallCollisionException("Hai colpito un muro!");
                }
                break;
            case 'D':
                playerX++;
                if (playerX > LABIRINTO[0].length-1 ) {
                    playerX--;
                    throw new OutOfBoundsException("Sei uscito dal labirinto!");
                }
                else if (LABIRINTO[playerY][playerX] == '#') {
                    playerX--;
                    throw new WallCollisionException("Hai colpito un muro!");
                }
                break;
            default:
                System.out.println("Mossa non valida!");

        }
    }

    private static boolean checkwin(){
        return LABIRINTO[playerY][playerX] == 'E';
    }

    /**
     * Metodo per stampare il labirinto attuale
     */
    private static void printMaze() {
        for(int i=0; i<LABIRINTO.length; i++) {
            for(int j=0; j<LABIRINTO[i].length; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print('P'+" ");
                } else {
                    System.out.print(LABIRINTO[i][j]+" ");
                }
            }
            System.out.println();
        }
    }
}
