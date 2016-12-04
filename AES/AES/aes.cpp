#include"constdata.h"
#include"encryfunc.h"
#include"decryfunc.h"
/*
**********RIJNDAEL加密算法的实现**********

Author:龚银超  学号：2014301500092  2014级计科一班

*/



int main() {


	printf("\n*************AES的实现****************\n");
	printf("密钥和数据块均选128位\n");
	printf("128位密钥已给定:0x20012101710198aeda79171460153094\n");
	printf("请输入要加密的明文(输入四个英文字母)：\n");

	char m1[4], m2[4], m3[4], m4[4];

		State[0]=(int)gets_s(m1); State[1] = (int)gets_s(m2); State[2] = (int)gets_s(m3); State[3] = (int)gets_s(m4);
		printf("得到如下ASCII编码：\n");
		printf("%x", m1); printf("%x", m2); printf("%x", m3); printf("%x", m4);
		printf("\n");

		printf("进行加密：\n"); 
		
		encry(); printf("输入任意进行解密\n"); 
		getchar();
		decry(); 
		system("pause");

	
	
}


