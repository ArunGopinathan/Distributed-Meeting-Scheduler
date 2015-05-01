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
public class Notification {
	@Element
	private String MeetingId;
	@Element
	private String MeetingName;
	@Element
	private String MeetingAgenda;
	@ElementList
	private Collection<MeetingDate> MeetingDates;
	@ElementList
	private Collection<Participant> Participants;
	@Element
	private String MeetingLocation;

	public String getMeetingId() {
		return MeetingId;
	}

	public void setMeetingId(String meetingId) {
		MeetingId = meetingId;
	}

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

	public Collection<Participant> getParticipants() {
		return Participants;
	}

	public void setParticipants(Collection<Participant> participants) {
		Participants = participants;
	}

	public String getMeetingLocation() {
		return MeetingLocation;
	}

	public void setMeetingLocation(String meetingLocation) {
		MeetingLocation = meetingLocation;
	}

}
