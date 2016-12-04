
unsigned char XTIME(unsigned char x) {
	return ((x << 1) ^ ((x & 0x80) ? 0x1b : 0x00));
}
//��GF��2^8���ϵĳ˷�����
unsigned char multiply(unsigned char a, unsigned char b) {
	unsigned char temp[8] = { a };
	unsigned char tempmultiply = 0x00;
	int i = 0;
	for (i = 1; i < 8; i++) {
		temp[i] = XTIME(temp[i - 1]);
	}
	tempmultiply = (b & 0x01) * a;
	for (i = 1; i <= 7; i++) {
		tempmultiply ^= (((b >> i) & 0x01) * temp[i]);
	}
	return tempmultiply;
}
int BitCount(unsigned char n)
{
	unsigned int c = 0; // ������
	while (n >0)
	{
		if ((n & 1) == 1) // ��ǰλ��1
			++c; // ��������1
		n >>= 1; // ��λ
	}
	return c;
}
//�����Ĳ���
char RC(int n) {
	int j;
	int RC[20];
	RC[0] = 0x01;
	for (j = 1; j <= n; j++) {
		RC[j] = multiply(RC[j - 1], 0x02);
	}
	int r = RC[n];
	return r;
}

//S���ӷ���仯����������Ԫ���������㣻
unsigned char S_Box(unsigned char n) {
	
	char m = 0xff;
	for (char i = 0x0; i <= 0xf; i++) {
		for (char j = 0x0; j <= 0xf; j++) {

			char t = (i << 4) | (i >> 4);
			a[i][j] = (t + j);
			//	printf("%x ", a[i][j]);

		}
	}
	for (char h = 0x0; h <= 0xf; h++) {
		for (char k = 0x0; k <= 0xf; k++) {

			
			if (multiply(n, a[h][k]) == 1)
			{
				//�õ���a[h][k]����n����Ԫ
				char s = a[h][k];
				t1 = (s & 0xf1);
				t2 = (s & 0xe3);
				t3 = (s & 0xc7);
				t4 = (s & 0x8f);
				t5 = (s & 0x1f);
				t6 = (s & 0x3e);
				t7 = (s & 0x7c);
				t8 = (s & 0xf8);
				v1 = (v1 + BitCount(t1));
				v2 = (v2 + BitCount(t2));
				v3 = (v3 + BitCount(t3));
				v4 = (v4 + BitCount(t4));
				v5 = (v5 + BitCount(t5));
				v6 = (v6 + BitCount(t6));
				v7 = (v7 + BitCount(t7));
				v8 = (v8 + BitCount(t8));
				//����˺�ģ����
				if (v1 % 2 == 1) {
					m = (m & 0xff);
				}
				else {
					m = (m & 0xfe);
				}

				if (v2 % 2 == 1) { m = (m & 0xff); }
				else { m = (m & 0xfd); }
				if (v3 % 2 == 1) { m = (m & 0xff); }
				else { m = (m & 0xfb); }
				if (v4 % 2 == 1) { m = (m & 0xff); }
				else { m = (m & 0xf7); }
				if (v5 % 2 == 1) { m = (m & 0xff); }
				else { m = (m & 0xef); }
				if (v6 % 2 == 1) { m = (m & 0xff); }
				else { m = (m & 0xdf); }
				if (v7 % 2 == 1) { m = (m & 0xff); }
				else { m = (m & 0xbf); }
				if (v8 % 2 == 1) { m = (m & 0xff); }
				else { m = (m & 0x7f); }


				//printf("0x%x ", m);
				break;
			}

		}
	}
	

	return m;
}

