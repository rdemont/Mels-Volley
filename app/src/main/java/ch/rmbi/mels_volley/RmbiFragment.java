package ch.rmbi.mels_volley;

import android.view.View;

import androidx.fragment.app.Fragment;

public abstract class RmbiFragment extends Fragment {

    public abstract void onClick(View view) ;
    public abstract void onExitFragment() ;
    //public abstract void onDoubleClick(/*View view*/);
}
