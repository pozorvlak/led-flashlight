package uk.org.assyrian.LEDFlashlight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LEDFlashlight extends Activity {
	
	private Button onOffButton;
	private AlertDialog mDialog;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DialogInterface.OnClickListener doNothing = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// do nothing
			}
		};
        mDialog = new AlertDialog.Builder(this)
        	.setMessage(getString(R.string.message))
        	.setPositiveButton("OK!", doNothing)
        	.setNegativeButton("Boo!", doNothing)
        	.create();
        this.onOffButton = (Button) this.findViewById(R.id.onoff);
        this.onOffButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mDialog.show();
			}
		});
    }
}