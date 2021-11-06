package ch.rmbi.mels_volley.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import ch.rmbi.mels_volley.R;
import ch.rmbi.mels_volley.utils.Functions;

public class RmbiNumberProgressBar extends View {

    private int _maxProgress = 100;

    /**
     * Current progress, can not exceed the max progress.
     */
    private int _currentProgress = 0;

    /**
     * The progress area bar color.
     */
    private int _reachedBarColor;

    /**
     * The bar unreached area color.
     */
    private int _unreachedBarColor;

    /**
     * The progress text color.
     */
    private int _textColor;

    /**
     * The progress text size.
     */
    private float _textSize;

    /**
     * The height of the reached area.
     */
    private float _reachedBarHeight;

    /**
     * The height of the unreached area.
     */
    private float _unreachedBarHeight;

    /**
     * The suffix of the number.
     */
    private String _suffix = "%";

    /**
     * The prefix.
     */
    private String _prefix = "";


    private final int default_text_color = Color.rgb(66, 145, 241);
    private final int default_reached_color = Color.rgb(66, 145, 241);
    private final int default_unreached_color = Color.rgb(204, 204, 204);
    private final float default_progress_text_offset;
    private final float default_text_size;
    private final float default_reached_bar_height;
    private final float default_unreached_bar_height;

    /**
     * For save and restore instance of progressbar.
     */
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
    private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
    private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
    private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_PREFIX = "prefix";
    private static final String INSTANCE_TEXT_VISIBILITY = "text_visibility";

    private static final int PROGRESS_TEXT_VISIBLE = 0;


    private static final int TEXT_FORMAT_NONE = 0 ;
    private static final int TEXT_FORMAT_SECOND = 1 ;
    private static final int TEXT_FORMAT_PERCENT = 2 ;


    private int _textFormat = TEXT_FORMAT_PERCENT;
    /**
     * The width of the text that to be drawn.
     */
    private float _drawTextWidth;

    /**
     * The drawn text start.
     */
    private float _drawTextStart;

    /**
     * The drawn text end.
     */
    private float _drawTextEnd;

    /**
     * The text that to be drawn in onDraw().
     */
    private String _currentDrawText;

    /**
     * The Paint of the reached area.
     */
    private Paint _reachedBarPaint;
    /**
     * The Paint of the unreached area.
     */
    private Paint _unreachedBarPaint;
    /**
     * The Paint of the progress text.
     */
    private Paint _textPaint;

    /**
     * Unreached bar area to draw rect.
     */
    private RectF _unreachedRectF = new RectF(0, 0, 0, 0);
    /**
     * Reached bar area rect.
     */
    private RectF _reachedRectF = new RectF(0, 0, 0, 0);

    /**
     * The progress text offset.
     */
    private float _offset;

    /**
     * Determine if need to draw unreached area.
     */
    private boolean _drawUnreachedBar = true;

    private boolean _drawReachedBar = true;

    private boolean _ifDrawText = true;

    /**
     * Listener
     */
    private OnProgressBarListener _listener;

    public enum ProgressTextVisibility {
        Visible, Invisible
    }

    public RmbiNumberProgressBar(Context context) {
        this(context, null);
    }

    public RmbiNumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RmbiNumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        default_reached_bar_height = dp2px(1.5f);
        default_unreached_bar_height = dp2px(1.0f);
        default_text_size = sp2px(10);
        default_progress_text_offset = dp2px(3.0f);

