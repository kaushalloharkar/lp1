import java.io.*;
import java.util.*;

public class pageReplacement
{
	Scanner sc = new Scanner(System.in);
        public static void main(String args[])throws IOException
        {
			Scanner sc = new Scanner(System.in);
			char ans;
			do{
				int choice;
				pageReplacement pg = new pageReplacement();
				System.out.println("Enter choice\n1. LRU\n2. Optimal");
				choice = sc.nextInt();
				switch(choice)
				{
						case 1: 
							pg.LRU();
							break;
						case 2:
							pg.Optimal();
							break;
						default:
							System.out.println("Please Select correct choice");
							break;
				}
				System.out.println("Do you want to Continue:(Y/N) ");
				ans = sc.next().charAt(0);
			}while(ans=='Y');	
	}
			
     static void LRU()throws IOException
     {
		BufferedReader obj=new BufferedReader(new InputStreamReader(System.in));
	       	int f,page=0,pgf=0,n;
	    	boolean flag;				//A boolean flag is set to true, indicating that a page fault has not occurred yet.
	       	int pages[]; 		
	                                               /*f is the number of page frames available in memory.
							page and pgf are used to keep track of the current page and the number of page faults.
								n represents the number of pages to be referenced.
									pages is an array to store the page numbers.*/
    	  	System.out.println("1.LRU");
     		
         	System.out.println("Enter no. of frames: ");
	     	f=Integer.parseInt(obj.readLine());
	      	int frame[]=new int[f];
			
      		for(int i=0;i<f;i++)
      		{
      			frame[i]=-1;
      		}

	       	System.out.println("Enter the no of pages ");
	      	n=Integer.parseInt(obj.readLine());
		pages=new int[n];

      		System.out.println("Enter the page no ");
      	      	for(int j=0;j<n;j++)
	     		pages[j]=Integer.parseInt(obj.readLine());   //string to integer
	       	
       		int pg=0;
       		for(pg=0;pg<n;pg++)
            	{
      			page=pages[pg];				
       			flag=true;
      			for(int j=0;j<f;j++)
     			{
       				if(page==frame[j])
       				{
       					flag=false;
      	 				break;
       				 }
       			}
			int temp,i;
       			if(flag)
        		{
        			if( frame[1]!=-1 && frame[2]!=-1 && frame[0]!=-1)
				{
					temp=pages[pg-3];
					if(temp==pages[pg-2] || temp==pages[pg-1]) 
						temp=pages[pg-4];    
					     
					for(i=0;i<f;i++)
						if(temp==frame[i])
							break;
					frame[i]=pages[pg];
				}
				else
				{
					if(frame[0]==-1)
						frame[0]=pages[pg];
					else if(frame[1]==-1)
						frame[1]=pages[pg];
					else if(frame[2]==-1)
						frame[2]=pages[pg];
				}  
			   

			       	System.out.print("frame :");
			      	for(int j=0;j<f;j++)
			       		System.out.print(frame[j]+"   ");
			    	System.out.println();
		       		pgf++;
		       	}
     		 	else
		      	{
			      	System.out.print("frame :");
			       	for(int j=0;j<f;j++)
			      		System.out.print(frame[j]+"  ");
			    	System.out.println();
		       	}
    	
      	 	}
       
        	System.out.println("Page fault:"+pgf);
       }
		
   static void Optimal()throws IOException
  {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	int numberOfFrames, numberOfPages, flag1, flag2, flag3, i, j, k, pos = 0, max;
	int faults = 0;
	int temp[] = new int[10];
				
	System.out.println("Enter number of Frames: ");
	numberOfFrames = Integer.parseInt(br.readLine());
	int frame[] = new int[numberOfFrames];
				
				
	System.out.println("Enter number of Pages: ");
	numberOfPages = Integer.parseInt(br.readLine());
				
	int pages[] = new int[numberOfPages];
	System.out.println("Enter the pages: ");
	for(i=0; i<numberOfPages; i++)
		pages[i] = Integer.parseInt(br.readLine());
				
	for(i = 0; i < numberOfFrames; i++)
		frame[i] = -1;
				
				
	for(i = 0; i < numberOfPages; ++i)
	{
		flag1 = flag2 = 0;
						
		for(j = 0; j < numberOfFrames; ++j)
		{
			if(frame[j] == pages[i])
			{
				flag1 = flag2 = 1;
				break;
			}
		}
						
		if(flag1 == 0)
		{
			for(j = 0; j < numberOfFrames; ++j)
			{
				if(frame[j] == -1)
				{
					faults++;
					frame[j] = pages[i];
					flag2 = 1;
					break;
				}
			}    
		}
						
		if(flag2 == 0)
		{
			flag3 =0;
							
			for(j = 0; j < numberOfFrames; ++j)
			{
				temp[j] = -1;
								
				for(k = i + 1; k < numberOfPages; ++k)
				{
					if(frame[j] == pages[k])
					{
						temp[j] = k;//future reference 
						break;
					}
				}
			}
							
			for(j = 0; j < numberOfFrames; ++j)
			{
				if(temp[j] == -1)
				{
				  	pos = j;
					flag3 = 1;
					break;
				}
			}
							
			if(flag3 ==0)
			{
				max = temp[0];
				pos = 0;
								
				for(j = 1; j < numberOfFrames; ++j)
				{
					if(temp[j] > max)
					{
						max = temp[j];
						pos = j;
					}
				}                
			}
							
			frame[pos] = pages[i];
			faults++;
		}
				
		for(j = 0; j < numberOfFrames; ++j)
		{
			System.out.print("\t"+ frame[j]);
							
		}
		System.out.println();
	}
					
	System.out.println("\n\nTotal Page Faults: "+ faults);

  }

}

