#include<stdio.h>
#include<stdlib.h>
#include<malloc.h>

#define PRO 5
#define MAX_SIZE 6

typedef struct PCB
    {
      char *name;//进程名
      char *p;//指针
      int time;//运行时间
      int number;//优先数
      char *state;//状态
    }MPCB;
typedef struct PCBQUE{
    struct PCB *pcb;
   int front;//队首
  int rear;//队尾
  int maxsize;//队列最大长度

}PCBQUE;
struct PCB pcblist[PRO];//进程
struct PCB mpcb;
struct PCBQUE Q;

