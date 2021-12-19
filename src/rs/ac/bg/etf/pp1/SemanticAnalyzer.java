package rs.ac.bg.etf.pp1;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.structure.SymbolDataStructure;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {

    int elemCnt=0;
	int printCallCount = 0;
	int varDeclCount = 0;
	int nVars;
	Obj currentMethod = null;
	Obj currentRecord =null;
	Obj currentClass = null;
	SymbolTableVisitor stv;
	boolean returnFound=false;
	boolean errorDetected=false;
	boolean currentStatementDoWhile=false;
	ArrayList<String> labels= new ArrayList<>();
	Logger log = Logger.getLogger(getClass());
	private String printNode(Obj node) 
	{
		stv=new DumpSymbolTableVisitor();
		stv.visitObjNode(node);
		return stv.getOutput();
		}
	public void report_error(String message, SyntaxNode info) {
		errorDetected=true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public boolean passed() 
	{
		return !errorDetected;
	}
	public void visit(VariableDeclaration variableDeclaration){
	    varDeclCount++;
	
	}
	
//*************************************************************************
	
//*************************************************************************
//Program
	
	public void visit(ProgName progName ) 
	{
		progName.obj=Tab.insert(Obj.Prog, progName.getProgramName(), Tab.noType);
		Tab.openScope();
	    Struct boolType=new Struct(Struct.Bool);
		Tab.insert(Obj.Type, "bool",boolType);
	
	}
	public void visit(Program program) 
	{
		nVars=Tab.currentScope.getnVars();
		boolean hasMain=false;
		Tab.chainLocalSymbols(program.getProgName().obj);
		SymbolDataStructure symbolDataStructure=	Tab.currentScope.getLocals();
		Obj objArray[] = new Obj[symbolDataStructure.symbols().size()];
		Collection<Obj> collection=symbolDataStructure.symbols();
		collection.toArray(objArray);

		  for (int i=0;i<objArray.length;i++) 
		  {
			    if (objArray[i].getKind()==Obj.Meth) {
			    if(objArray[i].getName().equals("main")) 
			    {
			    	hasMain=true;
			    }

			    }
		  }
		  if (!hasMain) 
		  {
			  report_error("Greska nije pronadjena funkcija main!", null);
		  }
		  
		Tab.closeScope();
	}
	
//*************************************************************************
	
//*************************************************************************
//Deklaracije promenljivih
	
	public void visit(SingleVarDeclaration singleVarDeclaration)
	{
		
		SyntaxNode syntaxNode2=singleVarDeclaration.getBrackets();
	SyntaxNode syntaxNode=singleVarDeclaration;
		
		while (syntaxNode.getClass()!=VariableDeclaration.class) 
		{
			syntaxNode = syntaxNode.getParent();
		}
		if (Tab.find(singleVarDeclaration.getVarName())!=Tab.noObj)
		{
			report_error("Greska promenljiva "+ singleVarDeclaration.getVarName()+" je vec deklarisana", singleVarDeclaration);
			return;
		}
		VariableDeclaration varDecl =(VariableDeclaration) syntaxNode;
		   if(WithBrackets.class.isInstance(syntaxNode2)) 
		 	{
		 		  
		 		  
		 		 if (varDecl.getType().struct.getKind()==Struct.Class) 
				   {
		 			   Obj varNode = Tab.insert(Obj.Var, singleVarDeclaration.getVarName(), new Struct(Struct.Array, varDecl.getType().struct));
		 			  report_info("Deklarisana niz promenljiva "+varNode.getName()+" "+ printNode(varNode)+singleVarDeclaration.getVarName(), singleVarDeclaration);
					   Obj classObj = Tab.find(varDecl.getType().getTypeName());
					  Tab.openScope();
					  for (Obj obj : classObj.getLocalSymbols()) 
					  {
						  Tab.currentScope().addToLocals(obj);  
					  }
					  varNode.setLocals(Tab.currentScope.getLocals());
					  Tab.closeScope();
				   }
		 		 else {
		 		 if (currentRecord==null) {
		 	     Obj varNode = Tab.insert(Obj.Var, singleVarDeclaration.getVarName(),new Struct(Struct.Array, varDecl.getType().struct));
		 	    report_info("Deklarisana niz promenljiva "+varNode.getName()+" "+ printNode(varNode)+singleVarDeclaration.getVarName(), singleVarDeclaration);
		 		 }
		 		 else 
		 		 {
		 			 Obj varNode = Tab.insert(Obj.Fld, singleVarDeclaration.getVarName(),new Struct(Struct.Array, varDecl.getType().struct));
		 			report_info("Deklarisana nizovsko polje rekorda "+varNode.getName()+" "+ printNode(varNode)+singleVarDeclaration.getVarName(), singleVarDeclaration);
		 		 }
		 		 }
		 
		 	}
		   else 
		   {
			//   report_info("Deklarisana promenljiva "+ singleVarDeclaration.getVarName(), singleVarDeclaration);
			   if (varDecl.getType().struct.getKind()==Struct.Class) 
			   {
				     Obj varNode = Tab.insert(Obj.Var, singleVarDeclaration.getVarName(), varDecl.getType().struct);
				     report_info("Deklarisana promenljiva "+varNode.getName()+" " +printNode(varNode), singleVarDeclaration);
				   Obj classObj = Tab.find(varDecl.getType().getTypeName());
				  Tab.openScope();
				  for (Obj obj : classObj.getLocalSymbols()) 
				  {
					  Tab.currentScope().addToLocals(obj);  
				  }
				  varNode.setLocals(Tab.currentScope.getLocals());
				  Tab.closeScope();

			   }
			   else {
				   if(currentRecord==null) {
			     Obj varNode = Tab.insert(Obj.Var, singleVarDeclaration.getVarName(), varDecl.getType().struct);
			     report_info("Deklarisana promenljiva "+varNode.getName()+" " +printNode(varNode), singleVarDeclaration);
				   }
				   
				   else 
			 		 {
			 			 Obj varNode = Tab.insert(Obj.Fld, singleVarDeclaration.getVarName(), varDecl.getType().struct);
			 			  report_info("Deklarisano polje rekorda "+varNode.getName()+" "+ printNode(varNode), singleVarDeclaration);
			 		 }
			   } 
			   
		   }

  
	}
//Polja klase
	public void visit(SingleClassVarDeclaration singleClassVar) 
	{
	
		SyntaxNode syntaxNode2=singleClassVar.getBrackets();
		SyntaxNode syntaxNode=singleClassVar;
		
		while (syntaxNode.getClass()!=ClassVarDeclaration.class) 
		{
			syntaxNode = syntaxNode.getParent();
		}
		if (Tab.find(singleClassVar.getVarName())!=Tab.noObj) 
		{
			report_error("Greska polje "+ singleClassVar.getVarName()+" je vec deklarisano", singleClassVar);
			return;
		}
		ClassVarDeclaration classVarDeclaration = (ClassVarDeclaration) syntaxNode;

		   if(WithBrackets.class.isInstance(syntaxNode2)) 
			 	{
				
			 	     Obj varNode = Tab.insert(Obj.Fld, singleClassVar.getVarName(),new Struct(Struct.Array, classVarDeclaration.getType().struct));
			 	     report_info("Deklarisano nizovsko polje "+ singleClassVar.getVarName()+" "+ printNode(varNode), singleClassVar);
			 	}
			   else 
			   {
					
					Obj varNode = Tab.insert(Obj.Fld, singleClassVar.getVarName(), classVarDeclaration.getType().struct);
					report_info("Deklarisano polje "+ singleClassVar.getVarName()+" "+ printNode(varNode), singleClassVar);
				   
			   }

	
	}
	

	public void visit(Type type) 
	{
		if(type.getParent().getClass()!=ClassName.class && type.getParent().getClass()!=RecordName.class) {
	Obj typeNode =	Tab.find(type.getTypeName());
	if (typeNode == Tab.noObj) 
	{
		report_error("Nije pronadjen tip " + type.getTypeName()+" u tabeli simbola!", null);
		type.struct = Tab.noType;
	}
	else 
	{
		if (Obj.Type == typeNode.getKind()) 
		{
			type.struct = typeNode.getType();
		}
		else 
		{
			report_error("Greska: Ime " + type.getTypeName()+" ne predstavlja tip!", type);
			type.struct = Tab.noType;
		}
	}
		}
	}
	
//*************************************************************************
	
//*************************************************************************	
//Deklarisanje metoda
	
	public void visit(MethodDeclType methodDeclType) 
	{
		MethodDeclaration methodDecl=(MethodDeclaration) methodDeclType.getParent();
	
		Obj obj = Tab.find(methodDeclType.getMethodName());
			if(obj==Tab.noObj) 
			{
		currentMethod =  Tab.insert(Obj.Meth,methodDeclType.getMethodName() , methodDeclType.getType().struct);
		report_info("Obradjuje se funkcija "+methodDeclType.getMethodName()+" "+printNode(currentMethod), methodDeclType );
		methodDeclType.obj = currentMethod;
		labels.clear();
		Tab.openScope();
			}
			else 
			{
				if(obj.getKind()==Obj.Meth) {
				//	if(sameSignature(obj,methodDecl.getFormParamList() )&&methodDeclType.getType().struct==obj.getType()) {
				 report_error("Funkcija "+methodDeclType.getMethodName()+" je vec deklarisana unutar opsega!",methodDeclType);	//}
				}
			}
		
	
		
	}
	public void visit(MethodVoidType methodVoidType) 
	{
		MethodDeclaration methodDecl=(MethodDeclaration) methodVoidType.getParent();
		
		Obj obj = Tab.find(methodVoidType.getMethodName());
			if(obj==Tab.noObj) 
			{
		currentMethod = Tab.insert(Obj.Meth, methodVoidType.getMethodName(), Tab.noType);
		 report_info("Obradjuje se funkcija "+methodVoidType.getMethodName()+" "+printNode(currentMethod),methodVoidType);
	methodVoidType.obj = currentMethod;
	labels.clear();
   	 Tab.openScope();
			}
			else 
			{
				if(obj.getKind()==Obj.Meth) {
					//if(sameSignature(obj,methodDecl.getFormParamList() )&&Tab.noType==obj.getType()) {
				 report_error("Funkcija "+methodVoidType.getMethodName()+" je vec deklarisana unutar opsega!",methodVoidType);	//}
				}	
			}
		
   	
	}
	public void visit(MethodDeclaration methodDeclaration) 
	{
		if(currentMethod!=null) {
		if (!returnFound && currentMethod.getType()!= Tab.noType) 
		{
			report_error("Semanticka greska na liniji "+methodDeclaration.getLine()+": funkcija "+currentMethod.getName()+" nema return iskaz!",null);
		}
		Tab.chainLocalSymbols(currentMethod);
		//Provera labela
		for (String labelName : labels) 
		{
			Obj obj = Tab.find(labelName);
			if(obj==Tab.noObj)
			{
				report_error("Semanticka greska metoda "+currentMethod.getName()+" ne sadrzi labelu "+labelName,null);	
			}
			
		}
		Tab.closeScope();
		returnFound=false;
		currentMethod=null;
		}
	}
	public void visit(MethodVoidName methodVoidName) 
	{
		MethodDeclVoid methodDecl=(MethodDeclVoid) methodVoidName.getParent();
		 
		Obj obj = Tab.find(methodVoidName.getMethodName());
			if(obj==Tab.noObj) 
			{
				
		currentMethod = Tab.insert(Obj.Meth,methodVoidName.getMethodName(), Tab.noType);
		report_info("Obradjuje se funkcija "+methodVoidName.getMethodName()+" "+printNode(currentMethod),methodVoidName);
		methodVoidName.obj = currentMethod;
		Tab.openScope();
		
			}
			else 
			{
				if(obj.getKind()==Obj.Meth) {
					if(sameSignature(obj,methodDecl.getFormParamList() )&&Tab.noType==obj.getType()) {
				 report_error("Funkcija "+methodVoidName.getMethodName()+" je vec deklarisana unutar opsega!",methodVoidName);	}
				}	
			}
		
	}
	public void visit(MethodName methodName) 
	{
		MethodDeclNoId methodDecl=(MethodDeclNoId) methodName.getParent();
		
		//ClassBodyWithConstructor
		 
			Obj obj = Tab.find(methodName.getMethodName());	 
			ClassBodyWithConstructor classBodyWithConstructor=null;

			SyntaxNode syntaxNode=methodName;
			
			while (syntaxNode.getClass()!=ClassBodyWithConstructor.class) 
			{
				syntaxNode = syntaxNode.getParent();
			}
		 classBodyWithConstructor =  (ClassBodyWithConstructor) syntaxNode;
				if(obj==Tab.noObj) 
				{
					currentMethod = Tab.insert(Obj.Meth,methodName.getMethodName(), classBodyWithConstructor.getType().struct);
					report_info("Obradjuje se funkcija "+methodName.getMethodName()+" "+printNode(currentMethod),methodName);
					methodName.obj = currentMethod;
					labels.clear();
					Tab.openScope();
				}
				else 
				{
					if(obj.getKind()==Obj.Meth) {
						if(sameSignature(obj,methodDecl.getFormParamList() )&&classBodyWithConstructor.getType().struct==obj.getType()) {
					 report_error("Funkcija "+methodName.getMethodName()+" je vec deklarisana unutar opsega!",methodName);	}
					}
				}

	}
	public void visit(MethodDeclVoid methodDeclVoid) 
	{
	   if(currentMethod!=null) {
		Tab.chainLocalSymbols(currentMethod);
		//Provera labela
		for (String labelName : labels) 
		{
			Obj obj = Tab.find(labelName);
			if(obj==Tab.noObj)
			{
				report_error("Semanticka greska metoda "+currentMethod.getName()+" ne sadrzi labelu "+labelName,null);	
			}
			
		}
		Tab.closeScope();
		currentMethod=null;
		returnFound=false;
	   }
	}
	public void visit(MethodDeclNoId methodDeclNoId) 
	{
		if (currentMethod!=null) {
		if (!returnFound ) 
		{
			report_error("Semanticka greska na liniji "+methodDeclNoId.getLine()+": funkcija "+currentMethod.getName()+" nema return iskaz!",null);
		}
		Tab.chainLocalSymbols(currentMethod);
		//Provera labela
		for (String labelName : labels) 
		{
			Obj obj = Tab.find(labelName);
			if(obj==Tab.noObj)
			{
				report_error("Semanticka greska metoda "+currentMethod.getName()+" ne sadrzi labelu "+labelName,null);	
			}
			
		}
		Tab.closeScope();
		returnFound=false;
		currentMethod=null;
		}
	}
	
//*************************************************************************
	
//*************************************************************************

	public void visit(SingleDesignator singleDesignator) 
	{
		boolean isfunction=false;
		Obj obj =Tab.find(singleDesignator.getName());
		SyntaxNode node=singleDesignator.getParent();
		   singleDesignator.obj=obj;
		if(DesignatorStatmentNoError.class.isInstance(node)) 
		{
			DesignatorStatmentNoError designatorStatement=(DesignatorStatmentNoError) node;
			DesignatorOperation designatorOp=designatorStatement.getDesignatorOperation();
			if (DesignatorFuncCall.class.isInstance(designatorOp)) 
			{
				isfunction=true;
			}
			}
			if (obj==Tab.noObj) 
			{
			singleDesignator.obj=obj;
			report_error("Greska na liniji "+singleDesignator.getLine()+" :ime "+singleDesignator.getName()+" nije deklarisano!", null);	
			}
			else 
			{
			node =singleDesignator.getParent();
			if (!FunctionCall.class.isInstance(node)&&!isfunction) 
			{
				if (!DesignatorArray.class.isInstance(singleDesignator.getParent())&&!DesignatorDot.class.isInstance(singleDesignator.getParent()))
				report_info("Pristupa se promenljivoj "+singleDesignator.getName()+" "+ printNode(singleDesignator.obj),singleDesignator);
			
		
			
			}
			}
        
	}
	
//*************************************************************************
	
//*************************************************************************
//Pristup elementu niza
	public void visit(DesignatorDot designatorDot) 
	{
		boolean isfunction=false;
		SyntaxNode node=designatorDot.getParent();
		Obj parObj=designatorDot.getDesignator().obj;
		Obj tempObj;
		
		if(DesignatorStatmentNoError.class.isInstance(node)) 
		{
			DesignatorStatmentNoError designatorStatement=(DesignatorStatmentNoError) node;
			DesignatorOperation designatorOp=designatorStatement.getDesignatorOperation();
			if (DesignatorFuncCall.class.isInstance(designatorOp)) 
			{
				isfunction=true;
			}
			}
			if (parObj==Tab.noObj) 
			{
				designatorDot.obj=Tab.noObj;
				report_error("Ne postoji polje  "+designatorDot.getName()+" klase "+parObj.getName(), designatorDot );	
				return;
			}
			if (!FunctionCall.class.isInstance(node)&&!isfunction) 
			{
			
				Obj fieldObj=null;
				Obj[] objs= new Obj[parObj.getType().getMembers().size()];
				
				parObj.getType().getMembers().toArray(objs);
				boolean found=false;
				for (Obj obj : objs) 
				{
				
					if (obj.getName().equals(designatorDot.getName())) 
					{
//						report_info(obj.getName(), null);
						found=true;
						designatorDot.obj=obj;
						
					}
				}
				if (found) 
				{
					report_info("Pristupa se polju "+designatorDot.getName()+" klase "+parObj.getName()+ " " + printNode(designatorDot.obj), designatorDot );	
				}
				else 
				{
					designatorDot.obj=Tab.noObj;
					report_error("Ne postoji polje  "+designatorDot.getName()+" klase "+parObj.getName(), designatorDot );	
				}

		
				 
			}
			else 
			{
				Obj fieldObj=null;
				Obj[] objs= new Obj[parObj.getType().getMembers().size()];
				
				parObj.getType().getMembers().toArray(objs);
				boolean found=false;
				for (Obj obj : objs) 
				{
				
					if (obj.getName().equals(designatorDot.getName())) 
					{
//						report_info(obj.getName(), null);
						found=true;
						designatorDot.obj=obj;
						
					}
				}
			}
	
	
		
		
		
	}
	public void visit(DesignatorArray designatorArray) 
	{
	
		
		Obj parentObj=designatorArray.getDesignator().obj;
		
		    if (parentObj!=Tab.noObj) 
		   	{
		    	if (parentObj.getType().getKind()==Struct.Array) 
		    	{
		    	
		    		if (designatorArray.getExpr().struct!=Tab.intType) 
		    		{
		    			report_error("Greska na liniji "+designatorArray.getLine()+" index nije celobrojna vrednost! ", null);
		    		}
		    		 designatorArray.obj=Tab.insert(Obj.Elem, "$"+elemCnt,parentObj.getType().getElemType());
		    			report_info("Pristupa se elementu niza "+parentObj.getName()+" "+printNode(designatorArray.obj),designatorArray );
    				elemCnt++;
		         
		    	}
		    	else 
		    	{
		    		designatorArray.obj=Tab.noObj;
		    		report_error("Greska na liniji "+designatorArray.getLine()+" promenljiva "+parentObj.getName()+" nije nizovnog tipa!", null);
		    	}
			
		
		}
		    		    else {  designatorArray.obj=Tab.noObj; report_error("Neispravan designator", null);}
		    
		

	    
		
	}

//*************************************************************************
	
//*************************************************************************
//Pozivi funkcija
	public void visit(FunctionCall functionCall) 
	{
		FactorActPars factorAct= (FactorActPars) functionCall.getFactorPars();
		if (functionCall.getDesignator().obj.getType()==Tab.noType) 
		{
			 report_error("Greska na liniji "+ functionCall.getLine()+" funkcija void tipa ne moze se koristiti kao faktor!", null);	 
		}
	
	      if (DesignatorDot.class.isInstance(functionCall.getDesignator())) 
	      {
	    	  DesignatorDot designatorDot=(DesignatorDot) functionCall.getDesignator();
	    Obj classObj=	designatorDot.getDesignator().obj;
	    String funcName=designatorDot.getName();
		Obj funcObj=null;
		Obj [] objs=new Obj[classObj.getType().getMembers().size()];
			 classObj.getType().getMembers().toArray(objs);
		boolean found=false;
		for (Obj obj : objs) 
		{
			if (obj.getName().equals(funcName)&&obj.getKind()==Obj.Meth) 
			{
				found=true;
				funcObj=obj;
				 designatorDot.obj=obj;
				 functionCall.getDesignator().obj=obj;
					
			}
		 }
		if (found) {
			//Form Actual Paramas--------------------------------------------------------------------------------------------------------
			
			Collection<Obj> collection=	funcObj.getLocalSymbols();
		     Obj[]  objArray= new Obj[collection.size()];
		     collection.toArray(objArray);
		     ArrayList<Obj> formParams=new ArrayList<>();
		     ArrayList<Struct> structs= new ArrayList<>();
		     for (Obj obj :objArray) 
		     {
		    	if (obj.getFpPos()!=0) 
		    	{
		    		formParams.add(obj);
		    	} 
		     }
		     if (ActualParsList.class.isInstance(factorAct.getActParsList())) 
			 {
		    	 ActualParsList actualParsList=(ActualParsList) factorAct.getActParsList();
		    	 SyntaxNode node = actualParsList.getActPars();
		    	 while (!SingleExpr.class.isInstance(node)) 
		    	 {
		    	ActualPars actualPars=(ActualPars)	node;
		    	 structs.add(actualPars.getExpr().struct);
		    	  node=actualPars.getActPars();
		    	 }
		    	  SingleExpr single = (SingleExpr ) node;
		    	 structs.add(single.getExpr().struct);
		    	 if (structs.size()!=formParams.size()) 
		    	 {
		    			report_error("Greska na liniji "+ designatorDot.getLine()+" funkcija: "+designatorDot.getName()+" nije pozvana sa potrebnim parametrima!", null); 
		    	 }
		    	 else 
		    	 {
		    		 int i=structs.size()-1;
		    		 boolean same=true;
		    		 for (Struct st : structs) 
		    		 {
		    			 if (!st.equals(formParams.get(i).getType()))
                             {
		    				 same=false;
		    				 break;
                             	}
		    			 i--;
		    		 }
		    		 if (!same) 
		    		 {
		    			 report_error("Greska na liniji "+ designatorDot.getLine()+" funkcija: "+designatorDot.getName()+" nije pozvana sa potrebnim parametrima!", null); 
		    		 }
		    	 }
			 }
		     else 
		     {
		    	 if (!formParams.isEmpty()) 
		    	 {
		    			report_error("Greska na liniji "+ designatorDot.getLine()+" funkcija: "+designatorDot.getName()+" nije pozvana sa potrebnim parametrima!", null);
		    	 }
		     }
		     
			
		   //Form Actual Paramas--------------------------------------------------------------------------------------------------------
			
			report_info("Pronadjen poziv funkcije "+designatorDot.getName()+" na liniji "+functionCall.getLine(), null);
			
			functionCall.struct=funcObj.getType();
			}
			else 
			{
				report_error("Greska na liniji "+ functionCall.getLine()+" ime: "+designatorDot.getName()+" nije funkcija!", null);
				functionCall.struct=Tab.noType;
			}
         }
		
	    		
		else {
			SingleDesignator singleDesignator=(SingleDesignator) functionCall.getDesignator();
			String funcName=singleDesignator.getName();
			
			Obj baseObj=Tab.find(funcName);
			
			if (baseObj==Tab.noObj) 
			{
				report_error("Greska na liniji "+ functionCall.getLine()+" ime: "+singleDesignator.getName()+" nije funkcija!", null);
				functionCall.struct=Tab.noType;
				return;
			}
			
			if (Obj.Meth==baseObj.getKind()) 
			{
				singleDesignator.obj=baseObj;
				functionCall.getDesignator().obj=baseObj;
			
				//CHR ORD LEN--------------------------------------------------------------------------------------------------
				if (baseObj==Tab.chrObj || baseObj==Tab.lenObj||baseObj==Tab.ordObj) 
				{
				
				 if (ActualParsList.class.isInstance(factorAct.getActParsList())) 
				 {
					 ActualParsList actualParsList=(ActualParsList) factorAct.getActParsList();
					 if(SingleExpr.class.isInstance(actualParsList.getActPars())) 
					 {
						SingleExpr singleExpr=(SingleExpr) actualParsList.getActPars();
						if (baseObj==Tab.chrObj&&singleExpr.getExpr().struct.getKind()!=Struct.Int) 
						{
							 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" je pozvana sa pogresnim parametrima!", null);	
						}
						if (baseObj==Tab.ordObj&&singleExpr.getExpr().struct.getKind()!=Struct.Char) 
						{
							 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" je pozvana sa pogresnim parametrima!", null);	
						}
						if (baseObj==Tab.lenObj&&(singleExpr.getExpr().struct.getKind()!=Struct.Array)) 
						{
							 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" je pozvana sa pogresnim parametrima!", null);		
						}
					 }
					 else 
					 {
						 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" je pozvana sa pogresnim parametrima!", null);
					 }
				 }
				 else {
						report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" je pozvana sa pogresnim parametrima!", null);
				 }
				}
				//CHR ORD LEN--------------------------------------------------------------------------------------------------
				//Form Actual Paramas--------------------------------------------------------------------------------------------------------
				else {
				Collection<Obj> collection=	baseObj.getLocalSymbols();
			     Obj[]  objArray= new Obj[collection.size()];
			     collection.toArray(objArray);
			     ArrayList<Obj> formParams=new ArrayList<>();
			     ArrayList<Struct> structs= new ArrayList<>();
			     for (Obj obj :objArray) 
			     {
			    	if (obj.getFpPos()!=0) 
			    	{
			    		formParams.add(obj);
			    	} 
			     }
			     if (ActualParsList.class.isInstance(factorAct.getActParsList())) 
				 {
			    	 ActualParsList actualParsList=(ActualParsList) factorAct.getActParsList();
			    	 SyntaxNode node = actualParsList.getActPars();
			    	 while (!SingleExpr.class.isInstance(node)) 
			    	 {
			    	ActualPars actualPars=(ActualPars)	node;
			    	 structs.add(actualPars.getExpr().struct);
			    	  node=actualPars.getActPars();
			    	 }
			    	  SingleExpr single = (SingleExpr ) node;
			    	 structs.add(single.getExpr().struct);
			    	 if (structs.size()!=formParams.size()) 
			    	 {
			    		 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" nije pozvana sa potrebnim parametrima!"+" "+structs.size()+" "+formParams.size(), null); 
			    	 }
			    	 else 
			    	 {
			    		 int i=structs.size()-1;
			    		 boolean same=true;
			    		 for (Struct st : structs) 
			    		 {
			    			 if (!st.equals(formParams.get(i).getType()))
                                 {
			    				 same=false;
			    				 break;
                                 	}
			    			 i--;
			    		 }
			    		 if (!same) 
			    		 {
			    			 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" nije pozvana sa potrebnim parametrima!", null); 
			    		 }
			    	 }
				 }
			     else 
			     {
			    	 if (!formParams.isEmpty()) 
			    	 {
			    			report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" nije pozvana sa potrebnim parametrima!", null);
			    	 }
			     }
			     
				}
			   //Form Actual Paramas--------------------------------------------------------------------------------------------------------
				report_info("Pronadjen poziv funkcije "+funcName+" na liniji "+singleDesignator.getLine(), null);
				functionCall.struct=baseObj.getType();
			}
			else 
			{
				report_error("Greska na liniji "+ singleDesignator.getLine()+" ime: "+funcName+" nije funkcija!", null);
				functionCall.struct=Tab.noType;
			}
			
		}
	
		
	
	}
	public void visit(DesignatorFuncCall functionCall) 
	{
		
		DesignatorStatmentNoError designatorStatement = (DesignatorStatmentNoError) functionCall.getParent();

		//FactorActPars factorAct= (FactorActPars) functionCall.getFactorPars();
	
	
	      if (DesignatorDot.class.isInstance(designatorStatement.getDesignator())) 
	      {
	    	  DesignatorDot designatorDot=(DesignatorDot) designatorStatement.getDesignator();
	    Obj classObj=	designatorDot.getDesignator().obj;
	    String funcName=designatorDot.getName();
		Obj funcObj=null;
		Obj [] objs=new Obj[classObj.getType().getMembers().size()];
			 classObj.getType().getMembers().toArray(objs);
		boolean found=false;
		for (Obj obj : objs) 
		{
			if (obj.getName().equals(funcName)&&obj.getKind()==Obj.Meth) 
			{
				found=true;
				funcObj=obj;
			  designatorDot.obj=obj;
			  designatorStatement.getDesignator().obj=obj;
				
			}
		 }
		if (found) {
			//Form Actual Paramas--------------------------------------------------------------------------------------------------------
			
			Collection<Obj> collection=	funcObj.getLocalSymbols();
		     Obj[]  objArray= new Obj[collection.size()];
		     collection.toArray(objArray);
		     ArrayList<Obj> formParams=new ArrayList<>();
		     ArrayList<Struct> structs= new ArrayList<>();
		     for (Obj obj :objArray) 
		     {
		    	if (obj.getFpPos()!=0) 
		    	{
		    		formParams.add(obj);
		    	} 
		     }
		     if (ActualParsList.class.isInstance(functionCall.getActParsList())) 
			 {
		    	 ActualParsList actualParsList=(ActualParsList) functionCall.getActParsList();
		    	 SyntaxNode node = actualParsList.getActPars();
		    	 while (!SingleExpr.class.isInstance(node)) 
		    	 {
		    	ActualPars actualPars=(ActualPars)	node;
		    	 structs.add(actualPars.getExpr().struct);
		    	  node=actualPars.getActPars();
		    	 }
		    	  SingleExpr single = (SingleExpr ) node;
		    	 structs.add(single.getExpr().struct);
		    	 if (structs.size()!=formParams.size()) 
		    	 {
		    			report_error("Greska na liniji "+ designatorDot.getLine()+" funkcija: "+designatorDot.getName()+" nije pozvana sa potrebnim parametrima!", null); 
		    	 }
		    	 else 
		    	 {
		    		 int i=structs.size()-1;
		    		 boolean same=true;
		    		 for (Struct st : structs) 
		    		 {
		    			 if (!st.equals(formParams.get(i).getType()))
                             {
		    				 same=false;
		    				 break;
                             	}
		    			 i--;
		    		 }
		    		 if (!same) 
		    		 {
		    			 report_error("Greska na liniji "+ designatorDot.getLine()+" funkcija: "+designatorDot.getName()+" nije pozvana sa potrebnim parametrima!", null); 
		    		 }
		    	 }
			 }
		     else 
		     {
		    	 if (!formParams.isEmpty()) 
		    	 {
		    			report_error("Greska na liniji "+ designatorDot.getLine()+" funkcija: "+designatorDot.getName()+" nije pozvana sa potrebnim parametrima!", null);
		    	 }
		     }
		     
			
		   //Form Actual Paramas--------------------------------------------------------------------------------------------------------
			
			report_info("Pronadjen poziv funkcije "+designatorDot.getName()+" na liniji "+functionCall.getLine(), null);
			
			functionCall.struct=funcObj.getType();
			}
			else 
			{
				report_error("Greska na liniji "+ functionCall.getLine()+" ime: "+designatorDot.getName()+" nije funkcija!", null);
				functionCall.struct=Tab.noType;
			}
         }
		
	    		
		else {
			SingleDesignator singleDesignator=(SingleDesignator) designatorStatement.getDesignator();
			String funcName=singleDesignator.getName();
			Obj baseObj=Tab.find(funcName);
		
			if (baseObj==Tab.noObj) 
			{
				
				report_error("Greska na liniji "+ functionCall.getLine()+" ime: "+singleDesignator.getName()+" nije funkcija!", null);
				functionCall.struct=Tab.noType;
				return;
			}
			
			if (Obj.Meth==baseObj.getKind()) 
			{
				designatorStatement.getDesignator().obj=baseObj;
			
				singleDesignator.obj=baseObj;
				//CHR ORD LEN--------------------------------------------------------------------------------------------------
				if (baseObj==Tab.chrObj || baseObj==Tab.lenObj||baseObj==Tab.ordObj) 
				{
				
				 if (ActualParsList.class.isInstance(functionCall.getActParsList())) 
				 {
					 ActualParsList actualParsList=(ActualParsList) functionCall.getActParsList();
					 if(SingleExpr.class.isInstance(actualParsList.getActPars())) 
					 {
						SingleExpr singleExpr=(SingleExpr) actualParsList.getActPars();
						if (baseObj==Tab.chrObj&&singleExpr.getExpr().struct.getKind()!=Struct.Int) 
						{
							 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" je pozvana sa pogresnim parametrima!", null);	
						}
						if (baseObj==Tab.ordObj&&singleExpr.getExpr().struct.getKind()!=Struct.Char) 
						{
							 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" je pozvana sa pogresnim parametrima!", null);	
						}
						if (baseObj==Tab.lenObj&&(singleExpr.getExpr().struct.getKind()!=Struct.Array)) 
						{
							 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" je pozvana sa pogresnim parametrima!", null);		
						}
					 }
					 else 
					 {
						 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" je pozvana sa pogresnim parametrima!", null);
					 }
				 }
				 else {
						report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" je pozvana sa pogresnim parametrima!", null);
				 }
				}
				//CHR ORD LEN--------------------------------------------------------------------------------------------------
				//Form Actual Paramas--------------------------------------------------------------------------------------------------------
				else {
				Collection<Obj> collection=	baseObj.getLocalSymbols();
			     Obj[]  objArray= new Obj[collection.size()];
			     collection.toArray(objArray);
			     ArrayList<Obj> formParams=new ArrayList<>();
			     ArrayList<Struct> structs= new ArrayList<>();
			     for (Obj obj :objArray) 
			     {
			    	if (obj.getFpPos()!=0) 
			    	{
			    		formParams.add(obj);
			    	} 
			     }
			     if (ActualParsList.class.isInstance(functionCall.getActParsList())) 
				 {
			    	 ActualParsList actualParsList=(ActualParsList) functionCall.getActParsList();
			    	 SyntaxNode node = actualParsList.getActPars();
			    	 while (!SingleExpr.class.isInstance(node)) 
			    	 {
			    	ActualPars actualPars=(ActualPars)	node;
			    	 structs.add(actualPars.getExpr().struct);
			    	  node=actualPars.getActPars();
			    	 }
			    	  SingleExpr single = (SingleExpr ) node;
			    	 structs.add(single.getExpr().struct);
			    	 if (structs.size()!=formParams.size()) 
			    	 {
			    		 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" nije pozvana sa potrebnim parametrima!"+" "+structs.size()+" "+formParams.size(), null); 
			    	 }
			    	 else 
			    	 {
			    		 int i=structs.size()-1;
			    		 boolean same=true;
			    		 for (Struct st : structs) 
			    		 {
			    			 if (!st.equals(formParams.get(i).getType()))
                                 {
			    				 same=false;
			    				 break;
                                 	}
			    			 i--;
			    		 }
			    		 if (!same) 
			    		 {
			    			 report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" nije pozvana sa potrebnim parametrima!", null); 
			    		 }
			    	 }
				 }
			     else 
			     {
			    	 if (!formParams.isEmpty()) 
			    	 {
			    			report_error("Greska na liniji "+ singleDesignator.getLine()+" funkcija: "+funcName+" nije pozvana sa potrebnim parametrima!", null);
			    	 }
			     }
			     
				}
			   //Form Actual Paramas--------------------------------------------------------------------------------------------------------
				report_info("Pronadjen poziv funkcije "+funcName+" na liniji "+singleDesignator.getLine(), null);
				functionCall.struct=baseObj.getType();
			}
			else 
			{
				report_error("Greska na liniji "+ singleDesignator.getLine()+" ime: "+funcName+" nije funkcija!", null);
				functionCall.struct=Tab.noType;
			}
			
		}
	
	}

