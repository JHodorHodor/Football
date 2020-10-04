package containers;

import logic.*;

public enum PlayerType {
    Human {
        @Override
        public Player createInstance() {
            return new PlayerHuman();
        }
    },
    MediumAI {
        @Override
        public Player createInstance() {
            return new PlayerSimpleAIMedium();
        }
    },
    HardAI {
        @Override
        public Player createInstance() {
            return new PlayerSimpleAIHard();
        }
    },
    RandomAI {
        @Override
        public Player createInstance() {
            return new PlayerRandomAI();
        }
    };
    public abstract Player createInstance();
}
