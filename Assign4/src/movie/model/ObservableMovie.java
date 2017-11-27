package movie.model;

import java.util.Observable;

public class ObservableMovie extends Observable{

    public void notifyObservers(Object arg){
        super.setChanged();
        super.notifyObservers(arg);
    }

    public void notifyObservers(){
        super.setChanged();
        super.notifyObservers();
    }

}