//*************************************************************************
	
//*************************************************************************
//Formalni parametri funkcije

	public void visit(FormParameter formParameter) 
	{
		if (currentMethod!=null) {
		currentMethod.setFpPos(currentMethod.getFpPos()+1);
	    currentMethod.setLevel(currentMethod.getLevel()+1);
		SyntaxNode syntaxNode2=formParameter.getBrackets();

		   if(WithBrackets.class.isInstance(syntaxNode2)) 
			 	{
				
				formParameter.obj = Tab.insert(Obj.Var, formParameter.getParamName(),new Struct(Struct.Array, formParameter.getType().struct));
				report_info("Deklarisana  nizovski parametar funkcije "+ formParameter.getParamName()+" "+printNode(formParameter.obj), formParameter);   
			 
			 	}
			   else 
			   {
				
					formParameter.obj = Tab.insert(Obj.Var,  formParameter.getParamName(), formParameter.getType().struct);
					report_info("Deklarisana parameter funkcije "+ formParameter.getParamName()+" "+printNode(formParameter.obj), formParameter);
					
			   }
		formParameter.obj.setFpPos(currentMethod.getFpPos());
	}
	}
//*************************************************************************	
	
//*************************************************************************
//Aktuelni parametri funkcije

	public void visit(SingleExpr singleExpr) 
	{
		
	}
	
