/*
 * Read all data in shopping list and then update the inventory accordingly
*/
package lab7part1c;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Jessica Bao 1001124828
 */
public class Lab7Part1c {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Constant Nameing
        final int MAXROWS = 50;
        final int MAXCOLUMNS = 50;
        final int item_code = 0;
        final int aisle_number = 1;
        final int cost = 2;
        final int quantity = 3;
        //String array naming cols
        final int category = 0;
        final int subcategory = 1;
        final int item_name = 2;
        int i = 0; // COunter
        String Line = "";
        double totalCost = 0;
        
       File inventory = new File("Inventory.txt");
        Scanner inFile;
        Scanner inLine;
        boolean valid = true;
        boolean hasLines = true;
        double[][] num = new double[MAXROWS][4];  
        String[][] words = new String[MAXROWS][3];
      
        try{
            inFile = new Scanner(inventory);
        }
        catch(FileNotFoundException fnf){
             inFile= new Scanner(System.in);
             System.out.println("Unfortunately, we cannot find the input file.");
             valid = false;
        }
        if(valid)
            
        {   
            while(inFile.hasNextLine())
                    {
                    Line = inFile.nextLine();// Reads in a line
                    inLine = new Scanner (Line);//Scanner inLine reads from string
               
                    if((inLine.hasNextInt())){
                        int temp = inLine.nextInt();
                        if((100000 <= temp)&&(temp<1000000000)){
                        num[i][item_code] = temp;
                        
                        //System.out.println("This is temp: "+temp);
                        
                        //if((!(100000.0 <= num[i][item_code]))||(!(num[i][item_code]<1000000000.0)))
                        if((inLine.hasNextInt())){
                            int tempAN = inLine.nextInt();
                            //System.out.println("MAde it to aisle");
                            if(tempAN < 40){
                            num[i][aisle_number] = tempAN;
                            //System.out.println("Stored: "+ num[i][aisle_number]);
                            if(inLine.hasNext()){
                                words[i][category] = inLine.next();
                                if(inLine.hasNext()){
                                        words[i][subcategory]= inLine.next();
                                        if(inLine.hasNextDouble()){
                                            num[i][cost]= inLine.nextDouble();
                                            if(inLine.hasNextDouble()){
                                                num[i][quantity] = inLine.nextDouble();
                                                //if(inFile.hasNextLine){
                                                    words[i][item_name] = inLine.nextLine();
                                            }
                                            else{valid = false;i--;}
                                        }
                                        else{valid = false;i--;}
                                 }
                                else{valid = false;i--;}
                            }
                            else{valid = false;i--;}
                            }
                            else{i--;}
                        }
                        else{valid = false;i--;}
                    }
                    else{ valid = false; i--;}
                    }
                    else{i--;}
                    i++;
                    }
        }        
        
                    
        printTable(num);
        System.out.printf("\n");
        printWordTable(words);
        totalCost = inventoryValue(num);
        System.out.printf("\nTotal Cost: $%4.2f\n",totalCost);
       
        
        //PART 1C..................................................
        System.out.println("Updated inventory after looking at shopping list.");
        File shopFile = new File("shoppinglist.txt");
        try{
            inFile = new Scanner(shopFile);
            valid = true;
        }
        catch(FileNotFoundException fnf){
             inFile= new Scanner(System.in);
             System.out.println("Unfortunately, we cannot find the shopping file for update.");
             valid = false;
        }
        if(valid)
        {   
            int quant = 0;

            boolean validLoop = true;
            int match = 0, lgth=i;
            String subCat = "";
            String cat = "";
            String itemName = "";
            String line;
            while(inFile.hasNextLine())
            {
            boolean hasIN = false;
            boolean hasSC = false;
            boolean hasCat = false;

                line = inFile.nextLine();
                inLine = new Scanner(line);
                if(inLine.hasNextInt()){
                    quant = inLine.nextInt();
                    //System.out.println("quantity: "+quant); // Test Code
                    if(inLine.hasNext()){
                        cat = inLine.next();
                        hasCat = true;
                        if(inLine.hasNext()){
                            subCat = inLine.next();
                            hasSC = true;
                            if(inLine.hasNextLine()){
                                itemName = inLine.nextLine();
                                hasIN = true;
                                System.out.println("The current entry on the shopping list is "+quant+" of "+itemName+" "+subCat+" "+cat+".");

                            
                            }
                            else{ 
                                System.out.println("The current entry on the shopping list is "+quant+" "+subCat+" "+cat);
                                }
                        }
                        else{ 
                        System.out.println("The current entry on the shopping list is "+quant+" "+cat);
                        }
                    }
                    
                }
                 
                
                
                if((hasCat)&&(!hasSC)){
                int rowMatch = findWordInInventory(cat,words,category,lgth);
                  //Test Code: //System.out.println("Row Match! in row "+rowMatch+" for "+itemName);
                                if(rowMatch == -1){
                                System.out.println("We're sorry, but "+cat+" is not in the inventory currently.");
                                }
                                else{
                                if(quant<=num[rowMatch][quantity])
                                {System.out.println("You are buying the desired quanity of " +quant+" "+cat+".");
                                num[rowMatch][quantity] -= quant;
                                }
                                }
            }
                else if((hasCat)&&(hasSC)&&(!hasIN)){
                            int rowMatch = findWordInInventory(subCat,words,subcategory,lgth);
                  //Test Code: //System.out.println("Row Match! in row "+rowMatch+" for "+itemName);
                                if(rowMatch == -1){
                                System.out.println("We're sorry, but "+subCat+" is not in the inventory currently.");
                                }
                                else{
                                if(quant<=num[rowMatch][quantity])
                                {System.out.println("You are buying the desired quanity of " +quant+" "+subCat+".");
                                num[rowMatch][quantity] -= quant;
                                }
                                }
            }
                else if ((hasCat)&&(hasSC)&&(hasIN))
            {

                               int rowMatch = findWordInInventory(itemName,words,item_name,lgth);
                  //Test Code: //System.out.println("Row Match! in row "+rowMatch+" for "+itemName);
                                if(rowMatch == -1){
                                System.out.println("We're sorry, but "+itemName+" is not in the inventory currently.");
                                }
                                else{
                                if(quant<=num[rowMatch][quantity])
                                {System.out.println("You are buying the desired quanity of " +quant+" "+itemName+".");
                                num[rowMatch][quantity] -= quant;
                                }
                                else{System.out.println("There is not enough of the desired item of "+itemName+".");}
                                }
            }
                                  
             else {System.out.println("Nothing in inventory found.");}
            
            
            }
                    
                }

