void processor(){ //模拟处理器指令执行
	if(strcmp(ready->name,"Producer")==0) //当前进程为生产者
		switch(pc)
		{
                case 0://produce指令
			printf("\t* 生产者生产了字符%c\n",str[sp]);
			rec_p[rp1]=str[sp];//添加到生产记录
		    sp=(sp+1)%len;
			pc++;
			ready->breakp=pc; //保存断点
			break;
		case 1: //p(s1)指令
			pc++;
			p(1);
			break;
		case 2: //put指令
			buffer[in]=rec_p[rp1]; //放到缓冲区
			printf("\t* %c字符成功入驻空缓存!\n",buffer[in]);
			rp1++; 	
			in=(in+1)%BUF;
			pc++;
			ready->breakp=pc; //保存断点
			break;
		case 3: //v(s2)指令
			pc++;
			printf("\t* 释放一个s2信号\n");
			v(2);
			break;
		case 4://goto01指令  
			printf("\t* 生产进程goto 0 操作\n");
			pc=0;
			count--; //剩余字符个数减1
			printf("\t* 剩余字符count=%d个\n",count);
			ready->breakp=pc; //保存断点

			if(count<=0){ //生产结束
				printf("\t* 生产者结束生产!\n");
				strcpy(p1->state,"Stop");
				strcpy(p1->reason,"Null");
				ready->breakp=-1;
				ready=ready->next;//在就绪队列中去掉
			}
		} 
	else  //当前进程为消费者
		switch(pc)
		{
		case 0: //p(s2)
			pc++;
			p(2); 
			break;
		case 1: //get
			printf("\t* 消费者取字符!\n");
			temp=buffer[out];
			out=(out+1)%BUF;
			pc++;
			ready->breakp=pc; //保存断点
			break;
		case 2: //v(s1)
			pc++;
			printf("\t* 释放一个s1\n");
			v(1);
			break;
		case 3: //consume
			printf("\t* 消费了字符%c\n",temp);
			rec_c[rp2]=temp;//添加到消费记录
			rp2++;
			con_cnt++;
			if(con_cnt>=len){
				strcpy(c1->state,"Stop");//完成态
				c1->breakp=-1;
				return;
			}
			pc++;
			ready->breakp=pc; //保存断点
			break;
		case 4: //goto0
			printf("\t* 消费进程goto 0 操作\n");
			pc=0;
			ready->breakp=pc; //保存断点
		}
}