//*************************************************************************	
	
//*************************************************************************
//Rekordi
	
	public void visit(RecordName recordName) 
	{
		if (Tab.find(recordName.getType().getTypeName())!=Tab.noObj) 
		{
			report_error("Ime "+recordName.getType().getTypeName()+" je vec definisano u okviru opsega", recordName);
		}
	
		recordName.obj=Tab.insert(Obj.Type, recordName.getType().getTypeName(), new Struct(Struct.Class,recordName.getType().struct));
		recordName.obj.setFpPos(2);
		currentRecord=recordName.obj;
		   report_info("Deklarisan rekord "+ recordName.getType().getTypeName()+" "+printNode(currentRecord),recordName);
		Tab.openScope();
	}
public void visit (RecordDecl recordDecl) 
{
	Tab.chainLocalSymbols(currentRecord.getType());
	currentRecord=null;
	
	Tab.closeScope();
}	
//*************************************************************************


//*************************************************************************
//Klase

public void visit(ClassDeclaration classDeclaration) 
{
//	Scope outerScope=Tab.currentScope.getOuter();
//SymbolDataStructure symbolDataStructure=	Tab.currentScope.getLocals();
//Obj objArray[] = new Obj[symbolDataStructure.symbols().size()];
//Collection<Obj> collection=symbolDataStructure.symbols();
//collection.toArray(objArray);

//  for (int i=0;i<objArray.length;i++) 
//  {
//	    if (objArray[i].getKind()==Obj.Meth) {
//		outerScope.addToLocals(objArray[i]);  
////		report_info("Funkcija je prosledjena u outer scope",null);
//	    }
////	    else 
////	    {
////	    	report_info("Promenljiva nije prosledjena u outer scope",null);	
////	    }
//  }
//	Tab.chainLocalSymbols(currentClass);
	Tab.chainLocalSymbols(currentClass.getType());
	currentClass=null;

	Tab.closeScope();
}
public void visit(ExtendedClassType extendedClassType) 
{
	
	Obj extendedClass=Tab.find(extendedClassType.getType().getTypeName());
	if (extendedClass.getKind()!=Obj.Type || (extendedClass.getType()==Tab.intType||extendedClass.getType()==Tab.charType||extendedClass.getType().getKind()==Struct.Bool)) 
	{
		report_error("Greska ime: "+extendedClassType.getType().getTypeName()+" ne predstavlja tip koji moze da se nasledi", extendedClassType);
	}
	else 
	{
		if (extendedClass.getFpPos()==2) 
		{
			report_error("Greska  "+extendedClassType.getType().getTypeName()+" predstavlja rekord i nemoguce ga je naslediti ", extendedClassType);
		}
		else 
		{
			Obj [] arrayObj=new Obj[extendedClass.getLocalSymbols().size()];
			extendedClass.getLocalSymbols().toArray(arrayObj);
			for (Obj obj : arrayObj)
			{
				Tab.currentScope.addToLocals(obj);		
			}
			Obj [] arrayObj2= new Obj[extendedClass.getType().getMembers().size()];
		   extendedClass.getType().getMembers().toArray(arrayObj2);
			for (Obj obj : arrayObj2)
			{
				Tab.currentScope.addToLocals(obj);		
			}
		}
	}
}

