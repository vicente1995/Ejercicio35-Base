import java.util.ArrayList;
import java.util.Random;

/**
 * Clase gestora del tablero de juego.
 * Guarda una matriz de enteros representado el tablero.
 * Si hay una mina en una posición guarda el número -1
 * Si no hay una mina, se guarda cuántas minas hay alrededor.
 * Almacena la puntuación de la partida
 * @author jesusredondogarcia
 *
 */
public class ControlJuego {
	
	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int [][] tablero;
	private int puntuacion;
	
	
	public ControlJuego() {
		//Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];
		
		//Inicializamos una nueva partida
		inicializarPartida();
	}
	
	
	/**Método para generar un nuevo tablero de partida:
	 * @pre: La estructura tablero debe existir. 
	 * @post: Al final el tablero se habrá inicializado con tantas minas como marque la variable MINAS_INICIALES. 
	 * 			El resto de posiciones que no son minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida(){
		//Borro del tablero la información que pudiera haber anteriormente (los pongo todos a cero):	
		for (int i = 0; i <tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				tablero[i][j]=0;
			}
		}

		//Me creo LADO_TABLERO*LADO_TABLERO números en un array list, uno para cada una de las posiciones del tablero:
		ArrayList<Integer> posiciones = new ArrayList<>();
		for (int i = 0; i < (LADO_TABLERO*LADO_TABLERO); i++) {
			posiciones.add(i);
		}


		//Saco 20 posiciones sin repetir del array y les coloco una mina en el tablero:
		Random rd = new Random();
		
		for (int i = 0; i < MINAS_INICIALES; i++) {
			int iPosElegida=rd.nextInt(posiciones.size());
			int posElegida=posiciones.get(iPosElegida);
			posiciones.remove(iPosElegida);
			
			//Meto una mina en esa posicion:
			tablero[posElegida/LADO_TABLERO][posElegida%LADO_TABLERO]=MINA;
		}

		//Calculo para todas las posiciones que no tienen minas, cuántas minas hay alrededor.
		
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if(tablero[i][j] != MINA){
					tablero[i][j]=calculoMinasAdjuntas(i, j);
				}
			}
		}
		
		//Pongo la puntuación a cero:
		
		puntuacion=0;
		depurarTablero();

		
	}
	
	/**Cálculo de las minas adjuntas:
	 * Para calcular el número de minas tenemos que tener en cuenta que no nos salimos nunca del tablero.
	 * Por lo tanto, como mucho la i y la j valdrán LADO_TABLERO-1.
	 * Por lo tanto, como mucho la i y la j valdrán como poco 0.
	 * @param i: posición verticalmente de la casilla a rellenar
	 * @param j: posición horizontalmente de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 */
	private int calculoMinasAdjuntas(int i, int j){
		int iInicial = Math.max(0, i-1);
		int iFinal = Math.min(LADO_TABLERO-1, i+1);
		int jInicial = Math.max(0, j-1);
		int jFinal = Math.min(LADO_TABLERO-1, j+1);
		
		int acumuladorMinas=0;
		
		for (int indI= iInicial; indI <= iFinal; indI++) {
			for (int indJ= jInicial; indJ <= jFinal; indJ++) {
				if(tablero[indI][indJ]==MINA){
					acumuladorMinas++;
				}
			}
		}
			
		return acumuladorMinas;
		
	}
	
	/**
	 * Método que nos permite 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por el GestorJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posición verticalmente de la casilla a abrir
	 * @param j: posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j){
		if(tablero[i][j]==MINA){
			return false;
		}
		
		puntuacion++;
		return true;
	}
	
	
	
	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas las casillas.
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son minas.
	 **/
	public boolean esFinJuego(){
		return puntuacion==LADO_TABLERO*LADO_TABLERO-MINAS_INICIALES;
	}
	
	
	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza para depurar
	 */
	public void depurarTablero(){
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuaci�n: "+puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta calcularlo, símplemente consultarlo
	 * @param i : posición vertical de la celda.
	 * @param j : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return tablero[i][j];
	}

	/**
	 * Método que devuelve la puntuación actual
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return puntuacion;
	}
	
}
