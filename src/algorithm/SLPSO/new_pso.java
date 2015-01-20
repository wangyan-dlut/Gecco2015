package algorithm.SLPSO;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
public class new_pso {
	private double[][] Wij; //��1��ʼ
	private double[][] Pij;//��1��ʼ
	private double[] Ri;//��1��ʼ
	private double[] Pi;
	private int[][] D;
	private int N;
	private int M;
	private int[][] Si;//��������ÿ���ڵ�ѡȡ��M���ڵ㣻
	private int[] Dpbest;
	private double[] RiPbest;
	private Random random=new Random();
	private ergodic Ergodic=new ergodic();
	private int[][][] X;
	private int[][] Y;
	private int[] Dbest;
	private int DBEST;
	private double[][] PijBest;
	private double[] RiBest;
	private double[] V;
	private double alpha;
	private double c1;
	private double c2;
	public void Run(int[][] D1,int N1,int iter,int show)
	{
		D=D1;
		N=N1;
		M=5;
		alpha=0.9;
		c1=2.0;
		c2=3.0;
		
		int iteration=iter;//��������
		int iteration_num=iteration;
		int showIteration=show;//ÿ�����ٴ��鿴һ��
		
		ParameterInitialization();
		while(iteration_num-->0)
		{
			step1();
			//showSi();
			step2();
			//showSi();
			step3();
			//showY();
			step4();
			//showDbest();
			step6();
			alpha=alpha-0.5/(iteration*1.000);
			
			if((iteration-iteration_num)%showIteration==0)
			{
				System.out.print("��ʼ��"+(iteration-iteration_num)+"�ε���\n");
				System.out.print(""+DBEST+"\n");
			}
		}
	}
	void showSi()
	{
		for(int i=1;i<N+1;i++)
		{
			for(int j=0;j<M;j++)
			{
				System.out.print(""+Si[i][j]+"  ");
			}
			System.out.print("\n");
		}
	}
	void showY()
	{
		int i,j;
		for(i=1;i<N+1;i++)
		{
			for(j=1;j<N+1;j++)
					System.out.print(""+Y[i][j]+"  ");
			System.out.print("\n");
		}
	}
	void showDbest()
	{
		for(int i=0;i<N;i++)
			System.out.print(""+Dbest[i]+"  ");
	}
	private void ParameterInitialization()
	{
		Wij=new double[N+1][N+1];
		Pij=new double[N+1][N+1];
		Pi=new double[N+1];
		Ri=new double[N+1];
		Si=new int[N+1][M];
		Dpbest=new int[N+1];
		RiPbest=new double[N+1];
		X=new int[N+1][N+1][N+1];
		Y=new int[N+1][N+1];
		Dbest=new int[N];
		DBEST=99999;
		PijBest=new double[N+1][N+1];
		RiBest=new double[N+1];
		V=new double[N+1];
		int i,j,k;
		for(i=1;i<N+1;i++)
			Dpbest[i]=9999;
		int[] Dik=new int[N+1];
		for(i=1;i<N+1;i++)
		{
			//����Dik
			for(j=1;j<N+1;j++)
			{
				if(i==j)
					continue;
				Dik[i]+=D[i][j];
			}
			//ÿ����һ��Dik �͸���һ��Pi��Wi
			for(j=1;j<N+1;j++)
			{
				if(i==j)
					Pij[i][j]=Wij[i][j]=-1;
				else
					Pij[i][j]=Wij[i][j]=D[i][j]/(Dik[i]*1.00);
				//System.out.print(""+Pij[i][j]+"  ");
			}
			//System.out.print("\n");
			Ri[i]=(random.nextInt(100)+1)/100.00;
		//	System.out.print(""+Ri[i]+"  ");
		}
	}
	private void step1()
	{
		int i,j;
		int x=1;//�����ж��Ѿ�ѡȡ�˼�����
		int[] tmp=new int[N+1];
		for(i=1;i<N+1;i++)
		{
			x=1;
			Si[i][0]=i;
			tmp[i]=1;
			//��ʼѰ�ҽڵ�
			int AvaliableNode=0;
			int select;
			int ii=0;
			for(j=1;j<N+1;j++)
			{
				if(tmp[j]==0 && Pij[i][j]<Ri[i])
					AvaliableNode++;
			}
			if(AvaliableNode>0)//��������������Ľڵ�
			{
				while(true)//Ѱ�ҽڵ㣬��Ҫ��������
				{
					int tmpAva=AvaliableNode;
					select=random.nextInt(tmpAva--);
					for(j=1;j<N+1;j++)
					{
						if(tmp[j]==0 && Pij[i][j]<Ri[i])
						{
							if(ii==select)
							{
								//System.out.print(x);
								Si[i][x++]=j;
								tmp[j]=1;
								break;
							}
							else
								ii++;
						}
					}
					if(x==AvaliableNode+1 || x==M)
						break;
					ii=0;
				}
			}
			if(AvaliableNode<M-1)
			{
				while(true)//���Ѱ�ҽڵ�,����Ҫ��������
				{
					j=random.nextInt(N)+1;
					if(tmp[j]==0)
					{
						Si[i][x++]=j;
						tmp[j]=1;
						if(x==M)
							break;
					}
				}
			}
		
			for(j=1;j<N+1;j++)
			{
				tmp[j]=0;
			}
		}
	}
	private void step2()
	{
		int i,j,k;
		for(i=1;i<N+1;i++)
		{
			int[] tmpResult=new int[M];
			int[] tmpS=new int[M+1];
			int len;
			for(j=1;j<M+1;j++)
			{
				tmpS[j]=Si[i][j-1];
			}
			int[][] tmpD=new int[M+1][M+1];
			for(j=1;j<M+1;j++)
			{
				for(k=1;k<M+1;k++)
					tmpD[j][k]=D[tmpS[j]][tmpS[k]];
			}
			len=Ergodic.Run(tmpD, M,tmpResult);
			//System.out.print(len);
			if(len<Dpbest[i])
			{
				Dpbest[i]=len;
				RiPbest[i]=Ri[i];
			}
			for(j=0;j<M;j++)
			{
				
				Si[i][j]=tmpS[tmpResult[j]];
			}
		}
	}
	private void step3()
	{
		changeSi();
		int i,j,k;
		for(i=1;i<N+1;i++)
			for(j=1;j<N+1;j++)
					Y[i][j]=0;
		for(i=1;i<N+1;i++)
		{
			for(j=1;j<N+1;j++)
			{
				for(k=1;k<N+1;k++)
				{
					Y[i][j]+=X[k][i][j];
				}
			}
		}
		for(i=1;i<N+1;i++)
		{
			for(j=1;j<N+1;j++)
				Pij[i][j]=Pij[i][j]/((Y[i][j]+1)*1.00);
		}
		
	}
	private void changeSi()
	{	
		int i,j,k;
		for(i=1;i<N+1;i++)
			for(j=1;j<N+1;j++)
				for(k=1;k<N+1;k++)
					X[i][j][k]=0;
		
		
		for(i=1;i<N+1;i++)
		{
			for(j=0;j<M;j++)
			{
				X[i][ Si[i][j] ][ Si[i][(j+1)%M] ]=1;
				X[i][ Si[i][(j+1)%M] ][ Si[i][j] ]=1;
			}
		}
	}
	private void step4()
	{
		int i,j,k;
		for(i=1;i<N+1;i++)
		{
			Pi[i]=0;
			for(j=1;j<N+1;j++)
			{
				Pi[i]+=Pij[i][j];
			}
		}
		int[] tmp=new int[N+1];
		int[] tmpD=new int[N];
		for(i=0;i<N;i++)
		{
			tmpD[i]=FindNextNode(tmp);
		}
		int len=length(tmpD);
		if(len<DBEST)
		{
			DBEST=len;
			for(i=0;i<N;i++)
				Dbest[i]=tmpD[i];
			for(i=1;i<N+1;i++)
			{
				RiBest[i]=Ri[i];
				for(j=1;j<N+1;j++)
				{
					PijBest[i][j]=Pij[i][j];
				}
			}
		}
		
	}
	private void step6()
	{	
		int i;
		double bita1=(random.nextInt(98)+1)/100.00;
		double bita2=(random.nextInt(98)+1)/100.00; 
		for(i=1;i<N+1;i++)
		{
			V[i]=alpha*V[i]+c1*bita1*(RiPbest[i]-Ri[i])+c2*bita2*(RiBest[i]-Ri[i]);
			Ri[i]=Ri[i]+V[i];
		}
	}
	private int FindNextNode(int[] tmp)
	{
		int i,j;
		double minPi=999;
		int select=0;
		for(i=1;i<N+1;i++)
		{
			if(tmp[i]==0 && minPi>Pi[i])
			{
				minPi=Pi[i];
				select=i;
			}
		}
		tmp[select]=1;
		return select;
	}
	private int length(int[] d)
	{
		int len=0;
		int i=0;
		for(i=0;i<N;i++)
		{
			len+=D[d[i]][d[(i+1)%N]];
		}
		return len;
	}
}//end