public void visit(ClassName className) 
{
	
	if (className.getParent().getClass()!=NewFactor.class) {
		if (Tab.find(className.getType().getTypeName())!=Tab.noObj) 
		{
			report_error("Ime "+className.getType().getTypeName()+" je vec definisano u okviru opsega", className);
		}
	className.obj=Tab.insert(Obj.Type,className.getType().getTypeName(), new Struct(Struct.Class));
	currentClass=className.obj;
	   report_info("Deklarisana  klasa "+ className.getType().getTypeName()+" "+printNode(currentClass),className);
	Tab.openScope();
	}else 
	{
		
		className.obj=Tab.find(className.getType().getTypeName());
	}
}

public void visit(NewFactor newFactor)
{
	

	Obj typeNode =	Tab.find(newFactor.getClassName().getType().getTypeName());
	if (typeNode == Tab.noObj) 
	{
		newFactor.struct=new Struct(Struct.None);
//		report_error("Nije pronadjen tip " + 	newFactor.getType().getTypeName()+" u tabeli simbola!", null);
//		newFactor.getType().struct = Tab.noType;
	}
	else 
	{
		if (Obj.Type == typeNode.getKind() ) 
		{
			if (FactorNewExpr.class.isInstance(newFactor.getFactorNew())) 
			{
				report_info("Kreiran niz objekata klase "+newFactor.getClassName().getType().getTypeName(), newFactor);
				newFactor.struct=new Struct(Struct.Array,typeNode.getType());
			}
			else 
			{
          if ( typeNode.getType()==Tab.intType||typeNode.getType()==Tab.charType||typeNode.getType().getKind()==Struct.Bool) 
          {
      		newFactor.struct=new Struct(Struct.None);
    		report_error("Greska: Ime " + newFactor.getClassName().getType().getTypeName()+" ne predstavlja klasni tip!", newFactor);
    		return;
          }
				report_info("Kreiran objekat klase "+newFactor.getClassName().getType().getTypeName(), newFactor);
				newFactor.struct=typeNode.getType();	
			}
		
		     
		}
		else 
		{
			newFactor.struct=new Struct(Struct.None);
		report_error("Greska: Ime " + newFactor.getClassName().getType().getTypeName()+" ne predstavlja klasni tip!", newFactor);
//			newFactor.getType().struct = Tab.noType;
		}
	}
	
}

