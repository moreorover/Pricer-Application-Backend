package martin.dev.pricer.obs;

public abstract class ParserObserver {
    protected Subject subject;
    public abstract void update();
    public abstract String getNAME();
}
