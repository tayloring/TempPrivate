void init(){ //初始化生产者和消费者两个进程
	s1=BUF;
	s2=0;
	p1=(link)malloc(sizeof(Pcb));//建立新的结点,并初始化为生产者
	strcpy(p1->name,"生产者");
	strcpy(p1->state,"就绪");
	strcpy(p1->reason,"Null");
	p1->breakp=0;
	p1->next=NULL;

	c1=(link)malloc(sizeof(Pcb));//建立新的结点,并初始化为消费者
	strcpy(c1->name,"消费者");
	strcpy(c1->state,"就绪");
	strcpy(c1->reason,"Null");
	c1->breakp=0;
	c1->next=NULL;

	ready=p1;
	ready->next=c1;//初始化为生产进程在前，消费进程在后
	c1->next=NULL;
	b_s1=NULL;
	b_s2=NULL;//阻塞进程为NULL
	pc=0;
	con_cnt=0; //消费计数器
}

