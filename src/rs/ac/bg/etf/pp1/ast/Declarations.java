// generated with ast extension for cup
// version 0.8
// 24/0/2022 17:21:16


package rs.ac.bg.etf.pp1.ast;

public class Declarations extends Decls {

    private Decls Decls;
    private Decl Decl;

    public Declarations (Decls Decls, Decl Decl) {
        this.Decls=Decls;
        if(Decls!=null) Decls.setParent(this);
        this.Decl=Decl;
        if(Decl!=null) Decl.setParent(this);
    }

    public Decls getDecls() {
        return Decls;
    }

    public void setDecls(Decls Decls) {
        this.Decls=Decls;
    }

    public Decl getDecl() {
        return Decl;
    }

    public void setDecl(Decl Decl) {
        this.Decl=Decl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Decls!=null) Decls.accept(visitor);
        if(Decl!=null) Decl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Decls!=null) Decls.traverseTopDown(visitor);
        if(Decl!=null) Decl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Decls!=null) Decls.traverseBottomUp(visitor);
        if(Decl!=null) Decl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Declarations(\n");

        if(Decls!=null)
            buffer.append(Decls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Decl!=null)
            buffer.append(Decl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Declarations]");
        return buffer.toString();
    }
}
