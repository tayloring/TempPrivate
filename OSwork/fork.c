#include<stdio.h>
#include<stdlib.h>
int myvar=0;
extern fork();
int main(){
pid_t fpid;
fpid=fork();

 // int fpid;
 // fpid =fork();

if(fpid<0){
printf("创建失败");
exit(1);

}
else if(fpid ==0){
   printf("我是子进程B,我的ID是：%d\n",getpid());
    myvar++;

}else{
 printf("子进程B创建成功\n");
printf("我是父进程，子进程为B，我的ID是：%d\n",getpid());

   wait();
  int pid;
  pid =fork();
  if(pid<0){
printf("子进程C创建失败");
 exit(1);


}else if(pid==0){

printf("我是子进程C,我的ID是：%d\n",getpid());
  myvar++;

  

}else{
 
 printf("子进程C创建成功\n");
 printf("我是父进程A,子进程为C,我的ID是：%d\n",getpid());
 myvar++;
printf("结果%d\n",myvar);
   exit(0);

}


exit(0);
  
}



}
