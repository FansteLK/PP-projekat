// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class NoVarDecls extends VarDecls {

    public NoVarDecls () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoVarDecls(\n");

        buffer.append(tab);
        buffer.append(") [NoVarDecls]");
        return buffer.toString();
    }
}