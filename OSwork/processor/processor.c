#include"struct.h"
#include"queue.h"


//初始化
void Init(){
 
  char *proname[5]={"P1","P2","P3","P4","P5"};
   int i;
   CreateQueue(&Q);
  printf("*******进程初始化*******\n");
  for(i=0;i<5;i++)
   {pcblist[i].name=proname[i];
   printf("请输入进程%d的运行时间和优先数:\n",i+1);
   scanf("%d%d",&pcblist[i].time,&pcblist[i].number);
   pcblist[i].state="R";
  getchar();
  
 
  Insert(&Q,pcblist[i]);
   printf("%s:%d %d %s\n",pcblist[i].name,pcblist[i].time,pcblist[i].number,pcblist[i].state);
   }

}


//显示函数
void display(){

  printf("************进程的状态**************\n");
 printf("进程名	运行时间	优先数	状态\n");
 
 for (int i=0;i<PRO;i++){
 
printf("%s	   %d		 %d       %s\n",pcblist[i].name,pcblist[i].time,pcblist[i].number,pcblist[i].state);
  }
printf("\n************进程的队列**************\n");
for(int i=0;i<MAX_SIZE-1;i++){
printf("%s>>",Q.pcb[i].name);}
}
//模拟处理器调度
void run(){

display();

int currenttime=pcblist[0].time-1;
int currentnumber=pcblist[0].number-1;
MPCB currentpcb=pcblist[0];
if(currenttime>=0){
currentpcb.time=currenttime;
currentpcb.number=currentnumber;
 pcblist[0]=currentpcb;
 
for(int i=0;i<MAX_SIZE-1;i++){
Insert(&Q,pcblist[i]);
}
display();
}else{

pcblist[0].state="E";
//Delete(&Q);

}




int j;
int A[MAX_SIZE-1];
for(int i=0;i<MAX_SIZE-1;i++){
A[i]=pcblist[i].number;
}
MaxNumber(A,MAX_SIZE-1);
printf("%d\n",A[0]);
for(int i=0;i<MAX_SIZE-1;i++){
if(pcblist[i].number==A[0]){
 j=i;//printf("i是%d",i);
  MPCB temp=pcblist[0];
 pcblist[0]=pcblist[i];
 pcblist[i]=temp;
for(int i=0;i<MAX_SIZE-1;i++){
Insert(&Q,pcblist[i]);
}
display();

break;

}else{

 
}
}
}



int main(){
Init();
int i;
    printf("输入1继续执行，输入0退出\n");
    scanf("%d",&i);
    while(i)
    {
      run();
        printf("输入1继续执行，输入0退出\n");
    scanf("%d",&i);
    }




}
