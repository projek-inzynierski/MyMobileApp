package agh.mymobileapp;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class AddApplicationActivity extends Activity {
	
	static final int REQUEST_IMAGE_CAPTURE = 1;
	
	Button submitApplicationButton;
	Button capturePhotoButton;
	EditText descriptionEditTextArea;
	PackageManager packageManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.addapplication_main);
	        
	        packageManager = getPackageManager();
	        
	        submitApplicationButton = (Button) findViewById(R.id.submitApplicationButton);
	        
	        capturePhotoButton = (Button) findViewById(R.id.capturePhotoButton);
	        
	        descriptionEditTextArea = (EditText) findViewById(R.id.descriptionEditTextArea);
	        
	        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        	capturePhotoButton.setEnabled(false);
	        	capturePhotoButton.setText(R.string.no_camera);
	        }
	        
	        submitApplicationButton.setOnClickListener(new OnClickListener() {
			

					@Override
					public void onClick(View arg0) {
				            /*if(!validate())
				                    Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
				            new HttpAsyncTask().execute("http://hmkcode.appspot.com/jsonservlet");*/
					}
				
			});
	        
	        capturePhotoButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					dispatchTakePictureIntent();
					
				}
			});
	        
	}
	
	private void dispatchTakePictureIntent() {
		
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    File photo = new File(Environment.getExternalStorageDirectory(), "dumb_photo.jpg");
	    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
	    	
	    }
	}
}