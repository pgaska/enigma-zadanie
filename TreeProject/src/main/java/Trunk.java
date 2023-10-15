import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Trunk {
    private static final float HEIGHT_GROWTH_PER_STEP = 1.0f;
    private static final float DIAMETER_GROWTH_PER_STEP = 0.4f;
    private float height;
    private float diameter;

    public Trunk(float height, float diameter) {
        this.height = height;
        this.diameter = diameter;
    }

    public void grow(int steps) {
        height += steps * HEIGHT_GROWTH_PER_STEP;
        diameter += steps * DIAMETER_GROWTH_PER_STEP;
    }
}
