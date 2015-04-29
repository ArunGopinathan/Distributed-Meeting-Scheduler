/**
 * 
 */
package edu.uta.cse.webservice;

import java.util.Collection;

/**
 * @author Arun
 *
 */
import org.simpleframework.xml.*;

@Root
public class NotificationsResponse {
	@ElementList
	private Collection<Notification> Notifications;

	public Collection<Notification> getNotifications() {
		return Notifications;
	}

	public void setNotifications(Collection<Notification> notifications) {
		Notifications = notifications;
	}

}
