#include"constdata.h"
#include"encryfunc.h"
#include"decryfunc.h"
/*
**********RIJNDAEL�����㷨��ʵ��**********

Author:������  ѧ�ţ�2014301500092  2014���ƿ�һ��

*/



int main() {


	printf("\n*************AES��ʵ��****************\n");
	printf("��Կ�����ݿ��ѡ128λ\n");
	printf("128λ��Կ�Ѹ���:0x20012101710198aeda79171460153094\n");
	printf("������Ҫ���ܵ�����(�����ĸ�Ӣ����ĸ)��\n");

	char m1[4], m2[4], m3[4], m4[4];

		State[0]=(int)gets_s(m1); State[1] = (int)gets_s(m2); State[2] = (int)gets_s(m3); State[3] = (int)gets_s(m4);
		printf("�õ�����ASCII���룺\n");
		printf("%x", m1); printf("%x", m2); printf("%x", m3); printf("%x", m4);
		printf("\n");

		printf("���м��ܣ�\n"); 
		
		encry(); printf("����������н���\n"); 
		getchar();
		decry(); 
		system("pause");

	
	
}


