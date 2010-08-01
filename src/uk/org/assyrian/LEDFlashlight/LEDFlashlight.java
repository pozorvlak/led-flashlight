package uk.org.assyrian.LEDFlashlight;

import java.util.List;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
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
    	mCamera.stopPreview();
    	Parameters p = mCamera.getParameters();
    	p.setFlashMode(mFlashMode);
    	mCamera.setParameters(p);
		return false;
	}

	private boolean turnFlashOn() {
		Parameters p = mCamera.getParameters();
		List<String> flashModes	= p.getSupportedFlashModes();
		if (flashModes.contains(Parameters.FLASH_MODE_TORCH)) {
			mFlashMode = p.getFlashMode();
			p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			mCamera.setParameters(p);
			mCamera.startPreview();
			return true;
		}
		return false;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mCamera.release();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mCamera = Camera.open();
	}
}