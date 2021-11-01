package ch.rmbi.mels_volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SimpleGestureFilter.SimpleGestureListener {



    private SimpleGestureFilter detector;
    public long vibrationMill = 0;
    private RmbiFragment _lastFragment = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPreferences(MODE_PRIVATE);

        vibrationMill = Config.instance(this).getVibrationMill();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fcvMain, new GameFragment());
        ft.commit();


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
// Detect touched area
        detector = new SimpleGestureFilter(this,this);
    }



    public void vibrateDefault()
    {
        vibrate(vibrationMill);
    }

    public void vibrate(long milliseconds)
    {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);

    }

    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {


        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT :
                nextFragment();
                break;
            case SimpleGestureFilter.SWIPE_LEFT :
                nextFragment();
                break;
            case SimpleGestureFilter.SWIPE_DOWN :
                upFragment();
                break;
            case SimpleGestureFilter.SWIPE_UP :
                upFragment();
                break;

        }

    }



    private void upFragment()
    {

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fcvMain);
        RmbiFragment nextF = null ;
        if ((f instanceof RmbiFragment) && !(f instanceof ConfigFragment))
        {
            _lastFragment = (RmbiFragment) f;
        }

        if (f instanceof ConfigFragment)
        {
            if (_lastFragment != null) {
                nextF = _lastFragment;
            }else {
                nextF = new GameFragment();
            }
        }else {

            nextF = new ConfigFragment();
        }

        ((RmbiFragment)f).onExitFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fcvMain, nextF);
        ft.commit();
    }

    private void nextFragment()
    {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fcvMain);
        RmbiFragment nextF = null ;
        if (f instanceof GameFragment)
        {
            nextF = new PregameFragment();
        }
        if (f instanceof PregameFragment)
        {
            nextF = new GameFragment();
        }

        if (nextF == null){
            if (_lastFragment != null) {
                nextF = _lastFragment;
            }else {
                nextF = new GameFragment();
            }
        }

        ((RmbiFragment)f).onExitFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fcvMain, nextF);
        ft.commit();
    }

    @Override
    public void onDoubleTap() {
        //Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }

    public void onDblClick(View view)  {
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        /*
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fcvMain);
        if (f instanceof RmbiFragment)
        {
            ((RmbiFragment)f).onDblClick();
        }

         */
    }

    public void onClick(View view)  {

        //Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fcvMain);
        if (f instanceof RmbiFragment)
        {
            ((RmbiFragment)f).onClick(view);
        }
    }
}