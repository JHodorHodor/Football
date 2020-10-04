package logic;

public interface Observable {
    void attach(Observer observer);
    void detach(Observer observer);
}
