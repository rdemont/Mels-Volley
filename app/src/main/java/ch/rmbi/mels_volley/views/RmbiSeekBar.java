package ch.rmbi.mels_volley.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatSeekBar;

import ch.rmbi.mels_volley.R;
import ch.rmbi.mels_volley.utils.Functions;

public class RmbiSeekBar extends AppCompatSeekBar {

    private final int TEXT_FORMAT_NONE = 0 ;
    private final int TEXT_FORMAT_SECOND = 1 ;

    private SeekBar.OnSeekBarChangeListener _onSeekBarChangeListener;
    private SeekBar.OnSeekBarChangeListener _parentOnSeekBarChangeListener;

    private int _textFormat = TEXT_FORMAT_NONE;

    private int _step = 1;

    private int _progress = 0 ;

    private TextView _tv = null ;


    public int getTextFormat() {
        return _textFormat;
    }

    public void setTextFormat(int textFormat) {
        _textFormat = textFormat;
    }


    public int getStep() {
        return _step;
    }

    public void setStep(int step) {
        if (_progress > 0){
            _progress = (_progress - getMin()) / step;
            super.setProgress(_progress);
        }
        this._step = step;
    }

    @Override
    public synchronized void setProgress(int progress) {
        _progress = progress;
        if (_step > 1){
            _progress = (_progress - getMin()) / _step;
        }
        super.setProgress(_progress);
    }

    @Override
    public synchronized int getProgress() {
        int localProgress = super.getProgress();
        if (_step>1 )
        {
            localProgress = getMin() + (localProgress*_step);
        }
        return localProgress;
    }

    public RmbiSeekBar(@NonNull Context context) {
        super(context);
        init();
    }

    public RmbiSeekBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initSeekBar(context,attrs);
        init();
    }

    public RmbiSeekBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSeekBar(context,attrs);
        init();
    }

    private void initSeekBar(Context context, AttributeSet attrs)
    {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RmbiSeekBar);
        setTextFormat(a.getInteger(R.styleable.RmbiSeekBar_textFormat,TEXT_FORMAT_NONE));
        setStep(a.getInteger(R.styleable.RmbiSeekBar_step,1));
        a.recycle();
        if (_step > 1){
            setMax((getMax()-getMin()) / _step);
        }

    }

    public void setTextFormat(){

    }

    private void init()
    {
        _onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int localProgress = progress;
                if (_step>1 )
                {
                    localProgress = getMin() + (progress*_step);
                }

                if (_tv != null){
                    _tv.setText(""+getTextFormated( localProgress));
                }


                if (_parentOnSeekBarChangeListener != null) {
                    _parentOnSeekBarChangeListener.onProgressChanged(seekBar,localProgress,fromUser);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (_parentOnSeekBarChangeListener != null) {
                    _parentOnSeekBarChangeListener.onStartTrackingTouch(seekBar);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (_parentOnSeekBarChangeListener != null) {
                    _parentOnSeekBarChangeListener.onStopTrackingTouch(seekBar);
                }
            }
        };
        super.setOnSeekBarChangeListener(_onSeekBarChangeListener);
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (_tv != null )
        {
            _tv.setText(getTextFormated(getProgress()));
        }
    }


    private String getTextFormated(int progress) {
        switch (_textFormat) {
            case TEXT_FORMAT_SECOND :
                return Functions.getInstance().getSecondInString(progress);

        }
        return ""+progress;

    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        _parentOnSeekBarChangeListener = l ;
    }

    public void setTextView(TextView tv)
    {
        _tv = tv ;
    }

}
