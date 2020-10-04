package logic;

public class PlayerSimpleAIHard extends PlayerSimpleAI {

    public PlayerSimpleAIHard(){
        super();
        this.setDifficulty(30);
    }

    @Override
    public String getName(){
        return "HardAI";
    }
}
