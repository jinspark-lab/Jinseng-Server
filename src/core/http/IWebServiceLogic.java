package core.http;

public interface IWebServiceLogic {
	/***
	 * 
	 *  Handle Response for each request that is given.
	 *  You should handle each functions of HttpMethod(Get, Post, Update, Delete) inside the implementation.
	 * 
	 * @param request
	 * @return
	 */
	public HttpResponse Respond(HttpRequest request);

}
