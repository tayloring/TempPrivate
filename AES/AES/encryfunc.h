
unsigned char XTIME(unsigned char x) {
	return ((x << 1) ^ ((x & 0x80) ? 0x1b : 0x00));
}
//域GF（2^8）上的乘法函数
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
	unsigned int c = 0; // 计数器
	while (n >0)
	{
		if ((n & 1) == 1) // 当前位是1
			++c; // 计数器加1
		n >>= 1; // 移位
	}
	return c;
}
//常数的产生
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

//S盒子仿射变化，包括求逆元，矩阵运算；
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
				//得到的a[h][k]就是n的逆元
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
				//矩阵乘和模除等
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

//密钥扩展函数
void KeyExpansion() {
	int i;
	int temp;
	int Rcon = 0x00000000;//轮加的常数
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
			int tobox = _rotl(temp, 8);//移位操作
			int a, b = 0xff, k, s[4];
			for (k = 0; k<4; k++) {
				s[k] = tobox & b;  //将后两位十六进制数存放数组
				tobox /= 0xff + 1; //数字右移，去掉最低两位数
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
	printf("加密密钥扩展：\n");
	for (i = 0; i < 44; i++) {
		//invW[i] = W[i];
		printf("0x%x\n", W[i]);

	}

}
//轮密钥加函数
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
			bn[a][k] = key[a] & b;  //将后两位十六进制数存放数组
			key[a] /= 0xff + 1; //数字右移，去掉最低两位数
								//bn[a][k] = s[k];			   //printf("0x%x\n", s[k]);
		}
	}
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {

			moment[i][j] = bn[i][j];

		}


	}

}

//S盒变化
void ByteSub(int state1[4]) {

	char bn[4][4];//S盒变化之后的值
	int a, b = 0xff, k, s[4];
	for (a = 0; a < 4; a++) {
		for (k = 0; k < 4; k++) {
			bn[a][k] = state1[a] & b;  //将后两位十六进制数存放数组
			state1[a] /= 0xff + 1; //数字右移，去掉最低两位数
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

	//第二行循环右移一个字节

	for (j = 4 - 1; j > 0; j--)
	{
		char f1 = c[1][4 - 1];
		c[1][j] = c[1][j - 1];
		c[1][0] = f1;
	}



	//第三行循环右移2个字节
	for (i = 0; i<2; i++)
	{
		char f2 = c[2][4 - 2];
		for (j = 4 - 1; j > 0; j--) {
			c[2][j] = c[2][j - 1];
			c[2][0] = f2;
		}
	}
	//第四行循环右移3个字节

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
//列混合变化函数
void  MixColumn(char state3[4][4]) {
	int i, j;
	char d[4][4];

	for (int h = 0; h < 4; h++) {

		for (int i = 0; i < 4; i++) {   //矩阵相乘然后摸除0X11B

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
//加密函数
void encry() {

	int i, j;

	KeyExpansion();
	printf("明文初始状态：0x");
	for (j = 0; j < 4; j++) {
		printf("%x", State[j]);
	}
	printf("\n");
	printf("密钥：      0x");
	for (i = 0; i < 4; i++) {
		printf("%x", CipherKey[i]);
	}
	printf("\n");
	AddRoundKey(State, 0);
	printf("轮加后状态：  0x");
	for (i = 0; i < 4; i++)
	{
		hub[i] = Back[i];
		printf("%x", Back[i]);
	}
	printf("\n");

	//循环
	for (int r = 1; r < Nr; r++) {
		printf("第%d轮\n", r);
		ByteSub(Back);
		printf("S盒变化后状态：");
		for (i = 0; i < 4; i++) {
			for (j = 0; j<4; j++)
			{
				printf("%x", (unsigned char)moment[i][j]);
			}
		}
		printf("\n");
		ShiftRow(moment);
		printf("行变化后状态：");
		for (i = 0; i < 4; i++) {
			for (j = 0; j<4; j++)
			{
				printf("%x", (unsigned char)moment[i][j]);
			}
		}
		printf("\n");
		MixColumn(moment);
		printf("列变化后状态:");
		for (i = 0; i < 4; i++) {
			for (j = 0; j<4; j++)
			{
				printf("%x", (unsigned char)moment[i][j]);
			}
		}
		printf("\n");
		printf("轮密钥:");
		for (i = 0; i < 4; i++) {
			printf("%x", W[Nb*r + i]);
		}printf("\n");
		AddRoundKey(Back, Nb*r);
		printf("轮加后状态：");
		for (i = 0; i < 4; i++) {
			printf("%x", Back[i]);
		}printf("\n");
	}


	printf("最后一轮"); printf("\n");
	ByteSub(Back);
	printf("S盒变化后状态：");
	for (i = 0; i < 4; i++) {
		for (j = 0; j<4; j++)
		{
			printf("%x", (unsigned char)moment[i][j]);
		}
	}printf("\n");
	ShiftRow(moment);
	printf("行变化后状态：");
	for (i = 0; i < 4; i++) {
		for (j = 0; j<4; j++)
		{
			printf("%x", (unsigned char)moment[i][j]);
		}
	}printf("\n");
	MixColumn(moment);
	printf("列变化后状态:");
	for (i = 0; i < 4; i++) {
		for (j = 0; j<4; j++)
		{
			printf("%x", (unsigned char)moment[i][j]);
		}
	}printf("\n");
	printf("轮密钥：");
	for (i = 0; i < 4; i++) {
		printf("%x", W[Nb*Nr - 1]);
	}printf("\n");
	AddRoundKey(Back, Nb*Nr - 4);
	printf("得到密文：");
	for (i = 0; i < 4; i++) {
		printf("%x", Back[i]);
	}printf("\n");

}
