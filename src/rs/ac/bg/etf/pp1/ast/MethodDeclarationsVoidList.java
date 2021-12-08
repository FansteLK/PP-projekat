// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclarationsVoidList extends MethodDeclsVoidList {

    private MethodDeclsVoidList MethodDeclsVoidList;
    private MethodDecl MethodDecl;

    public MethodDeclarationsVoidList (MethodDeclsVoidList MethodDeclsVoidList, MethodDecl MethodDecl) {
        this.MethodDeclsVoidList=MethodDeclsVoidList;
        if(MethodDeclsVoidList!=null) MethodDeclsVoidList.setParent(this);
        this.MethodDecl=MethodDecl;
        if(MethodDecl!=null) MethodDecl.setParent(this);
    }

    public MethodDeclsVoidList getMethodDeclsVoidList() {
        return MethodDeclsVoidList;
    }

    public void setMethodDeclsVoidList(MethodDeclsVoidList MethodDeclsVoidList) {
        this.MethodDeclsVoidList=MethodDeclsVoidList;
    }

    public MethodDecl getMethodDecl() {
        return MethodDecl;
    }

    public void setMethodDecl(MethodDecl MethodDecl) {
        this.MethodDecl=MethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclsVoidList!=null) MethodDeclsVoidList.accept(visitor);
        if(MethodDecl!=null) MethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclsVoidList!=null) MethodDeclsVoidList.traverseTopDown(visitor);
        if(MethodDecl!=null) MethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclsVoidList!=null) MethodDeclsVoidList.traverseBottomUp(visitor);
        if(MethodDecl!=null) MethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclarationsVoidList(\n");

        if(MethodDeclsVoidList!=null)
            buffer.append(MethodDeclsVoidList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDecl!=null)
            buffer.append(MethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclarationsVoidList]");
        return buffer.toString();
    }
}
