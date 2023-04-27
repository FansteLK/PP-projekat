package rs.ac.bg.etf.pp1;




import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.log4j.Logger;
import org.w3c.dom.CDATASection;

import java.util.Stack;
import rs.ac.bg.etf.pp1.CounterVisitor.FieldsCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.Type;
import rs.ac.bg.etf.pp1.ast.UnmatchedStatement;
import rs.ac.bg.etf.pp1.ast.AddopPlus;
import rs.ac.bg.etf.pp1.ast.And;
import rs.ac.bg.etf.pp1.ast.Assignop;
//import rs.ac.bg.etf.pp1.ast.Base;
import rs.ac.bg.etf.pp1.ast.BoolConst;
import rs.ac.bg.etf.pp1.ast.Expression;
import rs.ac.bg.etf.pp1.ast.FactorNewExpr;
import rs.ac.bg.etf.pp1.ast.BoolConstFactor;
import rs.ac.bg.etf.pp1.ast.BreakStmt;
import rs.ac.bg.etf.pp1.ast.CharConst;
import rs.ac.bg.etf.pp1.ast.CharConstFactor;
import rs.ac.bg.etf.pp1.ast.ClassDecl1;
import rs.ac.bg.etf.pp1.ast.ClassName;
import rs.ac.bg.etf.pp1.ast.CondFact;
import rs.ac.bg.etf.pp1.ast.CondFactExpr;
import rs.ac.bg.etf.pp1.ast.ConditionFact;
import rs.ac.bg.etf.pp1.ast.ConditionTerm;
import rs.ac.bg.etf.pp1.ast.ConditionTerms;
import rs.ac.bg.etf.pp1.ast.ConstructorDecl;
import rs.ac.bg.etf.pp1.ast.ConstructorDeclNoIdent;
import rs.ac.bg.etf.pp1.ast.ContinueStmt;
import rs.ac.bg.etf.pp1.ast.Designator;
//import rs.ac.bg.etf.pp1.ast.DesignatorAdded;
import rs.ac.bg.etf.pp1.ast.DesignatorArray;
import rs.ac.bg.etf.pp1.ast.DesignatorAssign;
import rs.ac.bg.etf.pp1.ast.DesignatorDecrement;
import rs.ac.bg.etf.pp1.ast.DesignatorDot;
import rs.ac.bg.etf.pp1.ast.DesignatorFuncCall;
import rs.ac.bg.etf.pp1.ast.DesignatorIncrement;
import rs.ac.bg.etf.pp1.ast.DesignatorList;
import rs.ac.bg.etf.pp1.ast.DesignatorStatmentNoError;
import rs.ac.bg.etf.pp1.ast.Do;
import rs.ac.bg.etf.pp1.ast.DoWhileStmt;
import rs.ac.bg.etf.pp1.ast.Else;
import rs.ac.bg.etf.pp1.ast.FunctionCall;
import rs.ac.bg.etf.pp1.ast.GoToStmt;
import rs.ac.bg.etf.pp1.ast.MatchedStatement;
import rs.ac.bg.etf.pp1.ast.MethodDeclNoId;
import rs.ac.bg.etf.pp1.ast.MethodDeclType;
import rs.ac.bg.etf.pp1.ast.MethodDeclVoid;
import rs.ac.bg.etf.pp1.ast.MethodDeclaration;
import rs.ac.bg.etf.pp1.ast.MethodName;
import rs.ac.bg.etf.pp1.ast.MethodTypeName;
import rs.ac.bg.etf.pp1.ast.MethodVoidName;
import rs.ac.bg.etf.pp1.ast.MethodVoidType;
import rs.ac.bg.etf.pp1.ast.MinusTermExpression;
import rs.ac.bg.etf.pp1.ast.MulopDiv;
import rs.ac.bg.etf.pp1.ast.MulopTimes;
import rs.ac.bg.etf.pp1.ast.MultipleStatements;
import rs.ac.bg.etf.pp1.ast.NewFactor;
import rs.ac.bg.etf.pp1.ast.NoStatements;
import rs.ac.bg.etf.pp1.ast.NotSuperStmt;
import rs.ac.bg.etf.pp1.ast.NumConst;
import rs.ac.bg.etf.pp1.ast.NumConstFactor;
import rs.ac.bg.etf.pp1.ast.NumConstant;
import rs.ac.bg.etf.pp1.ast.Or;
import rs.ac.bg.etf.pp1.ast.PrintStmt;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.ast.RbraceStatements;
import rs.ac.bg.etf.pp1.ast.ReadStmt;
import rs.ac.bg.etf.pp1.ast.Relop;
import rs.ac.bg.etf.pp1.ast.RelopEqual;
import rs.ac.bg.etf.pp1.ast.RelopGTE;
import rs.ac.bg.etf.pp1.ast.RelopGreater;
import rs.ac.bg.etf.pp1.ast.RelopLTE;
import rs.ac.bg.etf.pp1.ast.RelopLess;
import rs.ac.bg.etf.pp1.ast.RelopNotEqual;
import rs.ac.bg.etf.pp1.ast.ReturnExprStmt;
import rs.ac.bg.etf.pp1.ast.ReturnNoExprStmt;
import rs.ac.bg.etf.pp1.ast.Semi;
import rs.ac.bg.etf.pp1.ast.SingleCondFact;

