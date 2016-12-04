
void p(int s){
	if(s==1){ //p(s1)
		s1--;
		if(s1<0) 
			block(1); //阻塞当前生产进程
		else{
			printf("\t* s1信号申请成功!\n");
			ready->breakp=pc; //保存断点
		}
	}
	else{ //p(s2)
		s2--;
		if(s2<0) 
			block(2);//阻塞当前消费进程
		else{
			printf("\t* s2信号申请成功!\n");
			ready->breakp=pc; //保存断点
		}
	}
}

void v(int s){
	if(s==1){ //v(s1)
		s1++;
		if(s1<=0)
			wakeup(1); //唤醒生产进程
		ready->breakp=pc; //保存断点
	}
	else{ //v(s2)
		s2++;
		if(s2<=0) 
			wakeup(2);//唤醒消费进程
		ready->breakp=pc; //保存断点
	}
}



void block(int s){//阻塞函数的定义
	link p;
	int num1=0;
	int num2=0;
	if(s==1){//生产进程
		strcpy(p1->state,"Block");//改变状态
		strcpy(p1->reason,"S1");//说明原因
		p=b_s1;
		while(p){
			num1++;
			p=p->next;//p的值为NULL,表示队尾
		}
		if(!b_s1)
			b_s1=p1;
		else
			p=p1;
		p1->next=NULL;
		printf("\t* p1生产进程阻塞了!\n");
		ready->breakp=pc; //保存断点
		ready=ready->next;//在就绪队列中去掉,指向下一个	
		num1++;
	}
	else{//消费进程
		strcpy(c1->state,"Block");
		strcpy(c1->reason,"S2");
		p=b_s2;
		while(p){
			num2++;
			p=p->next;//p的值为NULL,表示队尾
		}	
		if(!b_s2)
			b_s2=c1;
		else
			p=c1;	
		ready->breakp=pc; //保存断点
		ready=ready->next;//在就绪队列中去掉,指向下一个		
		c1->next=NULL;
		printf("\t* c1消费进程阻塞了!\n");
		num2++;
	}	
	printf("\t* 阻塞的生产进程个数为:%d\n",num1);		
	printf("\t* 阻塞的消费进程个数为:%d\n",num2);
}

void wakeup(int s){//唤醒函数的定义
	link p;
	link q=ready;
	if(s==1){ //唤醒b_s1队首进程,生产进程队列
		p=b_s1;
		b_s1=b_s1->next;//阻塞指针指向下一个阻塞进程
		strcpy(p->state,"Ready");
		strcpy(p->reason,"Null");
		while(q)//插入就绪队列
			q=q->next;
		q=p;
		p->next=NULL;
		printf("\t* p1生产进程唤醒了!\n");
	}
	else{ //唤醒b_s2队首进程,消费进程队列
		p=b_s2;
		b_s2=b_s2->next;//阻塞指针指向下一个阻塞进程
		strcpy(p->state,"Ready");
		strcpy(p->reason,"Null");
		while(q->next)//插入就绪队列
			q=q->next;
		q->next=p;
		p->next=NULL;
		printf("\t* c1消费进程唤醒了!\n");
	}
}




void print(){
	int i,j;
	printf("********生产者消费者模拟********\n");
	printf("* 模拟过程的字符串为:\t");
	printf("%s\n",&str);
	
	printf("* 已生产:");
	for(j=0;j<=rp1;j++)
		printf("%c",rec_p[j]);
	printf("\n* 空缓存:");
	for(j=rp2;j<=rp1;j++)
		printf("%c",buffer[j]);
	printf("\n* 已消费:");
	for(j=0;j<=rp2;j++)
		printf("%c",rec_c[j]);
	printf("\n———————进程控制块的信息————————\n");
	printf("进程名\t\t状态\t等待原因\t断点\n");
	printf("%s\t%s\t%s\t\t%d\n\n",p1->name,p1->state,p1->reason,p1->breakp);

	printf("%s\t%s\t%s\t\t%d\n",c1->name,c1->state,c1->reason,c1->breakp);

	printf("********************\n");
	printf("1.继续 0.退出\n");
	scanf("%d",&i);


	if(i==0){
		exit(0);
	}
}



