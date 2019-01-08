package board;

public enum RockKind {
    BLACK,
    NONE,
    WHITE;

    @Override
    public String toString() {
        switch (this) {
            case BLACK:
                return "黒";
            case WHITE:
                return "白";
            case NONE:
                return "無";
        }
        return "わかんない";
    }

    static public RockKind reverse(RockKind now) {
        switch (now) {
            case BLACK:
                return WHITE;
            case WHITE:
                return BLACK;
            case NONE:
                return NONE;
        }
        return null;
    }

    public RockKind reverse() {
        return reverse(this);
    }
}
