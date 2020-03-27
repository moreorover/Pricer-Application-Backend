package martin.dev.pricer.obs;

public abstract class ParserObserver {
    protected String NAME;
    protected Subject subject;
    public abstract void update();
}
