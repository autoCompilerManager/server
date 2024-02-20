package kr.co.autocompiler.mit.autocompiler.model;

public class CompileReq {

    private String language;
    private String sourceCode;

    public String getLanguage() {
        return language;
    }
    public String getSourceCode() {
        return sourceCode;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

}
