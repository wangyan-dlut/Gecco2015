package algorithm.core.component;

import java.util.Random;

public class Encode {
	public static Code createIntPriorityCode(int size) {
		Code code = new Code();	
		for(int i=0; i<size; i++) {
			code.addGene(i);
		}
		Random rd = new Random();
		int t=0;
		while (t<size) {
			int temp_a=rd.nextInt(size);
			int temp_b=rd.nextInt(size);
			if(temp_a!=temp_b) {
				int temp_c = code.getIntGene(temp_a);
				code.setGene(temp_a, code.getIntGene(temp_b));
				code.setGene(temp_b, temp_c);
				t++;
			}
		}
		return code;
	}
	public static Code createDoublePriorityCode(int size) {
		Code code = new Code();	
		for(int i=0; i<size; i++) {
			code.addGene((double)i);
		}
		Random rd = new Random();
		int t=0;
		while (t<size) {
			int temp_a=rd.nextInt(size);
			int temp_b=rd.nextInt(size);
			if(temp_a!=temp_b) {
				double temp_c = code.getDoubleGene(temp_a);
				code.setGene(temp_a, code.getDoubleGene(temp_b));
				code.setGene(temp_b, temp_c);
				t++;
			}
		}
		return code;
	}
	public static Code createCodeWithBound(int dimension,double lbound, double ubound){
		Code code = new Code();
		Random random = new Random();
		for(int i=0; i<dimension; i++){
			code.addGene(lbound + (ubound - lbound) * random.nextDouble());
		}
		return code;
	}
	public static void printIntCode(Code code) {
		
		for(int i=0; i<code.getSize(); i++) {
			System.out.print(""+code.getIntGene(i)+" ");
		}
		System.out.println();
	}
	public static void printDoubleCode(Code code) {
		
		for(int i=0; i<code.getSize(); i++) {
			System.out.print(""+code.getDoubleGene(i)+" ");
		}
		System.out.println();
	}
}
