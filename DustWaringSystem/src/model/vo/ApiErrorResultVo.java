package model.vo;

import java.util.ArrayList;
import java.util.List;

public class ApiErrorResultVo {
	private List<String> msgs = new ArrayList<String>();
	private int status;
	private String httpStatus;

	public ApiErrorResultVo() {

	}

	public List<String> getMsgs() {
		return msgs;
	}
	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}
}
