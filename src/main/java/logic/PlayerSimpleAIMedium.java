package logic;

public class PlayerSimpleAIMedium extends PlayerSimpleAI {

    public PlayerSimpleAIMedium(){
        super();
        this.setDifficulty(2);
    }

    @Override
    public String getName(){
        return "MediumAI";
    }
}