//*************************************************************************

//*************************************************************************
//Constructors
public void visit(ConstructorDecl constructorDecl) 
{
	
	Type type =constructorDecl.getType();
	if (!type.getTypeName().equals(currentClass.getName()))
	{
		report_error("Tip konstruktora se ne podudara sa tipom klase "+type.getTypeName()+" "+currentClass.getName(), constructorDecl);
		return;
	}
	Obj consturctoObj=Tab.insert(Obj.Meth, type.getTypeName(), new Struct(Struct.Class));
	Scope outerScope=Tab.currentScope.getOuter();
	outerScope.addToLocals(consturctoObj);
}
public void visit(ConstructorDeclNoIdent constructorDeclNoIdent) 
{
	Type type=null;
	ClassBodyWithConstructor classBody= (ClassBodyWithConstructor) constructorDeclNoIdent.getParent();
	if(ClassBodyNotEmpty.class.isInstance(classBody)) 
	{
		ClassBodyNotEmpty classBodyNotEmpty = (ClassBodyNotEmpty) classBody.getParent();
		 type=classBodyNotEmpty.getType();
	}
	else 
	{
		Class2BodyNotEmpty classBodyNotEmpty = (Class2BodyNotEmpty) classBody.getParent();
		 type=classBodyNotEmpty.getType();
		
	}
	if (!type.getTypeName().equals(currentClass.getName()))
	{
		report_error("Tip konstruktora se ne podudara sa tipom klase "+type.getTypeName()+" "+currentClass.getName(), constructorDeclNoIdent);
		return;
	}
	Obj consturctoObj=Tab.insert(Obj.Meth, type.getTypeName(), new Struct(Struct.Class));
	Scope outerScope=Tab.currentScope.getOuter();
	outerScope.addToLocals(consturctoObj);
}
public void visit(SuperStmt superStmt) 
{
	
	SyntaxNode node = superStmt.getParent();
	Type type=null;
	if (ConstructorDeclNoIdent.class.isInstance(node.getParent())) 
	{
		ClassBodyWithConstructor classBody= (ClassBodyWithConstructor) node.getParent();
		node=node.getParent().getParent().getParent();
	
		type=classBody.getType();
		
	}
	else  if(ConstructorDecl.class.isInstance(node.getParent()))
	{
		ConstructorDecl constructor =(ConstructorDecl) node.getParent();
		node=node.getParent();
		type=constructor.getType();
	}
	else 
	{
		node=node.getParent();
		String funcName="";
		SyntaxNode nodeMethod=null;
		FormParamList formParamList=null;
		Struct struct=null;
		if(MethodDeclNoId.class.isInstance(node)) 
		{
			MethodDeclNoId methodDeclNoId=(MethodDeclNoId) node;
	   	SyntaxNode syntaxNode=methodDeclNoId;
			
			while (syntaxNode.getClass()!=ClassBodyWithConstructor.class) 
			{
				syntaxNode = syntaxNode.getParent();
			}
			ClassBodyWithConstructor	 classBodyWithConstructor =  (ClassBodyWithConstructor) syntaxNode;
			struct=classBodyWithConstructor.getType().struct;
			nodeMethod=methodDeclNoId;
			funcName=methodDeclNoId.getMethodName().getMethodName();
			formParamList=methodDeclNoId.getFormParamList();
		}
		else 
		{
			if (MethodDeclVoid.class.isInstance(node)) {
			MethodDeclVoid methodDeclVoid = (MethodDeclVoid) node;
          struct=Tab.noType;
			nodeMethod=methodDeclVoid;
			funcName=methodDeclVoid.getMethodVoidName().getMethodName();
			formParamList=methodDeclVoid.getFormParamList();}
			else 
			{
				MethodDeclaration methodDeclaration=(MethodDeclaration) node;
				struct=Tab.noType;
				nodeMethod=methodDeclaration;
				if(MethodDeclType.class.isInstance(methodDeclaration.getMethodTypeName())) 
				{
					MethodDeclType methodDeclType=(MethodDeclType) methodDeclaration.getMethodTypeName();
					funcName=methodDeclType.getMethodName();
				}
				else 
				{
					MethodVoidType methodVoidType=(MethodVoidType) methodDeclaration.getMethodTypeName();
					funcName= methodVoidType.getMethodName();
				}
				formParamList=methodDeclaration.getFormParamList();
				
				
			}
		}
		while(!ClassDecl1.class.isInstance(nodeMethod)&&!ClassDecl2.class.isInstance(nodeMethod))
		{
			nodeMethod=nodeMethod.getParent();
		}
		String className="";
		SyntaxNode extendsNode=null;
		if(ClassDecl1.class.isInstance(nodeMethod)) 
		{
			ClassDecl1 classDecl1=(ClassDecl1) nodeMethod;
          extendsNode= classDecl1.getExtends();
		}
		else 
		{
			ClassDecl2 classDecl2=(ClassDecl2) nodeMethod;
		     extendsNode=classDecl2.getExtends();
		}
		if (ExtendClass.class.isInstance(extendsNode)) 
		{
			ExtendClass extendClass=(ExtendClass) extendsNode;
			ExtendedClassType extendedClassType=(ExtendedClassType) extendClass.getExtendsType();
			String extendedClassName= extendedClassType.getType().getTypeName();
			Obj classObj= Tab.find(extendedClassName);
			Obj [] objs=new Obj[classObj.getType().getMembers().size()];
			 classObj.getType().getMembers().toArray(objs);
			boolean found=false;
			Obj foundObj=null;
			for (Obj obj : objs) 
			{
				if (obj.getName().equals(funcName)&&obj.getKind()==Obj.Meth) 
				{
					found=true;
					foundObj=obj;
				}
			}
			if (!found) 
			{
				report_error("Greska pozvana je super naredba metode koja nije definisana u nasledjenoj klasi!"+" na liniji: "+superStmt.getParent().getLine(),null);	
			
			}
			else 
			{
				if (!(sameSignature(foundObj,formParamList )&&foundObj.getType()==struct))
				{
					report_error("Greska pozvana je super naredba metode koja ne deli isti potpis sa metodom iz nasledjene klase!"+" na liniji: "+superStmt.getParent().getLine(),null);		
				}
			}
			return;
			
		}
		else 
		{
			report_error("Greska pozvana je super naredba  osnovne klase!"+" na liniji: "+superStmt.getParent().getLine(),null);	
		}
		//ovde raditi
		return;
	}
	node=node.getParent().getParent();
	if(ClassDecl1.class.isInstance(node)) 
	{
	ClassDecl1 classDecl1=(ClassDecl1) node;	
	if(type.getTypeName().equals(classDecl1.getClassName().getType().getTypeName())) {
		
	if(ExtendClass.class.isInstance(classDecl1.getExtends())) 
	{
		ExtendClass extendedClass=(ExtendClass) classDecl1.getExtends() ;
		
		if (ExtendedClassType.class.isInstance(extendedClass.getExtendsType())) 
		{
			ExtendedClassType extendedClassType =(ExtendedClassType) extendedClass.getExtendsType();
			Obj typeNode =	Tab.find(extendedClassType.getType().getTypeName());
			if (typeNode != Tab.noObj) 
			{
				if (Obj.Type == typeNode.getKind()) 
				{

					report_info("Pozvan je super konstruktor klase "+extendedClassType.getType().getTypeName()+" na liniji: "+(superStmt.getParent().getLine()+2), null);
				}
			}
		}
	

	  	
	}
	else 
	{
		report_error("Greska pozvani je super konstruktor osnovne klase!"+" na liniji: "+(superStmt.getParent().getLine()+2),null);
	}
	}
	else 
	{
		report_error("Greska konstruktor je pogresnog tipa!"+" na liniji: "+(superStmt.getParent().getLine()+2),null);
	}
	}
	else 
	{
	ClassDecl2 classDecl2=(ClassDecl2) node;
	if(type.getTypeName().equals(classDecl2.getClassName().getType().getTypeName())) {
	if(ExtendClass.class.isInstance(classDecl2.getExtends())) 
	{
		ExtendClass extendedClass=(ExtendClass) classDecl2.getExtends() ;
		
		if (ExtendedClassType.class.isInstance(extendedClass.getExtendsType())) 
		{
			ExtendedClassType extendedClassType =(ExtendedClassType) extendedClass.getExtendsType();
			Obj typeNode =	Tab.find(extendedClassType.getType().getTypeName());
			if (typeNode != Tab.noObj) 
			{
				if (Obj.Type == typeNode.getKind()) 
				{

					report_info("Pozvan je super konstruktor klase "+extendedClassType.getType().getTypeName()+" na liniji: "+(superStmt.getParent().getLine()+2), null);
				}
			}
		}
	
	}
	else 
	{
		report_error("Greska pozvani je super konstruktor osnovne klase!"+" na liniji: "+(superStmt.getParent().getLine()+2),null);
	}
	}
	else 
	{
		report_error("Greska konstruktor je pogresnog tipa!"+" na liniji: "+(superStmt.getParent().getLine()+2),null);
	}
}
}

