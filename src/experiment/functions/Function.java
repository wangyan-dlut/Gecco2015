package experiment.functions;

import org.ejml.data.DenseMatrix64F;

import algorithm.core.component.Code;

/**
 * Created by evolab.cn on 2015/1/16.
 */
public abstract class Function {
	protected double lbound;
	protected double ubound;
	protected int dimension;
	protected Code globalOptimum;
	DenseMatrix64F M;
	public abstract double run(Code code);
	public Function(double lbound, double ubound, int dimension){
		this.lbound = lbound;
		this.ubound = ubound;
		this.dimension = dimension;
		globalOptimum = new Code();
		for(int i=0; i<dimension; i++){
			globalOptimum.addGene(0.0);
		}
		/*留在子类中去初始化，不同的目标函数需要不同的M*/
		//M = RandomMatrices.createOrthogonal(dimension, dimension, new Random());

	}

	public void setGlobalOptimum(Code code){
		globalOptimum = code;
	}
	public void setDimension(int tempDimension){
		dimension = tempDimension;
		globalOptimum = new Code();
		for(int i=0; i<dimension; i++){
			globalOptimum.addGene(0.0);
		}

	}
	public int getDimension(){
		return dimension;
	}
	public double getLbound(){
		return lbound;
	}
	public double getUbound(){
		return ubound;
	}
	public Code getShiftedSolution(Code code){
		Code res = new Code();
		for(int i=0; i<dimension; i++){
			res.addGene(code.getDoubleGene(i) - globalOptimum.getDoubleGene(i));
		}
		return res;
	}
	public Code coordinatesRotate(Code code){
		Code rotateCode = new Code();
		for(int i=0; i<code.getSize(); i++){
			double temp = 0.0;
			for(int j=0; j<code.getSize(); j++){
				temp += code.getDoubleGene(j) * M.get(j, i);
			}
			rotateCode.addGene(temp);
		}
		return rotateCode;
	}
	/* Basic Functions */
	public double sphereFunction(Code code){
		double res = 0.0;
		double temp;
		for(int i=0; i<code.getSize(); i++){
			temp = code.getDoubleGene(i);
			res += temp * temp;
		}
		return res;
	}
	public double ellipticFunction(Code code){
		double res = 0.0;
		double temp;
		for(int i=0; i<code.getSize(); i++){
			temp = code.getDoubleGene(i);
			res += Math.pow( 1e6, i / ((double)(code.getSize()-1)) ) * temp * temp;
		}
		return res;
	}
	public double rot_ellipticFunction(Code code){
		return ellipticFunction(coordinatesRotate(code));
	}
	public double rastriginFunction(Code code){
		double res = 0.0;
		double temp;
		for(int i=0; i<code.getSize(); i++){
			temp = code.getDoubleGene(i);
			res += temp * temp - 10 * Math.cos(2 * Math.PI * temp) + 10;
		}
		return res;
	}
	public double rot_rastriginFunction(Code code){
		return rastriginFunction(coordinatesRotate(code));
	}
	public double ackleyFunction(Code code){
		double res = 0.0;
		double tempGene;
		double temp1 = 0.0;
		double temp2 = 0.0;
		for(int i=0; i<code.getSize(); i++){
			tempGene = code.getDoubleGene(i);
			temp1 += tempGene * tempGene;
			temp2 += Math.cos(2 * Math.PI * tempGene);
		}
		temp1 = Math.sqrt(temp1/code.getSize());
		temp2 = temp2/code.getSize();
		res = -20 * Math.exp(-0.2 * temp1) - Math.exp(temp2) + 20 + Math.E;
		return res;
	}
	public double rot_ackleyFunction(Code code){
		return ackleyFunction(coordinatesRotate(code));
	}
	public double schwefelFunction(Code code){
		double res = 0.0;
		double tempGene;
		for(int i=0; i<code.getSize(); i++){
			double temp = 0.0;
			for(int j=0; j<=i; j++){
				tempGene = code.getDoubleGene(j);
				temp += tempGene;
			}
			res += temp * temp;
		}
		return res;
	}
	public double rosenbrockFunction(Code code){
		double res = 0.0;
		double tempGene1;
		double tempGene2;
		for(int i=0; i<code.getSize()-1; i++){
			tempGene1 = code.getDoubleGene(i);
			tempGene2 = code.getDoubleGene(i + 1);
			res += 100 * Math.pow(tempGene1 * tempGene1 - tempGene2, 2) + Math.pow(tempGene1 - 1, 2);
		}
		return res;
	}

	public static Code onesCode(int dimension){
		Code code = new Code();
		for(int i=0; i<dimension; i++){
			code.addGene(i);
		}
		return code;
	}
	public void showM(){
		M.print();
	}

}
