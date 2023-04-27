package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.ClassVarDeclaration;
import rs.ac.bg.etf.pp1.ast.FormParam;
import rs.ac.bg.etf.pp1.ast.FormParamList;
import rs.ac.bg.etf.pp1.ast.FormParameter;
import rs.ac.bg.etf.pp1.ast.SingleClassVarDeclaration;
import rs.ac.bg.etf.pp1.ast.VarDecl;
import rs.ac.bg.etf.pp1.ast.VariableDeclaration;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {
	protected int count=0;
	public int getCount() 
	{
		return count;
	}
	public static class FormParamCounter extends CounterVisitor
	{
		public void visit(FormParameter formParameter) 
		{
			count++;
		}
	}
   public static class VarCounter extends CounterVisitor
   {
	   public void visit(VariableDeclaration variableDeclaration) 
	   {
		   count++;
	   }
   }
   public static class FieldsCounter extends CounterVisitor
   {
	   public void visit(SingleClassVarDeclaration field) 
	   {
		   count++;
	   }
   }
	
}

