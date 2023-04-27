// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclarationsNoId extends MethodDeclsNoIdent {

    private MethodDeclsNoIdent MethodDeclsNoIdent;
    private MethodDecl MethodDecl;

    public MethodDeclarationsNoId (MethodDeclsNoIdent MethodDeclsNoIdent, MethodDecl MethodDecl) {
        this.MethodDeclsNoIdent=MethodDeclsNoIdent;
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.setParent(this);
        this.MethodDecl=MethodDecl;
        if(MethodDecl!=null) MethodDecl.setParent(this);
    }

    public MethodDeclsNoIdent getMethodDeclsNoIdent() {
        return MethodDeclsNoIdent;
    }

    public void setMethodDeclsNoIdent(MethodDeclsNoIdent MethodDeclsNoIdent) {
        this.MethodDeclsNoIdent=MethodDeclsNoIdent;
    }

    public MethodDecl getMethodDecl() {
        return MethodDecl;
    }

    public void setMethodDecl(MethodDecl MethodDecl) {
        this.MethodDecl=MethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.accept(visitor);
        if(MethodDecl!=null) MethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.traverseTopDown(visitor);
        if(MethodDecl!=null) MethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.traverseBottomUp(visitor);
        if(MethodDecl!=null) MethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclarationsNoId(\n");

        if(MethodDeclsNoIdent!=null)
            buffer.append(MethodDeclsNoIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDecl!=null)
            buffer.append(MethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclarationsNoId]");
        return buffer.toString();
    }
}
