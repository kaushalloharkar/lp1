import java.util.*;
import java.io.*;

public class pp1
{
    public static void pass2(ic[] icarr, int length, symtab[] symarr) 
    {
        System.out.println("address\topcode \top1 \top2");
        int pos = -1;
        for (int i = 0; i < length; i++) 
        {
            pos = Integer.parseInt(icarr[i].op2);
            System.out.println(icarr[i].add + " " + icarr[i].opcode + " " + icarr[i].op1 + " " + symarr[pos].addre);
        }
    }
    

    public static void main(String args[]) 
    {
        ic[] icarr = new ic[50];
        int icIndex = 0;
        symtab[] symarr = new symtab[50];
        int symIndex = 0;
        int stopFlag = 0;

        Scanner input = new Scanner(System.in);
        System.out.println("Reading the File : ");
        int line = 0;
        int lc = 0;
        
        HashMap<String, String> optab = new HashMap<>();
        HashMap<String, String> type = new HashMap<>();
        // OPCODES
        optab.put("STOP", "00");
        optab.put("ADD", "01");
        optab.put("SUB", "02");
        optab.put("MULT", "03");
        optab.put("MOVER", "04");
        optab.put("MOVEM", "05");
        optab.put("COMP", "06");
        optab.put("BC", "07");
        optab.put("DIV", "08");
        optab.put("READ", "09");
        optab.put("PRINT", "10");
        optab.put("DC", "01");
        optab.put("DS", "02");
	optab.put("LE", "02");
        optab.put("AREG", "01");
        optab.put("BREG", "02");
        optab.put("CREG", "03");
        optab.put("DREG", "04");

        // TYPE
        type.put("STOP", "IS");
        type.put("ADD", "IS");
        type.put("SUB", "IS");
        type.put("MULT", "IS");
        type.put("MOVER", "IS");
        type.put("MOVEM", "IS");
        type.put("COMP", "IS");
        type.put("BC", "IS");
        type.put("DIV", "IS");
        type.put("READ", "IS");
        type.put("PRINT", "IS");
        type.put("DS", "DL");
        type.put("DC", "DL");
        type.put("START", "AD");
        type.put("END", "AD");

        try 
        {
            File myFile = new File("input1.txt");
            Scanner myReader = new Scanner(myFile);

            while (myReader.hasNextLine())
             {
                String data = myReader.nextLine();
                String[] result = data.split(" ");
		for(int i = 0; i < result.length; i++)
		{
			System.out.print(result[i]+" --- ");
		}
		System.out.println();
                line++;

                if (line == 1) 
                {
                    lc = Integer.parseInt(result[1]);
                }

                int pos;

                if (result[0].equals("END"))
                {
                    symtab.printSymtab(symarr, symIndex);
                    ic.printIC(icarr, icIndex);
                    pass2(icarr, icIndex, symarr);
                    break;
                }

                if (stopFlag == 1) 
                {
                    if (result[1].equals("DS") || result[1].equals("DC")) 
                    {
                        pos = symtab.checkSymInSymtab(symarr, symIndex, result[0]);

                        if (pos == -1)
                         {
                            // Symbol not found, add symbol in symtab
                            symarr[symIndex] = new symtab(result[0], Integer.toString(lc));
                            pos = symIndex;
                            symIndex++;
                        } 
                        else 
                        {
                            symarr[pos].addre = Integer.toString(lc);
                        }
                        
                        icarr[icIndex] = new ic(Integer.toString(lc), type.get(result[1]), optab.get(result[1]), "0", "C", result[2]);
                        icIndex++;
                        lc ++; 
                    } 
                }

                if (stopFlag == 0)
                {
                    switch (result[0])
                    {
                        case "READ":
                        case "PRINT":
                        	 
                            pos = symtab.checkSymInSymtab(symarr, symIndex, result[1]);

                            if (pos == -1) 
                            {
                                symarr[symIndex] = new symtab(result[1], "0");
                                pos = symIndex;
                                symIndex++;
                            }
                            icarr[icIndex] = new ic(Integer.toString(lc), type.get(result[0]), optab.get(result[0]), "0", "S", Integer.toString(pos));
                            icIndex++;
                            lc++;
                            break;

                        case "MOVER":
                        case "DIV":
                        case "MOVEM":
                        case "ADD":
                        case "SUB":
                        case "MULT":
                        case "COMP":
                        case "BC":
                        	    pos = symtab.checkSymInSymtab(symarr, symIndex, result[2]);
		                    if (pos == -1)
		                    {
		                        symarr[symIndex] = new symtab(result[2], "0");
		                        pos = symIndex;
		                        symIndex++;
		                    }
		                    icarr[icIndex] = new ic(Integer.toString(lc), type.get(result[0]), optab.get(result[0]), optab.get(result[1]), "S", Integer.toString(pos));
		                    icIndex++;
		                    lc++;
		                    break;
                        
                            

                        case "STOP":
                            icarr[icIndex] = new ic(Integer.toString(lc), type.get(result[0]), optab.get(result[0]), "00", "00", "00");
                            icIndex++;
                            lc++;
                            stopFlag = 1;
                            break;
                        default:
                            // Handle other cases or report errors as needed
                    }//end switch
                }//end if stopflag==0
            }//end while
             myReader.close();
        } catch (FileNotFoundException e)
        {
            System.out.println("An error occurred with the file!");
            e.printStackTrace();
        }
        input.close();
    }
}


class ic 
{
    String add, type, opcode, op1, op2type, op2;

    ic(String lc, String types, String id, String opop1, String opop2type, String opop2) 
    {
        add = lc;
        type = types;
        opcode = id;
        op1 = opop1;
        op2type = opop2type;
        op2 = opop2;
    }

    public static void printIC(ic[] icarr, int length) 
    {
        System.out.println("address\ttype\topcode\top1\top2type\top2");
        for (int i = 0; i < length; i++)
            System.out.println(icarr[i].add + "\t" + icarr[i].type + "\t" + icarr[i].opcode + "\t" + icarr[i].op1 + "\t" + icarr[i].op2type + "\t" + icarr[i].op2);
    }
}

class symtab 
{
    String symbs;
    String addre;

    symtab(String symbols, String addr) 
    {
        symbs = symbols;
        addre = addr;
    }

    public static int checkSymInSymtab(symtab[] symarr, int length, String symb)
    {
        int pos = -1;
        for (int i = 0; i < length; i++)
        {
            if (symarr[i].symbs.equals(symb))
            {
                pos = i;
                break;
            }
        }
        return pos;
    }

    public static void printSymtab(symtab[] symarr, int length)
    {
        System.out.println("\nSymbol Address ");
        for (int i = 0; i < length; i++)
            System.out.println(symarr[i].symbs + " " + symarr[i].addre);
    }
}

