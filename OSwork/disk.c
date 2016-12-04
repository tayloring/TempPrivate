#include<stdio.h>
#include<stdlib.h>

/*成组链接法分配磁盘空间模拟
   作者：龚银超         */

int A[8][3];//空闲块数，A[0]为专用块
int MA[3];//存储专用块
void Init(){
int a[4]={3,1,2,3};
int b[4]={3,4,5,6};
int c[4]={3,0,7,8};
  //初始化
for(int k=0;k<4;k++){
  A[0][k]=a[k];
  A[1][k]=b[k];
  A[4][k]=c[k];
}
 //将专用块复制到主存
   for(int k=0;k<4;k++){
   MA[k]=A[0][k];
}
printf("专用块：");
for(int i=0;i<4;i++){
printf(" %d ",A[0][i]);
}
printf("\n*****初始状态如下******\n");
 for(int i=0;i<9;i++){
  printf("\n");   
         
for(int j=0;j<=3;j++){
     printf("A[%d][%d]:%d\t",i,j,A[i][j]);
}
}


}

//用于磁盘分配
void diskdistribute(){
int h;
    printf("\n输入1继续分配，输入0退出\n");

    scanf("%d",&h);
    while(h)
    {
  
   int i,s;

if(MA[0]==1){
printf("该组只剩一块\n");
 s=MA[1];
 
for(int k=0;k<4;k++){
   A[0][k]=A[s][k];
}


 if(MA[0]<1){
  if(MA[0]==1){
 

 if(MA[1]==0){

   printf("系统无空闲块\n");

}else{


}
int e;
e=MA[0];
s=MA[e];
MA[0]=MA[0]-1;
printf("分配的块号%d",s);


} 
 
}else{
printf("该组已经分配完,\n");
//将专用块复制到主存
  //int count=0;
  // count++;
 //printf("将为你分配第%d块的空闲块\n",count);
   for(int k=0;k<4;k++){
   MA[k]=A[0][k];
}

}


}else{


i=MA[0];
s=MA[i];
MA[0]=MA[0]-1;
printf("MA[0]=%d",MA[0]);
printf("分配成功，分配的块号为%d\n",s);


}

 printf("输入1继续分配，输入0退出\n");
    scanf("%d",&h);
    }

}

//用于磁盘回收
void diskreturn(){
int i;
    printf("\n输入1继续回收，输入0退出\n");

    scanf("%d",&i);
    while(i)
    {

  printf("请输入要回收的块号：");
 int j;
  scanf("%d",&j);
if(MA[0]==3){
printf("当前组已满，新建一组\n");
for(int k=0;k<4;k++){
   A[j][k]=MA;
}
MA[0]=1;
MA[1]=j;

}else{

 int i;
 i=MA[0];
  MA[i]=j;
   MA[0]=MA[0]+1;
  printf("回收第%d块",j);


}
 printf("输入1继续回收，输入0退出\n");
    scanf("%d",&i);
    }

}


 


int main(){

 Init();

  printf("\n*******成组链接法分配磁盘空间模拟*******\n");
  printf("\n：按1分配磁盘空间\n");
  printf("\n：按2回收磁盘空间\n");
  printf("\n：按3退出程序\n");
int func=0;
scanf("%d",&func);
switch(func){
case 1:diskdistribute();
case 2:diskreturn();
case 3:return 1;

}
 
}



