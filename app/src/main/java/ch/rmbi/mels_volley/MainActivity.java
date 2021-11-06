package ch.rmbi.mels_volley;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SimpleGestureFilter.SimpleGestureListener {



    private SimpleGestureFilter _detector;
    public long _vibrationSec = 0;
    private RmbiFragment _lastFragment = null ;
    private boolean _isMatchFragment = true ;
    private String _menuTitle = "Pré-Match" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPreferences(MODE_PRIVATE);

        _vibrationSec = Config.instance(this).getVibrationSec();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fcvMain, new GameFragment());
        ft.commit();


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
// Detect touched area
        _detector = new SimpleGestureFilter(this,this);
    }



    public void vibrateDefault()
    {

        vibrate(_vibrationSec *1000);
    }

    public void vibrate(long milliseconds)
    {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);

    }

    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this._detector.onTouchEvent(me);
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

    private void openGameFragment()
    {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fcvMain);
        if (f instanceof RmbiFragment)
        {
            ((RmbiFragment)f).onExitFragment();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fcvMain, new GameFragment());
        ft.commit();
    }

    private void openPreGameFragment(){
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fcvMain);
        if (f instanceof RmbiFragment)
        {
            ((RmbiFragment)f).onExitFragment();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fcvMain, new PregameFragment());
        ft.commit();
    }

    private void openConfigFragment(){
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fcvMain);
        if (f instanceof RmbiFragment)
        {
            ((RmbiFragment)f).onExitFragment();
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fcvMain, new ConfigFragment());
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        MenuItem item = menu.findItem(R.id.mitMatch);

        item.setTitle(_menuTitle);

        return true ;

    }

    private boolean menuEvent(MenuItem item){

        switch (item.getItemId()){
            case R.id.mitConfig:
                openConfigFragment();
                _menuTitle = "Back";
                invalidateOptionsMenu();
                return true ;
            case R.id.mitMatch:
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.fcvMain);
                if (!(f instanceof ConfigFragment))
                {
                    _isMatchFragment = ! _isMatchFragment;
                }
                if (_isMatchFragment) {
                    _menuTitle = "Pré-Match";
                    openGameFragment();
                }else{
                    _menuTitle = "Match";
                    openPreGameFragment();
                }
                invalidateOptionsMenu();
                return true;
        }
        return false;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (menuEvent(item))
            return true ;
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (menuEvent(item))
            return true ;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fcvMain);
        if (f instanceof RmbiFragment)
        {
            if (f instanceof ConfigFragment){
                ((RmbiFragment)f).onExitFragment();
                openGameFragment();
                return ;
            }

        }
        super.onBackPressed();
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