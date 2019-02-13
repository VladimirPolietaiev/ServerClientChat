package threadServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Класс - сервер, принимает запросы от клиентов и отдает данные
 */
public class ThreadServer {
    public static void main(String args[]) {
        // Определяем номер порта, который будет "слушать" сервер
        int port = 1777;

        try {
            // Открыть серверный сокет (ServerSocket)
            ServerSocket servSocket = new ServerSocket(port);

            while (true) {
                System.out.println("Waiting for a connection on " + port);

                // Получив соединение начинаем работать с сокетом
                Socket fromClientSocket = servSocket.accept();

                // Стартуем новый поток для обработки запроса клиента
                new SocketThread(fromClientSocket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
}

// Этот отдельный класс для обработки запроса клиента,
// который запускается в отдельном потоке
class SocketThread extends Thread
{
    private Socket fromClientSocket;

    public SocketThread(Socket fromClientSocket) {
        this.fromClientSocket = fromClientSocket;
    }

    @Override
    public void run() {
        // Автоматически будут закрыты все ресурсы
        try (Socket localSocket = fromClientSocket;
             PrintWriter pw = new PrintWriter(localSocket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(localSocket.getInputStream()))) {

            // Читаем сообщения от клиента до тех пор пока он не скажет "bye"
            String str;
            while ((str = br.readLine()) != null) {
                // Печатаем сообщение
                System.out.println("The message: " + str);
                // Сравниваем с "bye" и если это так - выходим из цикла и закрываем соединение
                if (str.equals("bye")) {
                    // Тоже говорим клиенту "bye" и выходим из цикла
                    pw.println("bye");
                    break;
                } else {
                    // Посылаем клиенту ответ
                    str = "Server returns " + str;
                    pw.println(str);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
