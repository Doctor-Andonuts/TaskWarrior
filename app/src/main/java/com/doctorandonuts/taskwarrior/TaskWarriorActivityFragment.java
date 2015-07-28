package com.doctorandonuts.taskwarrior;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doctorandonuts.taskwarrior.sync.Msg;
import com.doctorandonuts.taskwarrior.sync.TLSClient;
import com.doctorandonuts.taskwarrior.sync.TaskWarriorSync;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.security.cert.CertificateException;
import java.text.ParseException;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskWarriorActivityFragment extends Fragment {
    TextView textOutput;



    public TaskWarriorActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_warrior, container, false);

        textOutput = (TextView)rootView.findViewById(R.id.textOutput);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new updateTaskWarriorData().execute();
    }


    private class updateTaskWarriorData extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... params) {
            TaskWarriorSync taskWarriorSync = new TaskWarriorSync();
            String data = taskWarriorSync.getTaskWarriorData();
            return data;
        }
        protected void onPostExecute(String data) {
            renderTaskWarrior(data);
        }
    }



    private void renderTaskWarrior(String data) {
        try {
            textOutput.setText(data);
        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }
}
