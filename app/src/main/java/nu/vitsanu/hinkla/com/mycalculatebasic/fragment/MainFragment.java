package nu.vitsanu.hinkla.com.mycalculatebasic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nu.vitsanu.hinkla.com.mycalculatebasic.GetCurrentMoneyRate;
import nu.vitsanu.hinkla.com.mycalculatebasic.R;

public class MainFragment extends Fragment{

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Show Rate

        TextView textView = getView().findViewById(R.id.txtShowRate);

        String urlAPI = "https://api.fixer.io/latest?symbols=THB&base=USD";
        try {

            GetCurrentMoneyRate getCurrentMoneyRate = new GetCurrentMoneyRate(getActivity());
            getCurrentMoneyRate.execute(urlAPI);
            String jsonString = getCurrentMoneyRate.get();
            Log.d("31MarchV1", "json0 ==>" + jsonString);

            jsonString = jsonString.substring(0, jsonString.length() - 2);
            Log.d("31MarchV1", "json1 ==>" + jsonString);

            jsonString = jsonString.substring(jsonString.length() - 5, jsonString.length());
            Log.d("31MarchV1", "json2 ==>" + jsonString);

            textView.setText("1--> "+ jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }   //Main method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        return view;
    }
}
