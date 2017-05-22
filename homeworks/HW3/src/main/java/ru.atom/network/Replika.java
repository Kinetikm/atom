package ru.atom.network;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.atom.gameinterfaces.Positionable;
import ru.atom.util.JsonHelper;


/**
 * Created by kinetik on 02.05.17.
 */
public class Replika {
    private String type;
    private int id;
    private String position;

    public Replika(Positionable obj) {
        this.type = obj.getClass().getSimpleName();
        this.id = obj.getId();
        this.position = JsonHelper.toJson(new Position(obj.getPosition().getX(), obj.getPosition().getY()));
    }
    
    public String getJson() {
        return JsonHelper.toJson(this);
    }   
    
    private class Position {
        private long x;
        private long y;

        @JsonCreator
        public Position(@JsonProperty("x") long x,@JsonProperty("y") long y) {
            this.x = x;
            this.y = y;
        }
    }

}
