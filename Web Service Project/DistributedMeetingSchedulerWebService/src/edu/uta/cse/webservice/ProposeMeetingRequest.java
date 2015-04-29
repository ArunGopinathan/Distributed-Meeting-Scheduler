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
public class ProposeMeetingRequest {
	@Element
	private String UserEmailId;
	@Element
	private String MeetingName;
	@Element
	private String MeetingAgenda;
	@ElementList
	private Collection<MeetingDate> MeetingDates;
	@Element
	private String MeetingLocation;
	@ElementList
	private Collection<Participant> Participants;

	public String getMeetingName() {
		return MeetingName;
	}

	public void setMeetingName(String meetingName) {
		MeetingName = meetingName;
	}

	public String getMeetingAgenda() {
		return MeetingAgenda;
	}

	public void setMeetingAgenda(String meetingAgenda) {
		MeetingAgenda = meetingAgenda;
	}

	public Collection<MeetingDate> getMeetingDates() {
		return MeetingDates;
	}

	public void setMeetingDates(Collection<MeetingDate> meetingDates) {
		MeetingDates = meetingDates;
	}

	public String getUserEmailId() {
		return UserEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		UserEmailId = userEmailId;
	}

	public String getMeetingLocation() {
		return MeetingLocation;
	}

	public void setMeetingLocation(String meetingLocation) {
		MeetingLocation = meetingLocation;
	}

	public Collection<Participant> getParticipants() {
		return Participants;
	}

	public void setParticipants(Collection<Participant> participants) {
		Participants = participants;
	}
}
