import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class Branch {
    private static final float GROWTH_PER_STEP = 0.5f;
    private float length = 1.0f;

    public Branch() {

    }

    public Branch(float length) {
        this.length = length;
    }

    public void grow(int steps) {
        length += steps * GROWTH_PER_STEP;
    }
}
