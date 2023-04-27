// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class MultipleStatements extends Statements {

    private SuperStatement SuperStatement;
    private StatementList StatementList;
    private RbraceStatements RbraceStatements;

    public MultipleStatements (SuperStatement SuperStatement, StatementList StatementList, RbraceStatements RbraceStatements) {
        this.SuperStatement=SuperStatement;
        if(SuperStatement!=null) SuperStatement.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
        this.RbraceStatements=RbraceStatements;
        if(RbraceStatements!=null) RbraceStatements.setParent(this);
    }

    public SuperStatement getSuperStatement() {
        return SuperStatement;
    }

    public void setSuperStatement(SuperStatement SuperStatement) {
        this.SuperStatement=SuperStatement;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public RbraceStatements getRbraceStatements() {
        return RbraceStatements;
    }

    public void setRbraceStatements(RbraceStatements RbraceStatements) {
        this.RbraceStatements=RbraceStatements;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SuperStatement!=null) SuperStatement.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
        if(RbraceStatements!=null) RbraceStatements.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SuperStatement!=null) SuperStatement.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
        if(RbraceStatements!=null) RbraceStatements.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SuperStatement!=null) SuperStatement.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        if(RbraceStatements!=null) RbraceStatements.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleStatements(\n");

        if(SuperStatement!=null)
            buffer.append(SuperStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RbraceStatements!=null)
            buffer.append(RbraceStatements.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleStatements]");
        return buffer.toString();
    }
}
