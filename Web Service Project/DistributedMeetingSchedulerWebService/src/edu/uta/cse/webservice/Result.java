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
public class Result {
	@Element
	private String Status;
	@Element
	private String Description;

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

}
