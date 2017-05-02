package ru.atom.network;

import ru.atom.gameinterfaces.Positionable;
import ru.atom.util.JsonGameObject;
import ru.atom.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kinetik on 02.05.17.
 */
public class Replika {
    private final Topic topic;
    private ArrayList<String> dataObjects = new ArrayList<>();

    public Replika(List<Positionable> objects) {
        this.topic = Topic.REPLICA;
        for (Positionable object: objects) {
            JsonGameObject jsonGameObject = new JsonGameObject(object);
            this.dataObjects.add(jsonGameObject.toJsonPosition());
        }
    }

    public String getJson() {
        return JsonHelper.toJson(this);
    }
}
