package ru.atom.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import ru.atom.util.JsonHelper;

/**
 * Created by ilysko on 02.05.17.
 */
public class Possess {
    private final Topic topic;
    private final int data;

    public Possess(Topic topic, int data) {
        this.topic = topic;
        this.data = data;
    }

    @JsonCreator
    public Possess(@JsonProperty("topic") Topic topic, @JsonProperty("data") JsonNode data) {
        this.topic = topic;
        this.data = (int) Integer.parseInt(data.toString());
    }

    public Topic getTopic() {
        return topic;
    }

    public int getData() {
        return data;
    }

    public String getJson() {
        return JsonHelper.toJson(this);
    }
}
