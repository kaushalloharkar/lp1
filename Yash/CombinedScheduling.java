import java.util.Scanner;

public class CombinedScheduling 
{

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Select a scheduling algorithm:");
        System.out.println("1. FCFS Scheduling");
        System.out.println("2. Round Robin Scheduling");
        System.out.println("3. Priority Scheduling");
        System.out.println("4. Preemptive SJF");
        System.out.print("Enter your choice (1/2/3/4): ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                FCFS(sc);
                break;
            case 2:
                roundRobinScheduling(sc);
                break;
            case 3:
                priorityScheduling(sc);
                break;
            case 4:
            	PreemptiveSJF(sc);
            	break;
            default:
                System.out.println("Invalid choice.");
        }

        sc.close();
    }

    public static void FCFS(Scanner sc)
     {
      
		System.out.println("enter no of process: ");
		int n = sc.nextInt();
		
		int pid[] = new int[n];   // process ids
		int ar[] = new int[n];     // arrival times
		int bt[] = new int[n];     // burst or execution times
		int ct[] = new int[n];     // completion times
		int ta[] = new int[n];     // turn around times
		int wt[] = new int[n];     // waiting times 
		int temp;
		float avgwt=0,avgta=0;
 
		for(int i = 0; i < n; i++)
		{
			System.out.println("enter process " + (i+1) + " arrival time: ");
			ar[i] = sc.nextInt();
			System.out.println("enter process " + (i+1) + " brust time: ");
			bt[i] = sc.nextInt();
			pid[i] = i+1;
		}
 
		//sorting according to arrival times with bubble sort
		for(int i = 0 ; i <n; i++)
		{
			for(int  j=0;  j < n-(i+1) ; j++)
			{
				if( ar[j] > ar[j+1] )
				{
					temp = ar[j];
					ar[j] = ar[j+1];
					ar[j+1] = temp;
					temp = bt[j];
					bt[j] = bt[j+1];
					bt[j+1] = temp;
					temp = pid[j];
					pid[j] = pid[j+1];
					pid[j+1] = temp;
				}
			}
		}
		
		// finding completion times
		for(int  i = 0 ; i < n; i++)
		{
			if( i == 0)
			{	
				ct[i] = ar[i] + bt[i];
			}
			else
			{
				if( ar[i] > ct[i-1])
				{
					ct[i] = ar[i] + bt[i];
				}
				else
				{
					ct[i] = ct[i-1] + bt[i];
				}
	
			}
			ta[i] = ct[i] - ar[i] ;          // turnaround time= completion time- arrival time
			wt[i] = ta[i] - bt[i] ;          // waiting time= turnaround time- burst time
			avgwt += wt[i] ;               // total waiting time
			avgta += ta[i] ;               // total turnaround time
		}
		
		System.out.println("\npid  arrival  brust  complete turn waiting");
		for(int  i = 0 ; i< n;  i++)
		{
			System.out.println(pid[i] + "  \t " + ar[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + ta[i] + "\t"  + wt[i] ) ;
		}
		sc.close();
		System.out.println("\naverage waiting time: "+ (avgwt/n));     // printing average waiting time.
		System.out.println("average turnaround time:"+(avgta/n));    // printing average turnaround time.
    }

    public static void roundRobinScheduling(Scanner sc)
    {
        String pr[] = new String[20];
        int n, tq;
        int bt[] = new int[20];

        

        System.out.println("ROUND ROBIN");
        System.out.print("Enter total number of Processes: ");
        n = sc.nextInt();

        for (int i = 0; i < n; i++)
        {
            System.out.print("Enter processes[" + (i + 1) + "]: ");
            pr[i] = sc.next();
        }
        for (int i = 0; i < n; i++) 
        {
            System.out.print("Enter Burst Time of " + pr[i] + ": ");
            bt[i] = sc.nextInt();
        }
        
        System.out.print("Enter time quantum: ");
        tq = sc.nextInt();

        System.out.println("\nProceses\tBurst Time\tTime quantum");
        for (int i = 0; i < n; i++) 
        {
            System.out.println(pr[i] + "\t\t" + bt[i] + "\t\t" + tq);
        }

        String remainpr[] = new String[20];
        int remainbt[] = new int[20];
        int btime;
        int tempbt[] = new int[20];
        String temp[] = new String[20];
        int ttime[] = new int[20];
        int k = 0;
        tempbt[0] = 0;

        for (int i = 0; i < n; i++)
        {
            remainpr[i] = pr[i];
            remainbt[i] = bt[i];
        }

        for (int i = 0, j = n; i != j; i++) 
        {
            if (remainbt[i] <= tq)
            {
                temp[i] = remainpr[i];
                tempbt[i + 1] = tempbt[i] + remainbt[i];
            } 
            else 
            {
                btime = remainbt[i] - tq;
                temp[i] = remainpr[i];
                tempbt[i + 1] = tempbt[i] + tq;
                remainpr[j] = remainpr[i];
                remainbt[j] = btime;
                j++;
                k = j;
            }
        }

        System.out.println("\n Gantt Chart\n");
        System.out.print(" | ");
        for (int i = 0; i < k; i++) 
        {
            System.out.print(temp[i] + "  |  ");
        }
        System.out.println();
        for (int i = 0; i <= k; i++)
        {
            System.out.print(tempbt[i] + "\t");
        }

        int AT = 0;
        int tt = 0;
        int sum = 0;
        System.out.println("\n");
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < k; j++)
            {
                if (pr[i].equals(temp[j]))
                {
                    tt = tempbt[j + 1] - AT;
                }
            }
            ttime[i] = tt;
            sum = sum + tt;
            System.out.println("Turn Around Time of " + pr[i] + " is: " + tt);
        }
        System.out.println("\nAverage Turn Around time = " + (sum / n));

        int sum1 = 0;
        int wt;
        System.out.println("\n");
        for (int i = 0; i < n; i++) 
        {
            wt = ttime[i] - bt[i];
            System.out.println("Waiting time of " + pr[i] + " is: " + wt);
            sum1 = sum1 + wt;
        }
        System.out.println("\nAverage waiting time = " + (sum1 / n));

        sc.close();
    }

    public static void priorityScheduling(Scanner sc)
    {
        int n, temp;
        String t;
        String p[] = new String[10];  //stores the process names
        int pr[] = new int[10];     //stores the priorities
        int bt[] = new int[10];  //stores the burst times

        System.out.println("****************** Smaller the Number, Higher the Priority ****************");
        System.out.print("Enter the number of processes: ");
        n = sc.nextInt();

        for (int i = 0; i < n; i++) 
        {
            System.out.print("Enter the process: ");
            p[i] = sc.next();
            System.out.print("Enter the Burst Time: ");
            bt[i] = sc.nextInt();
            System.out.print("Enter the Priority of the Process: ");
            pr[i] = sc.nextInt();
        }

        System.out.println("Process\tBurst Time\tPriority");
        for (int i = 0; i < n; i++) 
        {
            System.out.println(p[i] + "\t\t" + bt[i] + "\t\t" + pr[i]);
        }

        for (int i = 0; i < n - 1; i++) 
        {
            int k = i;
            for (int j = i + 1; j < n; j++)
            {
                if (pr[k] > pr[j])
                {
                    k = j;
                }
            }
            temp = pr[i];
            pr[i] = pr[k];
            pr[k] = temp;

            temp = bt[i];
            bt[i] = bt[k];
            bt[k] = temp;

            t = p[i];
            p[i] = p[k];
            p[k] = t;
        }

        int arrival = 0;
        float cnt = 0, wt = 0;
        float avgtt, avgwt;
        int FT[] = new int[10];
        int TT[] = new int[10];
        int WT[] = new int[10];
        FT[0] = bt[0];
        TT[0] = FT[0] - arrival;

        for (int i = 1; i < n; i++)
        {
            FT[i] = FT[i - 1] + bt[i];
            TT[i] = FT[i] - arrival;
        }

        System.out.println("GANTT CHART ");
        for (int j = 0; j < n; j++)
        {
            System.out.print("  " + p[j] + "   ");
        }
        System.out.println();
        System.out.print("0");
        for (int j = 0; j < n; j++)
        {
            System.out.print("      " + FT[j]);
        }
        System.out.println();

        for (int i = 0; i < n; i++)
        {
            System.out.println("Turn Around Time of " + p[i] + " = " + TT[i] + " msec");
            cnt = cnt + TT[i];
        }
        avgtt = cnt / n;
        System.out.println("Average Turn Around Time is = " + avgtt + " msec");

        for (int i = 0; i < n; i++)
        {
            WT[i] = TT[i] - bt[i];
        }
        for (int i = 0; i < n; i++)
        {
            System.out.println("Waiting Time of " + p[i] + " = " + WT[i] + " msec");
            wt = wt + WT[i];
        }
        avgwt = wt / n;
        System.out.println("Average Waiting Time is = " + avgwt + " msec");
    }
     public static void PreemptiveSJF(Scanner sc)
     {
      
        	System.out.println("enter no of process: ");
		int n = sc.nextInt();
		int pid[] = new int[n];   // process ids
		int ar[] = new int[n];     // arrival times
		int bt[] = new int[n];     // burst or execution times
		int ct[] = new int[n];     // completion times
		int ta[] = new int[n];     // turn around times
		int wt[] = new int[n];     // waiting times 
                int f[] = new int[n];   //An array to track whether a process is finished (0 for not finished, 1 for finished)
                int k[] = new int[n];    //An array to store the original burst times, as burst times will be modified during execution.
		int tot=0 //total number of completed processes 
		int st=0; //the current time 
		float avgwt=0,avgta=0;
 
		for(int i = 0; i < n; i++)
		{
			System.out.println("enter process " + (i+1) + " arrival time: ");
			ar[i] = sc.nextInt();
			System.out.println("enter process " + (i+1) + " brust time: ");
			bt[i] = sc.nextInt();
                        k[i] = bt[i];
			pid[i] = i+1;
		}
                
                while(true)         //This begins an infinite loop that will continue until all processes are completed. The 
                {		//loop will be terminated once tot (the count of completed processes) becomes equal to n (the total number of processes).		
                       
                       int min = 99 , c =n;           //These variables min and c are used to keep track of the process with the shortest remaining burst time    among those that have arrived and are not finished. min is initially set to a high value (99), and c represents the index of the selected process.
                       
                       if(tot == n)                //This checks if all processes have been completed. If tot is equal to n, it means all processes are done, and 
                               break;		//the loop can be exited using break.
                                            
                       for(int i=0;i<n;i++)
                       {
                               if(ar[i]<=st && f[i] == 0 && bt[i]<min )     //    The process has arrived (ar[i] <= st).The process is not yet 
                               {		//finished (f[i] == 0).The burst time of the current process is shorter than the current minimum (bt[i] < min).
                                      min = bt[i];
                                      c = i;  //min is updated with the shorter burst time, and c is updated to the index of the selected process.
    		       		}
                       }
                                            
                       if (c == n)   //This block of code is executed if no process is found that can be scheduled. then incree current time
                       	     st++;
                       else         //if process is found that can be scheduled
                       {
                             bt[c]--;                       
                             st++;
                             if(bt[c] == 0)                //f the burst time of the selected process becomes zero, it means the process has completed.
                             {				// The completion time ct[c] is set to the current time st, and the process is marked as finished (f[c] = 
                                     ct[c] =st;		//1). The count of completed processes (tot) is incremented.
                                     f[c]=1;
                                     tot++;
                             }
                        }
                }
                                    
                for(int i=0;i<n;i++)
                {
                         ta[i] = ct[i] - ar[i];
                         wt[i] = ta[i] - k[i];
                         avgwt += wt[i];
                         avgta += ta[i];
                }
                               
               System.out.println("pid  arrival  burst  complete turn waiting");

	    for(int i=0;i<n;i++)
	    {
	    	System.out.println(pid[i] +"\t"+ ar[i]+"\t"+ k[i] +"\t"+ ct[i] +"\t"+ ta[i] +"\t"+ wt[i]);
	    }
	    
	    System.out.println("\naverage tat is "+ (float)(avgta/n));
	    System.out.println("average wt is "+ (float)(avgwt/n));
	    sc.close();
    }
}

