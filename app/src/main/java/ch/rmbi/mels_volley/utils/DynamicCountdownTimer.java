package ch.rmbi.mels_volley.utils;

import android.os.CountDownTimer;

public class DynamicCountdownTimer {

    private CountDownTimer _timer = null;
    private long _millisUntilFinished = 0 ;
    private long _seconds = 0;
    private int _ticks = 0;
//    private boolean _supressFinish = false;
    private boolean _isRuning = false ;

    public boolean isRuning() {
        return _isRuning;
    }



    public DynamicCountdownTimer(long seconds, int ticks){
        setTimer(seconds, ticks);
    }
    public void stop(){
        if (_timer != null){
            _isRuning = false ;

            _timer.cancel();
            _timer = null;
        }
    }

    public void restart(){
        _isRuning = true ;
        setTimer(_millisUntilFinished/1000, _ticks);
        start();
    }

    public void updateSeconds(long seconds){
        if (_timer != null){

            _timer.cancel();
            _timer = null;
            _seconds = _millisUntilFinished + seconds;
            setTimer(_seconds, _ticks);
            start();
        }
    }

    public void setTimer(long seconds, int ticks){
        _seconds = seconds;
        _ticks = ticks;
        _timer = new CountDownTimer((_seconds  * 1000), ticks) {
            @Override
            public void onTick(long millisUntilFinished) {
                _millisUntilFinished = millisUntilFinished;
                callback.onTick(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                callback.onFinish();
            }
        };
    }

    public void start(){
        if (_timer != null){
            _timer.start();
            _isRuning = true ;
        }
    }

    public void cancel(){
        if (_timer != null){
            _timer.cancel();
            _isRuning = false ;
        }
    }

    public DynamicCountdownCallback callback = null;

    public void setDynamicCountdownCallback(DynamicCountdownCallback c){
        callback = c;
    }


    public interface DynamicCountdownCallback {
        void onTick(long millisUntilFinished);
        void onFinish();
    }

}