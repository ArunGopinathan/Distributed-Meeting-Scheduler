/**
 * 
 */
package edu.uta.cse.webservice;

/**
 * @author Arun
 *
 */
import org.simpleframework.xml.*;
@Root
public class Participant {
	@Element
	private String UserEmailId;

	public String getUserEmailId() {
		return UserEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		UserEmailId = userEmailId;
	}

}
