package bitbucket.main;

import bitbucket.client.Client;
import bitbucket.server.Server;

import java.util.Scanner;

/**
 * Стартовая точка программы. Содержит единственный метод main
 *
 * @author Влад
 */
public class Main {

    /**
     * Спрашивает пользователя о режиме работы (сервер или клиент) и передаёт
     * управление соответствующему классу
     *
     * @param args
     *            параметры командной строки
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Запустить программу в режиме сервера или клиента? (S(erver) / C(lient))");
        while (true) {
            char answer = Character.toLowerCase(in.nextLine().charAt(0));
            if (answer == 's') {
                new Server();
                break;
            } else if (answer == 'c') {
                new Client();
                break;
            } else {
                System.out.println("Некорректный ввод. Повторите.");
            }
        }
    }

}



