// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclarationsVoid extends MethodDeclsVoid {

    private MethodDeclsVoidList MethodDeclsVoidList;

    public MethodDeclarationsVoid (MethodDeclsVoidList MethodDeclsVoidList) {
        this.MethodDeclsVoidList=MethodDeclsVoidList;
        if(MethodDeclsVoidList!=null) MethodDeclsVoidList.setParent(this);
    }

    public MethodDeclsVoidList getMethodDeclsVoidList() {
        return MethodDeclsVoidList;
    }

    public void setMethodDeclsVoidList(MethodDeclsVoidList MethodDeclsVoidList) {
        this.MethodDeclsVoidList=MethodDeclsVoidList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclsVoidList!=null) MethodDeclsVoidList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclsVoidList!=null) MethodDeclsVoidList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclsVoidList!=null) MethodDeclsVoidList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclarationsVoid(\n");

        if(MethodDeclsVoidList!=null)
            buffer.append(MethodDeclsVoidList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclarationsVoid]");
        return buffer.toString();
    }
}
