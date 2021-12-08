// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:17


package rs.ac.bg.etf.pp1.ast;

public class NewFactor extends Factor {

    private ClassName ClassName;
    private FactorNew FactorNew;

    public NewFactor (ClassName ClassName, FactorNew FactorNew) {
        this.ClassName=ClassName;
        if(ClassName!=null) ClassName.setParent(this);
        this.FactorNew=FactorNew;
        if(FactorNew!=null) FactorNew.setParent(this);
    }

    public ClassName getClassName() {
        return ClassName;
    }

    public void setClassName(ClassName ClassName) {
        this.ClassName=ClassName;
    }

    public FactorNew getFactorNew() {
        return FactorNew;
    }

    public void setFactorNew(FactorNew FactorNew) {
        this.FactorNew=FactorNew;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ClassName!=null) ClassName.accept(visitor);
        if(FactorNew!=null) FactorNew.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ClassName!=null) ClassName.traverseTopDown(visitor);
        if(FactorNew!=null) FactorNew.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ClassName!=null) ClassName.traverseBottomUp(visitor);
        if(FactorNew!=null) FactorNew.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NewFactor(\n");

        if(ClassName!=null)
            buffer.append(ClassName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorNew!=null)
            buffer.append(FactorNew.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NewFactor]");
        return buffer.toString();
    }
}
