package atrahasis.core.network;

public class Response {

	private int statusCode;
	private Object toRender;
	private String response;
	private String redirect;

	public Response() {
		statusCode = 200;
		toRender = null;
		response = null;
		redirect = null;
	}
	
	public Response render(Object toRender) {
		this.toRender = toRender;
		return this;
	}
	
	public Response redirect(String redirectUrl) {
		this.redirect = redirectUrl;
		return this;
	}
	
	public Response statusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}
	
	public Response response(String response) {
		this.response = response;
		return this;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public Object getToRender() {
		return toRender;
	}

	public String getResponse() {
		return response;
	}

	public String getRedirect() {
		return redirect;
	}
	
}