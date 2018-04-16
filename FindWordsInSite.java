package test;

import java.io.*;
import java.net.*;

/**
 * @author saf
 * 
 *         Реализовать консольное приложение, которое ищет на странице
 *         https://news.yandex.ru/computers.html определенные слова (указанные
 *         как параметр командной строки или взятые из файла) и выводит
 *         результат в виде пар слово-количество вхождений, отсортированный по
 *         убыванию кол-ва вхождений. Поиск д.б. регистронезависимым. Падежи и
 *         т.д. не рассматриваются .
 * 
 */
public class FindWordsInSite {
	private static final String MY_URL = "https://news.yandex.ru/computers.html";
	private static int fromIndex, count, found;

	public void go() {
		// Создаем буферизированные потоки ввода и вывода, самозакрывающиеся
		// Файл с входными данными (словами для поиска), одна строка - одно слово
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new 
				FileInputStream("in.txt"), "UTF-8"));
		// Файл с выходными данными, где каждая строка: "слово - количество вхождений"
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new 
				FileOutputStream("out.txt")))){
			// html-страница
			URL site= new URL(MY_URL);
			BufferedReader brSite = new BufferedReader(new InputStreamReader(site.openStream(), "UTF-8"));
			String line = brSite.readLine().toLowerCase();
//			System.out.println(line); 
			// искомое слово, первый символ - результат перекодировки в UTF-8, его нужно исключить
			String word = br.readLine().substring(1).trim();
			// массив результатов индекс=количество вхождений, значение=слово. Его и сортировать не нужно.
			String[] res = new String[1000];
			while (word != null){
				fromIndex=count=found=0;
				do {
					found = line.indexOf(word.toLowerCase(), fromIndex);
					if (found != -1) {
						fromIndex=found+1; 
						count++;
					}
				} while (found !=-1);
				res[count]+=word+' ';
				try{word = br.readLine().trim();}catch (NullPointerException npe){break;}
			}
			// выводим результат на консоль и в файл
			String outString=""; 
			for (int i=res.length-1; i>=0; i--){
				if (res[i] != null) {
					outString=res[i].substring(4) + " - " + i;
					System.out.println(outString);
					bw.write(outString);
					bw.newLine();
				}
			}
		}catch (MalformedURLException me) {
			System.err.println("Неизвестный хост! \n" + me);
		}catch (IOException  e) {
			System.err.println("Возникла ошибка ввода-вывода! \n" + e);
		}
	}
	public static void main(String[] args) {
		FindWordsInSite obj = new FindWordsInSite();
		obj.go();
	}
}
