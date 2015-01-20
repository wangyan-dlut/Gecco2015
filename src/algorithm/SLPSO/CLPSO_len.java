package algorithm.SLPSO;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
public class CLPSO_len {
	void Run(int[][] len,int N,int iter,int show) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
		System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	//��ʼ��
		int  M=20;//����Ⱥ�Ĵ�С����������������ͬʱ������Ⱦɫ��ĸ���
		edge[ ][ ] E=new edge[N+1][N-1];//j,e
		edge[ ][ ][ ] X=new edge[M][N+1][2];//i,j,e 
		edge[ ][ ][ ] V=new edge[M][N+1][N-1];//i,j,e
		edge[ ][ ][ ] pbest=new edge[M][N+1][2];//i,j,e
		ParameterInitialization(len,E,X,V,pbest,M,N);//��ʼ��
		showlen(N,M,pbest,len);
		
		int showIteration=show;//ÿ�����ٴ��鿴һ��
		Random random=new Random();
		
		int iteration=iter;//��������
		int rg=0;
		double w=0.9;
		double c=2.0;
		int[][] Fij=new int[M][N+1];
		
		System.out.print("����Ⱥ��С��"+M+"������������"+iteration+"\n");
		int iteration_num=iteration;
		int keep=0;
		int iskeep=1;
		int i,j;
		while(iteration_num-->0 && keep<10000)
		{
			for(i=0;i<M;i++)
			{
				
				//��Vi���Ȱѽ������tmpV��
				edge[][] tmpV=new edge[N+1][N-1];
				for(j=1;j<N+1;j++) //ѭ������ÿ���ڵ���ٶ�Vij
				{
					if(rg==0)//ÿ��rg������һ��Fi(j)��ÿ��Ⱦɫ���ÿ���ڵ㶼��Ҫһ��Fi(j)
						 Fij[i][j]=selectFij(i,M,N,pbest,len);
					
					//ÿ���������10���ڵ���ٶ�
//					int cut=random.nextInt(N);
//					if(cut>=N/10)
//					{
//						for(int jj=0;jj<N-1;jj++)
//						{
//							tmpV[j][jj]=new edge();
//							tmpV[j][jj].copy(V[i][j][jj]);
//						}
//						continue;
//					}
						
					tmpV[j]=multiplyWV(w,V[i][j],N);//	wv
					edge[] tmpx=subtractPbestX(c,pbest[ Fij[i][j] ][j],X[i][j]);//	cr(pbest-x)
					addVX(tmpV[j],N,tmpx);//		wv+cr(pbest-x)
				}
				adjustPe(tmpV,N);
				copyEdge(V[i],tmpV,N);
				//showV(N,V[i]);
				
				//Vi������ϣ�����������Xi
				//showX(N,X[i],len);
				PositingUpdating(X[i],V[i],N,E,len);
			//	showX(N,X[i],len);
			}
			
			long endTime=System.currentTimeMillis(); //��ȡ����ʱ��


			//һ�ε�����ɺ󣬸���pbest
			for(i=0;i<M;i++)
			{
				if(length(len,pbest[i],N)>length(len,X[i],N))
				{
					copyPbest(pbest[i],X[i],N);
				}
			}
			if((iteration-iteration_num)%showIteration==0)
			{
				System.out.print("��ʼ��"+(iteration-iteration_num)+"�ε���\n");
				showbestlen(N,M,pbest,len,iskeep);
			}
			if(iskeep==0)
			{keep=0;}
			else
			{keep++;}
			iskeep=1;
			
			rg=(rg+1)%7;
			w=w-0.5/(iteration*1.000);
			System.gc();
		}
		i=showlen(N,M,pbest,len);
		showX(N,pbest[i]);

