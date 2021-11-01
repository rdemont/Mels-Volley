package ch.rmbi.mels_volley;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.rmbi.mels_volley.utils.DynamicCountdownTimer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends RmbiFragment implements DynamicCountdownTimer.DynamicCountdownCallback {

    TextView _tvTimeout;
    TextView _tvInfo;

    boolean _isDown = true ;
    long _seconds = 0 ;
    DynamicCountdownTimer _timer = null ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _tvTimeout = view.findViewById(R.id.tvTimeout);
        _tvInfo = view.findViewById(R.id.tvInfo);

        _tvInfo.setText("");
        _tvTimeout.setText("");
        // Inflate the layout for this fragment

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }


    private void countDown(long seconds,boolean isDown)
    {
        _seconds = seconds;
        _isDown = isDown;
        if (_timer != null)
        {
            _timer.cancel();
        }
        if(!isDown)
        {
            _tvTimeout.setTextColor(Color.RED);
        }


        _timer = new DynamicCountdownTimer(seconds, 1000) ;
        _timer.setDynamicCountdownCallback(this);
        _timer.start();
    }

    public void onClick(View view)  {
        //Toast.makeText(this, "You click on 'Click Me' button!", Toast.LENGTH_SHORT).show();
        long seconds = 0 ;

        switch (view.getId()) {
            case R.id.bTimeout:
                seconds = Config.instance(getActivity()).getTimeoutSec() ;
                _tvInfo.setText("Temps mort");
                countDown(seconds,true);
                break;

            case R.id.bHalftime:
                seconds = Config.instance(getActivity()).getHalfTimeSec() ;
                _tvInfo.setText("Half time");
                countDown(seconds,true);
                break;
            case R.id.tvInfo:
            case R.id.tvTimeout:
                if (_timer != null){
                    if (_timer.isRuning()){
                        _timer.stop();
                    }else {
                        _timer.restart();
                    }
                }
        }


    }

    @Override
    public void onExitFragment() {
        if (_timer != null)
        {
            _timer.cancel();
        }
    }


    @Override
    public void onTick(long millisUntilFinished) {
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
        _tvTimeout.setText(str);

        //vibration des 5 dernière seconde
        if ((seconds <=5 )&& (minutes==0) && _isDown)
        {
            if (getActivity() != null) {
                ((MainActivity) getActivity()).vibrate(200);
            }
        }
    }

    @Override
    public void onFinish() {
        if (_isDown) {
            if (getActivity() != null){
                ((MainActivity)getActivity()).vibrateDefault();
            }


            if (_timer!= null) {

                _timer.cancel();
                _isDown = false;
                _seconds = 60 * 15;
                _timer = new DynamicCountdownTimer(_seconds, 1000);
                _timer.setDynamicCountdownCallback(this);
                _timer.start();
            }
        }else {
            _tvTimeout.setText("");
            _tvInfo.setText("");
        }
    }
}