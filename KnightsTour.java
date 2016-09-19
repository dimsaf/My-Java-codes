
/**
 * Никлаус Вирт "Алгоримы и структуры данных" п 3.4
 * "Рекурсивные алгоритмы с возвратом"
 * 
 * Программа для описания ходов коня на шахматной доске.
 * Задача: заполнить ВСЮ доску ходами коня, не повторяясь
 * 
 *В КАЧЕСТВЕ АГРУМЕНТОВ КОМАНДНОЙ СТРОКИ НАДО ВВЕСТИ (ЧЕРЕЗ ПРОБЕЛ!):
 *РАЗМЕРНОСТЬ ДОСКИ,  НАЧАЛЬНЫЕ КООРДИНАТЫ КОНЯ X, Y (НАЧИНАЯ С 0).
 *
 *НАПРИМЕР:
 *5 3 1
 *
 *ЭТО ОЗНАЧАЕТ СЛЕДУЮЩУЮ ДОСКУ:
 *
 **********************************
*0	0	0	0	0	
*0	0	0	1	0	
*0	0	0	0	0	
*0	0	0	0	0	
*0	0	0	0	0
*
*ПРАВИЛЬНОЕ РЕШЕНИЕ ДЛЯ НЕЕ:
*
**********************************
*23	4	9	14	25	
*10	15	24	1	8	
*5	22	3	18	13	
*16	11	20	7	2	
*21	6	17	12	19
*  
*  
* @author Niclaus Wirth
*
*/

public class KnightsTour {
	// конь ходит буквой Г. Описываем это двумя массивами-дельтами относительно
	// его текущего положения
	private static final int[] DELTA_X = { 2, 1, -1, -2, -2, -1, 1, 2 };
	private static final int[] DELTA_Y = { 1, 2, 2, 1, -1, -2, -2, -1 };
	private int boardDimension;		// размер доски
	protected int[][] chessBoard;	// сама доска, пронумерованная ходами коня
	protected boolean done;			// результат достигнут (вся доска покрыта)
	private boolean endOfSteps;		// возможные шаги коня закончились
	private int x, y, u, v; // x, y - координаты ПРАВИЛЬНОГО хода, который
							// запоминается в массив chessBoard
							// u, v - координаты очередного допустимого хода
							// коня,

	public KnightsTour(String[] args) {
		boardDimension = Integer.valueOf(args[0]);
		chessBoard = new int[boardDimension][boardDimension];
		y = Integer.valueOf(args[1]);
		x = Integer.valueOf(args[2]);
		done = false;
		endOfSteps = false;
		chessBoard[x][y] = 1;
	} // END Constructor

	// пробуем сделать правильный ход
	public void TryNextMove(int x, int y, int i) {
		u = 0;
		v = 0;
		int k;	// номер допустимого хода из возможных (от 1 до 8)
		if (i < boardDimension * boardDimension) {
//			Draw(this);		// можно отрисовывать каждый шаг (особенно - на этапе проверки),
							// но сильно замедляет работу программы
			endOfSteps = false;
			k = next(x, y, -1);
			while (!endOfSteps && !Can.canBeDone(this, i + 1, u, v)) {
				// если ветка оказалась тупиковой - надо откатиться на
				// предыдущую и продолжить ходы
				k = next(x, y, k);
			}
			done = !endOfSteps;
		} else
			done = true;
	} // END TryNextMove

	// сделать следующий ДОПУСТИМЫЙ ход
	private int next(int x, int y, int k) {
		do {
			k++;
			if (k < 8) {
				u = x + DELTA_X[k];
				v = y + DELTA_Y[k];
			}
		} while (!((k == 8)
				|| ((0 <= u) && (u < boardDimension) && (0 <= v) && (v < boardDimension) && (chessBoard[u][v] == 0))));
		endOfSteps = (k == 8);
		return k;
	} // END Next

	// отрисовка доски
	private static void Draw(KnightsTour myHorse) {
		System.out.println("*********************************");
		for (int i = 0; i < myHorse.boardDimension; i++) {
			for (int j = 0; j < myHorse.boardDimension; j++) {
				System.out.print(myHorse.chessBoard[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		KnightsTour myHorse = new KnightsTour(args);
		myHorse.TryNextMove(myHorse.x, myHorse.y, 1);
		Draw(myHorse);
	}
}

// Класс, содержащий единственный метод "Может ли это привести к решению?"
// этот метод попутно записывает правильные ходы на доску и обнуляет ходы
// или ветки ходов, оказавиеся тупиковыми
class Can {
	public static boolean canBeDone(KnightsTour obj, int i, int u, int v) {
		obj.chessBoard[u][v] = i;
		obj.TryNextMove(u, v, i);
		if (!obj.done)
			obj.chessBoard[u][v] = 0;
		return obj.done;
	} // END CanBeDone

}
