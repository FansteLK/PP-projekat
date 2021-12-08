// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl1 extends ClassDecl {

    private ClassName ClassName;
    private Extends Extends;
    private VarDeclsClass VarDeclsClass;
    private ClassBody2 ClassBody2;

    public ClassDecl1 (ClassName ClassName, Extends Extends, VarDeclsClass VarDeclsClass, ClassBody2 ClassBody2) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.Extends=Extends;
        if(Extends!=null) Extends.setParent(this);
        this.VarDeclsClass=VarDeclsClass;
        if(VarDeclsClass!=null) VarDeclsClass.setParent(this);
        this.ClassBody2=ClassBody2;
        if(ClassBody2!=null) ClassBody2.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
    }

    public Extends getExtends() {
        return Extends;
    }

    public void setExtends(Extends Extends) {
        this.Extends=Extends;
    }

    public VarDeclsClass getVarDeclsClass() {
        return VarDeclsClass;
    }

    public void setVarDeclsClass(VarDeclsClass VarDeclsClass) {
        this.VarDeclsClass=VarDeclsClass;
    }

    public ClassBody2 getClassBody2() {
        return ClassBody2;
    }

    public void setClassBody2(ClassBody2 ClassBody2) {
        this.ClassBody2=ClassBody2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassName!=null) ClassName.accept(visitor);
        if(Extends!=null) Extends.accept(visitor);
        if(VarDeclsClass!=null) VarDeclsClass.accept(visitor);
        if(ClassBody2!=null) ClassBody2.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(Extends!=null) Extends.traverseTopDown(visitor);
        if(VarDeclsClass!=null) VarDeclsClass.traverseTopDown(visitor);
        if(ClassBody2!=null) ClassBody2.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(Extends!=null) Extends.traverseBottomUp(visitor);
        if(VarDeclsClass!=null) VarDeclsClass.traverseBottomUp(visitor);
        if(ClassBody2!=null) ClassBody2.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl1(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Extends!=null)
            buffer.append(Extends.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclsClass!=null)
            buffer.append(VarDeclsClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassBody2!=null)
            buffer.append(ClassBody2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl1]");
        return buffer.toString();
    }
}
