/**
 * 
 */
package edu.uta.cse.webservice;

import java.io.StringWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * @author Arun
 *
 */

/*
 * localhost link :
 * http://localhost:8080/DistributedMeetingSchedulerWebService/DMSWebService
 * /Authenticate/Arun/1234
 */
@Path("DMSWebService")
public class DMSWebServiceImpl {

	/**
	 * @param args
	 */
	@Deprecated
	@Path("/Authenticate/{username}/{password}")
	@GET
	@Produces(MediaType.TEXT_XML)
	public String Authenticate(@PathParam("username") String username,
			@PathParam("password") String password) {
		String result = "";
		result = "<User>" + "<FirstName>Arun</FirstName>"
				+ "<LastName>Gopinathan</LastName>" + "<MavEmail>a</MavEmail>"
				+ "<AndroidDeviceId />" +

				"</User>";

		return result;

	}

	@Path("/Authenticate2/{username}/{password}")
	@GET
	@Produces(MediaType.TEXT_XML)
	public String Authenticate2(@PathParam("username") String username,
			@PathParam("password") String password) {
		String result = "";

		MySQLHelper helper = new MySQLHelper();
		String query = "select * from login where MavEmail='" + username
				+ "' and Password='" + password + "'";
		ResultSet resultset = helper.executeQueryAndGetResultSet(query);

		User user = processUserResultSet(resultset);

		// dispose connection
		helper.disposeConnection();
		if (user != null)
			System.out.println(user.toString());

		result = getUserXml(user);

		return result;

	}

	// http://localhost:8080/DistributedMeetingSchedulerWebService/DMSWebService/Register?request=%3C?xml%20version=%221.0%22%20encoding=%22UTF-8%22?%3E%20%3Cuser%3E%20%3CFirstName%3EVenkataprabha%3C/FirstName%3E%20%3CLastName%3EVaradharajan%3C/LastName%3E%20%3CMavEmail%3EVenkataprab.Varadharajan@mavs.uta.edu%3C/MavEmail%3E%20%3CPassword%3E123%3C/Password%3E%20%3CAndroidDeviceId%3E%3C/AndroidDeviceId%3E%20%3C/user%3E
	@Path("/Register")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String register(@QueryParam("request") String request) {
		String response = "";
		User user = deserializeUserXML(request);
		String query = generateRegisterQuery(user);

		MySQLHelper helper = new MySQLHelper();
		helper.executeQuery(query);
		helper.disposeConnection();

		// System.out.println(request);

		return request;
	}

	@Path("/GetNotifications")
	@POST
	@Produces(MediaType.TEXT_XML)
	public String GetNotifications(String request) {
		String response = "";

		GetNotificationRequest getNotificationRequest = deserializeGetNotificationRequest(request);

		String getNotificationsQuery = generateGetNotificationsQuery(getNotificationRequest);

		MySQLHelper helper = new MySQLHelper();
		ResultSet notificationRS = helper
				.executeQueryAndGetResultSet(getNotificationsQuery);
		NotificationsResponse ns = new NotificationsResponse();
		GetNotificationRequest getnr;
		Collection<Notification> notifications = new ArrayList<Notification>();
		if (notificationRS != null) {
			getnr = new GetNotificationRequest();
			try {
				while (notificationRS.next()) {
					// each is a notification process one by one
					Notification notification = new Notification();
					int meetingId = notificationRS.getInt("MeetingId");

					String getMeetingQuery = "select MeetingName,Agenda,Location from proposedmeeting where MeetingId="
							+ meetingId;
					ResultSet meetingRS = helper
							.executeQueryAndGetResultSet(getMeetingQuery);
					// get the meeting details
					if (meetingRS.next()) {
						String meetingName = meetingRS.getString("MeetingName");
						String meetingAgenda = meetingRS.getString("Agenda");
						String meetingLocation = meetingRS
								.getString("Location");

						notification.setMeetingName(meetingName);
						notification.setMeetingAgenda(meetingAgenda);
						notification.setMeetingLocation(meetingLocation);

						Collection<MeetingDate> meetingDates = new ArrayList<MeetingDate>();
						String getMeetingDatesQuery = "select distinct MeetingDate from meetingdates where MeetingId="
								+ meetingId;

						ResultSet meetingDatesRS = helper
								.executeQueryAndGetResultSet(getMeetingDatesQuery);
						while (meetingDatesRS.next()) {
							MeetingDate meetingDate = new MeetingDate();
							String meetDate = meetingDatesRS
									.getString("MeetingDate");

							meetingDate.setMeetDate(meetDate);

							String getTimesQuery = "select MeetingStartTime, MeetingEndTime from meetingdates where MeetingDate='"
									+ meetDate + "'";
							ResultSet timesRS = helper
									.executeQueryAndGetResultSet(getTimesQuery);
							Collection<MeetingTime> meetingTimes = new ArrayList<MeetingTime>();

							while (timesRS.next()) {
								MeetingTime time = new MeetingTime();
								String meetingStartTime = timesRS.getTime(
										"MeetingStartTime").toString();
								String meetingEndTime = timesRS.getTime(
										"MeetingEndTime").toString();
								time.setMeetingStartTime(meetingStartTime);
								time.setMeetingEndTime(meetingEndTime);

								meetingTimes.add(time);
							}
							meetingDate.setMeetingTimes(meetingTimes);
							meetingDates.add(meetingDate);

						}
						notification.setMeetingDates(meetingDates);
						notification
								.setParticipants(new ArrayList<Participant>());
					}
					notifications.add(notification);
				}

				ns.setNotifications(notifications);

				Writer writer = new StringWriter();
				Serializer serializer = new Persister();

				serializer.write(ns, writer);

				response = writer.toString();

			} catch (Exception ex) {

			}
		}
		return response;
	}

