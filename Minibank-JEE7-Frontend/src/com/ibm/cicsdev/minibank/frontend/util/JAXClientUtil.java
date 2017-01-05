import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import com.ibm.cics.minibank.local.webapp.util.JAXClientUtil;

public class JAXClientUtil {

	private static JAXClientUtil instance = null;
	private Client jaxClient;

	public static JAXClientUtil getInstance() {
		if (instance == null) {
			instance = new JAXClientUtil();
		}
		return instance;
	}

	public Client getJaxClient() {
		if (jaxClient == null) {
			jaxClient=ClientBuilder.newClient();
		}
		return jaxClient;
	}

}