package memory;

import java.util.Random;
import java.util.Scanner;

public class Memory {

	static Scanner reader = new Scanner(System.in);
	final static int MAX = 4;
	final static int MAXJUGAGORES = 2;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char resp;
		
		do {
			jugar();
			resp = obtenerResp();
		} while (resp == 's' || resp == 'S');
	}

	private static char obtenerResp() {
		// TODO Auto-generated method stub
		char res;
		boolean correcto = true;
		
		do {
			System.out.print("Quieres seguir jugando? (s/n) : ");
			res = reader.next().charAt(0);
			if (res != 's' && res != 'S' && res != 'n' && res != 'N') {
				System.out.println("Se espera s / n. Vuelve a intentarlo");
				correcto = false;
			}
			
		} while (!correcto);
		
		return res;
	}

	private static void jugar() {
		// TODO Auto-generated method stub
		char [][] solucion = new char[MAX][MAX];
		char [][] secret = new char[MAX][MAX];
		int puntosJug1 = 0;
		int puntosJug2 = 0;
		int turno = 1;
		boolean correcto;
		char resp;
		
		inicializarTablero(solucion, MAX, '?');
		inicializarSecret(secret, MAX);
		
		do {
			mostrarTablero(solucion, MAX);
			correcto = tiradaJugador(solucion, secret, MAX, turno);
			if (correcto) {
				if (turno == 1) {
					puntosJug1++;
				}
				else {
					puntosJug2++;
				}
				System.out.println("Has acertado, te toca volver a jugar");
			}
			else {
				System.out.println("Lo siento, le toca al siguiente jugador");
				turno = (turno % MAXJUGAGORES) + 1;
			}
			resp = obtenerResp();
			
		} while ((puntosJug1 + puntosJug2 < 8) && (resp == 's' || resp == 'S'));
		
		if (puntosJug1 > puntosJug2) {
			System.out.println("Ha ganado el jugador 1");
		}
		else {
			if (puntosJug1 < puntosJug2) 
					System.out.println("Ha ganado el jugador 2");
			else 
					System.out.println("Han quedado en tablas");
		}
	}

	private static void mostrarTablero(char[][] sol, int m) {
		// TODO Auto-generated method stub
		int i, j;
		
		System.out.print("  ");
		for (j = 0; j < m; j++)
			System.out.print(j + " ");
		System.out.println();
		
		for (i = 0; i < m; i++) {
			System.out.print(i + " ");
			for (j = 0; j < m; j++)
				System.out.print(sol[i][j] + " ");
			System.out.println();
		}
	}

	private static boolean tiradaJugador(char[][] sol, char[][] secret, int m, int torn) {
		// TODO Auto-generated method stub
		int fila1, col1, fila2, col2;
		char valor;
		boolean correcto;
		
		System.out.println("Toca jugar al jugador " + torn);
		//Obtenemos la posición de la primera casilla i destapar
		System.out.println("Indica la fila i la columna de la primera casilla a destapar: ");
		do {
			fila1 = obtenerNum(m);
			col1 = obtenerNum(m);
			correcto = (comprobarPos(sol, fila1, col1) == '?');
		} while (!correcto);
		copiarCasilla(secret, sol, fila1, col1);
		mostrarTablero(sol, m);
		
		//Obtenemos la posición de la segunda casilla i destapar
		System.out.println("Indica la fila i la columna de la segunda casilla a destapar: ");
		do {
			fila2 = obtenerNum(m);
			col2 = obtenerNum(m);
			correcto = (comprobarPos(sol, fila2, col2) == '?');
		} while (!correcto);
		copiarCasilla(secret, sol, fila2, col2);
		mostrarTablero(sol, m);
		
		if (cassilasIguales(secret[fila1][col1], secret[fila2][col2])) {
			return true;
		}
		else {
			taparCasillas(sol, fila1, col1, fila2, col2, m);
			return false;
		}
		
	}

	private static void taparCasillas(char[][] sol, int fila1, int col1, int fila2, int col2, int m) {
		// TODO Auto-generated method stub
		sol[fila1][col1] = '?';
		sol[fila2][col2] = '?';
		mostrarTablero(sol, m);
	}

	private static boolean cassilasIguales(char car1, char car2) {
		// TODO Auto-generated method stub
		
		if (car1 == car2) return true;
		else return false;
	}

	private static void copiarCasilla(char[][] secret, char[][] sol, int fila, int col) {
		// TODO Auto-generated method stub
		sol[fila][col] = secret[fila][col];
	}

	private static char comprobarPos(char[][] sol, int fila, int col) {
		// TODO Auto-generated method stub
		
		return sol[fila][col];
	}

	private static int obtenerNum(int m) {
		// TODO Auto-generated method stub
		boolean correcto = false;
		int num = 0;
		
		do {
			try {
				System.out.print("Introdueix un número entre 0 i " + (m-1) + ": ");
				num = reader.nextInt();
				if (num >= 0 && num < m)
					correcto = true;
			}
			catch (Exception e) {
				System.out.println("Error, s'esperava un número enter");
				reader.nextLine();
			}
			
		} while (!correcto);
		
		return num;
	}

	private static void inicializarSecret(char[][] secret, int m) {
		// TODO Auto-generated method stub
		posaPeces(secret, m);
		remanaPeces(secret, m);
	}

	private static void remanaPeces(char[][] secret, int m) {
		// TODO Auto-generated method stub
		Random rnd = new Random();
		int i, j;
		char aux;
		int fila, col;
		
		for (i=0; i < m; i++) {
			for (j = 0; j < m; j++) {
				fila = rnd.nextInt(m);
				col = rnd.nextInt(m);
				aux = secret[i][j];
				secret[i][j] = secret[fila][col];
				secret[fila][col] = aux;
			}
		}
		//mostrarTablero(secret, m);
	}

	private static void posaPeces(char[][] secret, int m) {
		// TODO Auto-generated method stub
		char pieza = 'A';
		int i, j;
		int cont = 0;
		
		for (i=0; i < m; i++) {
			for (j = 0; j < m; j++) {
				secret[i][j] = pieza;
				cont++;
				if (cont == 2) {
					pieza++; 
					cont = 0;
				}
			}
		}
		//mostrarTablero(secret, m);
		
	}

	private static void inicializarTablero(char[][] solucion, int m, char car) {
		// TODO Auto-generated method stub
		//Pone todas las casillas de la matriz solucion con el caracter car
		int i, j;
		
		for (i = 0; i < m; i++)
			for (j = 0; j < m; j++)
				solucion[i][j] = car;
	}

}
