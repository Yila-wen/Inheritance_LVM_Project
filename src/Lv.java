public class Lv extends LVM{

    private int size;
    private Vg host;

    public Lv (String name, int size, Vg host){
        super(name);
        this.size = size;
        this.host = host;
        host.addLV(this);

    }

    public int getSize() {
        return size;
    }

    public Vg getHost() {
        return host;
    }
}
