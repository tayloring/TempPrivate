void control() //模拟处理器调度程序
{
	int rd;
	int num=0;

	link p=ready;
    if(ready==NULL) //若无就绪进程,结束
		return;

	while(p) //统计就绪进程个数
	{
		num++;
		p=p->next;//最终p变为NULL
	}	
	printf("\t* 就绪进程个数为:%d\n",num);
	
	time_t t; 
	srand((unsigned) time(&t));
	rd=rand()%num;//随机函数产生随机数
	if(rd==1){
		p=ready;
		ready=ready->next;
		ready->next=p;
		p->next=NULL;
		strcpy(ready->state,"Run");
		strcpy(ready->next->state,"Ready");
	}
	else 
		strcpy(ready->state,"Run");
	pc=ready->breakp;//传给insexe作为switch的参数
}
