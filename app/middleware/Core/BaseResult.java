package middleware.Core;

public class BaseResult {

    private boolean isSucceeded;

    private String exceptionType;

    private String exceptionMessage;

    public BaseResult() {
    }

    public BaseResult(boolean isSucceeded, String exceptionType, String exceptionMessage) {
        this.isSucceeded      = isSucceeded;
        this.exceptionType    = exceptionType;
        this.exceptionMessage = exceptionMessage;
    }

    public void Error(Exception exception) {
        isSucceeded      = false;
        exceptionType    = exception.getClass().getSimpleName();
        exceptionMessage = exception.getMessage();
    }

    public boolean isSucceeded() {
        return isSucceeded;
    }

    public void setSucceeded(boolean succeeded) {
        isSucceeded = succeeded;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
