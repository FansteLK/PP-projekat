// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class ClassBodyWithConstructor extends ClassBodyInternal {

    private ConstructorDeclNoIdent ConstructorDeclNoIdent;
    private Type Type;
    private MethodDeclsNoIdent MethodDeclsNoIdent;

    public ClassBodyWithConstructor (ConstructorDeclNoIdent ConstructorDeclNoIdent, Type Type, MethodDeclsNoIdent MethodDeclsNoIdent) {
        this.ConstructorDeclNoIdent=ConstructorDeclNoIdent;
        if(ConstructorDeclNoIdent!=null) ConstructorDeclNoIdent.setParent(this);
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.MethodDeclsNoIdent=MethodDeclsNoIdent;
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.setParent(this);
    }

    public ConstructorDeclNoIdent getConstructorDeclNoIdent() {
        return ConstructorDeclNoIdent;
    }

    public void setConstructorDeclNoIdent(ConstructorDeclNoIdent ConstructorDeclNoIdent) {
        this.ConstructorDeclNoIdent=ConstructorDeclNoIdent;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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
        if(ConstructorDeclNoIdent!=null) ConstructorDeclNoIdent.accept(visitor);
        if(Type!=null) Type.accept(visitor);
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorDeclNoIdent!=null) ConstructorDeclNoIdent.traverseTopDown(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorDeclNoIdent!=null) ConstructorDeclNoIdent.traverseBottomUp(visitor);
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(MethodDeclsNoIdent!=null) MethodDeclsNoIdent.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBodyWithConstructor(\n");

        if(ConstructorDeclNoIdent!=null)
            buffer.append(ConstructorDeclNoIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclsNoIdent!=null)
            buffer.append(MethodDeclsNoIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBodyWithConstructor]");
        return buffer.toString();
    }
}
