package ch.rmbi.mels_volley.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import ch.rmbi.mels_volley.R;


public class RmbiTextView  extends AppCompatTextView {


    public RmbiTextView(@NonNull Context context) {
        super(context);
    }

    public RmbiTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RmbiTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void applyStyle(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RmbiTextView);
        int cf = a.getInteger(R.styleable.RmbiTextView_fontName, 0);
        int fontName;
        switch (cf) {
            case 1:
                fontName = R.string.Roboto_Black;
                break;
            case 2:
                fontName = R.string.Roboto_BlackItalic;
                break;
            case 3:
                fontName = R.string.Roboto_Bold;
                break;
            case 4:
                fontName = R.string.Roboto_BoldItalic;
                break;
            case 5:
                fontName = R.string.Roboto_Italic;
                break;
            case 6:
                fontName = R.string.Roboto_Light;
                break;
            case 7:
                fontName = R.string.Roboto_LightItalic;
                break;
            case 8:
                fontName = R.string.Roboto_Medium;
                break;
            case 9:
                fontName = R.string.Roboto_MediumItalic;
                break;
            case 10:
                fontName = R.string.Roboto_Regular;
                break;
            case 11:
                fontName = R.string.Roboto_Thin;
                break;
            case 12:
                fontName = R.string.Roboto_ThinItalic;
                break;
            default:
                fontName = R.string.Roboto_Medium;
                break;
        }

        String customFont = getResources().getString(fontName);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + customFont + ".ttf");
        setTypeface(tf);
        a.recycle();
    }
}
