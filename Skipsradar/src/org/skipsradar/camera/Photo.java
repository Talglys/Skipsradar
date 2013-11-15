package org.skipsradar.camera;

import android.graphics.Bitmap;

/**
 * The photo class for storage of photo related information
 * @author Andreas
 *
 */
public class Photo {

	public static final String NO_INFO_MMSI = "Null";
	
	private Bitmap photo;
	private String name;
	private String shipMmsi;
	private String shipName;
	private String timeTag;
	
	/**
	 * Nomrmal constructor
	 * 
	 * @param photo the photo is the photo of the ship
	 * @param name of the photo in storage, not the ship
	 * @param shipMmsi the ship's mmsi number
	 * @param shipName the ship's name
	 * @param timeTag unused, don't bother
	 */
	public Photo(Bitmap photo, String name, String shipMmsi, String shipName, String timeTag) {
		this.photo = photo;
		this.name = name;
		this.shipMmsi = shipMmsi;
		this.shipName = shipName;
		this.timeTag = timeTag;
	}
	
	/**
	 * An easy way of storing ships, without creating a new class.
	 * This constructor should only be used when you're certain you
	 * won't need to use the photo part.
	 * 
	 * @param shipMmsi the ship's mmsi number
	 * @param shipName the ship's name
	 */
	public Photo(String shipMmsi, String shipName){
		this.shipMmsi = shipMmsi;
		this.shipName = shipName;
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
