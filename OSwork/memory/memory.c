#include"const.h"
#include"return.h"

//显示当前内存分配情况
void showmemory()
{
     struct MZone *Mpoint = Mhead;
      
     printf("内存使用情况\n");
     printf("起始地址\t长度\t状态\t任务\n"); 
      
     while( NULL!=Mpoint)
     {
         printf("%dk\t\t",Mpoint->begin_addr);
         printf("%dk\t",Mpoint->length);
         Mpoint->state?printf("空闲\t"):printf("占用\t");
         printf("%s\n",Mpoint->task_name);
         Mpoint = Mpoint->next;
     } 
     
      
}
 //分配空闲区给任务 
int Minsert(struct MZone* Mnew)
{
 
     struct MZone *Zinsert = Mhead;
     //flag用于指示是zinsert到了null，没有内存可以分配 
     int flag = 1;   
      
     while( Zinsert->length<Mnew->length || !Zinsert->state)
     {
             if( NULL!=Zinsert->next )
             {
                Zinsert = Zinsert->next;
             }
             else
             {   
                 Zinsert = Zinsert->next;
                 break;
             }
             
     }
      
     if( NULL==Zinsert ) 
     {
         return 0;
     }
     
     if( MSIZE == Zinsert->begin_addr+Mnew->length )
     {
          Zinsert->state = CANTUSE;
          strcpy(Zinsert->task_name , Mnew->task_name);
          Zinsert->next = NULL;
          return 1;
     }
     else
     {
         struct MZone *Ztail = (struct MZone *)malloc(sizeof(struct MZone));
         Zinsert->state = CANTUSE;
         strcpy(Zinsert->task_name , Mnew->task_name);
         Zinsert->length = Mnew->length;
         Zinsert->next = Ztail;
          
         memset( Ztail, 0, sizeof(char)*32 );
         Ztail->begin_addr = Zinsert->begin_addr + Mnew->length;
         Ztail->state = CANUSE;
         Ztail->length = MSIZE - Ztail->begin_addr;
         Ztail->next = NULL;
          
         return 1;
     }
}


//用于分配内存
void memoallocate(void)
{
     struct MZone *Mnew = (struct MZone*)malloc(sizeof(struct MZone));
     printf("输入要分配的内存大小(kb):\n");
     scanf("%d",&Mnew->length);
     printf("输入任务名:\n");
     scanf("%s",&Mnew->task_name);
     Minsert(Mnew)?printf("内存分配成功\n"):printf("没有符合大小的空闲分区，内存分配失败\n"); 
  
     free(Mnew);
}
 

int main(void)
{
     int func_ = 0;
      
     //初始化Mhead 
     Mhead = (struct MZone*)malloc(sizeof(struct MZone));
     Mhead->begin_addr = 0;
     Mhead->length = MSIZE;
     Mhead->state = CANUSE;
     memset(Mhead->task_name, 0, sizeof(char)*32 );
     Mhead->next = NULL;
      
     while( 1 )
     {
           printf("**************首次适应算法实现主存分配和回************\n");
           printf(":按1查看内存分配情况\n");
           printf(":按2申请分配内存\n");
           printf(":按3申请回收内存\n");
           printf(":按4退出程序\n");
           scanf("%d",&func_);
           switch( func_ )
           {
                   case 1 :showmemory();break;
                   case 2 :memoallocate();break;
                   case 3 :memoreturn();break; 
                   case 4 :return 1;
           }
          
     }       
}
