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
public class SaveUserResponseRequest {
	
	@Element
	private String MeetingId;
	@Element
	private String ParticipantEmail;
	@Element
	private String MeetDate;
	@Element
	private String MeetStartTime;
	@Element
	private String MeetEndTime;
	@Element
	private String UserResponse;
	
	public String getMeetingId() {
		return MeetingId;
	}
	public void setMeetingId(String meetingId) {
		MeetingId = meetingId;
	}
	public String getParticipantEmail() {
		return ParticipantEmail;
	}
	public void setParticipantEmail(String participantEmail) {
		ParticipantEmail = participantEmail;
	}
	public String getMeetDate() {
		return MeetDate;
	}
	public void setMeetDate(String meetDate) {
		MeetDate = meetDate;
	}
	public String getMeetStartTime() {
		return MeetStartTime;
	}
	public void setMeetStartTime(String meetStartTime) {
		MeetStartTime = meetStartTime;
	}
	public String getMeetEndTime() {
		return MeetEndTime;
	}
	public void setMeetEndTime(String meetEndTime) {
		MeetEndTime = meetEndTime;
	}
	public String getUserResponse() {
		return UserResponse;
	}
	public void setUserResponse(String userResponse) {
		UserResponse = userResponse;
	}

}
