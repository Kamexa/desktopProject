package test;

import java.util.Date;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import userEJB.UserRemote;
import beans.User;

public class UserTest {
	public static UserRemote UserLookup() throws NamingException {
		Hashtable<Object, Object> conf = new Hashtable<Object, Object>();
		conf.put(Context.INITIAL_CONTEXT_FACTORY,"org.wildfly.naming.client.WildFlyInitialContextFactory");
		conf.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(conf);
		return (UserRemote) context.lookup("ejb:/GpsProjectServer/UserSession!userEJB.UserRemote");
	}

	public static void main(String[] args) {
		try {
			UserRemote re = UserLookup();
			re.save(new User("testnom","testprenom","testnum","testemail",new Date()));
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
