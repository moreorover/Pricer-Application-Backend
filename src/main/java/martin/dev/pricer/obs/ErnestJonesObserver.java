package martin.dev.pricer.obs;

public class ErnestJonesObserver extends ParserObserver {

    public ErnestJonesObserver(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public String getNAME() {
        return "Ernest Jones";
    }

    @Override
    public void update() {
        System.out.println("Observer Ernest Jones notified!");
        System.out.println(this.subject.getUrl().getUrl());
    }
}
