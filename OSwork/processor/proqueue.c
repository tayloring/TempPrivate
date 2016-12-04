#include<stdio.h>
#include<stdlib.h>
#include <malloc.h> 

#define MAX_SIZE 6
typedef int ElemType; 
typedef struct {

  ElemType *base;
  int front;//队首
  int rear;//队尾
  int maxsize;//队列最大长度


}PCBQUE;

/* 循环队列的基本操作(9个) */


void InitQueue(PCBQUE *Q)
{ /* 构造一个空队列Q */
 Q->base=malloc(MAX_SIZE*sizeof(ElemType));
 if(!Q->base) /* 存储分配失败 */
  exit(0);
 Q->front=Q->rear=0;
}
void DestroyQueue(PCBQUE *Q)
{ /* 销毁队列Q，Q不再存在 */
 if(Q->base)
  free(Q->base);
 Q->base=NULL;
 Q->front=Q->rear=0;
}
void ClearQueue(PCBQUE *Q)
{ /* 将Q清为空队列 */
 Q->front=Q->rear=0;
}
int  QueueEmpty(PCBQUE Q)
{ /* 若队列Q为空队列，则返回TRUE；否则返回FALSE */

 if(Q.front==Q.rear) /* 队列空的标志 */
 
  return 0;
 else
  return 1;
}
int QueueLength(PCBQUE Q)
{ /* 返回Q的元素个数，即队列的长度 */
 return(Q.rear-Q.front+MAX_SIZE)%MAX_SIZE;
}
int GetHead(PCBQUE Q,ElemType *e)
{ /* 若队列不空，则用e返回Q的队头元素，并返回OK；否则返回ERROR */
 if(Q.front==Q.rear) /* 队列空 */
  return 1;
 *e=Q.base[Q.front];
 return 0;
}
int EnQueue(PCBQUE *Q,ElemType e)
{ /* 插入元素e为Q的新的队尾元素 */
 if((Q->rear+1)%MAX_SIZE==Q->front) /* 队列满 */
  return 1;
 Q->base[Q->rear]=e;
 Q->rear=(Q->rear+1)%MAX_SIZE;
 return 0;
}
int  DeQueue(PCBQUE *Q,ElemType *e)
{ /* 若队列不空，则删除Q的队头元素，用e返回其值，并返回OK；否则返回ERROR */
 if(Q->front==Q->rear) /* 队列空 */
  return 1;
 *e=Q->base[Q->front];
 Q->front=(Q->front+1)%MAX_SIZE;
 return 0;
}
int QueueTraverse(PCBQUE Q,void(*vi)(ElemType))
{ /* 从队头到队尾依次对队列Q中每个元素调用函数vi() */
 int i;
 i=Q.front;
 while(i!=Q.rear)
 {
  vi(Q.base[i]);
  i=(i+1)%MAX_SIZE;
 }
 printf("\n");
}


int main(){


int i=0,j,l;
 ElemType d;

 PCBQUE Q;

InitQueue(&Q);

//printf("初始化队列后，队列空否？%u(1:空 0:否)\n",QueueEmpty(Q));

printf("请输入整型队列元素(不超过%d个),-1为提前结束符: ",MAX_SIZE-1);


for(i=0;i<MAX_SIZE-1;i++){
scanf("%d",&d);
getchar();
EnQueue(&Q,d);
}


//l=QueueLength(Q);
for(i=0;i<MAX_SIZE-1;i++)

{
printf("%d",Q.base[i]);
  


//DeQueue(&Q,&d);

//printf("删除的元素是%d,请输入待插入的元素: ",d);

//scanf("%d",&d);

//EnQueue(&Q,d);





//printf("现在队列中的元素为: \n");

//QueueTraverse(Q,visit);

//printf("共向队尾插入了%d个元素\n",i+MAX_SIZE);

//if(l-2>0)

//printf("现在由队头删除%d个元素:\n",l-2); 

}


}