//*************************************************************************

//*************************************************************************
//Konstanta
public void visit(SingleConstDeclaration singleConstDeclaration) 
{
	SyntaxNode node = singleConstDeclaration;
	while(!ConstDecl.class.isInstance(node))  node=node.getParent();
	ConstDecl constDecl = (ConstDecl) node;
	Type type=constDecl.getType();
	ConstType constType=singleConstDeclaration.getConstType();
	Struct typeStruct = type.struct;
	if (Tab.find(singleConstDeclaration.getConstName().getConstname())!=Tab.noObj)
	{
		report_error("Greska konstanta sa imenom "+singleConstDeclaration.getConstName().getConstname()+" je vec deklarisana", singleConstDeclaration);
		return;
	}
	if (NumConst.class.isInstance(constType)) 
	{
		NumConst num= (NumConst) constType;
		
		if (typeStruct!=Tab.intType)
			report_error("Greska konstanta "+singleConstDeclaration.getConstName().getConstname()+" je pogresnog tipa", singleConstDeclaration);
		else 
		{
			 report_info("Deklarisana konstanta "+ singleConstDeclaration.getConstName().getConstname(), singleConstDeclaration);
			   singleConstDeclaration.getConstName().obj = Tab.insert(Obj.Con, singleConstDeclaration.getConstName().getConstname(), type.struct);
			  NumConst numConst=(NumConst) constType;
			  singleConstDeclaration.getConstName().obj.setAdr(numConst.getValue());
		}
	}
	if(CharConst.class.isInstance(constType)) 
	{
		if(typeStruct!=Tab.charType)
			report_error("Greska konstanta "+singleConstDeclaration.getConstName().getConstname()+" je pogresnog tipa", singleConstDeclaration);
		else 
		{
			 report_info("Deklarisana konstanta "+ singleConstDeclaration.getConstName().getConstname(), singleConstDeclaration);
		     singleConstDeclaration.getConstName().obj = Tab.insert(Obj.Con, singleConstDeclaration.getConstName().getConstname(), type.struct);
		     CharConst charConst=(CharConst) constType;
		     singleConstDeclaration.getConstName().obj.setAdr(charConst.getValue());
		     
		}
			
	}
	if(BoolConst.class.isInstance(constType)) 
	{

		if(!type.getTypeName().equals("boolean"))
			report_error("Greska konstanta "+singleConstDeclaration.getConstName().getConstname()+" je pogresnog tipa", singleConstDeclaration);
		else 
		{
			   report_info("Deklarisana konstanta "+ singleConstDeclaration.getConstName().getConstname(), singleConstDeclaration);
			   singleConstDeclaration.getConstName().obj = Tab.insert(Obj.Con, singleConstDeclaration.getConstName().getConstname(), type.struct);	
			   BoolConst boolConst = (BoolConst) constType;
			   if (boolConst.getValue().equals("true")) 
			   {
				   singleConstDeclaration.getConstName().obj.setAdr(1); 
			   }
			   else 
			   {
				   singleConstDeclaration.getConstName().obj.setAdr(0);
			   }
		}
			
		
	}
}

