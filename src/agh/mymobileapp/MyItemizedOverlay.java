package agh.mymobileapp;  

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.ResourceProxy;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

class MyItemizedIconOverlay extends ItemizedIconOverlay<OverlayItem>{
	 ArrayList<OverlayItem> overlayItemArray;
	 Resources res;
	  
	  public MyItemizedIconOverlay(
	    List<OverlayItem> pList,
	    org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<OverlayItem> pOnItemGestureListener,
	    ResourceProxy pResourceProxy, Resources res) {
	   super(pList, pOnItemGestureListener, pResourceProxy);
	   // TODO Auto-generated constructor stub
	   overlayItemArray = (ArrayList<OverlayItem>) pList;
	   this.res = res;
	  }
	  
	  @Override
	  public void draw(Canvas canvas, MapView mapview, boolean arg2) {
	   // TODO Auto-generated method stub
	   super.draw(canvas, mapview, arg2);
	   
	   if(!overlayItemArray.isEmpty()){
	    
	    //overlayItemArray have only ONE element only, so I hard code to get(0)
	    GeoPoint in = overlayItemArray.get(0).getPoint();
	    
	    Point out = new Point();
	    mapview.getProjection().toPixels(in, out);
	    
	    Bitmap bm = BitmapFactory.decodeResource(res, R.drawable.ic_menu_mylocation);
	    canvas.drawBitmap(bm, 
	      out.x - bm.getWidth()/2,  //shift the bitmap center
	      out.y - bm.getHeight()/2,  //shift the bitmap center
	      null);
	   }
	  }
	
}