		System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}
	 void showlength(int N,int[][] len)
	    {
	    	int i,j;
	    	for(i=1;i<N+1;i++)
	    	{
	    		System.out.print(i);
	    		for(j=1;j<N+1;j++)
	    		{
	    			System.out.print(" "+len[i][j]);
	    		}
	    		System.out.print("\n");
	    	}
	    }
	    void showX(int N,edge[][] X,int[][] len)
	    {
	    	int i,j;
			for(i=1;i<N+1;i++)
			{
				for(j=0;j<2;j++)
				{
					System.out.print("("+X[i][j].start+","+X[i][j].end+")  "+len[X[i][j].start][X[i][j].end]+"");
				}
				System.out.print("\n");
			}
	    }
	    void showX(int N,edge[][] X)
	    {
	    	int j,k;
	    	int[][] showx=new int [N+1][N+1];
	    	for( j=1;j<N+1;j++)
			{
	    		for(k=0;k<2;k++)
	    		{
	    			showx[X[j][k].start][X[j][k].end]=1;
	    		}
			}
	    	for(j=1;j<N+1;j++)
	    	{
	    		System.out.print(""+j);
	    		for(k=1;k<N+1;k++)
	    		{
	    			System.out.print("  "+showx[j][k] );
	    		}
	    		System.out.print("\n");
	    	}
			System.out.print("\n");
	    }
	    void showV(int N,edge[][] V)
	    {
	    	int j,k;
	    	for(j=1;j<N+1;j++)
	    	{
	    		for(k=0;k<N-1;k++)
	    			System.out.print("("+V[j][k].start+","+V[j][k].end+")/"+V[j][k].pe+"  ");
	    		System.out.print("\n");
	    	}
	    }
	    void showV(int N,edge[] V)
	    {
	    	int k;
	    	for(k=0;k<N-1;k++)
				System.out.print("("+V[k].start+","+V[k].end+")/"+V[k].pe+"  ");
			System.out.print("\n");
	    }
	    int showlen(int N,int M,edge[][][] X,int[][] len)
	    {
	    	int min=999999;
	    	int i;
	    	int besti=-1;
	    	for(i=0;i<M;i++)
	    	{
	    		//System.out.print(""+length(len,X[i],N)+"\n");
	    		int tmp=length(len,X[i],N);
	    		if(tmp<min)
	    		{
	    			min=tmp;
	    			besti=i;
	    		}
	    	}
	    	System.out.print(""+min+"\n");
	    	return besti;
	    }
	    int showbestlen(int N,int M,edge[][][] X,int[][] len,int iskeep)
	    {
	    	int min=999999;
	    	int i;
	    	int besti=-1;
	    	for(i=0;i<M;i++)
	    	{
	    		//System.out.print(""+length(len,X[i],N)+"\n");
	    		int tmp=length(len,X[i],N);
	    		if(tmp<min)
	    		{
	    			min=tmp;
	    			besti=i;
	    			iskeep=0;
	    		}
	    	}
	    	System.out.print(""+min+"\n");
	    	return besti;
	    }
	    //�����ʼ���ķ���
	 	void ParameterInitialization(int[][] len,edge[][] E,edge[][][] X,edge[][][] V,edge[][][] pbest, int M,int N)
		{
			int i,j,k,ii;
			Random random=new Random();
			for(i=1;i<N+1;i++)
				for(j=0;j<N-1;j++)
					E[i][j]=new edge();
			for(k=0;k<M;k++)
				for(i=1;i<N+1;i++)
					for(j=0;j<N-1;j++)
						V[k][i][j]=new edge();
			for(k=0;k<M;k++)
				for(i=1;i<N+1;i++)
					for(j=0;j<2;j++)
				{
					X[k][i][j]=new edge();
					pbest[k][i][j]=new edge();
				}
			//��ʼ��E
			for(i=1;i<N+1;i++)
			{
				ii=1;
				for(j=0;j<N-1;j++)
				{
					if(i==ii)
						ii++;
					E[i][j].start=i;
					E[i][j].end=ii;
					E[i][j].pe=1.0;
					E[i][j].statue=true;
					ii++;
				}
			}
			System.out.print("E��ʼ�����\n");
			//��ʼ��V
			for(k=0;k<M;k++)
			{
				for(i=1;i<N+1;i++)
				{
					ii=1;
					for(j=0;j<N-1;j++)
					{
						if(i==ii)
							ii++;
						V[k][i][j].start=i;
						V[k][i][j].end=ii;
						V[k][i][j].pe=random.nextInt(100)/100.0000;
						if(V[k][i][j].pe<0.001)
						{
							V[k][i][j].statue=false;
							V[k][i][j].pe=0;
						}
						else
							V[k][i][j].statue=true;
						ii++;
					}
				}
			}
			for(k=0;k<M;k++)
			{
				//System.out.print("������"+k+"�Ľ�ı�\n");
				adjustPe(V[k],N);
			}
			
			System.out.print("V��ʼ�����\n");
			//��ʼ��X
			for(k=0;k<M;k++)
			{
				int[] tmp=new int[N+1];//�����жϽ����û�б����ʵ�
				for(i=1;i<N+1;i++)
					tmp[i]=0;
				int tmpSum;
				
				int TTstart=random.nextInt(N)+1;//��һ������Ľڵ㿪ʼ�������
				int Tstart=TTstart;
				tmp[TTstart]=1;
				int Tend=random.nextInt(N)+1;
				while(tmp[Tend]==1)//�����һ���ڵ��Ƿ��ʹ������������
				{
					Tend=random.nextInt(N)+1;
				}
				tmp[Tend]=1;
				
				X[k][TTstart][1].start=TTstart; //����TTstart�ĳ�����
				X[k][TTstart][1].end=Tend;
				
			//	System.out.print(""+Tstart+" "+Tend+" \n");
				for(;;)
				{
					//System.out.print(""+Tend+"\n");
					X[k][Tend][0].start=Tend;//����Tend�ĵ����
					X[k][Tend][0].end=Tstart;
					
					//�ж����еĽڵ��Ƿ񶼱����ʵ�������ǣ�������ѭ��
					tmpSum=0;
					for(i=1;i<N+1;i++)
					{
						tmpSum+=tmp[i];
					}
					if(tmpSum==N)
						break;
					
					Tstart=Tend; //��ʼ������Ϊ��ǰ�ڵ�
					Tend=random.nextInt(N)+1; //Ѱ����һ���ڵ�
					while(tmp[Tend]==1)
					{
						Tend=random.nextInt(N)+1;
					}
					tmp[Tend]=1;
					
					X[k][Tstart][1].start=Tstart; //����Tstart�ĳ�����
					X[k][Tstart][1].end=Tend;
					//System.out.print(""+Tstart+" "+Tend+" \n");
				}
				//�����еĽڵ㶼���ҵ�ʱ��Ĭ�����һ���ҵ��Ľڵ�͵�һ�������������
				X[k][Tend][1].start=Tend;
				X[k][Tend][1].end=TTstart;
				X[k][TTstart][0].start=TTstart;
				X[k][TTstart][0].end=Tend;
			}
			System.out.print("X��ʼ�����\n");
			//��ʼ��pbest �����X��ͬ
			for(k=0;k<M;k++)
			{
				for(i=1;i<N+1;i++)
				{
					pbest[k][i][0].copy(X[k][i][0]);
					pbest[k][i][1].copy(X[k][i][1]);
				}		
			}
			System.out.print("pbest��ʼ�����\n");
		}
		//����V��������ͬ�ߵ�Pe
		void EqualPe(edge JK,int N,edge[][] V)
		{
//			for(int i=1;i<N+1;i++)
//			{
//				for(int j=0;j<N-1;j++)
//				{
//					if(V[i][j].start==JK.end && V[i][j].end==JK.start)
//					{
//						if(JK.pe>V[i][j].pe)
//							V[i][j].pe=JK.pe;
//						else
//							JK.pe=V[i][j].pe;
//					}
//				}
//			}
			int j=JK.end;
			int k=JK.start-1;
			if(k>=j)
				k--;
			if(V[j][k].pe>JK.pe)
			{
				JK.pe=V[j][k].pe;
			}
			else
			{
				V[j][k].pe=JK.pe;
			}
		}
		void adjustPe(edge[][] V,int N)
		{
			int j,k;
			for(j=1;j<N+1;j++)
			{
				for(k=0;k<N-1;k++ )
				{
					EqualPe(V[j][k],N,V);
					
					if(V[j][k].pe<=0.001)
					{
						V[j][k].pe=0;
						V[j][k].statue=false;
					}
					if(V[j][k].pe>0.001)
					{
						V[j][k].statue=true;
					}
				}
			}
		}
	    void copyEdge(edge[][] V1,edge[][] V2,int N)
	    {
	    	int j,k;
	    	for(j=1;j<N+1;j++)
	    		for(k=0;k<N-1;k++)
	    			V1[j][k].copy(V2[j][k]);
	    }
	    void  copyPbest(edge[][] pbest,edge[][]X,int N)
	    {
	    	int j,k;
	    	for(j=1;j<N+1;j++)
	    	{
	    		for(k=0;k<2;k++)
	    			pbest[j][k].copy(X[j][k]);
	    	}
	    }
		edge[] multiplyWV(double w,edge[] V,int N)//����wv
		{
			int i;
			edge[] tmpV=new edge[N-1];
			for(i=0;i<N-1;i++)
			{
				tmpV[i]=new edge();
				tmpV[i].copy(V[i]);
				tmpV[i].pe=w*tmpV[i].pe;
				if(tmpV[i].pe>1)
					tmpV[i].pe=1;
			}
			return tmpV;
		}
		edge[] subtractPbestX(double c,edge[] pbest,edge[] X)//��cr(pbest-x)
		{
			edge[] tmpx=new edge[2];
			Random random=new Random();
			double r;
			r=(random.nextInt(99)+1)/100.00;
			int i;
			for(i=0;i<2;i++)
			{
				tmpx[i]=new edge();
				tmpx[i].copy(pbest[i]);
				if(tmpx[i].equals(X[0]) || tmpx[i].equals(X[1]))
				{
					tmpx[i].pe=0;
					tmpx[i].statue=false;//����ʦ������a������b�ıߴ�a��ɾ�������������ɾ�������ǰ�pe��0��statue��false
				}
				tmpx[i].pe=c*r;
				if(tmpx[i].pe>1)
					tmpx[i].pe=1;
			}
			random=null;
			return tmpx;
		}
		void  addVX(edge[] V,int N,edge[] X)//��V+X
		{
//			for(int i=0;i<N-1;i++)//��VΪ�����ҵ�v�к�x��ͬ�������ߣ�Ȼ������������ߵ�pe
//			{
//				if(V[i].equals(X[0]))
//				{
////					if(V[i].pe>X[0].pe && V[i].pe!=0)
////						X[0].pe=V[i].pe;
////					else if(V[i].pe<X[0].pe && X[0].pe!=0)
////						V[i].pe=X[0].pe;
//				}
				int i=X[0].end;
				if(X[0].end>X[0].start)
					i-=2;
				else
					i-=1;
				if(V[i].pe<X[0].pe)
					V[i].pe=X[0].pe;
				
				 i=X[1].end;
				if(X[1].end>X[1].start)
					i-=2;
				else
					i-=1;
				if(V[i].pe<X[1].pe)
					V[i].pe=X[1].pe;
				
//			}
		}

		int selectFij(int i,int M,int N,edge[][][] pbest,int[][] len)//��Fi(j)
		{
			int select;
			double tmp=Math.exp(10*(i-1+1)/(M-1))-1;
			tmp=tmp/(Math.exp(10)-1);
			double pc=0.05+0.45*tmp;
			Random random=new Random();
			double ran=(random.nextInt(99)+1)/100.000;
			//System.out.print(" ran:"+ran+" pc:"+pc+"\n");
			if(ran>pc)
				 select=i;
			else
			{
				//������������i������ͬ��i1��i2
				int i1=random.nextInt(M);
				while(i1==i)
					i1=random.nextInt(M);
				int i2=random.nextInt(M);
				while(i2==i1 || i2==i)
					i2=random.nextInt(M);
				
				if(length(len,pbest[i1],N)>length(len,pbest[i2],N))
					select=i1;
				else
					select=i2;
				//System.out.print(" i1:"+length(len,pbest[i1],N)+" i2:"+length(len,pbest[i2],N)+"\n");
			}
			random=null;
			return select;
		}
		int length(int[][] len,edge[][] X,int N)//����һ��·���ĳ���
		{
			int length=0;
			int i,j;
			for(i=1;i<N+1;i++)
				for(j=0;j<2;j++)
			{
				length+=len[X[i][j].start][X[i][j].end];
			}
			return length/2;
		}
		void PositingUpdating(edge[][] X,edge[][] V,int N,edge[][] E,int[][] len)//����Xi
		{
			Random random=new Random();
			double alpha=(random.nextInt(98)+1)/100.00;   //����һ�������alpha
			edge[][] cutV=new edge[N+1][N-1];
			int j,k;
			for(j=1;j<N+1;j++) // ��alpha����V���õ����˺��V��cutV
			{
				for(k=0;k<N-1;k++)
				{
					cutV[j][k]=new edge();
					cutV[j][k].copy(V[j][k]);
					if(alpha>cutV[j][k].pe)
					{
						cutV[j][k].pe=0;
						cutV[j][k].statue=false;
					}
				}
			}
			//����NewX�����ڼ�¼���º��λ��
			edge[][] NewX=new edge[N+1][2];
			for(j=1;j<N+1;j++)
				for(k=0;k<2;k++)
					NewX[j][k]=new edge();
			
			//������һ��·���׵ķ�������
			int[] tmp=new int[N+1];//�����жϽ����û�б����ʵ�
			for(k=1;k<N+1;k++)
				tmp[k]=0;
			int tmpSum;
			
			int TTstart=random.nextInt(N)+1;//��һ������Ľڵ㿪ʼ����
			int Tstart=TTstart;
			tmp[TTstart]=1;
			int Tend=FindNextNode(Tstart,cutV[Tstart],X[Tstart],E[Tstart],tmp,N,len);
			tmp[Tend]=1;
			
			NewX[TTstart][1].start=TTstart;
			NewX[TTstart][1].end=Tend;
			for(;;)
			{
				//System.out.print(Tend);
				NewX[Tend][0].start=Tend;
				NewX[Tend][0].end=Tstart;
				
				//�ж����еĽڵ��Ƿ񶼱����ʵ�������ǣ�������ѭ��
				tmpSum=0;
				for(k=1;k<N+1;k++)
				{
					tmpSum+=tmp[k];
				}
				if(tmpSum==N)
					break;
				
				Tstart=Tend;
				Tend=FindNextNode(Tstart,cutV[Tstart],X[Tstart],E[Tstart],tmp,N,len);
				tmp[Tend]=1;
				
				NewX[Tstart][1].start=Tstart;
				NewX[Tstart][1].end=Tend;
			}
			NewX[Tend][1].start=Tend;
			NewX[Tend][1].end=TTstart;
			NewX[TTstart][0].start=TTstart;
			NewX[TTstart][0].end=Tend;
			
			//NewX������ϣ�����X
			for(j=1;j<N+1;j++)
			{
				for(k=0;k<2;k++)
					X[j][k].copy(NewX[j][k]);
			}
			random=null;
			cutV=null;
			NewX=null;
			tmp=null;
			
		}
		int FindNextNode(int Start,edge[] cutV,edge[] X,edge[] E,int[] tmp,int N,int len[][])
		{
			int k;
			edge FindEdge=new edge();
			//�ȴ�V�л�ȡ
			int  AvailableEdgeNum=0;
			int bestK=-1;
			int bestLen=99999;
			int length;
			for(k=0;k<N-1;k++)
			{
				if(cutV[k].pe>0 && tmp[cutV[k].end]==0)
				{
					length=len[ cutV[k].start][ cutV[k].end];
					if(length<bestLen)
					{
						bestK=k;
						bestLen=length;
					}
				}
			}
			if(bestK>=0)
				FindEdge.copy(cutV[bestK]);
			else
			{
				//��Xi��ѡ����õı�
				if(tmp[X[0].end]==0 && tmp[X[1].end]==0)
				{
					int len0=len[ X[0].start][X[0].end];
					int len1=len[X[1].start][ X[1].end];
					if(len0<len1)
						FindEdge.copy(X[0]);
					else
						FindEdge.copy(X[1]);
					//tmp[FindEdge.end]=1;
				}
				else if(tmp[X[0].end]==0)
				{
					FindEdge.copy(X[0]);
				//	tmp[FindEdge.end]=1;
				}
				else if(tmp[X[1].end]==0)
				{
					FindEdge.copy(X[1]);
				//	tmp[FindEdge.end]=1;
				}
				
				//���涼�������e��ѡ���
				else
				{	
					for(k=0;k<N-1;k++)
					{
						if(tmp[E[k].end]==0)
						{
							length=len[ E[k].start][ E[k].end];
							if(length<bestLen)
							{
								bestK=k;
								bestLen=length;
							}
						}
					}
					FindEdge.copy(E[bestK]);
					//tmp[FindEdge.end]=1;
				}
			}
			return FindEdge.end;
		}
}
