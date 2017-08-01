package operation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

import core.IJinsengServer;
import core.http.HttpServer;
import core.http.HttpServiceRouter;
import core.http.IWebServiceLogic;
import operation.ConfigInfo.ServeInfo;

public class ServiceLauncher {

	IJinsengServer server = null;
	
	public ServiceLauncher(){

	}
	
	public void loadService(ConfigInfo info){

		try {
			File rootPath = new File(".");
			URL url;
				url = rootPath.toURL();
			URL[] urls = new URL[]{url};
			ClassLoader cloader = new URLClassLoader(urls);

			if(info.getServerType().equals("HTTP")){

				int timeout = info.getTimeout() == 0 ? Integer.MAX_VALUE : info.getTimeout();
				
				if(info.getServeList().size() == 1){
					IWebServiceLogic logic = null;
					for(String serveTag : info.getServeList().keySet()){
						ServeInfo serveInfo = info.getServeElement(serveTag);
						Class cls = cloader.loadClass(serveInfo.getClassName());
						logic = (IWebServiceLogic)cls.newInstance();
					}
					if(logic != null)
						server = new HttpServer(logic, timeout);
				}else{
					HttpServiceRouter router = new HttpServiceRouter();
					for(String serveTag : info.getServeList().keySet()){
						ServeInfo serveInfo = info.getServeElement(serveTag);
						String urlPath = serveInfo.getUrl();
						Class cls = cloader.loadClass(serveInfo.getClassName());
						IWebServiceLogic logic = (IWebServiceLogic)cls.newInstance();
						
						router.setRoutingMethod(urlPath, logic);
					}
					
					server = new HttpServer(router, timeout);
				}
				
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void runService(){
		try {
			server.RunServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		server.EndServer();
	}
	
}
