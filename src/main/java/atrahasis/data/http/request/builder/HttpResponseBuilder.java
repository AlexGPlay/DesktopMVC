package atrahasis.data.http.request.builder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import atrahasis.data.http.request.components.HttpResponse;

public class HttpResponseBuilder {

	private CloseableHttpResponse response;
	
	public HttpResponseBuilder(CloseableHttpResponse response) {
		this.response = response;
	}
	
	public HttpResponse build() {
		String responseString = getResponse();
		
		Map<String, String> headers = getHeaders();
		
		int statusCode = response.getStatusLine().getStatusCode();
		String reasonPhrase = response.getStatusLine().getReasonPhrase();
		String statusLine = response.getStatusLine().toString();
		String protocolVersion = response.getProtocolVersion().toString();
		
		return new HttpResponse(responseString, statusCode, reasonPhrase, statusLine, protocolVersion, headers);
	}
	
	private Map<String, String> getHeaders(){
		Map<String, String> headers = new HashMap<>();
		
		for(Header header : response.getAllHeaders()) 
			headers.put(header.getName(), header.getValue());
		
		return headers;
	}
	
	private String getResponse() {
		try {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
}
