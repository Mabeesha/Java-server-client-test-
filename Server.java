
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mabeesha
 */
public class Server 
{
    public static StockDataBase stockDataBase;
    private static final String FileName = "stocks.csv";
    
    public Server()
    {
        stockDataBase = new StockDataBase(FileName);
    }
    
    public static void main(String[] args)
    {
        //stockDataBase = new StockDataBase("stocks.csv"); // creating the database by reading the stocks.csv file
        Server server = new Server();
        try {
            Frame.start();
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            ServerSocket serverSocket = new ServerSocket(2000);        // server socket is created , port number = 2000.
            System.out.println("Waiting for bids.....!");              // remove later
            
            while(true)
            {
                Socket sock               = serverSocket.accept();
                ServerThread serverThread = new ServerThread(sock);
                Thread thread             = new Thread(serverThread);
                thread.start();
            }
            
        }
        catch(Exception e){System.out.println(e);}
    }
}