	// /DistributedMeetingSchedulerWebService/DMSWebService/ProposeMeeting/
	// dont forget its a post request
	@Path("/ProposeMeeting")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String proposeMeeting(String request) {
		String response = "";
		try {
			ProposeMeetingRequest proposeMeetingRequest = deserializeProposeMeetingRequestXML(request);
			String userIdQuery = generateGetUserIdQuery(proposeMeetingRequest);
			MySQLHelper helper = new MySQLHelper();
			// get the userid
			ResultSet userResultSet = helper
					.executeQueryAndGetResultSet(userIdQuery);
			int userId = processUserIdResultSet(userResultSet);
			// insert in propose meeting and get id of inserted row
			String proposeMeetingQuery = generateProposeMeetingQuery(userId,
					proposeMeetingRequest);
			int meetingId = helper
					.executeInsertQueryAndReturnId(proposeMeetingQuery);

			// now insert meeting dates and Times for these Dates
			for (MeetingDate m : proposeMeetingRequest.getMeetingDates()) {
				for (MeetingTime t : m.getMeetingTimes()) {
					String meetingdateQuery = generateMeetingDateQuery(
							meetingId, m, t);
					helper.executeQuery(meetingdateQuery);

				}
				// now insert the participants for the meeting
				for (Participant p : proposeMeetingRequest.getParticipants()) {
					String participantQuery = generateParticipantsQuery(
							meetingId, p);
					helper.executeQuery(participantQuery);
				}

			}
			// dispose the connection object
			helper.disposeConnection();
			response = "SUCCESS";

		} catch (Exception ex) {
			response = "FAILURE";
		}

		return response;
	}

	public String generateGetNotificationsQuery(GetNotificationRequest request) {
		String query = "SELECT distinct p.MeetingId"
				+ " FROM proposedmeeting p inner join meetingdates md on md.meetingId = p.meetingId"
				+ " inner join participants pr on pr.meetingId = p.meetingId"
				+ " where pr.UserEmailId='" + request.getUserEmailId() + "'";
		
		System.out.println(query);
		return query;
	}

	public String generateParticipantsQuery(int meetingId, Participant p) {
		String query = "insert into participants(MeetingId,UserEmailId) values("
				+ meetingId + ",'" + p.getUserEmailId() + "')";

		return query;
	}

	public String generateMeetingDateQuery(int meetingId, MeetingDate m,
			MeetingTime t) {
		String query = "insert into meetingdates(MeetingId,MeetingDate,MeetingStartTime,MeetingEndTime) values( "
				+ meetingId
				+ ",'"
				+ m.getMeetDate()
				+ "','"
				+ t.getMeetingStartTime()
				+ "','"
				+ t.getMeetingEndTime()
				+ "')";

		return query;
	}

	public String generateProposeMeetingQuery(int userId,
			ProposeMeetingRequest mr) {
		String query = "insert into proposedmeeting(UserId,MeetingName,Agenda,Location) values("
				+ userId
				+ ",'"
				+ mr.getMeetingName()
				+ "','"
				+ mr.getMeetingAgenda()
				+ "','"
				+ mr.getMeetingLocation()
				+ "')";

		return query;

	}

	public int processUserIdResultSet(ResultSet rs) {
		int userID = -1;
		if (rs != null) {
			try {
				if (rs.next()) {
					userID = (rs.getInt("UserId"));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		return userID;
	}

	public String generateGetUserIdQuery(ProposeMeetingRequest request) {
		String query = "select UserId from login where MavEmail='"
				+ request.getUserEmailId() + "'";
		return query;
	}

	public String generateRegisterQuery(User user) {
		String query = "insert into login(FirstName,LastName,MavEmail,Password,DeviceId) values('"
				+ user.getFirstName()
				+ "','"
				+ user.getLastName()
				+ "','"
				+ user.getMavEmail() + "','" + user.getPassword() + "','')";
		System.out.println(query);
		return query;
	}

	public String getUserXml(User user) {
		String xml = "";
		Writer writer = new StringWriter();
		Serializer serializer = new Persister();
		try {
			serializer.write(user, writer);
			xml = writer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

	public User deserializeUserXML(String userxml) {
		// String parseXML = new String(userxml, "UTF-16");
		User user = new User();
		Writer writer = new StringWriter();
		Serializer serializer = new Persister();
		try {
			user = serializer.read(User.class, userxml);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	private GetNotificationRequest deserializeGetNotificationRequest(
			String requestxml) {
		GetNotificationRequest request = new GetNotificationRequest();
		Writer writer = new StringWriter();
		Serializer serializer = new Persister();
		try {
			request = serializer.read(GetNotificationRequest.class, requestxml);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return request;

	}

	private ProposeMeetingRequest deserializeProposeMeetingRequestXML(
			String requestxml) {
		ProposeMeetingRequest request = new ProposeMeetingRequest();
		Writer writer = new StringWriter();
		Serializer serializer = new Persister();
		try {
			request = serializer.read(ProposeMeetingRequest.class, requestxml);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return request;

	}

	public User processUserResultSet(ResultSet result) {
		User user = null;
		if (result != null) {
			try {
				if (result.next()) {
					user = new User();
					String firstName = result.getString("FirstName");
					if (firstName != "")
						user.setFirstName(firstName);
					String lastName = result.getString("LastName");
					if (lastName != "")
						user.setLastName(lastName);
					String mavEmail = result.getString("MavEmail");
					if (mavEmail != "")
						user.setMavEmail(mavEmail);
					String androidId = result.getString("DeviceId");
					if (androidId != "")
						user.setAndroidDeviceId(androidId);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return user;
	}

}
