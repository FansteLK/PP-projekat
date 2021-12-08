// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class SingleMethodVoidDeclaration extends MethodDeclsVoidList {

    private MethodDeclVoid MethodDeclVoid;

    public SingleMethodVoidDeclaration (MethodDeclVoid MethodDeclVoid) {
        this.MethodDeclVoid=MethodDeclVoid;
        if(MethodDeclVoid!=null) MethodDeclVoid.setParent(this);
    }

    public MethodDeclVoid getMethodDeclVoid() {
        return MethodDeclVoid;
    }

    public void setMethodDeclVoid(MethodDeclVoid MethodDeclVoid) {
        this.MethodDeclVoid=MethodDeclVoid;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclVoid!=null) MethodDeclVoid.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclVoid!=null) MethodDeclVoid.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclVoid!=null) MethodDeclVoid.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleMethodVoidDeclaration(\n");

        if(MethodDeclVoid!=null)
            buffer.append(MethodDeclVoid.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleMethodVoidDeclaration]");
        return buffer.toString();
    }
}
