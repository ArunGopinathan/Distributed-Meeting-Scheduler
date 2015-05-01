/**
 * 
 */
package edu.uta.cse.webservice;

/**
 * @author Arun
 *
 */
import org.simpleframework.xml.*;
import java.util.Collection;
@Root
public class OrganizerMeetingResponse {
	@ElementList
private Collection<Notification> Notifications;

	public Collection<Notification> getNotifications() {
		return Notifications;
	}

	public void setNotifications(Collection<Notification> notifications) {
		Notifications = notifications;
	}
}
