package nu.vitsanu.hinkla.com.mycalculatebasic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import nu.vitsanu.hinkla.com.mycalculatebasic.CalculateFragment;
import nu.vitsanu.hinkla.com.mycalculatebasic.GetCurrentMoneyRate;
import nu.vitsanu.hinkla.com.mycalculatebasic.MainActivity;
import nu.vitsanu.hinkla.com.mycalculatebasic.R;

public class MainFragment extends Fragment{

    private String jsonString;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();


//        Show Rate
        showRate();

    }


    //Main method


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_calculate) {
//            Replace Fragment
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentMainFragment, CalculateFragment.calculateInstance(jsonString))
                    .addToBackStack(null)
                    .commit();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main,menu);
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarMain);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

//        Setup Title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_th));
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("USD ==> THB");

        setHasOptionsMenu(true);
    }


    private void showRate() {
        TextView textView = getView().findViewById(R.id.txtShowRate);

        String urlAPI = "https://api.fixer.io/latest?symbols=THB&base=USD";
        try {

            GetCurrentMoneyRate getCurrentMoneyRate = new GetCurrentMoneyRate(getActivity());
            getCurrentMoneyRate.execute(urlAPI);
            jsonString = getCurrentMoneyRate.get();
            String forDateString = jsonString;

            Log.d("31MarchV1", "json0 ==>" + jsonString);

            jsonString = jsonString.substring(0, jsonString.length() - 2);
            Log.d("31MarchV1", "json1 ==>" + jsonString);

            jsonString = jsonString.substring(jsonString.length() - 5, jsonString.length());
            Log.d("31MarchV1", "json2 ==>" + jsonString);

            textView.setText("1--> "+ jsonString);

//            Fordate
            forDateString = forDateString.substring(1, forDateString.length() - 1);
            String[] strings = forDateString.split(",");
            Log.d("1AprilV1", "string[1]==>" + strings[1]);


            String result = "[{" + strings[1] + "}]";
            Log.d("1AprilV1", "result ==>" + result);

            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String dateString = jsonObject.getString("date");
            TextView textView1 = getView().findViewById(R.id.txtShowDate);
            textView1.setText(dateString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        return view;
    }
}
