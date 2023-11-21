#include<bits/stdc++.h>             //This line includes a common C++ header file, which includes most standard C++ libraries. 
using namespace std;

void display(int blockSize[], int m, int processSize[], int n, int allocation[]) {
    cout << "\nProcess No.\tProcess Size\tBlock no.\n";
    for (int i = 0; i < n; i++) 
    {
        cout << " " << i + 1 << "\t\t" << processSize[i] << "\t\t";
        if (allocation[i] != -1)
            cout << allocation[i] + 1;
        else
            cout << "Not Allocated";
        cout << endl;
    }
    cout << "\nBlock Sizes :: ";
    for (int i = 0; i < m; i++)
        cout << " " << blockSize[i];
}

void firstFit() {
    int m, n;
    cout << "Enter the number of memory blocks: ";
    cin >> m;
    int blockSize[m];
    cout << "Enter the sizes of memory blocks: ";
    for (int i = 0; i < m; i++) 
    {
        cin >> blockSize[i];
    }

    cout << "Enter the number of processes: ";
    cin >> n;
    int processSize[n];
    cout << "Enter the sizes of processes: ";
    for (int i = 0; i < n; i++) {
        cin >> processSize[i];
    }

    int allocation[n];
    memset(allocation, -1, sizeof(allocation));      //The memset() in C++ is used to assign a specific value to the contiguous memory blocks. 
    for (int i = 0; i < n; i++)    // This outer loop iterates through each process.
    {
        for (int j = 0; j < m; j++)   //This inner loop iterates through each memory block.
        {
            if (blockSize[j] >= processSize[i])
            {
                allocation[i] = j;                                    //allocating the block number to the process
                blockSize[j] = blockSize[j] - processSize[i];          //update the new block size
                //display(blockSize, m, processSize, n, allocation);
                break;
            }
        }
    }
    	display(blockSize, m, processSize, n, allocation);
}

void bestFit() {       //Best Fit tries to find the memory block that best matches the size of the process to minimize wasted memory
    int m, n;
    cout << "Enter the number of memory blocks: ";
    cin >> m;
    int blockSize[m];
    cout << "Enter the sizes of memory blocks: ";
    for (int i = 0; i < m; i++) {
        cin >> blockSize[i];
    }

    cout << "Enter the number of processes: ";
    cin >> n;
    int processSize[n];
    cout << "Enter the sizes of processes: ";
    for (int i = 0; i < n; i++) {
        cin >> processSize[i];
    }

    int allocation[n];
    memset(allocation, -1, sizeof(allocation));
    for (int i = 0; i < n; i++)        //This loop iterates through each process.
    {
        int bestIdx = -1;          //This variable bestIdx is used to keep track of the index of the best-fit memory block for the current process.
        for (int j = 0; j < m; j++)   //his inner loop iterates through each memory block
        {
            if (blockSize[j] >= processSize[i])          //If bestIdx is not -1, it means a potential best-fit block has been found. 
            {
                if (bestIdx == -1)
                    bestIdx = j;
                else if (blockSize[j] < blockSize[bestIdx])       //This condition checks if the current block is smaller (better fit) than the previously found 
                    bestIdx = j;				//best-fit block. If it is smaller, the current block is set as the new best fit.
            }
        }
        if (bestIdx != -1) 
        {
            allocation[i] = bestIdx;                                          //If a best-fit block was found, it means that process i can be allocated to the 
            blockSize[bestIdx] = blockSize[bestIdx] - processSize[i];       //memory block at index bestIdx. The allocation array is updated accordingly.
        }
    }
    display(blockSize, m, processSize, n, allocation);
}

void worstFit() {                                         // Worst Fit tries to find the memory block with the most extra space available, making it suitable for  
    int m, n;                                             //processes that cannot fit well in smaller blocks.
    cout << "Enter the number of memory blocks: ";
    cin >> m;
    int blockSize[m];
    cout << "Enter the sizes of memory blocks: ";
    for (int i = 0; i < m; i++) {
        cin >> blockSize[i];
    }

    cout << "Enter the number of processes: ";
    cin >> n;
    int processSize[n];
    cout << "Enter the sizes of processes: ";
    for (int i = 0; i < n; i++) {
        cin >> processSize[i];
    }

    int allocation[n];
    memset(allocation, -1, sizeof(allocation));
    for (int i = 0; i < n; i++) 
    {
        int worstIdx = -1;
        for (int j = 0; j < m; j++) 
        {
            if (blockSize[j] >= processSize[i]) 
            {
                if (worstIdx == -1)
                    worstIdx = j;
                else if (blockSize[j] > blockSize[worstIdx])          //This condition checks if the current block has more available space (i.e., larger size) 
                    worstIdx = j;			//than the previously found worst-fit block. If it does, the current block is set as the new worst fit.
            }
        }
        if (worstIdx != -1) 
        {
            allocation[i] = worstIdx;
            blockSize[worstIdx] = blockSize[worstIdx] - processSize[i];
        }
    }
    display(blockSize, m, processSize, n, allocation);
}

void nextFit() {             //Next Fit is similar to First Fit, but it starts searching for an available memory block from the last allocated block, allowing it to continue the search from where it left off.
    int m, n;
    cout << "Enter the number of memory blocks: ";
    cin >> m;
    int blockSize[m];
    cout << "Enter the sizes of memory blocks: ";
    for (int i = 0; i < m; i++)
    {
        cin >> blockSize[i];
    }

    cout << "Enter the number of processes: ";
    cin >> n;
    int processSize[n];
    cout << "Enter the sizes of processes: ";
    for (int i = 0; i < n; i++) 
    {
        cin >> processSize[i];
    }

    int allocation[n];
    memset(allocation, -1, sizeof(allocation));
    int j = 0;              //j will be used to keep track of the last allocated memory block
    for (int i = 0; i < n; i++)   //This loop iterates through each process.
    {
        while (j < m) 
        {
            if (blockSize[j] >= processSize[i])
            {
                allocation[i] = j;
                blockSize[j] = blockSize[j] - processSize[i]; 
                //display(blockSize, m, processSize, n, allocation);
                break;       //exits from the while loop as soon as the process is allocated 
            }
            j = (j + 1) % m;
        }
    }
    	display(blockSize, m, processSize, n, allocation);
}

int main() {
    int choice;
    do {
        cout << "Memory Allocation Algorithms Menu:\n";
        cout << "1. First Fit\n";
        cout << "2. Best Fit\n";
        cout << "3. Worst Fit\n";
        cout << "4. Next Fit\n";
        cout << "5. Exit\n";
        cout << "Enter your choice: ";
        cin >> choice;

        switch (choice) {
            case 1:
                firstFit();
                break;
            case 2:
                bestFit();
                break;
            case 3:
                worstFit();
                break;
            case 4:
                nextFit();
                break;
            case 5:
                cout << "Exiting the program.\n";
                break;
            default:
                cout << "Invalid choice. Please try again.\n";
        }
    } while (choice != 5);

    return 0;
}

