package ch.rmbi.mels_volley;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PregameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PregameFragment extends RmbiFragment  {

    TextView tvAttackLeft;
    TextView tvAttackRight ;
    TextView tvService;
    TextView tvApplause ;


    PreGameCountDown _cdtAttackLeft = null ;
    PreGameCountDown _cdtAttackRight = null ;
    PreGameCountDown _cdtService = null ;
    PreGameCountDown _cdtApplause  = null ;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PregameFragment() {
                    // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PregameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PregameFragment newInstance(String param1, String param2) {
        PregameFragment fragment = new PregameFragment();
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

        tvAttackLeft = view.findViewById(R.id.tvAttackLeft);
        tvAttackRight = view.findViewById(R.id.tvAttackRight);
        tvService = view.findViewById(R.id.tvService);
        tvApplause = view.findViewById(R.id.tvApplause);

        tvAttackLeft.setText("");
        tvAttackRight.setText("");
        tvService.setText("");
        tvApplause.setText("");
        // Inflate the layout for this fragment

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pregame, container, false);
    }


    public void onClick(View view)  {
        //Toast.makeText(this, "You click on 'Click Me' button!", Toast.LENGTH_SHORT).show();
        long seconds = 0 ;
        CountDownTimer cdt = null ;
        TextView tv = null ;
        switch (view.getId()) {
            case R.id.tvAttackLeft:
                if (_cdtAttackLeft != null)
                {
                    if (_cdtAttackLeft.isRunning()){
                        _cdtAttackLeft.stop();
                    }else {
                        _cdtAttackLeft.restart();
                    }
                }
                break;
            case R.id.tvAttackRight:
                if (_cdtAttackRight != null)
                {
                    if (_cdtAttackRight.isRunning()){
                        _cdtAttackRight.stop();
                    }else {
                        _cdtAttackRight.restart();
                    }
                }
                break;
            case R.id.tvService:
                if (_cdtService != null)
                {
                    if (_cdtService.isRunning()){
                        _cdtService.stop();
                    }else {
                        _cdtService.restart();
                    }
                }
                break;
            case R.id.tvApplause:
                if (_cdtApplause != null)
                {
                    if (_cdtApplause.isRunning()){
                        _cdtApplause.stop();
                    }else {
                        _cdtApplause.restart();
                    }
                }
                break;

            case R.id.bAttackLeft:
                seconds = Config.instance(getActivity()).getAttackLeft() ;
                if (_cdtAttackLeft != null){
                    _cdtAttackLeft.stop();
                }
                _cdtAttackLeft = new PreGameCountDown(seconds,tvAttackLeft,getActivity());
                _cdtAttackLeft.start();
                break;

            case R.id.bAttackRight:
                seconds = Config.instance(getActivity()).getAttackRight();
                if (_cdtAttackRight != null){
                    _cdtAttackRight.stop();
                }
                _cdtAttackRight = new PreGameCountDown(seconds,tvAttackRight,getActivity());
                _cdtAttackRight.start();
                break;
            case R.id.bService:
                seconds = Config.instance(getActivity()).getService() ;
                if (_cdtService!= null){
                    _cdtService.stop();
                }
                _cdtService = new PreGameCountDown(seconds,tvService,getActivity());
                _cdtService.start();
                break;
            case R.id.bApplause:
                seconds = Config.instance(getActivity()).getApplause() ;
                if (_cdtApplause != null){
                    _cdtApplause.stop();
                }
                _cdtApplause = new PreGameCountDown(seconds,tvApplause,getActivity());
                _cdtApplause.start();
                break;
        }
    }

    @Override
    public void onExitFragment() {
        if (_cdtAttackLeft != null)
        {
            _cdtAttackLeft.stop();
        }
        if (_cdtAttackRight != null)
        {
            _cdtAttackRight.stop();
        }
        if (_cdtService != null)
        {
            _cdtService.stop();
        }
        if (_cdtApplause  != null)
        {
            _cdtApplause.stop();
        }
    }

}