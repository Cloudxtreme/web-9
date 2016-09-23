package cn.netkiller.pojo;

public class Protocol {
	private String request;
	private String response;
	private boolean status;
	private String error;
	
	public Protocol() {
		super();
	}
	
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "Protocol [request=" + request + ", response=" + response + ", status=" + status + ", error=" + error + "]";
	}


}