import rs.ac.bg.etf.pp1.ast.SingleCondTerm;
import rs.ac.bg.etf.pp1.ast.SingleConstDecl;
import rs.ac.bg.etf.pp1.ast.SingleConstDeclaration;
import rs.ac.bg.etf.pp1.ast.SingleDesignator;
import rs.ac.bg.etf.pp1.ast.SingleFactor;
import rs.ac.bg.etf.pp1.ast.SingleStatement;
import rs.ac.bg.etf.pp1.ast.SingleStatementNoLabel;
import rs.ac.bg.etf.pp1.ast.SingleStatementWithIdent;
import rs.ac.bg.etf.pp1.ast.Statement;
import rs.ac.bg.etf.pp1.ast.StatementListNotEmpty;
import rs.ac.bg.etf.pp1.ast.Statements;
import rs.ac.bg.etf.pp1.ast.Stmts;
import rs.ac.bg.etf.pp1.ast.SuperStatement;
import rs.ac.bg.etf.pp1.ast.SuperStmt;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.TermExpression;
import rs.ac.bg.etf.pp1.ast.TermMul;
import rs.ac.bg.etf.pp1.ast.VarFactor;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.While;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor{
	private int mainPc;
	private int doCnt=0;
	private int ifCnt=0;
	private int ifUnCnt=0;
	private int elseCnt=0;
	private int doWhileCnt=0;
	private int superStmtCnt=0;
	private int conditionCnt=0;
	private int conditionEndCnt=0;
	private int breakCnt=0;
	private Stack<String> stackIfElse=new Stack<String>();
	private Stack<String> stackUnmatched = new Stack<String>();
	private Stack<String> stackMatched = new Stack<String>();
	private Stack<String> stackDoWhile =  new Stack<String>();
	private Stack<String> stackCondition= new Stack<String>();
	private Stack<String> stackConditionEnd = new Stack<String>();
	private Stack<String> stackContinue= new Stack<String>();

	public int getMainPc() 
	{
		return mainPc;
	}

	public void visit(PrintStmt printStmt) 
	{
		
		 if (printStmt.getExpr().struct==Tab.intType) 
		 {
			 if (NumConstant.class.isInstance(printStmt.getNumConstPrint()))
				{
					
					NumConstant numConstant=(NumConstant) printStmt.getNumConstPrint();
				int width=	numConstant.getValue();
				Obj con=Tab.insert(Obj.Con, "$",  Tab.intType);
				con.setAdr(width);
				Code.load(con);
				
				}
			 else 
			 {
				 Code.loadConst(5); 
			 }
//			 Code.loadConst(5);
			 
			 Code.put(Code.print);
			 
		 }
		 else if (printStmt.getExpr().struct==Tab.charType) 
		 {
			 Code.loadConst(1);
			 Code.put(Code.bprint);
			 
		 }
		 else 
		 {
		   Code.loadConst(1);
		   Code.put(Code.print);
		 }
	
	}
	public void visit(NumConstFactor numConstFactor) 
	{
	    
		Obj con=Tab.insert(Obj.Con, "$",  numConstFactor.struct);
		con.setLevel(0); 
		con.setAdr(numConstFactor.getValue());
		Code.load(con);
	}
	public void visit(CharConstFactor charConstFactor) 
	{
		
		Obj con=Tab.insert(Obj.Con, "$",  charConstFactor.struct);
		con.setLevel(0);
		con.setAdr((int) charConstFactor.getValue());
		Code.load(con);
	}
	public void visit(BoolConstFactor boolConstFactor) 
	{
	   
		Obj con=Tab.insert(Obj.Con, "$",  boolConstFactor.struct);
		con.setLevel(0);
		if(boolConstFactor.getValue().equals("true")) 
		{
			con.setAdr(1);
		}
		else 
		{
			con.setAdr(0);
		}
		Code.load(con);
	}
	public void visit(MethodDeclType methodDeclType) 
	{
		if ("main".equalsIgnoreCase(methodDeclType.getMethodName())) 
		{
			mainPc=Code.pc;
		
		
		}
	
		methodDeclType.obj.setAdr(Code.pc);
		SyntaxNode node=methodDeclType.getParent();
		VarCounter varCnt = new VarCounter();
		node.traverseTopDown(varCnt);
		FormParamCounter fpCount= new FormParamCounter();
		node.traverseTopDown(fpCount);
		Code.put(Code.enter);
		Code.put(fpCount.getCount());
		Code.put(fpCount.getCount()+varCnt.getCount());
		
	}
	public void visit(MethodVoidType methodVoidType) 
	{
		if("main".equalsIgnoreCase(methodVoidType.getMethodName())) 
		{
			
		mainPc=Code.pc;
		}
		
		methodVoidType.obj.setAdr(Code.pc);
		SyntaxNode node=methodVoidType.getParent();
		VarCounter varCnt = new VarCounter();
		node.traverseTopDown(varCnt);
		FormParamCounter fpCount= new FormParamCounter();
		node.traverseTopDown(fpCount);
		Code.put(Code.enter);
		Code.put(fpCount.getCount());
		Code.put(fpCount.getCount()+varCnt.getCount());
	}
	public void visit(MethodName methodName) 
	{
		methodName.obj.setAdr(Code.pc);
		SyntaxNode node=methodName.getParent();
		VarCounter varCnt = new VarCounter();
		node.traverseTopDown(varCnt);
		FormParamCounter fpCount= new FormParamCounter();
		node.traverseTopDown(fpCount);
		Code.put(Code.enter);
		Code.put(fpCount.getCount());
		Code.put(fpCount.getCount()+varCnt.getCount());
	}
	public void visit(MethodVoidName methodVoidName) 
	{
		methodVoidName.obj.setAdr(Code.pc);
		SyntaxNode node=methodVoidName.getParent();
		VarCounter varCnt = new VarCounter();
		node.traverseTopDown(varCnt);
		FormParamCounter fpCount= new FormParamCounter();
		node.traverseTopDown(fpCount);
		Code.put(Code.enter);
		Code.put(fpCount.getCount());
		Code.put(fpCount.getCount()+varCnt.getCount());
	}
	public void visit(MethodDeclaration methodDeclaration) 
	{
		Code.put(Code.exit);
		Code.put(Code.return_);
		
	}
	public void visit (MethodDeclVoid methodDeclVoid) 
	{
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	public void visit(MethodDeclNoId methodDeclNoId) 
	{
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(DesignatorAssign designatorAssign) 
	{
		DesignatorStatmentNoError designatorStatmentNoError=(DesignatorStatmentNoError) designatorAssign.getParent();
		Code.store(designatorStatmentNoError.getDesignator().obj);
	}
	

	public void visit(SingleDesignator singleDesignator) 
	{
	
	if (!DesignatorStatmentNoError.class.isInstance(singleDesignator.getParent())&&!ReadStmt.class.isInstance(singleDesignator.getParent())) 
	{
	
		Code.load(singleDesignator.obj);
	}
		
	}
	public void visit(DesignatorDot designatorDot) 
	{
		
		if (!DesignatorStatmentNoError.class.isInstance(designatorDot.getParent())&&!ReadStmt.class.isInstance(designatorDot.getParent()))
		{
			Code.load(designatorDot.obj);
		}
			
	}
	public void visit(DesignatorArray designatorArray) 
	{
	
		if(!DesignatorStatmentNoError.class.isInstance(designatorArray.getParent())&&!ReadStmt.class.isInstance(designatorArray.getParent()))
		{
			Code.load(designatorArray.obj);
		}

	}

	public void visit(DesignatorFuncCall designatorFuncCall) 
	{
	
		DesignatorStatmentNoError designatorStatmentNoError=(DesignatorStatmentNoError) designatorFuncCall.getParent();
		Obj funcObj=designatorStatmentNoError.getDesignator().obj;
	    if (funcObj.getName().equals("ord")||funcObj.getName().equals("chr")||funcObj.getName().equals("len")) 
	    {
	    	return;
	    }
		int offset=funcObj.getAdr()-Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
		if (funcObj.getType()!=Tab.noType) 
		{
			Code.put(Code.pop);
		}
	}
	public void visit(FunctionCall functionCall) 
	{  
          
		Obj funcObj=functionCall.getDesignator().obj;
		 if (funcObj.getName().equals("ord")||funcObj.getName().equals("chr")||funcObj.getName().equals("len")) 
		    {
		    	return;
		    }
		int offset=funcObj.getAdr()-Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}
	public void visit(ReturnExprStmt returnExpr) 
	{
		Code.put(Code.exit);
		Code.put(Code.return_);
		
	}
	public void visit(ReturnNoExprStmt returnExpr) 
	{
		
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	public void visit(Expression expr) 
	{
	 if (AddopPlus.class==expr.getAddop().getClass())
		Code.put(Code.add);
	 else Code.put(Code.sub);
	 
	}
	public void visit(TermMul termMul) 
	{
		if (MulopTimes.class == termMul.getMulop().getClass())
		Code.put(Code.mul);
		else if (MulopDiv.class == termMul.getMulop().getClass())
			Code.put(Code.div);
			else Code.put(Code.rem);
	}
	public void visit(DesignatorIncrement designatorIncrement) 
	{
		DesignatorStatmentNoError designatorStatement=(DesignatorStatmentNoError) designatorIncrement.getParent();
		Designator designator=designatorStatement.getDesignator();
		if(DesignatorDot.class.isInstance(designator)||DesignatorArray.class.isInstance(designator)) 
		{
			Code.put(Code.dup);	
		}
        Code.load(designator.obj);
        
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designator.obj);
	}
	public void visit(DesignatorDecrement designatorDecrement) 
	{
		DesignatorStatmentNoError designatorStatement=(DesignatorStatmentNoError) designatorDecrement.getParent();
		Designator designator=designatorStatement.getDesignator();
		if(DesignatorDot.class.isInstance(designator)||DesignatorArray.class.isInstance(designator)) 
		{
			Code.put(Code.dup);	
		}
        Code.load(designator.obj);
        
		Code.loadConst(-1);
		Code.put(Code.add);
		Code.store(designator.obj);
	}
	public void visit(ReadStmt readStmt) 
	{	
	if (readStmt.getDesignator().obj.getType()==Tab.intType)
	{
		Code.put(Code.read);
	}
	else 
	{
		Code.put(Code.bread);
	}

    Code.store(readStmt.getDesignator().obj);
    
    
	}
	public void visit(MinusTermExpression minusTerm) 
	{
		Code.put(Code.neg);
		
	
	}
//	public void visit(ClassName className)
//	{
//		
//		FieldsCounter fieldsCnt= new FieldsCounter();
//		className.getParent().traverseTopDown(fieldsCnt);
//		report_info(" broj polja klase "+className.getType().getTypeName()+" "+className.obj.getKind()+" " +className.obj.getType().getNumberOfFields(), null);
//		Obj classObj=Tab.insert(Obj.Type, className.getType().getTypeName(), new Struct(Struct.Class));
//	
//	}
	public void visit(NewFactor newFactor) 
	{
		if (FactorNewExpr.class.isInstance(newFactor.getFactorNew())) {
			String typeName = newFactor.getClassName().getType().getTypeName();
			FactorNewExpr expr =(FactorNewExpr) newFactor.getFactorNew();

			if (newFactor.getClassName().getType().getTypeName().equals("int")) 
			{
				Code.put(Code.newarray);
				Code.put(1);
				
			}
			else 
			{
				
				Code.put(Code.newarray);
				Code.put(0);
				
			}
		}
		else {
   Obj className=newFactor.getClassName().obj;
   report_info(" broj polja klase "+className.getName()+" "+className.getKind()+" " +className.getType().getNumberOfFields(), null);
	Code.put(Code.new_);
	Code.put2((className.getType().getNumberOfFields()+1)*4);	
	}
	}
	Logger log = Logger.getLogger(getClass());
	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}


	public void visit(GoToStmt stmt) 
	{
	
	String label=	stmt.getLabel().getLabelName();
    Obj labelObj= Tab.find(label);
   
    if(labelObj!=Tab.noObj&&labelObj.getKind()==Obj.NO_VALUE) {
//    int offset=labelObj.getAdr()-Code.pc;
    Code.putJump(labelObj.getAdr());
//    Code.put(Code.jmp);
//    Code.put2(offset);
    }
    else if(labelObj==Tab.noObj)  {
    	Obj labelObj1=Tab.insert(Obj.NO_VALUE, label, new Struct(Struct.None));
    	
     	labelObj1.setAdr(Code.pc+3); 
     	 
        Code.loadConst(0);
       Code.loadConst(0);	
   	   Code.putFalseJump(1, 0);

    }
	}
	public void visit(Stmts stmts) 
	{

		if (MatchedStatement.class.isInstance(stmts.getParent())) 
		{
			String ifObjName="ifMATCHED"+ifCnt;
			Obj ifObj=Tab.insert(Obj.NO_VALUE,ifObjName, Tab.noType);
			stackMatched.push(ifObjName);
			ifCnt++;
			ifObj.setAdr(Code.pc+3);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(1, 0);
		}
	}
	public void visit(SingleStatementWithIdent stmt) 
	{

		if (MatchedStatement.class.isInstance(stmt.getParent())) 
		{
			String ifObjName="ifMATCHED"+ifCnt;
			Obj ifObj=Tab.insert(Obj.NO_VALUE,ifObjName, Tab.noType);
			stackMatched.push(ifObjName);
			ifCnt++;
			ifObj.setAdr(Code.pc+3);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(1, 0);
		}
	SyntaxNode node= stmt.getSingleStatement().getParent().getParent().getParent();
	if(StatementListNotEmpty.class.isInstance(node))
	{
	
		StatementListNotEmpty statmentList=(StatementListNotEmpty) node;
		if(SingleStatementWithIdent.class.isInstance(statmentList.getStatement()))
				{
		
			SingleStatementWithIdent labelStmt=(SingleStatementWithIdent) statmentList.getStatement();
	 	String label=labelStmt.getLabel().getLabelName();
		Obj findLabelObj=Tab.find(label);
		if(findLabelObj==Tab.noObj||findLabelObj.getKind()!=Obj.NO_VALUE) 
		{
Obj labelObj=Tab.insert(Obj.NO_VALUE, label, new Struct(Struct.None));
labelObj.setAdr(Code.pc);
		}
		else if (findLabelObj.getKind()==Obj.NO_VALUE)
		{
			
			Code.fixup(findLabelObj.getAdr());
		}
	}
		else if (SingleStatementNoLabel.class.isInstance(statmentList.getStatement())) 
		{
			SingleStatementNoLabel stmtNoLabel=(SingleStatementNoLabel) statmentList.getStatement();
			if (DoWhileStmt.class.isInstance(stmtNoLabel.getSingleStatement())) {
			   DoWhileStmt doWhile = (DoWhileStmt) stmtNoLabel.getSingleStatement();
			   report_info("Postavio je do-objekat", null);
			   doWhile.getDo().obj=Tab.insert(Obj.NO_VALUE,"do"+doCnt, Tab.noType);
			   doCnt++;
			   doWhile.getDo().obj.setAdr(Code.pc);
			}
		
		}
		
	
}
	else 
	{
		
	}

	}
	
	public void visit(SingleStatementNoLabel stmt) 
	{
	
		if (MatchedStatement.class.isInstance(stmt.getParent())) 
		{
			String ifObjName="ifMATCHED"+ifCnt;
			Obj ifObj=Tab.insert(Obj.NO_VALUE,ifObjName, Tab.noType);
			stackMatched.push(ifObjName);
			ifCnt++;
			ifObj.setAdr(Code.pc+3);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(1, 0);
		}
		SyntaxNode node= stmt.getSingleStatement().getParent().getParent().getParent();
		if(StatementListNotEmpty.class.isInstance(node))
		{
		
			StatementListNotEmpty statmentList=(StatementListNotEmpty) node;
			if(SingleStatementWithIdent.class.isInstance(statmentList.getStatement()))
					{
			
				SingleStatementWithIdent labelStmt=(SingleStatementWithIdent) statmentList.getStatement();
		 	String label=labelStmt.getLabel().getLabelName();
			Obj findLabelObj=Tab.find(label);
			if(findLabelObj==Tab.noObj||findLabelObj.getKind()!=Obj.NO_VALUE) 
			{
	Obj labelObj=Tab.insert(Obj.NO_VALUE, label, new Struct(Struct.None));
	labelObj.setAdr(Code.pc);
			}
			else if (findLabelObj.getKind()==Obj.NO_VALUE)
			{
				
				Code.fixup(findLabelObj.getAdr());
			}
		}
			else if (SingleStatementNoLabel.class.isInstance(statmentList.getStatement())) 
			{
				SingleStatementNoLabel stmtNoLabel=(SingleStatementNoLabel) statmentList.getStatement();
				if (DoWhileStmt.class.isInstance(stmtNoLabel.getSingleStatement())) {
				   DoWhileStmt doWhile = (DoWhileStmt) stmtNoLabel.getSingleStatement();
//				   report_info("Postavio je do-objekat", null);
				   doWhile.getDo().obj=Tab.insert(Obj.NO_VALUE,"do"+doCnt, Tab.noType);
				   doCnt++;
				   doWhile.getDo().obj.setAdr(Code.pc);
				}
			
			}
		
	}
		else 
		{
			
		}
		

	}


	public void visit(SuperStmt superStmt) 
	{
		if(MultipleStatements.class.isInstance(superStmt.getParent())) 
		{
			MultipleStatements statements=(MultipleStatements) superStmt.getParent();
			  StatementListNotEmpty statementListNotEmpty=null;
			if (StatementListNotEmpty.class.isInstance(statements.getStatementList())) 
			{
            statementListNotEmpty= (StatementListNotEmpty) statements.getStatementList();
			}
			while(StatementListNotEmpty.class.isInstance(statementListNotEmpty)) 
			{
			statementListNotEmpty =  (StatementListNotEmpty) statementListNotEmpty.getStatementList();
			}
			if (SingleStatementNoLabel.class.isInstance(statementListNotEmpty.getStatement())) 
			{
				SingleStatementNoLabel singleStatementNoLabel=(SingleStatementNoLabel) statementListNotEmpty.getStatement();
				if(DoWhileStmt.class.isInstance(singleStatementNoLabel.getSingleStatement())) 
				{
				
					DoWhileStmt doWhileStmt=(DoWhileStmt) singleStatementNoLabel.getSingleStatement();
					  doWhileStmt.getDo().obj=Tab.insert(Obj.NO_VALUE,"do"+doCnt, Tab.noType);
					   doCnt++;
					   doWhileStmt.getDo().obj.setAdr(Code.pc);
				}
				
			}
			else 
			{
				SingleStatementWithIdent labelStmt=(SingleStatementWithIdent) statementListNotEmpty.getStatement();
			 	String label=labelStmt.getLabel().getLabelName();
				Obj findLabelObj=Tab.find(label);
				if(findLabelObj==Tab.noObj||findLabelObj.getKind()!=Obj.NO_VALUE) 
				{
					
		Obj labelObj=Tab.insert(Obj.NO_VALUE, label, new Struct(Struct.None));
		labelObj.setAdr(Code.pc);
				}
				else if (findLabelObj.getKind()==Obj.NO_VALUE)
				{
			
					Code.fixup(findLabelObj.getAdr());
				}
			}
			

		
		}
	}
	public void visit(NotSuperStmt superStmt) 
	{
		if(MultipleStatements.class.isInstance(superStmt.getParent())) 
		{
			MultipleStatements statements=(MultipleStatements) superStmt.getParent();
			  StatementListNotEmpty statementListNotEmpty=null;
			if (StatementListNotEmpty.class.isInstance(statements.getStatementList())) 
			{
            statementListNotEmpty= (StatementListNotEmpty) statements.getStatementList();
			
			while(StatementListNotEmpty.class.isInstance(statementListNotEmpty.getStatementList())) 
			{
			statementListNotEmpty =  (StatementListNotEmpty) statementListNotEmpty.getStatementList();
			}
			if (SingleStatementNoLabel.class.isInstance(statementListNotEmpty.getStatement())) 
			{
				SingleStatementNoLabel singleStatementNoLabel=(SingleStatementNoLabel) statementListNotEmpty.getStatement();
				if(DoWhileStmt.class.isInstance(singleStatementNoLabel.getSingleStatement())) 
				{
					DoWhileStmt doWhileStmt=(DoWhileStmt) singleStatementNoLabel.getSingleStatement();
					  doWhileStmt.getDo().obj=Tab.insert(Obj.NO_VALUE,"do"+doCnt, Tab.noType);
					   doCnt++;
					   doWhileStmt.getDo().obj.setAdr(Code.pc);
				}
	
				
			}
			else 
			{				SingleStatementWithIdent labelStmt=(SingleStatementWithIdent) statementListNotEmpty.getStatement();
		 	String label=labelStmt.getLabel().getLabelName();
			Obj findLabelObj=Tab.find(label);
			if(findLabelObj==Tab.noObj||findLabelObj.getKind()!=Obj.NO_VALUE) 
			{
				
	Obj labelObj=Tab.insert(Obj.NO_VALUE, label, new Struct(Struct.None));
	labelObj.setAdr(Code.pc);
			}
			else if (findLabelObj.getKind()==Obj.NO_VALUE)
			{
		
				Code.fixup(findLabelObj.getAdr());
			}
			}
			}

		
		}	
	
		
	}
	public void visit(SuperStatement statement) 
	{
		Obj superStmtObj=Tab.insert(Obj.NO_VALUE, "superStmt"+superStmtCnt, Tab.noType);
		superStmtCnt++;
		superStmtObj.setAdr(Code.pc);

	}
	public void visit(Else elseObj) 
	{
		while(!stackIfElse.empty()) {
		String ifObjName=stackIfElse.pop();
	Obj	ifObj=Tab.find(ifObjName);
	Code.fixup(ifObj.getAdr());
		}
//	Code.fixup(1);		
		
	}
	public void visit(UnmatchedStatement unmatchedStatement) 
	{
		
//		
        while (!stackUnmatched.empty()) {
		 String ifObjName=stackUnmatched.pop();
			Obj	ifObj=Tab.find(ifObjName);
			Code.fixup(ifObj.getAdr());
			
			
        }
       
	}
	public void visit(MatchedStatement matchedStatement) {
		
		  while (!stackMatched.empty()) {
				 String ifObjName=stackMatched.pop();
					Obj	ifObj=Tab.find(ifObjName);
					Code.fixup(ifObj.getAdr());
		        }	
		 
		
	}

	public void visit(DoWhileStmt doWhileStmt) 
	{
		
		
		  while (!stackDoWhile.empty()) {
				 String doWhileObjName=stackDoWhile.pop();
					Obj	doWhileObj=Tab.find(doWhileObjName);
					Code.fixup(doWhileObj.getAdr());
		        }	
		
	}

public void visit(ConditionTerms conditionTerms) 
	{
	SyntaxNode parent=conditionTerms.getParent();
	if ( UnmatchedStatement.class.isInstance(parent)||MatchedStatement.class.isInstance(parent)||DoWhileStmt.class.isInstance(parent)) {

		while (!stackCondition.isEmpty()) {
		
			 String condition=stackCondition.pop();
				Obj	conditionObj=Tab.find(condition);
				Code.fixup(conditionObj.getAdr());
		}
	
	
		if (UnmatchedStatement.class.isInstance(parent)) 
		{
			
			String ifObjName="ifU"+ifCnt;
			Obj ifObj=Tab.insert(Obj.NO_VALUE,ifObjName, Tab.noType);
			stackUnmatched.push(ifObjName);
			ifCnt++;
			ifObj.setAdr(Code.pc+3);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(1,0);
			//ifObj.setAdr(Code.pc);
		}
		else if (MatchedStatement.class.isInstance(parent)) 
		{
		
			String ifObjName="ifM"+ifCnt;
			Obj ifObj=Tab.insert(Obj.NO_VALUE,ifObjName, Tab.noType);
			stackIfElse.push(ifObjName);
			ifCnt++;
			ifObj.setAdr(Code.pc+3);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(1,0);	
		}
	while (!stackConditionEnd.empty()) {
		String endCondition=stackConditionEnd.pop();
		Obj conditionEndObj=Tab.find(endCondition);
		Code.fixup(conditionEndObj.getAdr());

	}
	SyntaxNode statement = conditionTerms;
	while (!UnmatchedStatement.class.isInstance(statement)&&!MatchedStatement.class.isInstance(statement)&&!DoWhileStmt.class.isInstance(statement))
{
statement =statement.getParent();
}

	}
	
		}
public void visit(SingleCondTerm singleCondTerm) 
{
	SyntaxNode parent=singleCondTerm.getParent();
	if ( UnmatchedStatement.class.isInstance(parent)||MatchedStatement.class.isInstance(parent)||DoWhileStmt.class.isInstance(parent)) {
		

		while (!stackCondition.isEmpty()) {
			
			 String condition=stackCondition.pop();
				Obj	conditionObj=Tab.find(condition);
				Code.fixup(conditionObj.getAdr());
		}
	
		if (UnmatchedStatement.class.isInstance(parent)) 
		{
			
			String ifObjName="ifU"+ifCnt;
			Obj ifObj=Tab.insert(Obj.NO_VALUE,ifObjName, Tab.noType);
			stackUnmatched.push(ifObjName);
			ifCnt++;
			ifObj.setAdr(Code.pc+3);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(1,0);
			//ifObj.setAdr(Code.pc);
		}
		else if (MatchedStatement.class.isInstance(parent)) 
		{
		
			String ifObjName="ifM"+ifCnt;
			Obj ifObj=Tab.insert(Obj.NO_VALUE,ifObjName, Tab.noType);
			stackIfElse.push(ifObjName);
			ifCnt++;
			ifObj.setAdr(Code.pc+3);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(1,0);	
		}
	while (!stackConditionEnd.empty()) {
		
		String endCondition=stackConditionEnd.pop();
		Obj conditionEndObj=Tab.find(endCondition);
		Code.fixup(conditionEndObj.getAdr());

	}
	SyntaxNode statement = singleCondTerm;
	while (!UnmatchedStatement.class.isInstance(statement)&&!MatchedStatement.class.isInstance(statement)&&!DoWhileStmt.class.isInstance(statement))
{
statement =statement.getParent();
}
	}
	
		}
	
public void visit(Or or) {
	SyntaxNode statement = or;

//	if (DoWhileStmt.class.isInstance(statement)) {
	while (!stackCondition.isEmpty()) {
		
		 String condition=stackCondition.pop();
			Obj	conditionObj=Tab.find(condition);
			Code.fixup(conditionObj.getAdr());
//	}
	}
		
//	}
//	while (!UnmatchedStatement.class.isInstance(statement)||!MatchedStatement.class.isInstance(statement)||!DoWhileStmt.class.isInstance(statement))
//{
//statement =statement.getParent();
//}
//	if (DoWhileStmt.class.isInstance(statement)) 
//	{
//		
//	}
//		 report_info("or", null);
//		  while (!stackIfElse.empty()) {
//				 String ifObjName=stackIfElse.pop();
//					Obj	ifObj=Tab.find(ifObjName);
//					Code.fixup(ifObj.getAdr());
//		        }	
	}

public void visit(SingleCondFact singleCondFact) 
{
	SyntaxNode parent= singleCondFact.getParent();
	SyntaxNode statement = singleCondFact;
	while (!UnmatchedStatement.class.isInstance(statement)&&!MatchedStatement.class.isInstance(statement)&&!DoWhileStmt.class.isInstance(statement))
{
statement =statement.getParent();
}
//	
//	if(ConditionTerms.class.isInstance(parent)||SingleCondTerm.class.isInstance(parent)) 
//	{
//		if (DoWhileStmt.class.isInstance(statement)) 
//		{
//
//			DoWhileStmt doWhile=(DoWhileStmt) statement;
//			
//		}
//		else if (UnmatchedStatement.class.isInstance(statement)) 
//		{
//			
//		}
//		else if (MatchedStatement.class.isInstance(statement)) 
//		{
//			
//		}
//		
	//}
	if(ConditionTerms.class.isInstance(parent)||SingleCondTerm.class.isInstance(parent)) 
//		{
	{
		
		if (DoWhileStmt.class.isInstance(statement)) 
		{
			DoWhileStmt doWhileStmt=(DoWhileStmt) statement;
			Code.putJump(doWhileStmt.getDo().obj.getAdr());

//			
		}
		else if (UnmatchedStatement.class.isInstance(statement)) 
		{
			String conditionObjName="conditionEnd"+conditionEndCnt;
			Obj conditionObj=Tab.insert(Obj.NO_VALUE,conditionObjName, Tab.noType);
			stackConditionEnd.push(conditionObjName);
			conditionEndCnt++;
			conditionObj.setAdr(Code.pc+3);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(1, 0);
			
		}
		else if (MatchedStatement.class.isInstance(statement)) 
		{
	
			String conditionObjName="conditionEnd"+conditionEndCnt;
			Obj conditionObj=Tab.insert(Obj.NO_VALUE,conditionObjName, Tab.noType);
			stackConditionEnd.push(conditionObjName);
			conditionEndCnt++;
			conditionObj.setAdr(Code.pc+3);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(1, 0);
		}
		
}
}
public void visit(ConditionTerm conditionTerm) 
{
	SyntaxNode parent= conditionTerm.getParent();
	SyntaxNode statement = conditionTerm;
	while (!UnmatchedStatement.class.isInstance(statement)&&!MatchedStatement.class.isInstance(statement)&&!DoWhileStmt.class.isInstance(statement))
{
statement =statement.getParent();
}
//	if(ConditionTerms.class.isInstance(parent)||SingleCondTerm.class.isInstance(parent)) 
//	{
//		
//	}
	if(ConditionTerms.class.isInstance(parent)||SingleCondTerm.class.isInstance(parent)) 
//		{
	{
		if (DoWhileStmt.class.isInstance(statement)) 
		{
			DoWhileStmt doWhileStmt=(DoWhileStmt) statement;
			Code.putJump(doWhileStmt.getDo().obj.getAdr());

//			
		}
		else if (UnmatchedStatement.class.isInstance(statement)) 
		{
		
			String conditionObjName="conditionEnd"+conditionEndCnt;
			Obj conditionObj=Tab.insert(Obj.NO_VALUE,conditionObjName, Tab.noType);
			stackConditionEnd.push(conditionObjName);
			conditionEndCnt++;
			conditionObj.setAdr(Code.pc+3);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(1, 0);
		}
		else if (MatchedStatement.class.isInstance(statement)) 
		{
		
			String conditionObjName="conditionEnd"+conditionEndCnt;
			Obj conditionObj=Tab.insert(Obj.NO_VALUE,conditionObjName, Tab.noType);
			stackConditionEnd.push(conditionObjName);
			conditionEndCnt++;
			conditionObj.setAdr(Code.pc+3);
			Code.loadConst(0);
			Code.loadConst(0);
			Code.putFalseJump(1, 0);
		}
	
}
	
}
public void visit(ConditionFact conditionFact) 
{

	SyntaxNode statement = conditionFact;
	while (!UnmatchedStatement.class.isInstance(statement)&&!MatchedStatement.class.isInstance(statement)&&!DoWhileStmt.class.isInstance(statement))
{
statement =statement.getParent();
}


	if (DoWhileStmt.class.isInstance(statement)) 
	{
	
		DoWhileStmt doWhile=(DoWhileStmt) statement;
		String conditionObjName="condition"+conditionCnt;
		Obj conditionObj=Tab.insert(Obj.NO_VALUE,conditionObjName, Tab.noType);
		stackCondition.push(conditionObjName);
	    conditionCnt++;
		conditionObj.setAdr(Code.pc+1);
		if (RelopEqual.class.isInstance(conditionFact.getRelop())) 
		{
			Code.putFalseJump(Code.eq, 0);
		}
		else if (RelopNotEqual.class.isInstance(conditionFact.getRelop())) 
		{
			Code.putFalseJump(Code.ne, 0);
		}
		else if (RelopGreater.class.isInstance(conditionFact.getRelop())) 
		{
			Code.putFalseJump(Code.gt, 0);
		}
		else if (RelopGTE.class.isInstance(conditionFact.getRelop())) 
		{
			Code.putFalseJump(Code.ge,0);
		}
		else if (RelopLess.class.isInstance(conditionFact.getRelop())) 
		{
			Code.putFalseJump(Code.lt, 0);
		}
		else if (RelopLTE.class.isInstance(conditionFact.getRelop())) 
		{
			Code.putFalseJump(Code.le, 0);
		}
         
//			Code.putJump(doWhile.getDo().obj.getAdr());
		
	}
	else if (MatchedStatement.class.isInstance(statement)) 
	{
	
		
		
//			String ifObjName="ifM"+ifCnt;
//			Obj ifObj=Tab.insert(Obj.NO_VALUE,ifObjName, Tab.noType);
//			stackIfElse.push(ifObjName);
//			ifCnt++;
//			ifObj.setAdr(Code.pc+1);
		String conditionObjName="condition"+conditionCnt;
		Obj conditionObj=Tab.insert(Obj.NO_VALUE,conditionObjName, Tab.noType);
		stackCondition.push(conditionObjName);
	    conditionCnt++;
		conditionObj.setAdr(Code.pc+1);
			if (RelopEqual.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.eq, 0);
			}
			else if (RelopNotEqual.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.ne, 0);
			}
			else if (RelopGreater.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.gt, 0);
			}
			else if (RelopGTE.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.ge, 0);
			}
			else if (RelopLess.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.lt, 0);
			}
			else if (RelopLTE.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.le, 0);
			}
			
