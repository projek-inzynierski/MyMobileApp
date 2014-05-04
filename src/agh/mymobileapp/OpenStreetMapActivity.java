package agh.mymobileapp;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.OverlayItem.HotspotPlace;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

	public class OpenStreetMapActivity extends Activity {
	 
	 private MapView myOpenMapView;
	 private MapController myMapController;
	 
	 LocationManager locationManager;
	 
	 ItemizedIconOverlay<OverlayItem> overlayItemArray;
	 ArrayList<OverlayItem> items;
	 
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.openstreetmap_main);
	        
	        myOpenMapView = (MapView)findViewById(R.id.mapview);
	        myOpenMapView.setBuiltInZoomControls(true);
	        myMapController = (MapController) myOpenMapView.getController();
	        myMapController.setZoom(12);
	        
	
	        
	        
	        ResourceProxy mResourceProxy = new ResourceProxyImpl(getApplicationContext());
	    	
			items = new ArrayList<OverlayItem>();
			
		    locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		        
		    //for demo, getLastKnownLocation from GPS only, not from NETWORK
		    Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		    
		    //--- Create OverlayS
		    
		   
		    
			items.add(new OverlayItem("Jakis opis wysypiska", "dumb_photo.jpg", new GeoPoint(49645565, 20969188)));
			
			/* OnTapListener for the Markers, shows a simple Toast. */
			overlayItemArray = new ItemizedIconOverlay<OverlayItem>(items, 
					new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
						@Override
						public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
							 LayoutInflater inflater = getLayoutInflater();
							 View view = inflater.inflate(R.layout.toast_main,
							                                   (ViewGroup) findViewById(R.id.toastLayout));

							 TextView toastName = (TextView) view.findViewById(R.id.toastName);
							 ImageView toastImg = (ImageView) view.findViewById(R.id.toastImage);
							 
							 toastName.setText(item.getTitle());
							 File photo = new File(Environment.getExternalStorageDirectory(), item.getSnippet());
							 toastImg.setImageURI(Uri.fromFile(photo));
							 Toast toast = new Toast(getApplicationContext());
							 toast.setView(view);
							 toast.setDuration(Toast.LENGTH_LONG);
							 toast.show();
						
							return true; // We 'handled' this event.
						}
						@Override
						public boolean onItemLongPress(final int index, final OverlayItem item) {
							Toast.makeText(
									OpenStreetMapActivity.this,
									"Item '" + item.getTitle() + "' (index=" + index
											+ ") got long pressed", Toast.LENGTH_LONG).show();
							return false;
						}
					}, mResourceProxy);
				this.myOpenMapView.getOverlays().add(this.overlayItemArray);
			
	        //---
			if(lastLocation != null){
			   	updateLoc(lastLocation);
			}
	        
	   
	        
	        //Add Scale Bar
	        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
	        myOpenMapView.getOverlays().add(myScaleBarOverlay);
	    }
	    
	    @Override
	 protected void onResume() {
		  // TODO Auto-generated method stub
		  super.onResume();
		  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
		  locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
	 }
	
	 @Override
	 protected void onPause() {
		  // TODO Auto-generated method stub
		  super.onPause();
		  locationManager.removeUpdates(myLocationListener);
	 }
	
	 private void updateLoc(Location loc){
	     GeoPoint locGeoPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());
	     myMapController.setCenter(locGeoPoint);
	     
	     setOverlayLoc(loc);
	     
	     myOpenMapView.invalidate();
	 }
	 
	 private void setOverlayLoc(Location overlayloc){
		  GeoPoint overlocGeoPoint = new GeoPoint(overlayloc);
		  OverlayItem newMyLocationItem = new OverlayItem(
		       "My Location", "My Location des", overlocGeoPoint);
		  //Create new marker
		  Drawable icon = this.getResources().getDrawable(R.drawable.ic_menu_mylocation);

		  //Set the bounding for the drawable
		  icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());

		  //Set the new marker to the overlay
		  newMyLocationItem.setMarker(icon);
		  newMyLocationItem.setMarkerHotspot(HotspotPlace.CENTER);
		  overlayItemArray.addItem(newMyLocationItem);
		     //---
	 }
	    
	 private LocationListener myLocationListener = new LocationListener(){
			
			  @Override
			  public void onLocationChanged(Location location) {
			   // TODO Auto-generated method stub
			   updateLoc(location);
			  }
			
			  @Override
			  public void onProviderDisabled(String provider) {
			   // TODO Auto-generated method stub
			   
			  }
			
			  @Override
			  public void onProviderEnabled(String provider) {
			   // TODO Auto-generated method stub
			   
			  }
			
			  @Override
			  public void onStatusChanged(String provider, int status, Bundle extras) {
			   // TODO Auto-generated method stub
			   
			  }
	     
	    };
  
}