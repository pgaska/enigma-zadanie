import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public abstract class Tree {
    private static final float INITIAL_HEIGHT = 2.0f;
    private static final float INITIAL_DIAMETER = 0.3f;
    protected Trunk trunk;
    protected List<Branch> branches = new ArrayList<>();
    protected int leavesCount;

    public Tree() {
        this.trunk = new Trunk(INITIAL_HEIGHT, INITIAL_DIAMETER);
    }

    public void grow(int steps) {
        trunk.grow(steps);
        branches.forEach(branch -> branch.grow(steps));
    }

    public void growBranch(float initialLength) {
        branches.add(new Branch(initialLength));
    }

    public void growBranch() {
        branches.add(new Branch());
    }

    public void growLeaves(int count) {
        leavesCount += count;
    }
}