//			Code.loadConst(0);
//			Code.loadConst(0);
//			Code.putFalseJump(0, 0);
		
		
	}
	else if (UnmatchedStatement.class.isInstance(statement)) 
	{
		
	
			
//			String ifObjName="ifU"+ifCnt;
//			Obj ifObj=Tab.insert(Obj.NO_VALUE,ifObjName, Tab.noType);
//			stackUnmatched.push(ifObjName);
//			ifCnt++;
//			ifObj.setAdr(Code.pc+1);
		String conditionObjName="condition"+conditionCnt;
		Obj conditionObj=Tab.insert(Obj.NO_VALUE,conditionObjName, Tab.noType);
		stackCondition.push(conditionObjName);
	    conditionCnt++;
		conditionObj.setAdr(Code.pc+1);
			if (RelopEqual.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.eq, 0);
			}
			else if (RelopNotEqual.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.ne, 0);
			}
			else if (RelopGreater.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.gt, 0);
			}
			else if (RelopGTE.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.ge, 0);
			}
			else if (RelopLess.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.lt, 0);
			}
			else if (RelopLTE.class.isInstance(conditionFact.getRelop())) 
			{
				Code.putFalseJump(Code.le, 0);
			}
		}
	
}
public void visit(CondFactExpr conditionFact) 
{


	SyntaxNode statement = conditionFact;
	while (!UnmatchedStatement.class.isInstance(statement)&&!MatchedStatement.class.isInstance(statement)&&!DoWhileStmt.class.isInstance(statement))
{
statement =statement.getParent();
}
	
	
		
		
		TermExpression termExpr =(TermExpression) conditionFact.getExpr();
		SingleFactor singleFactor=(SingleFactor) termExpr.getTerm();
//		BoolConstFactor boolConstFactor= (BoolConstFactor) singleFactor.getFactor();
		if (DoWhileStmt.class.isInstance(statement)) 
		{
		
//			DoWhileStmt doWhile=(DoWhileStmt) statement;
//			Code.put(Code.pop);
//			if (boolConstFactor.getValue().equals("false")) 
//			{
				String conditionObjName="condition"+conditionCnt;
				Obj conditionObj=Tab.insert(Obj.NO_VALUE,conditionObjName, Tab.noType);
				stackCondition.push(conditionObjName);
			    conditionCnt++;
				conditionObj.setAdr(Code.pc+2);
//				Code.loadConst(0);
				Code.loadConst(0);
				Code.putFalseJump(1, 0);

		
				
			//}
		}
		else if (MatchedStatement.class.isInstance(statement)) 
		{
	
		//	Code.put(Code.pop);
		//	if (boolConstFactor.getValue().equals("false")) 
		//	{
			
			
//				String ifObjName="ifM"+ifCnt;
//				Obj ifObj=Tab.insert(Obj.NO_VALUE,ifObjName, Tab.noType);
//				stackIfElse.push(ifObjName);
//				ifCnt++;
//				ifObj.setAdr(Code.pc+3);
				String conditionObjName="condition"+conditionCnt;
				Obj conditionObj=Tab.insert(Obj.NO_VALUE,conditionObjName, Tab.noType);
				stackCondition.push(conditionObjName);
			    conditionCnt++;
				conditionObj.setAdr(Code.pc+2);

				
				Code.loadConst(0);
				Code.putFalseJump(1, 0);
			}
			
		//}
		else if (UnmatchedStatement.class.isInstance(statement)) 
		{
		
		//	Code.put(Code.pop);
		//	if (boolConstFactor.getValue().equals("false")) 
			//{
		
//				
//				String ifObjName="ifU"+ifCnt;
//				Obj ifObj=Tab.insert(Obj.NO_VALUE,ifObjName, Tab.noType);
//				stackUnmatched.push(ifObjName);
//				ifCnt++;
//				ifObj.setAdr(Code.pc+3);
				
				String conditionObjName="condition"+conditionCnt;
				Obj conditionObj=Tab.insert(Obj.NO_VALUE,conditionObjName, Tab.noType);
				stackCondition.push(conditionObjName);
			    conditionCnt++;
				conditionObj.setAdr(Code.pc+2);
				
				
				Code.loadConst(0);
				Code.putFalseJump(1, 0);
			}
	//	}


}
public void visit(BreakStmt breakStmt) 
{
	
	String breakObjName="break"+breakCnt;
	Obj breakObj=Tab.insert(Obj.NO_VALUE,breakObjName, Tab.noType);
	stackDoWhile.push(breakObjName);
    breakCnt++;
	breakObj.setAdr(Code.pc+3);
	
	Code.loadConst(0);
	Code.loadConst(0);
	Code.putFalseJump(1, 0);
}

public void visit(ContinueStmt continueStmt)
{
	String continueObjName="continue"+breakCnt;
	Obj continueObj=Tab.insert(Obj.NO_VALUE,continueObjName, Tab.noType);
	stackContinue.push(continueObjName);
    breakCnt++;
	continueObj.setAdr(Code.pc+3);
	
	Code.loadConst(0);
	Code.loadConst(0);
	Code.putFalseJump(1, 0);

}
public void visit(While while1) 
{
	while(!stackContinue.isEmpty()) 
	{
		 String continueeObjName=stackContinue.pop();
			Obj	continueObj=Tab.find(continueeObjName);
			Code.fixup(continueObj.getAdr());	
	}
}

}
