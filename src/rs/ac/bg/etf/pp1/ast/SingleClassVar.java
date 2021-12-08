// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class SingleClassVar extends VarDeclClassList {

    private SingleClassVarDecl SingleClassVarDecl;

    public SingleClassVar (SingleClassVarDecl SingleClassVarDecl) {
        this.SingleClassVarDecl=SingleClassVarDecl;
        if(SingleClassVarDecl!=null) SingleClassVarDecl.setParent(this);
    }

    public SingleClassVarDecl getSingleClassVarDecl() {
        return SingleClassVarDecl;
    }

    public void setSingleClassVarDecl(SingleClassVarDecl SingleClassVarDecl) {
        this.SingleClassVarDecl=SingleClassVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleClassVarDecl!=null) SingleClassVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleClassVarDecl!=null) SingleClassVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleClassVarDecl!=null) SingleClassVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleClassVar(\n");

        if(SingleClassVarDecl!=null)
            buffer.append(SingleClassVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleClassVar]");
        return buffer.toString();
    }
}
