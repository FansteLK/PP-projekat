// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class ClassVarDeclarations extends VarDeclsClass {

    private VarDeclsClass VarDeclsClass;
    private VarDeclClass VarDeclClass;

    public ClassVarDeclarations (VarDeclsClass VarDeclsClass, VarDeclClass VarDeclClass) {
        this.VarDeclsClass=VarDeclsClass;
        if(VarDeclsClass!=null) VarDeclsClass.setParent(this);
        this.VarDeclClass=VarDeclClass;
        if(VarDeclClass!=null) VarDeclClass.setParent(this);
    }

    public VarDeclsClass getVarDeclsClass() {
        return VarDeclsClass;
    }

    public void setVarDeclsClass(VarDeclsClass VarDeclsClass) {
        this.VarDeclsClass=VarDeclsClass;
    }

    public VarDeclClass getVarDeclClass() {
        return VarDeclClass;
    }

    public void setVarDeclClass(VarDeclClass VarDeclClass) {
        this.VarDeclClass=VarDeclClass;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclsClass!=null) VarDeclsClass.accept(visitor);
        if(VarDeclClass!=null) VarDeclClass.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclsClass!=null) VarDeclsClass.traverseTopDown(visitor);
        if(VarDeclClass!=null) VarDeclClass.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclsClass!=null) VarDeclsClass.traverseBottomUp(visitor);
        if(VarDeclClass!=null) VarDeclClass.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassVarDeclarations(\n");

        if(VarDeclsClass!=null)
            buffer.append(VarDeclsClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclClass!=null)
            buffer.append(VarDeclClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassVarDeclarations]");
        return buffer.toString();
    }
}
