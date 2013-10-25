package org.skipsradar.camera;

import android.graphics.Bitmap;

public class Photo {

	public static final String NO_INFO_MMSI = "Null";
	
	private Bitmap photo;
	private String name;
	private String shipMmsi;
	private String shipName;
	private String timeTag;
	
	public Photo(Bitmap photo, String name, String shipMmsi, String shipName, String timeTag) {
		this.photo = photo;
		this.name = name;
		this.shipMmsi = shipMmsi;
		this.shipName = shipName;
		this.timeTag = timeTag;
	}
	
	public Bitmap getPhoto() {
		return photo;
	}
	public String getName() {
		return name;
	}
	public String getShipMmsi() {
		return shipMmsi;
	}
	public String getShipName() {
		return shipName;
	}
	public String getTimeTag() {
		return timeTag;
	}
}
