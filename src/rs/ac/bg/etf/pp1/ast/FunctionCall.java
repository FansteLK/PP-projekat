// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:17


package rs.ac.bg.etf.pp1.ast;

public class FunctionCall extends Factor {

    private Designator Designator;
    private FactorPars FactorPars;

    public FunctionCall (Designator Designator, FactorPars FactorPars) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.FactorPars=FactorPars;
        if(FactorPars!=null) FactorPars.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public FactorPars getFactorPars() {
        return FactorPars;
    }

    public void setFactorPars(FactorPars FactorPars) {
        this.FactorPars=FactorPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(FactorPars!=null) FactorPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(FactorPars!=null) FactorPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(FactorPars!=null) FactorPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FunctionCall(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorPars!=null)
            buffer.append(FactorPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FunctionCall]");
        return buffer.toString();
    }
}
