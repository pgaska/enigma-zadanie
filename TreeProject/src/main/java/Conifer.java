import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class Conifer extends Tree {
    private int coneCount;

    public void growCones(int count) {
        coneCount += count;
    }

    public void looseCones(int count) {
        if (count > coneCount) {
            coneCount = 0;
        } else {
            coneCount -= count;
        }
    }
}
