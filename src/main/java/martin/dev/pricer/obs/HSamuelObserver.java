package martin.dev.pricer.obs;

public class HSamuelObserver extends ParserObserver {

    private final String NAME = "H. Samuel";

    public HSamuelObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public String getNAME() {
        return NAME;
    }

    @Override
    public void update() {
        System.out.println("Observer HSamuel notified!");
        System.out.println(this.subject.getUrl().getUrl());
    }
}
