package com.dreamer.weixin.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
    private EventType type;
    private String actorCard;
    private String OwnerCard;
    private Map<String, Object> exts = new HashMap<String, Object>();

    public EventModel() {

    }

    public EventModel setExt(String key, Object value) {
        exts.put(key, value);
        return this;
    }

    public EventModel(EventType type) {
        this.type = type;
    }

    public Object getExt(String key) {
        return exts.get(key);
    }


    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }


    public String getActorCard() {
        return actorCard;
    }

    public EventModel setActorCard(String actorCard) {
        this.actorCard = actorCard;
        return this;
    }

    public String getOwnerCard() {
        return OwnerCard;
    }

    public EventModel setOwnerCard(String ownerCard) {
        this.OwnerCard = ownerCard;
        return this;
    }

    public Map<String, Object> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, Object> exts) {
        this.exts = exts;
        return this;
    }
}
