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
public class MeetingDate {
	
	@Element
	private String MeetDate;
	@ElementList
	private Collection<MeetingTime> MeetingTimes;
	
	public String getMeetDate() {
		return MeetDate;
	}
	public void setMeetDate(String meetDate) {
		MeetDate = meetDate;
	}
	public Collection<MeetingTime> getMeetingTimes() {
		return MeetingTimes;
	}
	public void setMeetingTimes(Collection<MeetingTime> meetingTimes) {
		MeetingTimes = meetingTimes;
	}

}
