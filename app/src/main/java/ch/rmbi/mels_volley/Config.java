package ch.rmbi.mels_volley;

import android.app.Activity;
import android.content.SharedPreferences;

public class Config {

    private static Config _instance = null ;
    private SharedPreferences.Editor _editor = null;
    private SharedPreferences _sharedPreferences = null ;
    private long _vibrationMill = 5000;
    private long _preServiceSec = 50;
    private long _timeoutSec = 30;
    private long _halfTimeSec = 300;

    private long _attackLeft = 5*60; //5 minutes
    private long _attackRight = 5*60; //5 minutes
    private long _service = 3*60; //3 minutes
    private long _applause = 2*60; //2 minutes

    private static String VIBRATION_MILL = "VIBRATION_MILL" ;
    private static String PRE_SERVICE_SEC = "PRE_SERVICE_SEC" ;
    private static String TIMEOUT_SEC = "TIMEOUT_SEC" ;
    private static String HALF_TIME_SEC = "HALF_TIME_SEC" ;

    private static String ATTTACK_LEFT_SEC = "ATTTACK_LEFT_SEC" ;
    private static String ATTTACK_RIGHT_SEC = "ATTTACK_RIGHT_SEC" ;
    private static String SERVICE_SEC = "SERVICE_SEC" ;
    private static String APPLAUSE_SEC = "APPLAUSE_SEC" ;


    public long getVibrationMill() {
        return _vibrationMill;
    }

    public void setVibrationMill(long _vibrationMill) {
        this._vibrationMill = _vibrationMill;
    }
    public long getPreServiceSec() {
        return _preServiceSec;
    }

    public void setPreServiceSec(long _preServiceSec) {
        this._preServiceSec = _preServiceSec;
    }

    public long getTimeoutSec() {
        return _timeoutSec;
    }

    public void setTimeoutSec(long _timeoutSec) {
        this._timeoutSec = _timeoutSec;
    }

    public long getHalfTimeSec() {
        return _halfTimeSec;
    }

    public void setHalfTimeSec(long _halfTimeSec) {
        this._halfTimeSec = _halfTimeSec;
    }

    public long getAttackLeft() {
        return _attackLeft;
    }

    public void setAttackLeft(long _AttackLeft) {
        this._attackLeft = _AttackLeft;
    }

    public long getAttackRight() {
        return _attackRight;
    }

    public void setAttackRight(long _AttackRight) {
        this._attackRight = _AttackRight;
    }

    public long getService() {
        return _service;
    }

    public void setService(long _Service) {
        this._service = _Service;
    }

    public long getApplause() {
        return _applause;
    }

    public void setApplause(long _Applause) {
        this._applause = _Applause;
    }

    private Config(Activity activity)
    {
        _sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = _sharedPreferences.edit();
        load();
    }

    private void load()
    {
        _vibrationMill = _sharedPreferences.getLong(VIBRATION_MILL,300000);
        _preServiceSec = _sharedPreferences.getLong(PRE_SERVICE_SEC,60*5);
        _timeoutSec = _sharedPreferences.getLong(TIMEOUT_SEC,30);

    }
    public void save()
    {
        _editor.putLong(VIBRATION_MILL, _vibrationMill);
        _editor.putLong(PRE_SERVICE_SEC, _preServiceSec);
        _editor.putLong(TIMEOUT_SEC, _timeoutSec);
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
