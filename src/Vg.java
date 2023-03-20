import java.util.ArrayList;
public class Vg extends LVM{

    private ArrayList<Pv> Pvs;
    private ArrayList<Lv> Lvs;

    public Vg (String name, Pv a){
        super(name);
        Pvs.add(a);
    }

    public void addPV(Pv a){
        Pvs.add(a);
    }

    public void addLV(Lv a){
        if (a.getHost().getUuid() == this.getUuid()){Lvs.add(a);}
    }

    public int getSize(){
        int sum = 0;

        for (int i =0;i<Pvs.size();i++){
            sum+= Pvs.get(i).getPhdStored().getSize();
        }

        return sum;
    }

    public int getFreeSpace(){
        int sum = getSize();

        for (int i=0;i<Lvs.size();i++){
            sum-=Lvs.get(i).getSize();
        }

        return sum;
    }
}
