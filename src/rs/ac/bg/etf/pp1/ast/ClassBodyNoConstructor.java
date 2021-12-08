// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class ClassBodyNoConstructor extends ClassBodyInternal {

    private MethodDeclsNoIdent MethodDeclsNoIdent;

    public ClassBodyNoConstructor (MethodDeclsNoIdent MethodDeclsNoIdent) {
        this.MethodDeclsNoIdent=MethodDeclsNoIdent;
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.setParent(this);
    }

    public MethodDeclsNoIdent getMethodDeclsNoIdent() {
        return MethodDeclsNoIdent;
    }

    public void setMethodDeclsNoIdent(MethodDeclsNoIdent MethodDeclsNoIdent) {
        this.MethodDeclsNoIdent=MethodDeclsNoIdent;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBodyNoConstructor(\n");

        if(MethodDeclsNoIdent!=null)
            buffer.append(MethodDeclsNoIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBodyNoConstructor]");
        return buffer.toString();
    }
}
