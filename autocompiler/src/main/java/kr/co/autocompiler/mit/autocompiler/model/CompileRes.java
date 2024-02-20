package kr.co.autocompiler.mit.autocompiler.model;

public class CompileRes {
    private int responseCode;
    private String responseMsg;
    private String currentStatus;

    public String getCurrentStatus() {
        return currentStatus;
    }
    public int getResCode() {
        return responseCode;
    }
    public String getResponseMsg() {
        return responseMsg;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}
