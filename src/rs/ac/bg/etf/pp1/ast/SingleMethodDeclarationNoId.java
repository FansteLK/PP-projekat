// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class SingleMethodDeclarationNoId extends MethodDeclsNoIdent {

    private MethodDeclNoIdent MethodDeclNoIdent;

    public SingleMethodDeclarationNoId (MethodDeclNoIdent MethodDeclNoIdent) {
        this.MethodDeclNoIdent=MethodDeclNoIdent;
        if(MethodDeclNoIdent!=null) MethodDeclNoIdent.setParent(this);
    }

    public MethodDeclNoIdent getMethodDeclNoIdent() {
        return MethodDeclNoIdent;
    }

    public void setMethodDeclNoIdent(MethodDeclNoIdent MethodDeclNoIdent) {
        this.MethodDeclNoIdent=MethodDeclNoIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclNoIdent!=null) MethodDeclNoIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclNoIdent!=null) MethodDeclNoIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclNoIdent!=null) MethodDeclNoIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleMethodDeclarationNoId(\n");

        if(MethodDeclNoIdent!=null)
            buffer.append(MethodDeclNoIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleMethodDeclarationNoId]");
        return buffer.toString();
    }
}
