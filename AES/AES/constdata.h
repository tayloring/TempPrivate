#include<stdio.h>
#include<stdlib.h>
#include<conio.h>
/*ȫ�ֱ�����һЩ����*/

#define keysize 128  // ��Կ����
#define datasize 128 //���ݿ鳤��
#define Nb 4  //���ݿ��ά��������������datasize/32
#define Nk 4  //128λ��Կ�Ķ�ά���������������keysize/32
#define Nr 10 //RIJNDAEL�㷨�ĵ�������,��Nb=4,Nk=4ʱ��Nr=10;

int  CipherKey[4] = {0x20012101,0x710198ae,0xda791714,0x60153094};  //128λ��Կ
 int State[4]={   // 0xaf010001,
		           //  0x01a198af,
		            // 0xda781734,0x486153588
 };//Ҫ���ܵ����ģ��Լ�����
 
 //�м�״̬
	int Back[4];
	char moment[4][4];
	char invmoment[4][4];
	int hub[4];
int W[Nb*(Nr + 1)];//�洢��չ��Կ
int invW[Nb*(Nr + 1)];

char t1, t2, t3, t4, t5, t6, t7, t8;
int v1 = 1, v2 = 1, v3 = 0, v4 = 0, v5 = 0, v6 = 1, v7 = 1, v8 = 0;
int iv1 = 1, iv2 = 0, iv3 = 1, iv4 = 0, iv5 = 0, iv6 = 0, iv7 = 0, iv8 = 0;
char a[16][16] = {};
//�б仯����
char m[4][4] = { 0x02,0x03,0x01,0x01,
				0x01,0x02,0x03,0x01,
				0x01,0x01,0x02,0x03,
				0x03,0x01,0x01,0x02
};
//����仯����
char invm[4][4] = { 0x0E,0x0B,0x0D,0x09,
                    0x09,0x0E,0x0B,0x0D,
	                0x0D,0x09,0x0E,0x0B,
	                0x0B,0x0D,0x09,0x0E

};
