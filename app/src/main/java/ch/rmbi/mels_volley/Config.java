package ch.rmbi.mels_volley;

import android.app.Activity;
import android.content.SharedPreferences;

public class Config {

    private static Config _instance = null ;
    private SharedPreferences.Editor _editor = null;
    private SharedPreferences _sharedPreferences = null ;
    private int _vibrationSec = 5;
    private int _preServiceSec = 50;
    private int _timeoutSec = 30;
    private int _halfTimeSec = 300;

    private int _attackLeft = 5*60; //5 minutes
    private int _attackRight = 5*60; //5 minutes
    private int _service = 3*60; //3 minutes
    private int _applause = 2*60; //2 minutes

    private static String VIBRATION_SEC = "VIBRATION_SEC" ;
    private static String PRE_SERVICE_SEC = "PRE_SERVICE_SEC" ;
    private static String TIMEOUT_SEC = "TIMEOUT_SEC" ;
    private static String HALF_TIME_SEC = "HALF_TIME_SEC" ;

    private static String ATTTACK_LEFT_SEC = "ATTTACK_LEFT_SEC" ;
    private static String ATTTACK_RIGHT_SEC = "ATTTACK_RIGHT_SEC" ;
    private static String SERVICE_SEC = "SERVICE_SEC" ;
    private static String APPLAUSE_SEC = "APPLAUSE_SEC" ;


    public int getVibrationSec() {
        return _vibrationSec;
    }

    public void setVibrationSec(int vibrationSec) {
        _vibrationSec = vibrationSec;
    }
    public long getPreServiceSec() {
        return _preServiceSec;
    }

    public void setPreServiceSec(int _preServiceSec) {
        this._preServiceSec = _preServiceSec;
    }

    public int getTimeoutSec() {
        return _timeoutSec;
    }

    public void setTimeoutSec(int _timeoutSec) {
        this._timeoutSec = _timeoutSec;
    }

    public int getHalfTimeSec() {
        return _halfTimeSec;
    }

    public void setHalfTimeSec(int _halfTimeSec) {
        this._halfTimeSec = _halfTimeSec;
    }

    public int getAttackLeft() {
        return _attackLeft;
    }

    public void setAttackLeft(int attackLeft) {
        this._attackLeft = attackLeft;
    }

    public int getAttackRight() {
        return _attackRight;
    }

    public void setAttackRight(int _AttackRight) {
        this._attackRight = _AttackRight;
    }

    public int getService() {
        return _service;
    }

    public void setService(int _Service) {
        this._service = _Service;
    }

    public int getApplause() {
        return _applause;
    }

    public void setApplause(int _Applause) {
        this._applause = _Applause;
    }

    private Config(Activity activity)
    {
        _sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);
        _editor = _sharedPreferences.edit();
        load();
    }

    private void load()
    {
        _vibrationSec = _sharedPreferences.getInt(VIBRATION_SEC, _vibrationSec);
        _preServiceSec = _sharedPreferences.getInt(PRE_SERVICE_SEC,_preServiceSec);
        _timeoutSec = _sharedPreferences.getInt(TIMEOUT_SEC,_timeoutSec);
        _halfTimeSec = _sharedPreferences.getInt(HALF_TIME_SEC,_halfTimeSec);
        _attackLeft = _sharedPreferences.getInt(ATTTACK_LEFT_SEC,_attackLeft);
        _attackRight = _sharedPreferences.getInt(ATTTACK_RIGHT_SEC,_attackRight);
        _service = _sharedPreferences.getInt(SERVICE_SEC,_service);
        _applause = _sharedPreferences.getInt(APPLAUSE_SEC,_applause);

    }
    public void save()
    {
        _editor.putInt(VIBRATION_SEC, _vibrationSec);
        _editor.putInt(PRE_SERVICE_SEC, _preServiceSec);
        _editor.putInt(TIMEOUT_SEC, _timeoutSec);
        _editor.putInt(HALF_TIME_SEC, _halfTimeSec);
        _editor.putInt(ATTTACK_LEFT_SEC, _attackLeft);
        _editor.putInt(ATTTACK_RIGHT_SEC, _attackRight);
        _editor.putInt(SERVICE_SEC, _service);
        _editor.putInt(APPLAUSE_SEC, _applause);

        _editor.commit();
    }




    public static Config instance(Activity activity)
    {
        if (_instance == null)
        {
            _instance = new Config(activity);


        }
        return _instance ;

    }
}
