// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class SingleClassDecl extends VarDeclsClass {

    private VarDeclClass VarDeclClass;

    public SingleClassDecl (VarDeclClass VarDeclClass) {
        this.VarDeclClass=VarDeclClass;
        if(VarDeclClass!=null) VarDeclClass.setParent(this);
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
        if(VarDeclClass!=null) VarDeclClass.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclClass!=null) VarDeclClass.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclClass!=null) VarDeclClass.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleClassDecl(\n");

        if(VarDeclClass!=null)
            buffer.append(VarDeclClass.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleClassDecl]");
        return buffer.toString();
    }
}
