package cn.netkiller.pojo;

public class Protocol {
	private String send;
	private String receive;
	private String error;

	/**
	 * @param send
	 * @param receive
	 */
	public Protocol(String send, String receive) {
		this.send = send;
		this.receive = receive;
	}

	@Override
	public String toString() {
		return "Protocol [send=" + send + ", receive=" + receive + "]";
	}

	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	

}
