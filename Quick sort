/**
 * Здесь я реализовал механизм "быстрой сортировки" массива, описанный в книге:  
 * Никлаус Вирт. Алгоритмы и структуры данных. В отличие от неэффективных методов 
 * (сортировка вставками, сортировка выбором и сортировка пузырьком),
 * этот метод дает Time Complexity = N*log(N) вместо N*N.
 * 
 * Суть метода:
 * делим массив пополам, с разних концов идет к середине. Если число слева меньше среднего и число справа 
 * больше среднего, меняем их местами. Доходим до середины. Применяем тот же метод к левой и правой половине
 * массива и т.д., пока длина отрезка выборки не составит 1 элемент. 
 * Метод рекурсивно вызывает сам себя.
 * 
 * Начальный массив формируется случайным образом, 
 * отсортированный массив выводится на консоль и в файл "out.txt", где сохраняется лог выполнения программы.
 * 
 * @author saf
 */

package sort;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class MyArraySort {
	private int[] arrayToSort = new int[1000];

	private MyArraySort(){
		Random rand = new Random();
		for (int i=0; i<arrayToSort.length; i++) arrayToSort[i] = rand.nextInt();
	}
	
	private void sort(int left, int right) {
		int i,j,middle, tmp;
		i=left; j=right;
		middle=arrayToSort[(int)((i+j)/2)];
		do {
			while (arrayToSort[i]<middle) {i++;}
			while (middle<arrayToSort[j]) {j--;}
			if (i<=j){
				tmp=arrayToSort[i]; arrayToSort[i]=arrayToSort[j]; arrayToSort[j]=tmp;
				i++; j--;
			}
		} while (!(i>j));
		if (left<j) sort(left,j);
		if (i<right) sort(i, right);
	}

	private void print(String message){
		String outString=Arrays.toString(arrayToSort)+"\n"; 
		// Создаем буферизированный поток вывода в файл, самозакрывающийся в try-with-resourses
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("out.txt", true)))){
			bw.write(message+outString);
			System.out.print(message+outString);
		}catch (IOException  e) {
			System.err.println("Возникла ошибка записи в файл! \n" + e);
		}
	}

	public static void main(String[] args) {
		MyArraySort obj = new MyArraySort();
		obj.print("Unsorted array:\n");
		obj.sort(0, obj.arrayToSort.length-1);
		obj.print("Sorted array:\n");
	}
}
