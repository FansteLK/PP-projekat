// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStatmentNoError extends DesignatorStatement {

    private Designator Designator;
    private DesignatorOperation DesignatorOperation;

    public DesignatorStatmentNoError (Designator Designator, DesignatorOperation DesignatorOperation) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignatorOperation=DesignatorOperation;
        if(DesignatorOperation!=null) DesignatorOperation.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignatorOperation getDesignatorOperation() {
        return DesignatorOperation;
    }

    public void setDesignatorOperation(DesignatorOperation DesignatorOperation) {
        this.DesignatorOperation=DesignatorOperation;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(DesignatorOperation!=null) DesignatorOperation.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignatorOperation!=null) DesignatorOperation.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignatorOperation!=null) DesignatorOperation.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStatmentNoError(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorOperation!=null)
            buffer.append(DesignatorOperation.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStatmentNoError]");
        return buffer.toString();
    }
}
