package ru.atom.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import ru.atom.util.JsonHelper;

/**
 * Created by ilysko on 02.05.17.
 */
public class Possess {
    private final int data;

    public Possess(int data) {
        this.data = data;
    }

    @JsonCreator
    public Possess(@JsonProperty("data") JsonNode data) {
        this.data = (int) Integer.parseInt(data.toString());
    }
    
    public int getData() {
        return data;
    }

    public String getJson() {
        return JsonHelper.toJson(this);
    }
}
