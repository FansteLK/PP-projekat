// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class Program implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private ProgName ProgName;
    private Decls Decls;
    private MethodDecls MethodDecls;

    public Program (ProgName ProgName, Decls Decls, MethodDecls MethodDecls) {
        this.ProgName=ProgName;
        if(ProgName!=null) ProgName.setParent(this);
        this.Decls=Decls;
        if(Decls!=null) Decls.setParent(this);
        this.MethodDecls=MethodDecls;
        if(MethodDecls!=null) MethodDecls.setParent(this);
    }

    public ProgName getProgName() {
        return ProgName;
    }

    public void setProgName(ProgName ProgName) {
        this.ProgName=ProgName;
    }

    public Decls getDecls() {
        return Decls;
    }

    public void setDecls(Decls Decls) {
        this.Decls=Decls;
    }

    public MethodDecls getMethodDecls() {
        return MethodDecls;
    }

    public void setMethodDecls(MethodDecls MethodDecls) {
        this.MethodDecls=MethodDecls;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgName!=null) ProgName.accept(visitor);
        if(Decls!=null) Decls.accept(visitor);
        if(MethodDecls!=null) MethodDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgName!=null) ProgName.traverseTopDown(visitor);
        if(Decls!=null) Decls.traverseTopDown(visitor);
        if(MethodDecls!=null) MethodDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgName!=null) ProgName.traverseBottomUp(visitor);
        if(Decls!=null) Decls.traverseBottomUp(visitor);
        if(MethodDecls!=null) MethodDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Program(\n");

        if(ProgName!=null)
            buffer.append(ProgName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Decls!=null)
            buffer.append(Decls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDecls!=null)
            buffer.append(MethodDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Program]");
        return buffer.toString();
    }
}
