package org.moeaframework.problem.mip;

import java.io.IOException;
import java.util.stream.IntStream;

import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.RealMatrix;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.BinaryIntegerVariable;
import org.moeaframework.core.variable.BinaryVariable;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.Permutation;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

import cl.usach.diinf.optimization.wqeissplus.helpers.IOUtils;

public class SDMDMCHFMVTTLVRP extends AbstractProblem implements Problem {
	
	RealMatrix c1 = new Array2DRowRealMatrix();
	RealMatrix c2 = new Array2DRowRealMatrix();
	RealMatrix d  = new Array2DRowRealMatrix();
	RealMatrix q  = new Array2DRowRealMatrix();
	RealMatrix w  = new Array2DRowRealMatrix();
	RealMatrix o  = new Array2DRowRealMatrix();
	RealMatrix g  = new Array2DRowRealMatrix();
	RealMatrix a  = new Array2DRowRealMatrix();
	RealMatrix r  = new Array2DRowRealMatrix();
	
	int n1 = 0;
	int n2 = 0;
	int gdata = 0;
	
	int i1 = 2;
	int i2 = 3;
		
	int[] trucks = IntStream.range(0, i1).toArray();
	int[] helicopter = IntStream.range(i1, i1 + i2).toArray();
	
	// User Params
	
	
	public SDMDMCHFMVTTLVRP() {
		super(7, 2, 2);
		try {
			c1 = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("C1.csv").getFile());
			c2 = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("C2.csv").getFile());
			d  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("d.csv").getFile());
			q  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("Q.csv").getFile());
			w  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("W.csv").getFile());
			o  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("O.csv").getFile());
			g  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("G.csv").getFile());
			a  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("A.csv").getFile());
			r  = IOUtils.readMatrix(this.getClass().getClassLoader().getResource("R.csv").getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 1) Get n1
		for (int i = 0; i < c1.getColumnVector(0).getDimension(); i++) {
			double data =  c1.getColumnVector(0).getEntry(i);
			if (data != 100000) {
				n1 = i;
				break;
			}
		}
		n2 = c1.getColumnDimension() - n1;
		gdata = d.getColumnDimension();
		System.out.println("Hola");
	}

	@Override
	public void evaluate(Solution solution) {
		double ctt = 0.0;
		BinaryIntegerVariable s    = (BinaryIntegerVariable) solution.getVariable(0);
		BinaryIntegerVariable p    = (BinaryIntegerVariable) solution.getVariable(1);
		BinaryIntegerVariable k    = (BinaryIntegerVariable) solution.getVariable(2);
		BinaryIntegerVariable i    = (BinaryIntegerVariable) solution.getVariable(3);
		BinaryIntegerVariable j    = (BinaryIntegerVariable) solution.getVariable(4);
		BinaryIntegerVariable ga   = (BinaryIntegerVariable) solution.getVariable(5);
		BinaryVariable open 	   = (BinaryVariable) solution.getVariable(6);
		if (k.getValue() < i1) { // Truck
			ctt =  g.getEntry(s.getValue(), p.getValue()) * c1.getEntry(s.getValue(), p.getValue());
		} else { // Not truck
			ctt = c2.getEntry(s.getValue(), p.getValue());
		}
		
		int apertureCost = 0;
		for (int idx = 0; idx < o.getRowDimension(); idx++) {
			apertureCost += o.getEntry(idx, 0) * open.get(idx);
		}		
		
		
		solution.setObjective(0, 0);
		solution.setObjective(1, 0);
		solution.setConstraint(0, 0 <= 0.0 ? 0.0 : 1);
		solution.setConstraint(1, 0 <= 0.0 ? 0.0 : 1);
	}

	@Override
	public Solution newSolution() {
		Solution solution = new Solution(7, 2, 2);
		//s
		solution.setVariable(0, EncodingUtils.newBinaryInt(0, g.getColumnDimension()));
		//p
		solution.setVariable(1, EncodingUtils.newBinaryInt(0, g.getColumnDimension()));
		// k
		solution.setVariable(2, EncodingUtils.newBinaryInt(0, i1 + i2 - 1));
		// i
		solution.setVariable(3, EncodingUtils.newBinaryInt(0, n1 - 1));
		// j
		solution.setVariable(4, EncodingUtils.newBinaryInt(n1, n1 + n2 -1));
		// g
		solution.setVariable(5, EncodingUtils.newBinaryInt(0, gdata -1));
		// open
		solution.setVariable(6, EncodingUtils.newBinary(o.getRowDimension()));
		
		return solution;
	}
}
