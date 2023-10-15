import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString(callSuper = true)
public class LeafyTree extends Tree {
    public void looseLeaves() {
        leavesCount = 0;
    }
}
