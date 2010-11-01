package uk.org.assyrian.LEDFlashlight;

import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LEDFlashlight extends Activity {
	
	private Button onOffButton;
	private String mFlashMode;
	private boolean mIsFlashOn;
	private Camera mCamera = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// Log.d("LEDFlashlight", "entered onCreate");
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.onOffButton = (Button) this.findViewById(R.id.onoff);
        this.onOffButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mIsFlashOn) {
					mIsFlashOn = turnFlashOff();
				} else {
					mIsFlashOn = turnFlashOn();
				}
				onOffButton.setText(mIsFlashOn ? R.string.turn_off : R.string.turn_on);
			}
		});
    }

    private boolean turnFlashOff() {
    	// Log.d("LEDFlashlight", "Turning flash off");
    	mCamera.stopPreview();
    	Parameters p = mCamera.getParameters();
    	p.setFlashMode(mFlashMode);
    	mCamera.setParameters(p);
    	Log.d("LEDFlashlight", "Turned flash off");
		return false;
	}

	private boolean turnFlashOn() {
		// Log.d("LEDFlashlight", "Turning flash on");
		Parameters p = mCamera.getParameters();
		List<String> flashModes	= p.getSupportedFlashModes();
		if (flashModes.contains(Parameters.FLASH_MODE_TORCH)) {
			mFlashMode = p.getFlashMode();
			p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			mCamera.setParameters(p);
			mCamera.startPreview();
			Log.d("LEDFlashlight", "Turned flash on");
			return true;
		}
		Log.d("LEDFlashlight", "FLASH_MODE_TORCH not supported");
		return false;
	}
	
	@Override
	protected void onPause() {
		// Log.d("LEDFlashlight", "Pausing");
		super.onPause();
		mCamera.release();
		// Log.d("LEDFlashlight", "Leaving onPause");
	}
	
	@Override
	protected void onResume() {
		// Log.d("LEDFlashlight", "Entering onResume");
		super.onResume();
		mCamera = Camera.open();
	}
}