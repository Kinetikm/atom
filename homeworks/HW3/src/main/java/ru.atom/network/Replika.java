package ru.atom.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import ru.atom.gameinterfaces.GameObject;

import java.util.ArrayList;

/**
 * Created by kinetik on 02.05.17.
 */
public class Replika {
    private final Topic topic;
    private final ArrayList<GameObject> objects;
    private ArrayList<String> dataObjects;

    public Replika(Topic topic, ArrayList<GameObject> objects) {
        this.topic = topic;
        this.objects = objects;
    }

    @JsonCreator
    public Message(@JsonProperty("topic") Topic topic, @JsonProperty("data") JsonNode data) {
        this.topic = topic;
        this.data = data.toString();
    }

    public Topic getTopic() {
        return topic;
    }

    public String getData() {
        return data;
    }
}