//*************************************************************************

//*************************************************************************
//Labela
public void visit(Label label) 
{
	if(!GoToStmt.class.isInstance(label.getParent())) {
	 report_info("Deklarisana labela "+ label.getLabelName(), label);
     label.obj = Tab.insert(Obj.NO_VALUE, label.getLabelName(), new Struct(Struct.None));
	}
}
//*************************************************************************

//*************************************************************************
//Term,Expr
public void visit(FactorNewExpr factorNewExpr) 
{
	if ( factorNewExpr.getExpr().struct!=Tab.intType ) 
	{
		report_error("Greska na liniji:"+factorNewExpr.getLine()+" ne-int konstruktorski parametri!", null);
	}
	
}

public void visit(VarFactor varFactor) 
{
	varFactor.struct=varFactor.getDesignator().obj.getType();
}
public void visit(NumConstFactor numConstFactor) 
{
	numConstFactor.struct=Tab.intType;
}
public void visit(CharConstFactor charConstFactor) 
{
	charConstFactor.struct=Tab.charType;
}
public void visit(BoolConstFactor boolConstFactor) 
{
	boolConstFactor.struct=new Struct(Struct.Bool);
}
public void visit(FactorExpr factorExpr) 
{
	factorExpr.struct=factorExpr.getExpr().struct;
}

public void visit(SingleFactor singleFactor) 
{
	singleFactor.struct=singleFactor.getFactor().struct;
}
public void visit(TermMul termMul) 
{
	Struct t=termMul.getFactor().struct;
	Struct tf=termMul.getTerm().struct;
	if (tf.equals(t) && tf==Tab.intType ) 
	{
		termMul.struct=tf;
	}
	else
	{
		report_error("Greska na liniji:"+termMul.getLine()+" nekompatibilni tipovi u izrazu za mnozenje!", null);
		termMul.struct=Tab.noType;
	}
}
public void visit(TermExpression termExpression) 
{
	termExpression.struct=termExpression.getTerm().struct;
}
public void visit(MinusTermExpression minusTermExpression) 
{
	if (minusTermExpression.getTerm().struct==Tab.intType) {
	minusTermExpression.struct=minusTermExpression.getTerm().struct;
	}
	else 
	{
		minusTermExpression.struct=Tab.noType;
		report_error("Greska na liniji:"+minusTermExpression.getLine()+" minus mora stajati ispred int tipa!", null);	
	}
}
public void visit(Expression expression) 
{
	Struct te= expression.getExpr().struct;
	Struct t= expression.getTerm().struct;
	
	if (te.equals(t) && te==Tab.intType ) 
	{
		expression.struct=te;
	}
	else
	{
		report_error("Greska na liniji:"+expression.getLine()+" nekompatibilni tipovi u izrazu za sabiranje!", null);
		expression.struct=Tab.noType;
	}
}
public void visit(CondFactExpr condFactExpr) 
{
	condFactExpr.struct=condFactExpr.getExpr().struct;
}
public void visit(ConditionFact conditionFact) 
{
	Struct st1=conditionFact.getExpr().struct;
	Struct st2=conditionFact.getExpr1().struct;
	if (!st1.compatibleWith(st2)) 
	{
//		report_error("Greska na liniji:"+conditionFact.getLine()+" nekompatibilni tipovi u izrazu za relacije!"+ st1.getKind()+" "+st2.getKind(), null);
		report_error("Greska na liniji:"+conditionFact.getLine()+" nekompatibilni tipovi u izrazu za relacije!", null);
		conditionFact.struct=new Struct(Struct.None);
	}
	else 
	{
		if (!((RelopEqual.class.isInstance(conditionFact.getRelop()))||(RelopNotEqual.class.isInstance(conditionFact.getRelop())))&&((st1.getKind()==Struct.Array)||(st1.getKind()==Struct.Class))) 
		{
//			report_error("Greska na liniji:"+conditionFact.getLine()+" nekompatibilni tipovi u izrazu za relacije!"+ st1.getKind()+" "+st2.getKind(), null);
			report_error("Greska na liniji:"+conditionFact.getLine()+" nekompatibilan relacioni operater za tipove klase i niza!", null);
			conditionFact.struct=new Struct(Struct.None);
		}
		else 
		{
			conditionFact.struct=new Struct(Struct.Bool);
		}
		
	}	
}
public void visit(SingleCondFact singleCondFact)
{
	singleCondFact.struct=singleCondFact.getCondFact().struct;
}
public void visit(ConditionTerm conditionTerm) 
{
	Struct st1=conditionTerm.getCondFact().struct;
	Struct st2= conditionTerm.getCondTerm().struct;
	if (st1.getKind()==Struct.Bool && st2.getKind()==Struct.Bool) 
	{
	conditionTerm.struct=st1;	
	}
	else 
	{
		report_error("Greska na liniji:"+conditionTerm.getLine()+" parametri konjukcije nisu logickog tipa!", null);
		conditionTerm.struct=new Struct(Struct.None);
	}
}
public void visit(SingleCondTerm singleCondTerm) 
{
	singleCondTerm.struct=singleCondTerm.getCondTerm().struct;
}
public void visit(ConditionTerms conditionTerms) 
{
	Struct st1 = conditionTerms.getCondTerm().struct;
	Struct st2 = conditionTerms.getCondition().struct;
	if (st1.getKind()==Struct.Bool && st2.getKind()==Struct.Bool) 
	{
	conditionTerms.struct=st1;	
	}
	else 
	{
		report_error("Greska na liniji:"+conditionTerms.getLine()+" parametri disjunkcije nisu logickog tipa!", null);
		conditionTerms.struct=new Struct(Struct.None);
	}
	
}

//*************************************************************************

