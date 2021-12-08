// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class ClassBodyWithConstructorVoid extends ClassBody {

    private ConstructorDecl ConstructorDecl;
    private MethodDeclsVoid MethodDeclsVoid;

    public ClassBodyWithConstructorVoid (ConstructorDecl ConstructorDecl, MethodDeclsVoid MethodDeclsVoid) {
        this.ConstructorDecl=ConstructorDecl;
        if(ConstructorDecl!=null) ConstructorDecl.setParent(this);
        this.MethodDeclsVoid=MethodDeclsVoid;
        if(MethodDeclsVoid!=null) MethodDeclsVoid.setParent(this);
    }

    public ConstructorDecl getConstructorDecl() {
        return ConstructorDecl;
    }

    public void setConstructorDecl(ConstructorDecl ConstructorDecl) {
        this.ConstructorDecl=ConstructorDecl;
    }

    public MethodDeclsVoid getMethodDeclsVoid() {
        return MethodDeclsVoid;
    }

    public void setMethodDeclsVoid(MethodDeclsVoid MethodDeclsVoid) {
        this.MethodDeclsVoid=MethodDeclsVoid;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstructorDecl!=null) ConstructorDecl.accept(visitor);
        if(MethodDeclsVoid!=null) MethodDeclsVoid.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorDecl!=null) ConstructorDecl.traverseTopDown(visitor);
        if(MethodDeclsVoid!=null) MethodDeclsVoid.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorDecl!=null) ConstructorDecl.traverseBottomUp(visitor);
        if(MethodDeclsVoid!=null) MethodDeclsVoid.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBodyWithConstructorVoid(\n");

        if(ConstructorDecl!=null)
            buffer.append(ConstructorDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclsVoid!=null)
            buffer.append(MethodDeclsVoid.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBodyWithConstructorVoid]");
        return buffer.toString();
    }
}
