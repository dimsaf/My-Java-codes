
import java.io.*;
import java.net.*;

public class ServerGUI {
	private Socket clientSocket;
	private BufferedReader reader;
	private PrintWriter writer;
	private String outString;
	private static String inputFile;
	// private static final String INPUT_FILE = "d:\\Java\\myIp.txt";

	private void go() {
		try (ServerSocket serverSocket = new ServerSocket(3456)) {
			// создаем серверный сокет
			System.out.println("Waiting for client...");
			// переходим в состояние ожидания соединений от клиента
			clientSocket = serverSocket.accept();
			// если клиент достучался - организуем потоки чтения\записи
			// Поток чтения сообщений от клиента
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			// Поток записи сообщений для клиента
			// Очень важный момент! PrintWriter делаем с автосливом!!!
			writer = new PrintWriter(clientSocket.getOutputStream(), true);
			// Организуем вход по паролю в цикле
			outString = "Enter password:";
			writer.println(outString);
			System.out.println("I see client! I said: " + outString);
			outString = "Wrong password! Enter password ones more:";
			while (!(reader.readLine().equalsIgnoreCase("carrot"))) {
				writer.println(outString);
			}
			outString = "Password is correct! Connected";
			writer.println(outString);
			System.out.println(outString); // если мы дошли до этой строки,
											// значит мы прошли цикл
			transferFile();
			reader.close();
			writer.close();
			clientSocket.close(); // закрываем соединение с клиентом
		} catch (IOException e) { // на тот случай если возникнет ошибка при
			// обмене по сети
			e.printStackTrace(); // печать текста исключения
		}
	}

	// Метод для передачи файла от сервера к клиенту
	private void transferFile() throws IOException {
		inputFile = reader.readLine();
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)))) {
			String in = fileReader.readLine();
			while (in != null) {
				writer.println(in);
				in = fileReader.readLine();
			}
		} catch (FileNotFoundException e) { // если принимаемый файл отсуттвует
			writer.println("Error! File not found! Choose another file!");
		}
	}

	public static void main(String[] args) {
		new ServerGUI().go();
	}
}
