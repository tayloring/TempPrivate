#include <stdio.h>
#include <stdlib.h>
#include <string.h> 
const int CANUSE = 1;
const int CANTUSE = 0;
const int MSIZE = 128;
 
 
//内存分配
struct MZone
{
  int begin_addr; //空闲区起始地址
       //一个连续空闲区的长度
    int length;       
       //状态
    int state;
       //内存中的任务名
    char task_name[32];    
       //指向下一个空闲分区
    struct MZone *next;    
};
 
//内存头指针
struct MZone *Mhead = NULL;
 
