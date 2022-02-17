package com.slyworks.rxjava_book;

import android.media.audiofx.NoiseSuppressor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joshua Sylvanus, 9:24 AM, 27-Dec-21.
 */
enum NotifyMethod{ PUSH_IMMEDIATELY, WAIT_FOR_PULL }
interface Observer{
    <T> void notify(String event,T data);
}

public class AppController {
    //region Vars
    private static AppController INSTANCE = null;

    private List<String> mEvents = new ArrayList<>();
    private Map<String, List<Subscription>> mObservers = new HashMap<String, List<Subscription>>();
    private Map<String, Object> mEventMap = new HashMap<>();
    //endregion

    public static AppController getInstance(){
        if(INSTANCE == null){
            INSTANCE = new AppController();
        }

        return INSTANCE;
    }

    private AppController(){}

    public void addEvent(String event){
        if(mEvents.contains(event)) return;

        mEvents.add(event);
        mObservers.put(event, new ArrayList<>());
    }

    public void removeEvent(String event){
        mEvents.remove(event);
        mObservers.remove(event);
        mEventMap.remove(event);
    }

    public Subscription subscribeTo(String event, Observer observer){
        NotifyMethod notifyMethod = NotifyMethod.PUSH_IMMEDIATELY;

        Subscription s = new Subscription(event, observer, notifyMethod);

        mObservers.get(event).add(s);

        return s;
    }
    public Subscription subscribeTo(String event, Observer observer, NotifyMethod notifyMethod){
        Subscription s = new Subscription(event, observer, notifyMethod);

        mObservers.get(event).add(s);

        return s;
    }

    public <T> void notifyObservers(String event, T data){
        List<Subscription> observers = mObservers.get(event);

        if(observers == null) return;;
        if(observers.isEmpty()) return;

        for(Subscription subscriber : observers){
            if(subscriber.getNotifyMethod() == NotifyMethod.PUSH_IMMEDIATELY)
                subscriber.getObserver().notify(event, data);

            /*to avoid caching unnecessarily, only cache if there is an "cold" Observer*/
            if(subscriber.getNotifyMethod() == NotifyMethod.WAIT_FOR_PULL)
                cacheData(event, data);
        }
    }

    private <T> void cacheData(String event, T data){
        mEventMap.put(event, data);
    }

    public <T> T pullData(String event, Observer observer){
        return (T) mEventMap.get(event);
    }

    class Subscription{
        //region Vars
        private String mEvent;
        private Observer mObserver;
        private NotifyMethod mNotifyMethod;
        //endregion


        public Subscription(String event, Observer observer, NotifyMethod notifyMethod) {
            mEvent = event;
            mObserver = observer;
            mNotifyMethod = notifyMethod;
        }

        public String getEvent() { return mEvent; }
        public void setEvent(String event) { mEvent = event; }
        public Observer getObserver() { return mObserver; }
        public void setObserver(Observer observer) { mObserver = observer; }
        public NotifyMethod getNotifyMethod() { return mNotifyMethod; }
        public void setNotifyMethod(NotifyMethod notifyMethod) { mNotifyMethod = notifyMethod; }

        public void clear(){
            mObservers.get(this.mEvent).remove(this);
        }

        public void clearAndRemove(){
            mObservers.get(this.mEvent).remove(this);
            removeEvent(this.mEvent);
        }
    }
}
