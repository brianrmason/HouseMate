package cscie97.asn2.housemate.model;

public class Event  {

	private String houseID;
	private String roomID;
	private String deviceID;
	private String eventInfo;

    public Event(String houseID, String roomID, String deviceID, String eventInfo) {
        this.houseID = houseID;
        this.roomID = roomID;
        this.deviceID = deviceID;
        this.eventInfo = eventInfo;
    }

	// Getters
    public String getHouseID() {
		return houseID;
	}

	public String getRoomID() {
		return roomID;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public String getEventInfo() {return eventInfo;}

	// Setters
	public void setHouseID(String houseID) {
		this.houseID = houseID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	/**
	 * Returns a string representation of the Event for easy logging and debugging.
	 *
	 * @return A formatted string representing the event details.
	 */
	@Override
	public String toString() {
		return "Event [houseID=" + houseID + ", roomID=" + roomID +
				", eventInfo=" + eventInfo + ", deviceID=" + deviceID + "]";
	}

}