        //load styled attributes.
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RmbiNumberProgressBar,
                defStyleAttr, 0);

        _reachedBarColor = attributes.getColor(R.styleable.RmbiNumberProgressBar_progress_reached_color, default_reached_color);
        _unreachedBarColor = attributes.getColor(R.styleable.RmbiNumberProgressBar_progress_unreached_color, default_unreached_color);
        _textColor = attributes.getColor(R.styleable.RmbiNumberProgressBar_progress_text_color, default_text_color);
        _textSize = attributes.getDimension(R.styleable.RmbiNumberProgressBar_progress_text_size, default_text_size);
        _textFormat = attributes.getInt(R.styleable.RmbiNumberProgressBar_textType,TEXT_FORMAT_PERCENT);
        _reachedBarHeight = attributes.getDimension(R.styleable.RmbiNumberProgressBar_progress_reached_bar_height, default_reached_bar_height);
        _unreachedBarHeight = attributes.getDimension(R.styleable.RmbiNumberProgressBar_progress_unreached_bar_height, default_unreached_bar_height);
        _offset = attributes.getDimension(R.styleable.RmbiNumberProgressBar_progress_text_offset, default_progress_text_offset);



        int textVisible = attributes.getInt(R.styleable.RmbiNumberProgressBar_progress_text_visibility, PROGRESS_TEXT_VISIBLE);
        if (textVisible != PROGRESS_TEXT_VISIBLE) {
            _ifDrawText = false;
        }

        setProgress(attributes.getInt(R.styleable.RmbiNumberProgressBar_progress_current, 0));
        setMax(attributes.getInt(R.styleable.RmbiNumberProgressBar_progress_max, 100));

        attributes.recycle();
        initializePainters();
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) _textSize;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return Math.max((int) _textSize, Math.max((int) _reachedBarHeight, (int) _unreachedBarHeight));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (_ifDrawText) {
            calculateDrawRectF();
        } else {
            calculateDrawRectFWithoutProgressText();
        }

        if (_drawReachedBar) {
            canvas.drawRect(_reachedRectF, _reachedBarPaint);
        }

        if (_drawUnreachedBar) {
            canvas.drawRect(_unreachedRectF, _unreachedBarPaint);
        }

        if (_ifDrawText){
            switch (_textFormat){
                case TEXT_FORMAT_NONE:
                    _currentDrawText = ""+getProgress();
                    break;
                case TEXT_FORMAT_SECOND:
                    _currentDrawText = Functions.getInstance().getSecondInString(getProgress());
                    break;
//            case TEXT_FORMAT_PERCENT:
//                mCurrentDrawText = String.format("%d", getProgress() * 100 / getMax());
//                mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
//                break ;
                default:
                    _currentDrawText = String.format("%d", getProgress() * 100 / getMax());
                    _currentDrawText = _prefix + _currentDrawText + _suffix;
            }
            canvas.drawText(_currentDrawText, _drawTextStart, _drawTextEnd, _textPaint);
        }

    }

    private void initializePainters() {
        _reachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _reachedBarPaint.setColor(_reachedBarColor);

        _unreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _unreachedBarPaint.setColor(_unreachedBarColor);

        _textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        _textPaint.setColor(_textColor);
        _textPaint.setTextSize(_textSize);
    }


    private void calculateDrawRectFWithoutProgressText() {
        _reachedRectF.left = getPaddingLeft();
        _reachedRectF.top = getHeight() / 2.0f - _reachedBarHeight / 2.0f;
        _reachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMax() * 1.0f) * getProgress() + getPaddingLeft();
        _reachedRectF.bottom = getHeight() / 2.0f + _reachedBarHeight / 2.0f;

        _unreachedRectF.left = _reachedRectF.right;
        _unreachedRectF.right = getWidth() - getPaddingRight();
        _unreachedRectF.top = getHeight() / 2.0f + -_unreachedBarHeight / 2.0f;
        _unreachedRectF.bottom = getHeight() / 2.0f + _unreachedBarHeight / 2.0f;
    }

    private void calculateDrawRectF() {

        switch (_textFormat){
            case TEXT_FORMAT_NONE:
                _currentDrawText = ""+getProgress();
                break;
            case TEXT_FORMAT_SECOND:
                _currentDrawText = Functions.getInstance().getSecondInString(getProgress());
                break;
//            case TEXT_FORMAT_PERCENT:
//                mCurrentDrawText = String.format("%d", getProgress() * 100 / getMax());
//                mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
//                break ;
            default:
                _currentDrawText = String.format("%d", getProgress() * 100 / getMax());
                _currentDrawText = _prefix + _currentDrawText + _suffix;
        }

        _currentDrawText = Functions.getInstance().getSecondInString(getProgress());


        _drawTextWidth = _textPaint.measureText(_currentDrawText);

        if (getProgress() == 0) {
            _drawReachedBar = false;
            _drawTextStart = getPaddingLeft();
        } else {
            _drawReachedBar = true;
            _reachedRectF.left = getPaddingLeft();
            _reachedRectF.top = getHeight() / 2.0f - _reachedBarHeight / 2.0f;
            _reachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMax() * 1.0f) * getProgress() - _offset + getPaddingLeft();
            _reachedRectF.bottom = getHeight() / 2.0f + _reachedBarHeight / 2.0f;
            _drawTextStart = (_reachedRectF.right + _offset);
        }

        _drawTextEnd = (int) ((getHeight() / 2.0f) - ((_textPaint.descent() + _textPaint.ascent()) / 2.0f));

        if ((_drawTextStart + _drawTextWidth) >= getWidth() - getPaddingRight()) {
            _drawTextStart = getWidth() - getPaddingRight() - _drawTextWidth;
            _reachedRectF.right = _drawTextStart - _offset;
        }

        float unreachedBarStart = _drawTextStart + _drawTextWidth + _offset;
        if (unreachedBarStart >= getWidth() - getPaddingRight()) {
            _drawUnreachedBar = false;
        } else {
            _drawUnreachedBar = true;
            _unreachedRectF.left = unreachedBarStart;
            _unreachedRectF.right = getWidth() - getPaddingRight();
            _unreachedRectF.top = getHeight() / 2.0f + -_unreachedBarHeight / 2.0f;
            _unreachedRectF.bottom = getHeight() / 2.0f + _unreachedBarHeight / 2.0f;
        }
    }

    /**
     * Get progress text color.
     *
     * @return progress text color.
     */
    public int getTextColor() {
        return _textColor;
    }

    /**
     * Get progress text size.
     *
     * @return progress text size.
     */
    public float getProgressTextSize() {
        return _textSize;
    }

    public int getUnreachedBarColor() {
        return _unreachedBarColor;
    }

    public int getReachedBarColor() {
        return _reachedBarColor;
    }

    public int getProgress() {
        return _currentProgress;
    }

    public int getMax() {
        return _maxProgress;
    }

    public float getReachedBarHeight() {
        return _reachedBarHeight;
    }

    public float getUnreachedBarHeight() {
        return _unreachedBarHeight;
    }

    public void setProgressTextSize(float textSize) {
        this._textSize = textSize;
        _textPaint.setTextSize(_textSize);
        invalidate();
    }

    public void setProgressTextColor(int textColor) {
        this._textColor = textColor;
        _textPaint.setColor(_textColor);
        invalidate();
    }

    public void setUnreachedBarColor(int barColor) {
        this._unreachedBarColor = barColor;
        _unreachedBarPaint.setColor(_unreachedBarColor);
        invalidate();
    }

    public void setReachedBarColor(int progressColor) {
        this._reachedBarColor = progressColor;
        _reachedBarPaint.setColor(_reachedBarColor);
        invalidate();
    }

    public void setReachedBarHeight(float height) {
        _reachedBarHeight = height;
    }

    public void setUnreachedBarHeight(float height) {
        _unreachedBarHeight = height;
    }

    public void setMax(int maxProgress) {
        if (maxProgress > 0) {
            this._maxProgress = maxProgress;
            invalidate();
        }
    }

    public void setSuffix(String suffix) {
        if (suffix == null) {
            _suffix = "";
        } else {
            _suffix = suffix;
        }
    }

    public String getSuffix() {
        return _suffix;
    }

    public void setPrefix(String prefix) {
        if (prefix == null)
            _prefix = "";
        else {
            _prefix = prefix;
        }
    }

    public String getPrefix() {
        return _prefix;
    }

    public void incrementProgressBy(int by) {
        if (by > 0) {
            setProgress(getProgress() + by);
        }

        if(_listener != null){
            _listener.onProgressChange(getProgress(), getMax());
        }
    }

    public void setProgress(int progress) {
        if (progress <= getMax() && progress >= 0) {
            this._currentProgress = progress;
            invalidate();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getProgressTextSize());
        bundle.putFloat(INSTANCE_REACHED_BAR_HEIGHT, getReachedBarHeight());
        bundle.putFloat(INSTANCE_UNREACHED_BAR_HEIGHT, getUnreachedBarHeight());
        bundle.putInt(INSTANCE_REACHED_BAR_COLOR, getReachedBarColor());
        bundle.putInt(INSTANCE_UNREACHED_BAR_COLOR, getUnreachedBarColor());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putString(INSTANCE_SUFFIX, getSuffix());
        bundle.putString(INSTANCE_PREFIX, getPrefix());
        bundle.putBoolean(INSTANCE_TEXT_VISIBILITY, getProgressTextVisibility());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            _textColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            _textSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            _reachedBarHeight = bundle.getFloat(INSTANCE_REACHED_BAR_HEIGHT);
            _unreachedBarHeight = bundle.getFloat(INSTANCE_UNREACHED_BAR_HEIGHT);
            _reachedBarColor = bundle.getInt(INSTANCE_REACHED_BAR_COLOR);
            _unreachedBarColor = bundle.getInt(INSTANCE_UNREACHED_BAR_COLOR);
            initializePainters();
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            setPrefix(bundle.getString(INSTANCE_PREFIX));
            setSuffix(bundle.getString(INSTANCE_SUFFIX));
            setProgressTextVisibility(bundle.getBoolean(INSTANCE_TEXT_VISIBILITY) ? ProgressTextVisibility.Visible : ProgressTextVisibility.Invisible);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public void setProgressTextVisibility(ProgressTextVisibility visibility) {
        _ifDrawText = visibility == ProgressTextVisibility.Visible;
        invalidate();
    }

    public boolean getProgressTextVisibility() {
        return _ifDrawText;
    }

    public void setOnProgressBarListener(OnProgressBarListener listener){
        _listener = listener;
    }

    public interface OnProgressBarListener {

        void onProgressChange(int current, int max);
    }
}
