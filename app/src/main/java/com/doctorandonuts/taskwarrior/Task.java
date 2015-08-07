package com.doctorandonuts.taskwarrior;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Mr Saturn on 8/4/2015.
 */
public class Task {

    private String description;
    private String raw;

    public void importTask(JSONObject jsonTask) {
        try {
            description = jsonTask.getString("description");
            raw = jsonTask.toString();
        }
        catch(Exception e) {
            return;
        }
    }

    public String getDescription() {
        return description;
    }

    public String getRaw() {
        return raw;
    }

    //    "id":19,
//    "brainpower":"M",
//    "description":"Pay Medical bill",
//    "entry":"20150529T202744Z",
//    "estimate":"15min",
//    "modified":"20150803T230955Z",
//    "priority":"H",
//    "status":"pending",
//    "tags":["home","computer"],
//    "uuid":"b0c9e6e1-4345-4203-91f5-e87b686218c2",
//    "urgency":7.26712

}
