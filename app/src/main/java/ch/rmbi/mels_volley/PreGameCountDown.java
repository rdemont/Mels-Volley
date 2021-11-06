package ch.rmbi.mels_volley;

import android.app.Activity;
import android.graphics.Color;
import android.widget.ProgressBar;
import android.widget.TextView;

import ch.rmbi.mels_volley.utils.DynamicCountdownTimer;
import ch.rmbi.mels_volley.views.RmbiNumberProgressBar;

public class PreGameCountDown implements DynamicCountdownTimer.DynamicCountdownCallback {
    private long _seconds = 0 ;
    private TextView _tv = null;
    private RmbiNumberProgressBar _pb = null;
    private boolean _isDown = true ;
    private Activity _activity = null ;
    private DynamicCountdownTimer _timer = null ;


    public PreGameCountDown(long seconds, TextView tv, Activity activity){

        _seconds = seconds;
        _tv = tv;
        _activity = activity;
    }

    public PreGameCountDown(long seconds, TextView tv, RmbiNumberProgressBar pb, Activity activity){

        _seconds = seconds;
        _tv = tv;
        _pb = pb;
        _activity = activity;
    }


    public PreGameCountDown(long seconds, TextView tv, RmbiNumberProgressBar pb, Activity activity, boolean isDown){

        _seconds = seconds;
        _tv = tv;
        _pb = pb;
        _activity = activity;
        _isDown = isDown;
    }


    public PreGameCountDown(long seconds, TextView tv, Activity activity, boolean isDown){

        _seconds = seconds;
        _tv = tv;
        _activity = activity;
        _isDown = isDown;
    }

    public boolean isRunning() {
        if (_timer == null)
            return false ;
        return _timer.isRuning();
    }

    public void restart()
    {
        if(_timer != null)
        {
            _timer.restart();
        }
    }

    public void stop()
    {
        if(_timer != null)
        {
            _timer.stop();
        }
    }

    public void start()
    {
        if (_timer != null)
        {
            _timer.cancel();
        }
        if(_pb != null){
            _pb.setMax((int)_seconds);
        }

        _timer = new DynamicCountdownTimer(_seconds,1000);
        _timer.setDynamicCountdownCallback(this);
        _timer.start();
    }


    public void onTick(long millisUntilFinished) {
/*
        if(!_isDown)
        {
            //_tv.setTextColor(Color.RED);

        }
*/
        if (_pb != null){
            if(_isDown)
            {
                _pb.setProgress((int)millisUntilFinished/1000);
            }else {
                _pb.setProgress((int) (_seconds - millisUntilFinished/1000));
            }

        }


        String str = "" ;
        long minutes = ((millisUntilFinished / 1000) % 3600)/60;
        long seconds = (millisUntilFinished / 1000) % 60;
        if (!_isDown)
        {
            minutes = ((((_seconds*1000)-millisUntilFinished) / 1000) % 3600)/60;
            seconds = (((_seconds*1000)-millisUntilFinished) / 1000) % 60;

        }
        if (minutes>0 ) {
            if (minutes<=9){
                str += "0";
            }
            str += minutes + ":";
        }
        if ((seconds<=9) && (minutes > 0)){
            str += "0";
        }
        str += seconds;
        if (_tv != null){
            _tv.setText(str);
        }


        //vibration des 5 dernière seconde
        if ((seconds <=5 )&& (minutes==0) && _isDown)
        {
            if (_activity != null) {
                ((MainActivity) _activity).vibrate(200);
            }
        }
    }


    public void onFinish() {
        if (_isDown) {
            if (_activity != null){
                ((MainActivity)_activity).vibrateDefault();
            }

            _seconds = 60*2 ; // 2 minutes

            _isDown = false ;
            start();

        }else {
            if (_tv != null) {
                _tv.setText("");
            }

        }
    }
}
