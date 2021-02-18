package gurankio.menu.io.util;

public enum CharPacks {

    SIMPLE('\'', '\\', '\'', '-', '>', '<', ' '),
    ADVANCED('│', '├', '└', '─', '>', '<', ' ');
	
    // TODO: should be changeable, waiting for files...
    public static final CharPacks selected = SIMPLE;
    public static final Integer WIDTH = 4;

    private final Character lineVertical;
    private final Character lineT;
    private final Character lineCorner;
    private final Character arrowBody;
    private final Character arrowHead;
    private final Character arrowReverse;
    private final Character spacer;

    CharPacks(Character lineVertical, Character lineT, Character lineCorner, Character arrowBody, Character arrowHead, Character arrowReverse, Character spacer) {
        this.lineVertical = lineVertical;
        this.lineT = lineT;
        this.lineCorner = lineCorner;
        this.arrowBody = arrowBody;
        this.arrowHead = arrowHead;
        this.arrowReverse = arrowReverse;
        this.spacer = spacer;
    }

    public Character getLineVertical() {
        return lineVertical;
    }

    public Character getLineT() {
        return lineT;
    }

    public Character getLineCorner() {
        return lineCorner;
    }

    public Character getArrowBody() {
        return arrowBody;
    }

    public Character getArrowHead() {
        return arrowHead;
    }

    public Character getArrowReverse() {
        return arrowReverse;
    }

    public Character getSpacer() {
        return spacer;
    }

    public String getSpacer(int count) {
        return getSpacer().toString().repeat(Math.max(0, count));
    }

    public String buildArrow(Character c) {
        return c.toString() + getArrowBody() + getArrowHead() + getSpacer();
    }

    public String buildArrowReverse(Character c) {
        return c.toString() + getArrowReverse() + getArrowBody() + getSpacer();
    }

}
