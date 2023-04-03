import java.util.ArrayList;
public class Storage {

    private ArrayList<Phd>  Phds;
    private ArrayList<Pv> Pvs;
    private ArrayList<Vg> Vgs;
    private ArrayList<Lv> Lvs;


    public Storage(){
        this.Phds = new ArrayList<Phd>();
        this.Pvs = new ArrayList<Pv>();
        this.Vgs = new ArrayList<Vg>();
        this.Lvs = new ArrayList<Lv>();
    }


    public boolean addPhd(Phd a){
        if (Phds.add(a)){return true;}
        return false;
    }
    public boolean phdNameTaken(String a){
        for (Phd b:getPhds()) {
            if (a.equals(b.getName())){return true;}
        }
        return false;
    }
    public boolean phdTaken(Phd a){

        for (Pv b:this.getPvs()) {
            if(a.getUuid().equals(b.getPhdStored().getUuid())){return true;}
        }
        return false;
    }
    public boolean addPv(Pv a){
        if (Pvs.add(a)){return true;}
        return false;
    }

    public boolean pvNameTaken(String a){
        for (Pv b:getPvs()) {
            if (a.equals(b.getName())){return true;}
        }
        return false;
    }
    //WORK
    public boolean pvTaken(Pv a){
        for (Vg b:getVgs()) {
            for (Pv c:b.getPvs()) {
                if(a.getUuid().equals(c.getUuid())){return true;}
            }
        }
        return false;
    }

    public boolean addVg(Vg a){

        if (Vgs.add(a)){return true;}
        return false;
    }
    public boolean vgNameTaken(String a){
        for (Vg b:getVgs()) {
            if (a.equals(b.getName())){return true;}
        }
        return false;
    }

    public boolean addLv(Lv a){
        if (Lvs.add(a)){return true;}
        return false;
    }
    public boolean lvNameTaken(String a ){
        for (Lv b:getLvs()) {
            if (a.equals(b.getName())){return true;}
        }
        return false;
    }

    public ArrayList<Phd> getPhds() {
        return Phds;
    }

    public ArrayList<Pv> getPvs() {
        return Pvs;
    }

    public ArrayList<Vg> getVgs() {
        return Vgs;
    }

    public ArrayList<Lv> getLvs() {
        return Lvs;
    }
}
//a