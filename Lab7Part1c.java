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
               
                    if(inLine.hasNextInt()){
                        num[i][item_code] = inLine.nextInt(); // Scanner does parsing autmatically. Ex. txt file being read.
                        if(inLine.hasNextInt()){
                            num[i][aisle_number] = inLine.nextInt();
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
                        else{valid = false;i--;}
                    }
                    else{ valid = false; i--;}
                    i++;
                    }
        }        
        else{valid = false;}
                    
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
            boolean hasQuant = false;
            boolean hasIN = false;
            boolean hasSC = false;
            boolean hasCat = false;
            boolean validLoop = true;
            int match = 0;
            String subCat = "";
            String cat = "";
            String line;
            while(inFile.hasNextLine())
            {
//            boolean hasQuant = false;
//            boolean hasIN = false;
//            boolean hasSC = false;
//            boolean hasCat = false;

                line = inFile.nextLine();
                inLine = new Scanner(line);
                if(inLine.hasNextInt()){
                    quant = inLine.nextInt();
                    hasQuant = true;
                    //System.out.println("quantity: "+quant); // Test Code
                    if(inLine.hasNext()){
                        cat = inLine.next();
                        hasCat = true;
                        if(inLine.hasNext()){
                            subCat = inLine.next();
                            hasSC = true;
                            if(inLine.hasNextLine()){
                                String itemName = inLine.nextLine();
                                hasIN = true;
                                System.out.println("The current entry on the shopping list is "+quant+" of "+itemName+" "+subCat+" "+cat+".");
                                int rowMatch = findWordInInventory(itemName,words,item_name);
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
                            else{ 
//                                System.out.println("The current entry on the shopping list is "+quant+" "+subCat+" "+cat);
//                            int rowMatch = findWordInInventory(subCat,words,subcategory);
//                  System.out.println("Row Match! in row "+rowMatch+" for ");
//                                if(rowMatch == -1){
//                                System.out.println("We're sorry, but "+subCat+" is not in the inventory currently.");
//                                }
//                                else{
//                                if(quant<=num[rowMatch][quantity])
//                                {System.out.println("You are buying the desired quanity of " +quant+" "+subCat+".");
//                                num[rowMatch][quantity] -= quant;
//                                }
//                                }
                                }
                        }
                        else{ 
                        System.out.println("The current entry on the shopping list is "+quant+" "+cat);
                        int rowMatch = findWordInInventory(cat,words,category);
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
                    }
                    else{i--; }
                }
                else{i--;}  
                
//                if(hasItemName!=true)
//                {
//                    if(hasSubCategory)
//                    {
//                        int rowMatch = findWordInInventory(subCat,words,subcategory);
//                        //De-Bug: System.out.println("ROW MATCH! "+rowMatch);
//                        if(rowMatch==-1){System.out.println("We could not find the desired "+subCat);}
//                        if(quant<=num[rowMatch][quantity])
//                        {System.out.println("You are buying the desired quantity of "+quant+" "+subCat);
//                        num[rowMatch][quantity] -= quant;
//                        }
//                        else
//                        {
//                            int ans=0;
//                            validLoop = true;
//                            int counter = rowMatch++;
//                            //for(int counter = rowMatch++;counter<words.length;counter++)
//                            for(int aa = counter;(i < words.length)&&(validLoop); aa++)
//                            {
//                                //De-bugging: System.out.println("For loop beginning");
//
//                                if(words[aa][subcategory].equalsIgnoreCase(subCat))
//                                {
//                                    //System.out.println("true");
//                                    rowMatch = aa;
//                                    validLoop = false;
//                                }
//                                else
//                                {  //System.out.println("false");
//                                    rowMatch = -1;
//                                }
//                             }
//                            if(rowMatch==-1)
//                            {
//                                System.out.println("There is not enough of the desired "+subCat);
//                            }
//                            else
//                            {
//                                System.out.println("You are buying the desired quantity of "+quant+" "+subCat);
//                                num[rowMatch][quantity] -= quant;
//                            }
//                           
//                        }
//                    }
//                    else if(hasCategory){
//                        validLoop = true;
//                        for(int n = 0; (n < words.length)&&(validLoop);n++){
//                        if(cat.equalsIgnoreCase(words[n][category])){
//                            if(quant<=num[n][quantity]) //test quant. if good then exit loop
//                            {
//                                validLoop = false;
//                                match = n;
//                            }    
//                            else //continue loop.
//                            {
//                                match = -2; 
//                            }
//                        }
//                        else{ 
//                            validLoop = true; 
//                            match=-1;
//                            
//                        }
//                            }
//                        if(match==-1)
//                        {System.out.println("The desired category of "+cat+" could not be found.");}
//                        else if (match == -2){System.out.println("Unfortunately there is not enough "+cat+".");}
//                        else{
//                            System.out.println("You are buying your desired quantity of "+quant+" "+cat+".");
//                            num[match][quantity] -= quant;
//                        }
//            if((hasQuant)&&(!hasCat)){
//                System.out.println("Error there is no names/ categories given.");}
//            else if((hasQuant)&&(hasCat)&&(!hasSC)){
//                int rowMatch = findWordInInventory(cat,words,category);
//                  //Test Code: //System.out.println("Row Match! in row "+rowMatch+" for "+itemName);
//                                if(rowMatch == -1){
//                                System.out.println("We're sorry, but "+cat+" is not in the inventory currently.");
//                                }
//                                else{
//                                if(quant<=num[rowMatch][quantity])
//                                {System.out.println("You are buying the desired quanity of " +quant+" "+cat+".");
//                                num[rowMatch][quantity] -= quant;
//                                }
//                                }
//            }
//            else if((hasQuant)&&(hasCat)&&(hasSC)&&(!hasIN)){
//                            int rowMatch = findWordInInventory(subCat,words,subcategory);
//                  //Test Code: //System.out.println("Row Match! in row "+rowMatch+" for "+itemName);
//                                if(rowMatch == -1){
//                                System.out.println("We're sorry, but "+subCat+" is not in the inventory currently.");
//                                }
//                                else{
//                                if(quant<=num[rowMatch][quantity])
//                                {System.out.println("You are buying the desired quanity of " +quant+" "+subCat+".");
//                                num[rowMatch][quantity] -= quant;
//                                }
//                                }
//            }
//            else{System.out.println("Nothing in inventory found.");}
                  
            
            
            
            
            }
                    
                }
              
                
            
            
        
        
        printTable(num);
        System.out.printf("\n");
        printWordTable(words);
        totalCost = inventoryValue(num);
        System.out.printf("\nTotal Cost: $%4.2f\n",totalCost);
    }
        
        
        
        
        
        
        
        
    public static int findWordInInventory(String Word,String[][] words,int col)
    {
      //De-bugging: System.out.println("Everything passed.");
       int ans=0;
       int count = 0;
       boolean valid = true;
      
       for(int i = 0;(i < words.length)&&(valid); i++)
       {
           
           
           if(words[i][col].equalsIgnoreCase(Word))
           {
               //System.out.println("true");
               ans = i;
               valid = false;
           }
           else
           {  
                      //System.out.println("salmon");

               ans = -1; 
               
           }
           //count++;
           //System.out.println("For loop beginning: "+i+". ans: "+ans+". valid: "+valid);
        }

       //System.out.println(ans);
       return ans;
    }

    
    public static void printTable(double[][] num)
    {
        String sp = "     ";
        System.out.printf("Item Code   |%5sAisle Number|%11s Cost |%7s Quantity |%7s\n",sp,sp,sp,sp);
        for(int i = 0; (i<num.length)&&(num[i][0]!=0);i++){
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