//*************************************************************************
//Return expression
public void visit(ReturnExprStmt returnExprStmt) 
{
	if(currentMethod==null) 
	{
		report_error("Greska"+returnExprStmt.getLine()+" return naredba se mora nalaziti u okviru odgovarajuce metode!", null);	
	}
	else {
		
	returnFound=true;
	Struct currMethType=currentMethod.getType();
	if(!currMethType.compatibleWith(returnExprStmt.getExpr().struct)) 
	{
		if (currMethType.getKind()!=Struct.Bool||returnExprStmt.getExpr().struct.getKind()!=Struct.Bool) {
		report_error("Greska na liniji:"+returnExprStmt.getLine()+" tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije "+currentMethod.getName(), null);	
		}
	}
	}
}
public void visit(ReturnNoExprStmt returnNoExprStmt) 
{
	if(currentMethod==null) 
	{
		report_error("Greska na liniji:"+returnNoExprStmt.getLine()+" return naredba se mora nalaziti u okviru odgovarajuce metode!", null);	
	}
}
//*************************************************************************

//**************************************************************************
//Statements

//PrintStatement
public void visit(PrintStmt printStmt) 
{
	
	Expr expr=(Expr) printStmt.getExpr();
	if (expr.struct!=Tab.intType && expr.struct!=Tab.charType&&expr.struct.getKind()!=Struct.Bool) 
	{
		report_error("Greska na liniji:"+printStmt.getLine()+" vrednost prvog argumenta funkcije print mora biti char, bool ili int ", null);	
	}
}
//BreakStmt
public void visit(BreakStmt breakStmt) 
{
	SyntaxNode node = breakStmt;
	while (!DoWhileStmt.class.isInstance(node)&&(node!=null)) 
	{
		node=node.getParent();
	}
	if (node==null)
	{
		report_error("Greska na liniji:" +breakStmt.getLine()+" naredba break se mora nalaziti u okruzujucem do-while statement-u! ", null);		
	}
}

public void visit(ContinueStmt continueStmt) 
{
	SyntaxNode node = continueStmt;
	while (!DoWhileStmt.class.isInstance(node)&&(node!=null)) 
	{
		node=node.getParent();
	}
	if (node==null)
	{
		report_error("Greska na liniji:" +continueStmt.getLine()+" naredba continue se mora nalaziti u okruzujucem do-while statement-u! ", null);		
	}
}
public void visit(GoToStmt goToStmt) 
{
	
String labelName=	goToStmt.getLabel().getLabelName();
labels.add(labelName);
goToStmt.getLabel().obj = Tab.find(labelName);
}
public void visit(DoWhileStmt doWhileStmt) 
{
	if (doWhileStmt.getCondition().struct.getKind()!=Struct.Bool) 
	{
		{
			report_error("Greska na liniji:" +doWhileStmt.getCondition().getLine()+" condition mora biti logickog tipa! ", null);	
	}
}
}
public void visit(MatchedStatement matchedStatement) 
{
	if (matchedStatement.getCondition().struct.getKind()!=Struct.Bool) 
	{
		{
			report_error("Greska na liniji:" +matchedStatement.getLine()+" condition mora biti logickog tipa! ", null);	
	}
	}
}
public void visit(UnmatchedStatement unmatchedStatement) 
{
	if (unmatchedStatement.getCondition().struct.getKind()!=Struct.Bool) 
	{
		{
			report_error("Greska na liniji:" +unmatchedStatement.getLine()+" condition mora biti logickog tipa! ", null);	
	}
	
}
	
}
public void visit(ReadStmt readStmt) 
{
	Designator designator=readStmt.getDesignator();
	
	if (designator.obj.getType().getKind()!=Struct.Int&&designator.obj.getType().getKind()!=Struct.Bool&&designator.obj.getType().getKind()!=Struct.Char) 
	{
		report_error("Greska na liniji:" +readStmt.getLine()+" argument read naredbe mora biti jedan od tipova (int,char,bool)! "+designator.obj.getType().getKind()+" " +designator.obj.getType().getElemType(), null);	
	}

}


public void visit(DesignatorIncrement designatorIncrement) 
{
	
	if(DesignatorStatmentNoError.class.isInstance(designatorIncrement.getParent())) {
		DesignatorStatmentNoError designatorStatmentNoError = (DesignatorStatmentNoError) designatorIncrement.getParent();	
		Designator designator=designatorStatmentNoError.getDesignator();
		
		
		if (designator.obj==Tab.noObj||designator.obj.getType()!=Tab.intType||(designator.obj.getKind()!=Obj.Var&&designator.obj.getKind()!=Obj.Fld&&designator.obj.getKind()!=Obj.Elem) ) 
		{
			
			report_error("Greska na liniji:" +designatorStatmentNoError.getLine()+" argument pre postinkrementa mora biti int tipa! ", null);	
		}
		designatorIncrement.struct=designator.obj.getType();
		}
		else 
		{
			report_error("Greska u sintaksnoj analizi naredbe za inkrementiranje! ", null);	
		}
	}
public void visit(DesignatorDecrement designatorDecrement) 
{
	if(DesignatorStatmentNoError.class.isInstance(designatorDecrement.getParent())) {
	DesignatorStatmentNoError designatorStatmentNoError = (DesignatorStatmentNoError) designatorDecrement.getParent();	
	Designator designator=designatorStatmentNoError.getDesignator();

	if (designator.obj.getType()!=Tab.intType||(designator.obj.getKind()!=Obj.Var&&designator.obj.getKind()!=Obj.Fld&&designator.obj.getKind()!=Obj.Elem) ) 
	{
		report_error("Greska na liniji:" +designatorStatmentNoError.getLine()+" argument pre postdekrementa mora biti int tipa! ", null);	
	}
	}
	else 
	{
		report_error("Greska u sintaksnoj analizi naredbe za dekrementiranje! ", null);	
	}
}
public void visit(DesignatorAssign designatorAssign) 
{
	if(DesignatorStatmentNoError.class.isInstance(designatorAssign.getParent())) {
		DesignatorStatmentNoError designatorStatmentNoError = (DesignatorStatmentNoError) designatorAssign.getParent();	
		Obj designatorObj=designatorStatmentNoError.getDesignator().obj;
		if (designatorObj==Tab.noObj) 
		{
			report_error("Greska na liniji:" +designatorStatmentNoError.getLine()+" leva strana assign naredbe mora biti definisana! " , null);	
			return;
		}
		if (designatorObj.getKind()!=Obj.Var&&designatorObj.getKind()!=Obj.Elem&&designatorObj.getKind()!=Obj.Fld) 
		{
			report_error("Greska na liniji:" +designatorStatmentNoError.getLine()+" leva strana assign naredbe mora biti promenljiva polje ili element niza! " , null);	
			return;
		}
	
		
		if (designatorObj==Tab.noObj||!designatorAssign.getExpr().struct.assignableTo(designatorObj.getType()))
		{
			if(designatorAssign.getExpr().struct.getKind()!=Struct.Bool || designatorObj.getType().getKind()!=Struct.Bool) 
			{
				
			
			report_error("Greska na liniji:" +designatorStatmentNoError.getLine()+" argumenti assign naredbe moraju biti kompatibilni! " , null);	
			}
			}
		}
		else 
		{
			report_error("Greska u sintaksnoj analizi naredbe assign! ", null);	
		}
}
//*************************************************************************


public boolean sameSignature(Obj baseObj,FormParamList formParams2) 
{
	Collection<Obj> collection=	baseObj.getLocalSymbols();
    Obj[]  objArray= new Obj[collection.size()];
    collection.toArray(objArray);
    ArrayList<Obj> formParams=new ArrayList<>();
    ArrayList<Struct> structs= new ArrayList<>(); 
    for (Obj obj :objArray) 
    {
   	if (obj.getKind()==Obj.Var) 
   	{
   		formParams.add(obj);
   	} 
    }
    if (FormParamLs.class.isInstance(formParams2)) 
	 {
    	FormParamLs formParamList=(FormParamLs) formParams2;
   	 SyntaxNode node = formParamList.getFormPars();
   	 while (!SingleFormParam.class.isInstance(node)) 
   	 {
   		FormParams formPars=(FormParams) node;
   		FormParameter formParam= (FormParameter) formPars.getFormParam();
   	 structs.add(formParam.getType().struct);
   	  node=formPars.getFormPars();
   	 }
   	SingleFormParam single = (SingleFormParam ) node;
   	FormParameter formParam= (FormParameter) single.getFormParam();
   	 structs.add(formParam.getType().struct);
   	 if (structs.size()!=formParams.size()) 
   	 {
   		 return false;
   	 }
   	 else 
   	 {
   		 int i=structs.size()-1;
   		 boolean same=true;
   		 for (Struct st : structs) 
   		 {
   			 if (!st.equals(formParams.get(i).getType()))
                    {
   				 same=false;
   				 break;
                    	}
   			 i--;
   		 }
   		 if (!same) 
   		 {
   			return false;
   		 }
   	 }
	 }
    else 
    {
   	 if (!formParams.isEmpty()) 
   	 {
   		return false;
   	 }
    }
    return true;
	}
}
