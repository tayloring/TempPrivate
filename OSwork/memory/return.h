
 

 //回收内存
int Mreturn(char taskname[])
{
    struct MZone *front = NULL;
    struct MZone *position = Mhead;
    struct MZone *tail = Mhead->next;
     
    while( 0!=strcmp(position->task_name,taskname) ) 
    {
           front = position;
           if( NULL!=position->next )
           {
               position = position->next;
           }
           else
           {
               position = NULL;
               break;
           }
           tail = position->next;
    }
     
    if( NULL==position )
    {
        printf("内存中没有此任务");   
    }
    else
    {
      //cantuse是不能用
      if( NULL!=tail&&NULL!=front )
       {
     
            if( front->state&&tail->state )
            {
                front->length = front->length + position->length + tail->length;
                front->next = tail->next;
                free(position);
                free(tail);
            }
            else if( front->state&&!tail->state )
            {
                front->length = front->length + position->length;
                front->next = position->next;
                free(position);
            }
            else if( !front->state&&tail->state )
            {
                position->length = position->length + tail->length;
                memset( position->task_name, 0, sizeof(char)*32 );
                position->next = tail->next;
                position->state = CANUSE;
                free(tail);
            }
            else if( !front->state&&!tail->state )
            {
                memset( position->task_name, 0, sizeof(char)*32 );
                position->state = CANUSE;   
            }
       }
       else if( NULL!=tail&&NULL==front )
       {
         if( !tail->state )
          {
             memset( position->task_name, 0, sizeof(char)*32 );
             position->state = CANUSE;
          }
          else
          {
             position->length = position->length + tail->length;
             position->next = NULL;
             free(tail);
          }
       } 
       else if( NULL==tail&&NULL!=front )
       {
         if(front->state)
          { 
              front->length = front->length + position->length;
              front->next = NULL;
              free(position);
          }
          else
          {
              memset( position->task_name, 0, sizeof(char)*32 );
              position->state = CANUSE;
          }
       }
       else if( NULL==tail&&NULL==front )
       {
            memset( position->task_name, 0, sizeof(char)*32 );
            position->state = CANUSE; 
       }
    printf("内存回收成功\n");
   }
}
  
//回收内存
void memoreturn(void)
{
     char tname[32];
     printf("输入要回收的任务名\n");
     scanf("%s",tname);
     Mreturn(tname); 
      
} 
