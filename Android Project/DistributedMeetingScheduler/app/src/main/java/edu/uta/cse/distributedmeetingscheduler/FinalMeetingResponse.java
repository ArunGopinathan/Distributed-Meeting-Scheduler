/**
 * 
 */
package edu.uta.cse.distributedmeetingscheduler;

/**
 * @author Arun
 *
 */
import org.simpleframework.xml.*;
@Root
public class FinalMeetingResponse {

	@Element
	private Notification notification;

	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}
}
