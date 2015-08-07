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
    ListView listView;

    public TaskWarriorActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_task_warrior, container, false);

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
            //Log.d("TaskWarrior", data);
            try {
                String[] split = data.split("\n");
                //Log.d("JSON", "Split Length" + split.length);

                JSONObject[] jsonObject = new JSONObject[split.length];
                for(Integer i=0; i<split.length; i++) {
                    if(split[i].substring(0,1).equals("{")) {
                        //Log.d("JSON", "SUBSTR(0,1) #" + i + ": " + split[i].substring(0,1));
                        //Log.d("JSON", "Split #" + i + ": " + split[i]);
                        jsonObject[i] = new JSONObject(split[i]);
                    }
                }
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
            Task[] task = new Task[taskData.length];
            for(Integer i=0; i<taskData.length; i++) {
                task[i] = new Task();
                task[i].importTask(taskData[i]);
            }

            final ArrayList<String> list = new ArrayList<>();
            list.clear();
            for (Integer i = 0; i < task.length; i++) {
                //Log.d("ARG", task[i].getDescription());
                //Log.d("ARG", task[i].getRaw());
                list.add(task[i].getDescription());
            }
            final ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                    android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
        } catch(Exception e) {
            Log.e("TaskWarrior", "PROBLEM:" + e.toString());
        }
    }
}

