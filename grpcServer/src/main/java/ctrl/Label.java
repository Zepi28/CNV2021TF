package ctrl;

public class Label {
    private final String label;
    private final double score;

    public Label(String label, double score) {
        this.label = label;
        this.score = score;
    }

    public Label(String label) {
        this.label = label;
        score = -1;
    }

    @Override
    public String toString() {
        if (score == -1) {
            return label;
        } else {
            return label + " (" + score + ")";
        }
    }
    //TODO : tentar fazer isto melhor
    public boolean isLabel(String label) {
        return this.label.equalsIgnoreCase(label);
    }
}
