#include<stdio.h>
#include<stdlib.h>
#include<conio.h>
/*全局变量和一些常量*/

#define keysize 128  // 密钥长度
#define datasize 128 //数据块长度
#define Nb 4  //数据块二维数组列数，等于datasize/32
#define Nk 4  //128位密钥的二维数组的列数，等于keysize/32
#define Nr 10 //RIJNDAEL算法的迭代轮数,当Nb=4,Nk=4时，Nr=10;

int  CipherKey[4] = {0x20012101,0x710198ae,0xda791714,0x60153094};  //128位密钥
 int State[4]={   // 0xaf010001,
		           //  0x01a198af,
		            // 0xda781734,0x486153588
 };//要加密的明文，自己输入
 
 //中间状态
	int Back[4];
	char moment[4][4];
	char invmoment[4][4];
	int hub[4];
int W[Nb*(Nr + 1)];//存储扩展密钥
int invW[Nb*(Nr + 1)];

char t1, t2, t3, t4, t5, t6, t7, t8;
int v1 = 1, v2 = 1, v3 = 0, v4 = 0, v5 = 0, v6 = 1, v7 = 1, v8 = 0;
int iv1 = 1, iv2 = 0, iv3 = 1, iv4 = 0, iv5 = 0, iv6 = 0, iv7 = 0, iv8 = 0;
char a[16][16] = {};
//列变化矩阵
char m[4][4] = { 0x02,0x03,0x01,0x01,
				0x01,0x02,0x03,0x01,
				0x01,0x01,0x02,0x03,
				0x03,0x01,0x01,0x02
};
//列逆变化矩阵
char invm[4][4] = { 0x0E,0x0B,0x0D,0x09,
                    0x09,0x0E,0x0B,0x0D,
	                0x0D,0x09,0x0E,0x0B,
	                0x0B,0x0D,0x09,0x0E

};
