public class Pv extends LVM{

    private Phd PhdStored;

    public Pv (String name, Phd a){
        super(name);
        PhdStored = a;

    }

    public Phd getPhdStored() {
        return PhdStored;
    }
}
//a