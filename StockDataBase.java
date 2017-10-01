//Symbol,Security Name,Market Category,Test Issue,Financial Status,Round Lot Size,Price 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mabeesha
 */
public class StockDataBase 
{
    private int no_symbols              = 3067;
    private int no_attributes           = 7;
    private int max_no_users_per_symbol = 100;
    
    private String[][]   stock_data;//stock data stores in a 2D array
    private String[][][] bid_history;//bid history stores in a 3D array
    
    public StockDataBase(String FileName)
    {
        stock_data        = new String[no_symbols][no_attributes];
        bid_history = new String[no_symbols][max_no_users_per_symbol][2];
        
        String csvFile = FileName;
        String line;
        String cvsSplitBy = ",";
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            int i = 0;
            while ((line = br.readLine()) != null) 
            {
                String price_Temp = "";//some tuples have 7 or 8 commas(instead of 6) so this get messed
                // use comma as separator
                String[] tuple = line.split(cvsSplitBy);
                int offset = tuple.length-7;
                //updates list
                for(int j=0;j<no_attributes;j++)
                {
                    if(tuple.length == 7)
                    {
                        stock_data[i][j] = tuple[j];
                        price_Temp = tuple[6];
                    }
                    else
                    {
                        price_Temp = tuple[tuple.length-1];
                        stock_data[i][1] = "";
                        if(j==0)stock_data[i][j] = tuple[0];
                        else if(j==1)
                        {
                            for(int k=0;k<offset;k++)
                            {
                                stock_data[i][1] = stock_data[i][1]+tuple[1+k];
                            }
                        }
                        else if(j>1)
                        {
                            stock_data[i][j] = tuple[j+offset];
                        }
                        
                    }
                }
                //updates bid_history
                bid_history[i][0][0] = tuple[0];
                bid_history[i][0][1] = null;
                bid_history[i][1][0] = "Original";
                bid_history[i][1][1] = price_Temp;
                i++;
            }

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        /*for(int i=0;i<no_symbols;i++)
        {
            System.out.format("%d ",i+1);
            for(int j=0;j<7;j++)
            {
                System.out.format("%s\t",stock_data[i][j]);
            }
            System.out.format("\n");
            //System.out.println(bid_history[i][0][0]);
        }*/
    }
    
    public void update(String symbol,String user,String price) 
    {
        for(int i=0;i<this.no_symbols;i++)
        {
            if(stock_data[i][0].equals(symbol)) 
            {   
                //System.out.println("price before update :" +stock_data[i][6]);
                stock_data[i][6]        = price;        //updates stock_data
                //System.out.println("price after update :" +stock_data[i][6]);
                for(int j =0;j<100;j++)                 //updates bid_history
                {
                    if(bid_history[i][j][0] == null)
                    {
                        bid_history[i][j][0] = user;
                        bid_history[i][j][1] = price;
                        //System.out.println(bid_history[i][j][0]+" : "+bid_history[i][j][1]);
                        break;
                    }

                }
                break;
            }
        }
    }
    
    public String getPrice(String symbol)
    {
        for(int i=0;i<no_symbols;i++)
        {
            if(stock_data[i][0].equals(symbol))return stock_data[i][6];
        }
        return "-1";
    }
    
    public String[][] getBidHistory(String symbol)
    {
        for(int i=0;i<no_symbols;i++)
        {
            if(bid_history[i][0][0].equals(symbol))return bid_history[i];
        }
        return null;
    }
    
    /*public static void main(String[] args)
    {
        StockDataBase database = new StockDataBase("stocks.csv");
        database.update("AAL", "MABBA", "100");
    }*/
    
}
