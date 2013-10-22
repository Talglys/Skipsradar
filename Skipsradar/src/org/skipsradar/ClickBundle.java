package org.skipsradar;

import java.util.ArrayList;

import org.mixare.MixContext;
import org.mixare.MixState;
import org.mixare.lib.marker.Marker;

public class ClickBundle {

	private ArrayList<Marker> markers;
	private float[] coords;
	private MixContext context;
	private MixState state;
	
	public ClickBundle(ArrayList<Marker> markers, float[] coords, MixContext context, MixState state) {
		this.markers = markers;
		this.coords = coords;
		this.context = context;
		this.state = state;
	}
	
	public ArrayList<Marker> getMarkers(){
		return markers;
	}
	
	public float[] getCoords(){
		return coords;
	}
	
	public MixContext getContext(){
		return context;
	}
	
	public MixState getState(){
		return state;
	}
}
