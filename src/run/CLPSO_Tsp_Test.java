package run;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import algorithm.SLPSO.*;

public class CLPSO_Tsp_Test {

	public static void main(String[] args) throws FileNotFoundException {
		String patch="data/tsp/dj38.txt";
		int N=38;
		int[ ][ ] len=FileHandling(patch,N);
		long start;
		long end;
		CLPSO clpso=new CLPSO();
		CLPSO_pe clpso_pe=new CLPSO_pe();
		CLPSO_len clpso_len=new CLPSO_len();
		new_pso newPso=new new_pso();
		start=System.currentTimeMillis(); 
		
		//clpso.Run(len, N,5000,500);
		//clpso_pe.Run(len, N,5000,200);
		//clpso_len.Run(len, N,5000,100);
		newPso.Run(len, N,5000,100);
		
		
		end=System.currentTimeMillis(); 
		System.out.print("\ntime:  "+(end-start));
	}
static int[][] FileHandling(String patch,int N) throws FileNotFoundException
{
	Scanner in=new Scanner(new File(patch));
	double[][] tmp=new double[N+1][2];
	for(int i=1;i<N+1;i++)
	{
		    tmp[i][0]=in.nextDouble();
			tmp[i][0]=in.nextDouble();
			tmp[i][1]=in.nextDouble();
		
	}
	int[][] len=new int[N+1][N+1];
	for(int i=1;i<N+1;i++)
	{
		for(int j=1;j<N+1;j++)
		{
			if(i==j)
			{len[i][j]=-1;}
			else
			{
				double tmpx=tmp[j][0]-tmp[i][0];
				double tmpy=tmp[j][1]-tmp[i][1];
				double tmpxy=Math.sqrt(tmpx*tmpx+tmpy*tmpy);
				len[i][j]=(int)Math.round(tmpxy);
			}
		}
	}
	return len;
}
}

