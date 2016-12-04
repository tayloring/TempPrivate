

int CreateQueue(PCBQUE *Q){
/* 构造一个空队列Q */
 Q->pcb=malloc(MAX_SIZE*sizeof(mpcb));
 if(!Q->pcb) /* 存储分配失败 */
  exit(0);
 Q->front=Q->rear=0;


}

int Insert(PCBQUE *Q,MPCB e){

  /* 插入元素e为Q的新的队尾元素 */
 if((Q->rear+1)%MAX_SIZE==Q->front) /* 队列满 */
  return 1;
 Q->pcb[Q->rear]=e;
 Q->rear=(Q->rear+1)%MAX_SIZE;
 return 0;


}

int Delete(PCBQUE *Q){
/* 若队列不空，则删除Q的队头元素，用e返回其值，并返回OK；否则返回ERROR */
 if(Q->front==Q->rear) /* 队列空 */
  return 1;
//*e=Q->pcb[Q->front];
 Q->front=(Q->front+1)%MAX_SIZE;
  //free(Q);
 return 0;



}
void MaxNumber(int *a,int n){

  int temp;
  int i,j;

  for(i=0;i<n-1;i++){
   for(j=0;j<n-1-i;j++){
   if(a[j]<a[j+1]){
     temp=a[j];
     a[j]=a[j+1];
     a[j+1]=temp;
     }
    }
  }


}

