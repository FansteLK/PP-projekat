// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class ClassBodyNotEmpty extends ClassBody {

    private Type Type;
    private ClassBodyInternal ClassBodyInternal;

    public ClassBodyNotEmpty (Type Type, ClassBodyInternal ClassBodyInternal) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ClassBodyInternal=ClassBodyInternal;
        if(ClassBodyInternal!=null) ClassBodyInternal.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ClassBodyInternal getClassBodyInternal() {
        return ClassBodyInternal;
    }

    public void setClassBodyInternal(ClassBodyInternal ClassBodyInternal) {
        this.ClassBodyInternal=ClassBodyInternal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ClassBodyInternal!=null) ClassBodyInternal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ClassBodyInternal!=null) ClassBodyInternal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ClassBodyInternal!=null) ClassBodyInternal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBodyNotEmpty(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassBodyInternal!=null)
            buffer.append(ClassBodyInternal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBodyNotEmpty]");
        return buffer.toString();
    }
}
