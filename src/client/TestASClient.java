package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestASClient {

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

// çàïóñêàåì ïîäêëþ÷åíèå ñîêåòà ïî èçâåñòíûì êîîðäèíàòàì è íèöèàëèçèðóåì ïðè¸ì ñîîáùåíèé ñ êîíñîëè êëèåíòà
        try(Socket socket = new Socket("localhost", 3345);
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream());	)
        {

            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = oos & reading channel = ois initialized.");

// ïðîâåðÿåì æèâîé ëè êàíàë è ðàáîòàåì åñëè òðó
            while(!socket.isOutputShutdown()){

// æä¸ì êîíñîëè êëèåíòà íà ïðåäìåò ïîÿâëåíèÿ â íåé äàííûõ
                if(br.ready()){

// äàííûå ïîÿâèëèñü - ðàáîòàåì
                    System.out.println("Client start writing in channel...");
                    Thread.sleep(1000);
                    String clientCommand = br.readLine();

// ïèøåì äàííûå ñ êîíñîëè â êàíàë ñîêåòà äëÿ ñåðâåðà
                    oos.writeUTF(clientCommand);
                    oos.flush();
                    System.out.println("Clien sent message " + clientCommand + " to server.");
                    Thread.sleep(1000);
// æä¸ì ÷òîáû ñåðâåð óñïåë ïðî÷åñòü ñîîáùåíèå èç ñîêåòà è îòâåòèòü

// ïðîâåðÿåì óñëîâèå âûõîäà èç ñîåäèíåíèÿ
                    if(clientCommand.equalsIgnoreCase("quit")){

// åñëè óñëîâèå âûõîäà äîñòèãíóòî ðàçúåäèíÿåìñÿ
                        System.out.println("Client kill connections");
                        Thread.sleep(2000);

// ñìîòðèì ÷òî íàì îòâåòèë ñåðâåð íà ïîñëåäîê
                        if(ois.available()!=0)		{
                            System.out.println("reading...");
                            String in = ois.readUTF();
                            System.out.println(in);
                        }

// ïîñëå ïðåäâàðèòåëüíûõ ïðèãîòîâëåíèé âûõîäèì èç öèêëà çàïèñè ÷òåíèÿ
                        break;
                    }

// åñëè óñëîâèå ðàçúåäèíåíèÿ íå äîñòèãíóòî ïðîäîëæàåì ðàáîòó
                    System.out.println("Client wrote & start waiting for data from server...");
                    Thread.sleep(2000);

// ïðîâåðÿåì, ÷òî íàì îòâåòèò ñåðâåð íà ñîîáùåíèå(çà ïðåäîñòàâëåííîå åìó âðåìÿ â ïàóçå îí äîëæåí áûë óñïåòü îòâåòèòü
                    if(ois.available()!=0)		{

// åñëè óñïåë çàáèðàåì îòâåò èç êàíàëà ñåðâåðà â ñîêåòå è ñîõðàíÿåìå¸ â ois ïåðåìåííóþ,  ïå÷àòàåì íà êîíñîëü
                        System.out.println("reading...");
                        String in = ois.readUTF();
                        System.out.println(in);
                    }
                }
            }

            System.out.println("Closing connections & channels on clentSide - DONE.");

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}