//��Կ��չ����
void KeyExpansion() {
	int i;
	int temp;
	int Rcon = 0x00000000;//�ּӵĳ���
	for (i = 0; i < Nk; i++) {
		W[i] = CipherKey[i];
	}
	for (i = 4; i < 44; i++) {
		temp = W[i - 1];
		if (i%Nk == 0) {
			int f = (i / Nk);
			char e = RC(f);
			int tf = (e << 3 * 8) | (e >> 3 * 8);
			char rcon = (Rcon + tf);
			int tobox = _rotl(temp, 8);//��λ����
			int a, b = 0xff, k, s[4];
			for (k = 0; k<4; k++) {
				s[k] = tobox & b;  //������λʮ���������������
				tobox /= 0xff + 1; //�������ƣ�ȥ�������λ��
								   //bite[k] = s[k];			   //printf("0x%x\n", s[k]);
			}

			int bak[4];
			for (int l = 0; l < 4; l++) {
				//printf("0x%x\n",s[l]);
				bak[l] = S_Box(s[l]);

			}

			int th1 = (bak[0] << 3 * 8) | (bak[0] >> 3 * 8);
			int th2 = (bak[1] << 2 * 8) | (bak[1] >> 2 * 8);
			int th3 = (bak[2] << 1 * 8) | (bak[2] >> 1 * 8);
			int temp2 = (th1 + th2 + th3 + bak[3]);
			temp2 = (temp2 ^ rcon);
			W[i] = (W[i - Nk] ^ temp2);


		}
		else
		{

			W[i] = (W[i - Nk] ^ temp);

		}

	}
	printf("������Կ��չ��\n");
	for (i = 0; i < 44; i++) {
		//invW[i] = W[i];
		printf("0x%x\n", W[i]);

	}

}
//����Կ�Ӻ���
void  AddRoundKey(int state[4], int number)
{

	int key[4];
	char bn[4][4];
	// int back[4];
	//int back[4];
	/*for (int i = 0; i <4; i++) {
	//for (int j = 0; j < 4; j++) {
	int th1 = (state[i][0] << 3 * 8) | (state[i][0] >> 3 * 8);
	int th2 = (state[i][1] << 2 * 8) | (state[i][1] >> 2 * 8);
	int th3 = (state[i][2] << 1 * 8) | (state[i][2] >> 1 * 8);
	key[i] = (th1 + th2 + th3 + state[i][3]);
	//}
	}*/
	for (int j = 0; j <4; j++) {
		Back[j] = (state[j] ^ W[number + j]);
		key[j] = Back[j];
	}
	int a, b = 0xff, k, s[4];
	for (a = 0; a < 4; a++) {
		for (k = 0; k < 4; k++) {
			bn[a][k] = key[a] & b;  //������λʮ���������������
			key[a] /= 0xff + 1; //�������ƣ�ȥ�������λ��
								//bn[a][k] = s[k];			   //printf("0x%x\n", s[k]);
		}
	}
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {

			moment[i][j] = bn[i][j];

		}


	}

}

