package ch.rmbi.mels_volley;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import ch.rmbi.mels_volley.utils.Functions;
import ch.rmbi.mels_volley.views.RmbiSeekBar;
import ch.rmbi.mels_volley.views.RmbiTextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfigFragment extends RmbiFragment {
    RmbiSeekBar _sbVibration;
    RmbiTextView _tvVibrationVal ;
    CheckBox _cbVibration ;

    RmbiSeekBar _sbAttackLeft;
    RmbiTextView _tvAttackLeftVal ;
    CheckBox _cbAttackLeft ;

    RmbiSeekBar _sbAttackRight;
    RmbiTextView _tvAttackRightVal ;
    CheckBox _cbAttackRight ;

    RmbiSeekBar _sbService;
    RmbiTextView _tvServiceVal ;
    CheckBox _cbService ;

    RmbiSeekBar _sbApplause;
    RmbiTextView _tvApplauseVal ;
    CheckBox _cbApplause ;

    RmbiSeekBar _sbTimeout;
    RmbiTextView _tvTimeoutVal ;
    CheckBox _cbTimeout ;

    RmbiSeekBar _sbHalftime;
    RmbiTextView _tvHalftimeVal ;
    CheckBox _cbHalftime ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConfigFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfigFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfigFragment newInstance(String param1, String param2) {
        ConfigFragment fragment = new ConfigFragment();
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

    private void initVibration(View view )
    {
        _sbVibration = view.findViewById(R.id.sbVibration);
        _tvVibrationVal = view.findViewById(R.id.tvVibrationVal);
        _cbVibration = view.findViewById(R.id.cbVibration);
        int vibrationSec = Config.instance(getActivity()).getVibrationSec();
        _sbVibration.setProgress(vibrationSec);
        _cbVibration.setChecked(vibrationSec > 0);
        _sbVibration.setTextView(_tvVibrationVal);
        _sbVibration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Config.instance(getActivity()).setVibrationSec(progress);
                _cbVibration.setChecked(progress>0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        _cbVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                {
                    Config.instance(getActivity()).setVibrationSec(0);
                }else {
                    Config.instance(getActivity()).setVibrationSec(_sbVibration.getProgress());
                }
            }
        });

    }

    private void initHalftime(View view )
    {
        _sbHalftime = view.findViewById(R.id.sbHalftime);
        _tvHalftimeVal = view.findViewById(R.id.tvHalftimeVal);
        _cbHalftime = view.findViewById(R.id.cbHalftime);
        int HalftimeSec = Config.instance(getActivity()).getHalfTimeSec();
        _sbHalftime.setProgress(HalftimeSec);
        _cbHalftime.setChecked(HalftimeSec > 0);
        _sbHalftime.setTextView(_tvHalftimeVal);
        _sbHalftime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Config.instance(getActivity()).setHalfTimeSec(progress);
                _cbHalftime.setChecked(progress>0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        _cbHalftime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                {
                    Config.instance(getActivity()).setHalfTimeSec(0);
                }else {
                    Config.instance(getActivity()).setHalfTimeSec(_sbHalftime.getProgress());
                }
            }
        });

    }

    private void initAttackLeft(View view )
    {
        _sbAttackLeft = view.findViewById(R.id.sbAttackLeft);
        _tvAttackLeftVal = view.findViewById(R.id.tvAttackLeftVal);
        _cbAttackLeft = view.findViewById(R.id.cbAttackLeft);
        int attackLeftSec = Config.instance(getActivity()).getAttackLeft();
        _sbAttackLeft.setTextView(_tvAttackLeftVal);
        _sbAttackLeft.setProgress(attackLeftSec);
        _cbAttackLeft.setChecked(attackLeftSec > 0);
        _sbAttackLeft.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Config.instance(getActivity()).setAttackLeft(progress);
                _cbAttackLeft.setChecked(progress>0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        _cbAttackLeft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                {
                    Config.instance(getActivity()).setAttackLeft(0);
                }else {
                    Config.instance(getActivity()).setAttackLeft(_sbAttackLeft.getProgress());
                }
            }
        });

    }


    private void initAttackRight(View view )
    {
        _sbAttackRight = view.findViewById(R.id.sbAttackRight);
        _tvAttackRightVal = view.findViewById(R.id.tvAttackRightVal);
        _cbAttackRight = view.findViewById(R.id.cbAttackRight);
        int attackRightSec = Config.instance(getActivity()).getAttackRight();
        _sbAttackRight.setTextView(_tvAttackRightVal);
        _sbAttackRight.setProgress(attackRightSec);
        _cbAttackRight.setChecked(attackRightSec > 0);
        _sbAttackRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Config.instance(getActivity()).setAttackRight(progress);
                _cbAttackRight.setChecked(progress>0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        _cbAttackRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                {
                    Config.instance(getActivity()).setAttackRight(0);
                }else {
                    Config.instance(getActivity()).setAttackRight(_sbAttackRight.getProgress());
                }
            }
        });

    }


    private void initService(View view )
    {
        _sbService = view.findViewById(R.id.sbService);
        _tvServiceVal = view.findViewById(R.id.tvServiceVal);
        _cbService = view.findViewById(R.id.cbService);
        int serviceSec = Config.instance(getActivity()).getService();
        _sbService.setTextView(_tvServiceVal);
        _sbService.setProgress(serviceSec);
        _cbService.setChecked(serviceSec > 0);
        _sbService.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Config.instance(getActivity()).setService(progress);
                _cbService.setChecked(progress>0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        _cbService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                {
                    Config.instance(getActivity()).setService(0);
                }else {
                    Config.instance(getActivity()).setService(_sbService.getProgress());
                }
            }
        });

    }


    private void initApplause(View view )
    {
        _sbApplause = view.findViewById(R.id.sbApplause);
        _tvApplauseVal = view.findViewById(R.id.tvApplauseVal);
        _cbApplause = view.findViewById(R.id.cbApplause);
        int applauseSec = Config.instance(getActivity()).getApplause();
        _sbApplause.setTextView(_tvApplauseVal);
        _sbApplause.setProgress(applauseSec);
        _cbApplause.setChecked(applauseSec > 0);
        _sbApplause.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Config.instance(getActivity()).setApplause(progress);
                _cbApplause.setChecked(progress>0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        _cbApplause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                {
                    Config.instance(getActivity()).setApplause(0);
                }else {
                    Config.instance(getActivity()).setApplause(_sbApplause.getProgress());
                }
            }
        });

    }


    private void initTimeout(View view )
    {
        _sbTimeout = view.findViewById(R.id.sbTimeout);
        _tvTimeoutVal = view.findViewById(R.id.tvTimeoutVal);
        _cbTimeout = view.findViewById(R.id.cbTimeout);
        int timeoutVal = Config.instance(getActivity()).getTimeoutSec();
        _sbTimeout.setTextView(_tvTimeoutVal);
        _sbTimeout.setProgress(timeoutVal);
        _cbTimeout.setChecked(timeoutVal > 0);
        _sbTimeout.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Config.instance(getActivity()).setTimeoutSec(progress);
                _cbTimeout.setChecked(progress>0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        _cbTimeout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                {
                    Config.instance(getActivity()).setTimeoutSec(0);
                }else {
                    Config.instance(getActivity()).setTimeoutSec(_sbTimeout.getProgress());
                }
            }
        });

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initVibration(view);
        initAttackLeft(view);
        initAttackRight(view);
        initTimeout(view);
        initHalftime(view);
        initService(view);
        initApplause(view);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config, container, false);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onExitFragment() {
        Config.instance(getActivity()).save();
    }


}