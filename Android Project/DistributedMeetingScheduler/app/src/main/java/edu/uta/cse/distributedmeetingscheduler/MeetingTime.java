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
public class MeetingTime {

	@Element
	private String MeetingStartTime;
	@Element
	private String MeetingEndTime;

	public String getMeetingStartTime() {
		return MeetingStartTime;
	}

	public void setMeetingStartTime(String meetingStartTime) {
		MeetingStartTime = meetingStartTime;
	}

	public String getMeetingEndTime() {
		return MeetingEndTime;
	}

	public void setMeetingEndTime(String meetingEndTime) {
		MeetingEndTime = meetingEndTime;
	}

}
