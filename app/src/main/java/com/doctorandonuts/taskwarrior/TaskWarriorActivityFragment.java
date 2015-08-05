package com.doctorandonuts.taskwarrior;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.doctorandonuts.taskwarrior.sync.TaskWarriorSync;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskWarriorActivityFragment extends Fragment {
    TextView textOutput;
    ListView listView;

    public TaskWarriorActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_warrior, container, false);

        textOutput = (TextView)rootView.findViewById(R.id.textOutput);
        listView = (ListView)rootView.findViewById(R.id.listView);
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
            //String data = taskWarriorSync.getTaskWarriorData();
            //return data;
            return "DATA";
        }
        protected void onPostExecute(String data) {
            renderTaskWarrior(data);
        }
    }

    private void renderTaskWarrior(String data) {
        try {
            textOutput.setText("START");

//            Task[] task = new Task[4];
//            for(Integer i=0; i<4; i++) {
//                task[i] = new Task();
//                task[i].addText("Task: " + i.toString());
//            }

            final ArrayList<String> list = new ArrayList<>();
            for (Integer i = 0; i < 4; i++) {
                list.add(i.toString());
            }
            final ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);

            textOutput.setText("END");

        }catch(Exception e){
            Log.e("TaskWarrior", "PROBLEM:" + e.toString());
        }
    }
}

