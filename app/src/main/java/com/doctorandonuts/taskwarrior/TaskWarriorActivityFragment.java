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

import org.json.JSONObject;

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

    private class updateTaskWarriorData extends AsyncTask<Void, Void, JSONObject[]> {
        protected JSONObject[] doInBackground(Void... params) {
            TaskWarriorSync taskWarriorSync = new TaskWarriorSync();
            String data = taskWarriorSync.getTaskWarriorData();
            try {
                Log.d("TaskWarrior", data);
                String[] split = data.split("\n");
                JSONObject[] jsonObject = new JSONObject[split.length];
                //Log.d("JSON", "Split Length" + split.length);
                Log.d("JSON", "Split: " + split);
                Log.d("JSON", "Split118: " + split[118]);
                Log.d("JSON", "Split119: " + split[119]);
                Log.d("JSON", "Split120: " + split[120]);
                Log.d("JSON", "Split121: " + split[121]);
                Log.d("JSON", "Split122: " + split[122]);
                Log.d("JSON", "Split123: " + split[123]);
                Log.d("JSON", "Split124: " + split[124]);
                Log.d("JSON", "Split125: " + split[125]);
                Log.d("JSON", "Split126: " + split[126]);
                for(Integer i=0; i<split.length; i++) {
                    //Log.d("JSON", "Loop Number " + i);
                    //Log.d("JSON", "Split " + split[i]);
                    jsonObject[i] = new JSONObject(split[i]);
                }
                Log.d("JSON", jsonObject[0].toString());
                Log.d("JSON", jsonObject[1].toString());
                Log.d("JSON", jsonObject[2].toString());
                return jsonObject;
            } catch (Exception e) {
                Log.e("TaskWarrior", "Error parsing JSON data");
            }
            return null;
        }
        protected void onPostExecute(JSONObject[] taskData) {
            proccessTaskWarriorData(taskData);
        }
    }

    private void proccessTaskWarriorData(JSONObject[] taskData) {
        try {
            textOutput.setText(taskData[3].getString("description"));
//            Task[] task = new Task[4];
//            for(Integer i=0; i<4; i++) {
//                task[i] = new Task();
//                task[i].addText("Task: " + i.toString());
//            }

//            final ArrayList<String> list = new ArrayList<>();
//            for (Integer i = 0; i < 4; i++) {
//                list.add(i.toString());
//            }
//            final ArrayAdapter adapter = new ArrayAdapter(getActivity(),
//                    android.R.layout.simple_list_item_1, list);
//            listView.setAdapter(adapter);
        } catch(Exception e) {
            Log.e("TaskWarrior", "PROBLEM:" + e.toString());
        }
    }
}

