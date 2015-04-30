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
public class GetNotificationRequest {
	@Element
	private String UserEmailId;

	public String getUserEmailId() {
		return UserEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		UserEmailId = userEmailId;
	}

}