//S�б仯
void ByteSub(int state1[4]) {

	char bn[4][4];//S�б仯֮���ֵ
	int a, b = 0xff, k, s[4];
	for (a = 0; a < 4; a++) {
		for (k = 0; k < 4; k++) {
			bn[a][k] = state1[a] & b;  //������λʮ���������������
			state1[a] /= 0xff + 1; //�������ƣ�ȥ�������λ��
								   //bn[a][k] = s[k];			   //printf("0x%x\n", s[k]);
		}
	}

	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			//	b[i][j] = 0xff;
			char temp = bn[j][i];
			moment[j][i] = S_Box(temp);
			bn[j][i] = moment[j][i];
		}

	}

	int key[4];
	for (int i = 0; i <4; i++) {
		//for (int j = 0; j < 4; j++) {
		int th1 = (bn[i][0] << 3 * 8) | (bn[i][0] >> 3 * 8);
		int th2 = (bn[i][1] << 2 * 8) | (bn[i][1] >> 2 * 8);
		int th3 = (bn[i][2] << 1 * 8) | (bn[i][2] >> 1 * 8);
		key[i] = (th1 + th2 + th3 + bn[i][3]);
		//}
	}
	for (int j = 0; j <4; j++) {
		Back[j] = key[j];

	}


}
void  ShiftRow(char state2[4][4]) {


	int i, j;
	char c[4][4];

	for (i = 0; i < 4; i++) {
		for (j = 0; j < 4; j++) {

			c[i][j] = state2[i][j];

		}


	}

	//�ڶ���ѭ������һ���ֽ�

	for (j = 4 - 1; j > 0; j--)
	{
		char f1 = c[1][4 - 1];
		c[1][j] = c[1][j - 1];
		c[1][0] = f1;
	}



	//������ѭ������2���ֽ�
	for (i = 0; i<2; i++)
	{
		char f2 = c[2][4 - 2];
		for (j = 4 - 1; j > 0; j--) {
			c[2][j] = c[2][j - 1];
			c[2][0] = f2;
		}
	}
	//������ѭ������3���ֽ�

	for (i = 0; i<4; i++)
	{


		char f3 = c[3][4 - 3];
		for (j = 4 - 1; j > 0; j--) {
			c[3][j] = c[3][j - 1];
			c[3][0] = f3;
		}

	}

	for (i = 0; i < 4; i++) {
		for (j = 0; j < 4; j++) {

			moment[i][j] = c[i][j];

		}


	}
	int key[4];
	for (int i = 0; i <4; i++) {
		//for (int j = 0; j < 4; j++) {
		int th1 = (c[i][0] << 3 * 8) | (c[i][0] >> 3 * 8);
		int th2 = (c[i][1] << 2 * 8) | (c[i][1] >> 2 * 8);
		int th3 = (c[i][2] << 1 * 8) | (c[i][2] >> 1 * 8);
		key[i] = (th1 + th2 + th3 + c[i][3]);
		//}
	}
	for (int j = 0; j <4; j++) {
		Back[j] = key[j];

	}

	//	return c[4][4];



}
//�л�ϱ仯����
void  MixColumn(char state3[4][4]) {
	int i, j;
	char d[4][4];

	for (int h = 0; h < 4; h++) {

		for (int i = 0; i < 4; i++) {   //�������Ȼ������0X11B

			d[i][h] = (multiply(m[i][0], state3[0][h]) ^ multiply(m[i][1], state3[1][h]) ^ multiply(m[i][2], state3[2][h]) ^ multiply(m[i][3], state3[3][h]));

		}

	}
	int key[4];
	for (int i = 0; i <4; i++) {

		int th1 = (d[i][0] << 3 * 8) | (d[i][0] >> 3 * 8);
		int th2 = (d[i][1] << 2 * 8) | (d[i][1] >> 2 * 8);
		int th3 = (d[i][2] << 1 * 8) | (d[i][2] >> 1 * 8);
		key[i] = (th1 + th2 + th3 + d[i][3]);

	}
	for (int j = 0; j <4; j++) {
		Back[j] = key[j];

	}
	for (i = 0; i < 4; i++) {
		for (j = 0; j < 4; j++) {

			moment[i][j] = d[i][j];

		}


	}



}
//���ܺ���
void encry() {

	int i, j;

	KeyExpansion();
	printf("���ĳ�ʼ״̬��0x");
	for (j = 0; j < 4; j++) {
		printf("%x", State[j]);
	}
	printf("\n");
	printf("��Կ��      0x");
	for (i = 0; i < 4; i++) {
		printf("%x", CipherKey[i]);
	}
	printf("\n");
	AddRoundKey(State, 0);
	printf("�ּӺ�״̬��  0x");
	for (i = 0; i < 4; i++)
	{
		hub[i] = Back[i];
		printf("%x", Back[i]);
	}
	printf("\n");

	//ѭ��
	for (int r = 1; r < Nr; r++) {
		printf("��%d��\n", r);
		ByteSub(Back);
		printf("S�б仯��״̬��");
		for (i = 0; i < 4; i++) {
			for (j = 0; j<4; j++)
			{
				printf("%x", (unsigned char)moment[i][j]);
			}
		}
		printf("\n");
		ShiftRow(moment);
		printf("�б仯��״̬��");
		for (i = 0; i < 4; i++) {
			for (j = 0; j<4; j++)
			{
				printf("%x", (unsigned char)moment[i][j]);
			}
		}
		printf("\n");
		MixColumn(moment);
		printf("�б仯��״̬:");
		for (i = 0; i < 4; i++) {
			for (j = 0; j<4; j++)
			{
				printf("%x", (unsigned char)moment[i][j]);
			}
		}
		printf("\n");
		printf("����Կ:");
		for (i = 0; i < 4; i++) {
			printf("%x", W[Nb*r + i]);
		}printf("\n");
		AddRoundKey(Back, Nb*r);
		printf("�ּӺ�״̬��");
		for (i = 0; i < 4; i++) {
			printf("%x", Back[i]);
		}printf("\n");
	}


	printf("���һ��"); printf("\n");
	ByteSub(Back);
	printf("S�б仯��״̬��");
	for (i = 0; i < 4; i++) {
		for (j = 0; j<4; j++)
		{
			printf("%x", (unsigned char)moment[i][j]);
		}
	}printf("\n");
	ShiftRow(moment);
	printf("�б仯��״̬��");
	for (i = 0; i < 4; i++) {
		for (j = 0; j<4; j++)
		{
			printf("%x", (unsigned char)moment[i][j]);
		}
	}printf("\n");
	MixColumn(moment);
	printf("�б仯��״̬:");
	for (i = 0; i < 4; i++) {
		for (j = 0; j<4; j++)
		{
			printf("%x", (unsigned char)moment[i][j]);
		}
	}printf("\n");
	printf("����Կ��");
	for (i = 0; i < 4; i++) {
		printf("%x", W[Nb*Nr - 1]);
	}printf("\n");
	AddRoundKey(Back, Nb*Nr - 4);
	printf("�õ����ģ�");
	for (i = 0; i < 4; i++) {
		printf("%x", Back[i]);
	}printf("\n");

}
