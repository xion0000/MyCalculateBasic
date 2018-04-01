package nu.vitsanu.hinkla.com.mycalculatebasic;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CalculateFragment extends Fragment{

    //    Explicit
    private String factorString;
    private ArrayList<String> stringArrayList;

    public static CalculateFragment calculateInstance(String factorString) { //space for send value to new fragment
        CalculateFragment calculateFragment = new CalculateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Factor", factorString);
        calculateFragment.setArguments(bundle);
        return calculateFragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Setup arrayList
        stringArrayList = new ArrayList<>();


//        Get value from argument
        factorString = getArguments().getString("Factor");
        Log.d("1AprilV1", "Factor ==>" + factorString);


//        Create Toolbar
        createToolbar();

//Exchange controller
        exchangeController();


    }   // Main Method

    private void myAlertdialog(String titleString, String messageString) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_alert);
        builder.setTitle(titleString);
        builder.setMessage(messageString);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        builder.show();

    }

    private void exchangeController() {
        Button button = getView().findViewById(R.id.btnExchange);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Get Value From input box
                EditText editText = getView().findViewById(R.id.edtMoney);
                String usdString = editText.getText().toString().trim();

                // to check input box not empty
                if (usdString.isEmpty()) {
                    //Have space
                    myAlertdialog("Have space","please fill your money on the blank");
                } else {
                 // No space
                    double usdAdouble = Double.parseDouble(usdString); // change text to double type
                    double factorAdouble = Double.parseDouble(factorString);
                    double thbAdouble = usdAdouble * factorAdouble;
                    String thbString = Double.toString(thbAdouble);
                    myAlertdialog("your " + usdString + " usd", thbString + " THB.");

//                    Create list view
                    Calendar calendar = Calendar.getInstance(); //use calendar of JAVA
                    DateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss");
                    String dateString = dateFormat.format(calendar.getTime());
                    Log.d("1APrilV2", "Date ==>" + dateString);

                    String listString = dateString +
                            "\n" +  // \n is enter to new line
                            usdString +
                            " X " +
                            factorString +
                            " = " +
                            thbString;

                    stringArrayList.add(listString);
                    Log.d("1APrilV2", "Current ArrayList ==>" + stringArrayList.toString());

                    createListView();

                }


            }
        });
    }

    private void createListView() {

        ListView listView = getView().findViewById(R.id.listviewExchange);
        String resultString = stringArrayList.toString();
        resultString = resultString.substring(1, resultString.length() - 1);
        String[] strings = resultString.split(",");

        for (int  i=0; i<strings.length; i+=1) {
            Log.d("1APrilV2", "strings[" + i + "] ==> " + strings[i]);
        }

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strings);
        listView.setAdapter(stringArrayAdapter);


    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarCalculate);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Exchange Money");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Please fill money in USD");

//        Setup Navigator (Back on menu)
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack(); //popBackStack = return where you from
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculate, container, false);
        return view;
    }
}   // Main class
