
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mabeesha
 */
public class ServerThread implements Runnable
{
    private final Socket sock;
    private String user;
    private String symbol;
    private String price;
    
    public ServerThread(Socket x)
    {
        this.sock = x;
    }

    @Override
    public void run() 
    {
        try 
        {
            Scanner in      = new Scanner(sock.getInputStream());
            PrintWriter out = new PrintWriter(sock.getOutputStream());
            user            = in.nextLine();
            symbol          = in.nextLine();
            while("-1".equals(Server.stockDataBase.getPrice(symbol)))
            {
                out.println("-1");
                out.flush();
                symbol  = in.nextLine();
            }
            out.println("Price: "+Server.stockDataBase.getPrice(symbol));
            out.flush();
            while(true)
            {
                price = in.nextLine();
                Server.stockDataBase.update(symbol, user, price);//updates the price in stockDataBase
            }
            
        } catch (Exception e) {System.out.println(e);}
        
        
    }
    
}
