#include<stdlib.h>
#include<stdio.h>
#include<pthread.h>
#include<semaphore.h>
#include<unistd.h>
pthread_mutex_t x,wsem;   //A mutex lock used to protect the critical section for readers,A mutex lock used to protect the critical section for writers
int readcount;  //A counter to keep track of the number of readers in the critical section.
pthread_t tid;

void *reader(void *param);
void *writer(void *param);

void initialize()
{
	pthread_mutex_init(&x,NULL);
	pthread_mutex_init(&wsem,NULL);
	readcount=0;
}
void *reader(void *param)
{
	
	int waittime;
	waittime=rand()%5;
	printf("Reader trying to enter\n");
	pthread_mutex_lock(&x);
	readcount++;
	if(readcount==1)
	pthread_mutex_lock(&wsem);
	printf("%d readers inside\n",readcount);
	pthread_mutex_unlock(&x);
	sleep(waittime);
	readcount--;
	if(readcount==0)
	pthread_mutex_unlock(&wsem);
	pthread_mutex_unlock(&x);
	printf("Reader leaving\n");

}
void *writer(void *param)
{
	int waittime;
	waittime=rand()%3;
	printf("Writer is trying to enter\n");
	pthread_mutex_lock(&wsem);
	printf("Writer inside\n");
	sleep (waittime);
	pthread_mutex_unlock(&wsem);
	printf("Writer leaving\n");
}
int main()
{
	int n1,n2,i;
	printf("\n Enter the number of readers");
	scanf("%d",&n1);
	printf("\n Enter the number of writers");
	scanf("%d",&n2);
	initialize();
	for(i=0;i<n1;i++)
	    pthread_create(&tid,NULL,reader,NULL);
	for(i=0;i<n2;i++)
	    pthread_create(&tid,NULL,writer,NULL);
	sleep(30);
	exit(0);
}





