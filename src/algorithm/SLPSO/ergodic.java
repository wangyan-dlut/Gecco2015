package algorithm.SLPSO;

public class ergodic {
	int Run(int[][] len,int N,int[] tmpSs)
	{
		this.len=len;
		this.N=N;
		tmp_result=new int[N];
		result=new int[N];
		minlen=99999;
		String prefix="";
		int[] a=new int[N];
		for(int i=0;i<N;i++ )
		{
			a[i]=i+1;
		}
		
		sort(prefix,a);
		
		for(int i=0;i<N;i++)
			tmpSs[i]=result[i];
		return length(result);
	}
	 private void sort(String prefix, int[] a ) {
		 if (a.length == 1) {
			// System.out.println(prefix + a[0]+"\n");
			 prefix+=a[0];
			 char[] tmp=prefix.toCharArray();
			 for(int i=0;i<N;i++)
			 {
				 tmp_result[i]=tmp[i]-'0';
			 }
			 int tmplen=length(tmp_result);
		//	 System.out.println(tmplen);
			 if(tmplen<minlen)
			 {
				 minlen=tmplen;
				 System.arraycopy(tmp_result,0,result,0,N);
			 }
		 }

		 for (int i = 0; i < a.length; i++) {
			 sort(prefix + a[i], copy(a, i));
		 }
	 }

	private int[] copy(int[] a,int index){
		 int[] b = new int[a.length-1];
		 System.arraycopy(a, 0, b, 0, index);
		 System.arraycopy(a, index+1, b, index, a.length-index-1);
		 return b;
	}

	private int length(int[] a)
	{
		int i;
		int length=0;
		for(i=0;i<N;i++)
		{
			length+=len[a[i] ][ a[(i+1)%N] ];
		}
		return length;
	}
	int N;
	int [][] len;
	int[] result;
	int[] tmp_result;
	int minlen=99999;
}
