package listening;

import java.io.Serializable;

public class Response implements Serializable {

	private static final long serialVersionUID = -4311158671713476273L;

	private String message;
	private String[] answer;

	public Response(String message, String[] answer){
		this.message = message;
		this.answer = answer;
	}

	public Response(String message){
		this(message, null);
	}

	public Response(String[] answer){
		this(null, answer);
	}

	public String[] getAnswer() {
		return answer;
	}

	public void setAnswer(String[] answer) {
		this.answer = answer;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
