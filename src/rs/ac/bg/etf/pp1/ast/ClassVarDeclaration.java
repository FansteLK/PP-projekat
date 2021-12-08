// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class ClassVarDeclaration extends VarDeclClass {

    private Type Type;
    private VarDeclClassList VarDeclClassList;

    public ClassVarDeclaration (Type Type, VarDeclClassList VarDeclClassList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarDeclClassList=VarDeclClassList;
        if(VarDeclClassList!=null) VarDeclClassList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarDeclClassList getVarDeclClassList() {
        return VarDeclClassList;
    }

    public void setVarDeclClassList(VarDeclClassList VarDeclClassList) {
        this.VarDeclClassList=VarDeclClassList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarDeclClassList!=null) VarDeclClassList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarDeclClassList!=null) VarDeclClassList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarDeclClassList!=null) VarDeclClassList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassVarDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclClassList!=null)
            buffer.append(VarDeclClassList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassVarDeclaration]");
        return buffer.toString();
    }
}
