package lab5part1b;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * @author Jessica Bao 1001124828
 */
public class Lab5Part1b
{    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {   // Cost of dinner for multiple people at a restaurant
        // Max of 8 diners
        // Each diner could have a drink, an appetizer, a main dish, and a dessert
        // Data is read in from a file :  first line is number of diners, taxrate, tip percent
        // Every line afer first:  diner_number, cost, item_category, item name
        // Item categories are single words: "drink", "appetizer", "entree", "dessert"
        System.out.println("This system calculates the cost of a group meal");
        // ** 1.b) Create a constant for the maximum number of diners that the program can handle
        final int MAXDINERS = 200;
        // ** 1.c) Modify the variables below to use arrays instead of individual items
        double[] drink = new double[9]; //Java sarts from zero, but diners start from 1
        double[] appetizer = new double[9];
        double[] entree = new double[9];
        double[] dessert = new double[9];
        double[] dinerCost = new double[9];
        
        //double drink2, drink3, drink4, drink5, drink6, drink7, drink8;
        //double appetizer1, appetizer2, appetizer3, appetizer4,  appetizer5, appetizer6, appetizer7, appetizer8; 
        //double entree1, entree2, entree3, entree4, entree5, entree6, entree7, entree8;
        //double dessert1, dessert2, dessert3, dessert4, dessert5, dessert6, dessert7, dessert8;
        //double diner1, diner2, diner3, diner4, diner5, diner6, diner7, diner8;  
        
        // ** 1.d) Add variables/arrays in order to save the meal item names from the file 
       String[] drinkNames = new String[9];
       String[] appetizerNames = new String[9];
       String[] entreeNames = new String[9];
       String[] dessertNames = new String[9];
        
        //drink1 = appetizer1 = dessert1 = entree1 = 0;

        int drinkCount, appetizerCount, entreeCount, dessertCount;
        double drinkCost, appetizerCost, entreeCost, dessertCost;        
        boolean billAvailable = true;
        double pretaxBill = 0;
        double taxRate = 8.25;
        double tipPercent = 18;
        double totalBill = 0;
        int party = 1;
        int diner = 0;
        double cost = 0;
        String itemCat = "";
        String itemName = "";
        boolean validFirstLine = false;
        boolean validOtherLines = false;
        String inputLine = new String();
        Scanner inLine = new Scanner(inputLine);
        File bill = new File("Bill1.txt");
        Scanner billFile;
        try
        {   billFile = new Scanner(bill);
        }
        catch (FileNotFoundException fnf)
        {   billFile = new Scanner(System.in);
            System.out.println("No input file found ");
            billAvailable = false;
        }
        System.out.println();   
        // ** 1.e) Initialize the variables in the arrays
        // I talked to Dr.T in her office hours. She said to leave a comment here
        // because variable arrays are already initialized in Java
        drinkCount = appetizerCount = entreeCount = dessertCount = 0;
        drinkCost = appetizerCost = entreeCost = dessertCost = 0.0; 
        //diner1 = diner2 = diner3 = diner4 = 
          //      diner5 = diner6 = diner7 = diner8 = 0;

        if (billAvailable && billFile.hasNextInt())
        {   // read first line of file to get number of diners
          party = billFile.nextInt();
          if(billFile.hasNextDouble())
          {
          taxRate = billFile.nextDouble();
          if(billFile.hasNextDouble())
          {
          tipPercent = billFile.nextDouble();
          if(billFile.hasNextLine())
          {
          billFile.nextLine();
          validFirstLine = true;
    
          for(int j = 0;(billFile.hasNextInt()) && (j < MAXDINERS);j++)
          { //diner_number, cost, item_category, item name
              inputLine = billFile.nextLine();
              inLine = new Scanner(inputLine);
              if(inLine.hasNextInt())
              {
            diner = inLine.nextInt();
            if(inLine.hasNextDouble())
            {
            cost = inLine.nextDouble();
            if(inLine.hasNext())
            {
            itemCat = inLine.next();
            if(inLine.hasNextLine())
            {
            itemName = inLine.nextLine();
            validOtherLines = true;
            
            // ** 1.f) Write code to save the costs and names for the 
            // four dinner items for Diner X from the file into the 
            // cost variables and string variables 
            // for drink, dessert, entree, and appetizer            

    System.out.println("Diner "+diner+" enjoyed"+itemName+" as their "+itemCat);
            pretaxBill += cost;
            // calculate total amounts and costs for each kind of item
            if (itemCat.equalsIgnoreCase("drink"))
            {   drinkCount++;   
                drinkCost += cost;
                drink[diner] = cost;
                drinkNames[diner] = itemName;
            }
            else if (itemCat.equalsIgnoreCase("appetizer"))
            {   appetizerCount++;    
                appetizerCost += cost;
                appetizer[diner] = cost;
                appetizerNames[diner] = itemName;
            }
            else if (itemCat.equalsIgnoreCase("entree"))
            {   entreeCount++;   
                entreeCost += cost;
                entree[diner] = cost;
                entreeNames[diner] = itemName;
            }  
            else if (itemCat.equalsIgnoreCase("dessert"))
            {   dessertCount++;   
                dessertCost += cost;
                dessert[diner] = cost;
                dessertNames[diner] = itemName;
            }  
            // Find total cost for each diner
            /*
            switch (diner)
            {   case 1: diner1 += cost;  break;
                case 2: diner2 += cost;  break; 
                case 3: diner3 += cost;  break;   
                case 4: diner4 += cost;  break;  
                case 5: diner5 += cost;  break;
                case 6: diner6 += cost;  break; 
                case 7: diner7 += cost;  break;   
                case 8: diner8 += cost;  break;                     
            }
            */
          dinerCost[diner] += cost;
          
          
          }
          
          
          System.out.println();
          //System.out.println("Diner 1 had a drink costing $"+drink1+", an appetizer for $"+appetizer1+
           //       ", an entrée of $" +entree1+ ", and dessert costing $"+dessert1);
          // 
          System.out.printf("The cost of the dinner before tax is $%8.2f\n",pretaxBill);
          System.out.print("The tax rate is "+taxRate+"%");  
          System.out.println(" and the tip percent is "+tipPercent+"%");
          // The line below is replaces with the calls to calcPct
          // totalBill = pretaxBill + taxRate*pretaxBill*.01 + tipPercent/100*pretaxBill;          
          totalBill = pretaxBill + calcPct(taxRate,pretaxBill)+calcPct(tipPercent,pretaxBill);

          System.out.printf("The cost of the dinner with tax and tip is $%8.2f\n",totalBill);
           
          System.out.println();
          printCat(drinkCount,"drink",drinkCost);
          printCat(appetizerCount,"appetizer",appetizerCost);
          printCat(entreeCount,"entree",entreeCost);
          printCat(dessertCount,"dessert",dessertCost);
        }
            }
              }
              if(!validOtherLines)
                  j--;
              System.out.println("\n ***Please check the format of the order you entered in for diners.");
          }
          }
          
        System.out.println();
        System.out.printf("Total Bill : $%8.2f\n",totalBill);
        System.out.printf("Average amount per person for a party of %d is $%8.2f\n",party,totalBill/party);        
        // ** 1.g Rewrite the section below to use the arrays. This should change
        //    this from multiple lines to a loop with one line.
        // ** 1.h Replace the calculation (diner * (1+taxRate*.01+tipPercent/100)) 
        //    with ONE call to calcPct
        
        
        for(int n = 1; n <= party; n++)
        {
            printDinerCost(n,calcPct(100+taxRate + tipPercent,dinerCost[n]));
        }
            
//        if (diner1 > 0) printDinerCost(1, diner1 * (1+taxRate*.01+tipPercent/100) );
//        if (diner2 > 0) printDinerCost(2, diner2 * (1+taxRate*.01+tipPercent/100) );  
//        if (diner3 > 0) printDinerCost(3, diner3 * (1+taxRate*.01+tipPercent/100) );
//        if (diner4 > 0) printDinerCost(4, diner4 * (1+taxRate*.01+tipPercent/100) ); 
//        if (diner5 > 0) printDinerCost(5, diner5 * (1+taxRate*.01+tipPercent/100) );
//        if (diner6 > 0) printDinerCost(6, diner6 * (1+taxRate*.01+tipPercent/100) );  
//        if (diner7 > 0) printDinerCost(7, diner7 * (1+taxRate*.01+tipPercent/100) );
//        if (diner8 > 0) printDinerCost(8, diner8 * (1+taxRate*.01+tipPercent/100) ); 
        
        //1.i
        
        for(int n = 1; n <= party; n++)
        {
            System.out.println("Diner "+n+ " had a"+(drink[n]>0 ? drinkNames[n]: " water or no")+ " drink, " +(appetizer[n]>0 ? appetizerNames[n]: "no")+ " appetizer," +(entree[n]>0 ? entreeNames[n]: " no")+ " entree, and"+(dessert[n]>0 ? dessertNames[n]: " no") +" dessert.");
        }
    }
          }
        }
        if(!validFirstLine)
            System.out.println("The calculations are dependent upon the data entered in on the first line. Please enter in party #, tax, and sales tip at the top.");
    }
    public static void printCat(int count, String cat, double cost) 
    {
        System.out.printf("%d people had %ss at a cost of $%8.2f\n",count,cat,cost);
    }
    public static void printDinerCost(int count, double cost) 
    {
        System.out.printf("Diner %d had a meal with the cost of $%8.2f\n",count,cost);
    }
    /*
    // ** 1.a) calcPct takes a percentageRate and an amount and should return the value 
    that is the percentage of the entered amount;  Ex. if percentageRate is entered 
    as 3 and the amount is entered as 200, then the output of calcPct should be 6 
    because 6 is 3% of 200.
    */
    public static double calcPct(double percentageRate, double amount)
    {
        return percentageRate*.01*amount;   // Replace the 0 with the correct calculation to return 
                    // the percentageRate of the entered amount
    }
    
}
