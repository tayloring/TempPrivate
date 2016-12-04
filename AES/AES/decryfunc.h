/*解密的各函数*/
//逆S盒函数
unsigned char InvS_Box(unsigned char n) {
	char ah;
	char m = 0xff;
	char s = n;
	t1 = (s & 0x52);
	t2 = (s & 0x29);
	t3 = (s & 0x94);
	t4 = (s & 0x4a);
	t5 = (s & 0x25);
	t6 = (s & 0x92);
	t7 = (s & 0x49);
	t8 = (s & 0xa4);
	iv1 = (iv1 + BitCount(t1));
	iv2 = (iv2 + BitCount(t2));
	iv3 = (iv3 + BitCount(t3));
	iv4 = (iv4 + BitCount(t4));
	iv5 = (iv5 + BitCount(t5));
	iv6 = (iv6 + BitCount(t6));
	iv7 = (iv7 + BitCount(t7));
	iv8 = (iv8 + BitCount(t8));

	if (iv1 % 2 == 1) {
		m = (m & 0xff);
	}
	else {
		m = (m & 0xfe);
	}

	if (iv2 % 2 == 1) { m = (m & 0xff); }
	else { m = (m & 0xfd); }
	if (iv3 % 2 == 1) { m = (m & 0xff); }
	else { m = (m & 0xfb); }
	if (iv4 % 2 == 1) { m = (m & 0xff); }
	else { m = (m & 0xf7); }
	if (iv5 % 2 == 1) { m = (m & 0xff); }
	else { m = (m & 0xef); }
	if (iv6 % 2 == 1) { m = (m & 0xff); }
	else { m = (m & 0xdf); }
	if (iv7 % 2 == 1) { m = (m & 0xff); }
	else { m = (m & 0xbf); }
	if (iv8 % 2 == 1) { m = (m & 0xff); }
	else { m = (m & 0x7f); }


	//printf("0x%x ", m);
	//break;

	for (char i = 0x0; i <= 0xf; i++) {
		for (char j = 0x0; j <= 0xf; j++) {

			char t = (i << 4) | (i >> 4);
			a[i][j] = (t + j);
			//	printf("%x ", a[i][j]);

		}
	}


	for (char h = 0x0; h <= 0xf; h++) {
		for (char k = 0x0; k <= 0xf; k++) {

			//	a[i][j]=i+j;
			if (multiply(m, a[h][k]) == 1)
			{
				  ah = a[h][k];
				
			}

		}
	}

	return ah;


	
}
//逆列变化
void InvMixCloumn(char state3[4][4]) {
	int i, j;
	char d[4][4];
	/*for (int i = 0; i < 4; i++) {
	for (int j = 0; j < 4; j++) {

	d[i][j] = state3[i][j];


	}


	}*/
	for (int h = 0; h < 4; h++) {

		for (int i = 0; i < 4; i++) {
			
			d[i][h] = (multiply(invm[i][0], state3[0][h]) ^ multiply(invm[i][1], state3[1][h]) ^ multiply(invm[i][2], state3[2][h]) ^ multiply(invm[i][3], state3[3][h]));

			

		}

	}

	for (i = 0; i < 4; i++) {
		for (j = 0; j<4; j++)
		{
			invmoment[i][j] = d[i][j];
		}
	}

}
//逆密钥扩展
void InvKeyExpansion() {
	int i, j;
	char d[4];
	printf("解密密钥扩展：\n");
	for ( i = 0; i < 44; i++) {
		invW[i] = W[i];
		printf("0x%x\n", invW[i]);

		}
	
	for (i = 4; i < 40; i++) {
		int a, b = 0xff, k, s[4];
		for (k = 0; k < 4; k++) {
			s[k] = invW[i] & b;  //将后两位十六进制数存放数组
			invW[i] /= 0xff + 1; //数字右移，去掉最低两位数
							   //bite[k] = s[k];			   //printf("0x%x\n", s[k]);
		}
		for (int h = 0; h < 4; h++) {
			d[h] = (multiply(invm[h][0], s[0]) ^ multiply(invm[h][1], s[1]) ^ multiply(invm[h][2], s[2]) ^ multiply(invm[h][3], s[3]));
		}
		
			//for (int j = 0; j < 4; j++) {
			int th1 = (d[0] << 3 * 8) | (d[0] >> 3 * 8);
			int th2 = (d[1] << 2 * 8) | (d[1] >> 2 * 8);
			int th3 = (d[2] << 1 * 8) | (d[2] >> 1 * 8);
			int v = (th1 + th2 + th3 + d[3]);
			//}
		invW[i] = v;
	}
	


}
//逆轮密钥加
void InvAddround(int state[4],int number) {
	int key[4];
	char bn[4][4];

	for (int j = 0; j <4; j++) {
		Back[j] = (state[j] ^ invW[number + j]);
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

			invmoment[i][j] = bn[i][j];

		}


	}

}
//逆S盒变化
void InvByteSub(int state[4]) {

	char c[4][4];
	//int i, j;
	char bn[4][4];//S盒变化之后的值
				  //char   bc[4][4];
				  //struct M bb;
	int a, b = 0xff, k, s[4];
	for (a = 0; a < 4; a++) {
		for (k = 0; k < 4; k++) {
			bn[a][k] = state[a] & b;  //将后两位十六进制数存放数组
			state[a] /= 0xff + 1; //数字右移，去掉最低两位数
								   //bn[a][k] = s[k];			   //printf("0x%x\n", s[k]);
		}
		for (int i = 0; i < 4; i++) {
			char temp = bn[a][i];
			invmoment[i][a] = InvS_Box(temp);

		}

	}

	/*for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			//	b[i][j] = 0xff;
			char temp = bn[j][i];
			invmoment[j][i] = InvS_Box(temp);
			//c[j][i] = invmoment[j][i];
		}

	}*/

	/*int key[4];
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

	}*/
	

}
//逆行变化
void InvShiftRow(char state2[4][4]) {
	int i, j;
	char c[4][4];

	for (i = 0; i <4; i++) {
		for (j = 0; j < 4; j++) {
			c[i][j] = state2[i][j];
		}
	}

	//第二行循环右移3字节
	for (i = 0; i<4; i++)
	{
		char f3 = c[1][4 - 3];
		for (j = 4 - 1; j > 0; j--) {
			c[1][j] = c[1][j - 1];
			c[1][0] = f3;
		}
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
	for (j = 4 - 1; j > 0; j--)
	{
		char f1 = c[3][4 - 1];
		c[3][j] = c[3][j - 1];
		c[3][0] = f1;
	}

	for (i = 0; i < 4; i++) {
		for (j = 0; j < 4; j++) {

			invmoment[i][j] = c[i][j];
		}
	}
	//	return c[4][4];

	/*int key[4];
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

	}*/

}
//解密函数
void decry() {
	int i, j;
	printf("要解密的密文：");
	for (i = 0; i < 4; i++) {
		printf("%x", Back[i]);
	}printf("\n");

	InvKeyExpansion();
	InvAddround(Back, 40);
	for (int r = 9; r > 0; r--) {
		printf("第%d轮\n", r);
		InvByteSub(Back);
		printf("S盒变化后状态：");
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++)
			{
				printf("%x", (unsigned char)invmoment[i][j]);
			}
		}
		printf("\n");
		InvShiftRow(invmoment);
		printf("行变化后状态：");
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++)
			{
				printf("%x", (unsigned char)invmoment[i][j]);
			}
		}
		printf("\n");
		InvMixCloumn(invmoment);
		printf("列变化后状态:");
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 4; j++)
			{
				printf("%x", (unsigned char)invmoment[i][j]);
			}
		}
		printf("\n");
		printf("轮密钥:");
		for (i = 0; i < 4; i++) {
			printf("%x", W[Nb*r + i]);
		}printf("\n");
		InvAddround(Back, Nb*i);
		//printf("轮加后状态：");
	}
	printf("最后一轮"); printf("\n");
	InvByteSub(Back);
	printf("S盒变化后状态：");
	for (i = 0; i < 4; i++) {
		for (j = 0; j<4; j++)
		{
			printf("%x", (unsigned char)invmoment[i][j]);
		}
	}printf("\n");
	InvShiftRow(invmoment);
	//InvMixCloumn(invmoment);
	printf("行变化后状态：");
	for (i = 0; i < 4; i++) {
		for (j = 0; j<4; j++)
		{
			printf("%x", (unsigned char)invmoment[i][j]);
		}
	}printf("\n");
	InvAddround(Back, 0); printf("\n");
	printf("得到明文：");
	for (i = 0; i < 4; i++) {
		printf("%x", (hub[i] ^ Back[i]));
	}printf("\n");
	printf("得到明文字符：");
	for (i = 0; i < 4; i++) {
		printf("%s", (hub[i] ^ Back[i]));
	}printf("\n");

} 