        printTable(num);
        System.out.printf("\n");
        printWordTable(words);
        totalCost = inventoryValue(num);
        System.out.printf("\nTotal Cost: $%4.2f\n",totalCost);
    }
        

        
    public static int findWordInInventory(String Word,String[][] words,int col, int lgth)
    {
      //De-bugging: System.out.println("Everything passed.");
       int ans=-1;
      
      
       for(int i = 0;(i < lgth); i++)
       {

           if(words[i][col].equalsIgnoreCase(Word))
           {
               //System.out.println("true");
               ans = i;
               
           }
        }

       //System.out.println(ans);
       return ans;
    }

    
    public static void printTable(double[][] num)
    {
        String sp = "     ";
        System.out.printf("Item Code   |%5sAisle Number|%11s Cost |%7s Quantity |%7s\n",sp,sp,sp,sp);
        for(int i = 0; (i<num.length)&&(num[i][3]!=0);i++){
            for(int j = 0;j<num[1].length;j++)
            {
                System.out.printf("%11.2f |%5s",num[i][j],sp );
                
            }
        System.out.println();
        }
    }
    public static void printWordTable(String[][] words)
    {
        String sp = "     ";
        System.out.printf("Category     %5s|Subcategory %2s| Item Name\n",sp,sp);
        for(int i = 0;( i<words.length)&&(words[i][0]!=null);i++){
            for(int j = 0;j<words[1].length;j++)
            {
                System.out.printf("|%11s %5s",words[i][j],sp );
                
            }
        System.out.println();
        }
    }
    public static double inventoryValue(double[][] num)
    {
        double totalCost = 0;
        for(int i =0;i<num.length;i++){
        totalCost += (num[i][2]*num[i][3]);
        }
        return totalCost;
        
    }
}
