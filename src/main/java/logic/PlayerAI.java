package logic;

import containers.Vertex;
import java.util.List;

public interface PlayerAI extends Player {
    void calibrate(List<Vertex> Goals, List<Vertex> ownGoals);
}
