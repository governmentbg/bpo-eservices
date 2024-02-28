package eu.ohim.sp.common.ui.exception;

public enum SystemError {
	ERROR_IO("error.io.error", "Failed to read/write file"),
	ERROR_REPOSITORY("error.repository.error", "Failed to read repository"),
	ERROR_QUEUE("error.generic.queue", "Error writing to the queue");
	

	private String errorCode;
	private String errorMessage;
	
	private SystemError(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

	
	
}
