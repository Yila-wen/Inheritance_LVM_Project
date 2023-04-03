import java.util.Scanner;
public class runner {
    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        String cmd ="";
        Storage lvmStorage = new Storage();

        System.out.println("Welcome to the LVM system.");
        while (!cmd.equals("exit")){
            System.out.print("cmd#: ");
            cmd =s.nextLine();

            if (cmd.contains("install drive ")){
                cmd = cmd.substring(14);
                int index = cmd.indexOf(" ");
                if (!lvmStorage.phdNameTaken(cmd.substring(0,index))){
                    if (lvmStorage.addPhd(new Phd(cmd.substring(0,index),Integer.parseInt(cmd.substring(index+1,cmd.length()-1))))){
                    System.out.println("Drive "+cmd.substring(0,index)+" installed");
                    }
                }
                else System.out.println("Drive was not created");
            }
            else if (cmd.contains("list drives")){
                for (int i =0;i<lvmStorage.getPhds().size();i++){
                    System.out.println(lvmStorage.getPhds().get(i).getName() + " "+
                            "["+lvmStorage.getPhds().get(i).getSize()+"G] ["+
                            lvmStorage.getPhds().get(i).getUuid()+"]");
                }
            }
            else if (cmd.contains("pvcreate ")){
                cmd = cmd.substring(9);
                int index = cmd.indexOf(" ");
                int phdObj = -1;
                String phdStr = cmd.substring(index+1);
                for (int i=0;i<lvmStorage.getPhds().size();i++){
                    if (phdStr.equals(lvmStorage.getPhds().get(i).getName())) {
                        phdObj = i;
                    }
                }
                if (phdObj != -1){
                    if (!lvmStorage.phdTaken(lvmStorage.getPhds().get(phdObj)) && !lvmStorage.pvNameTaken(cmd.substring(0,index))){
                        if (lvmStorage.addPv(new Pv(cmd.substring(0,index),lvmStorage.getPhds().get(phdObj)))){
                        System.out.println(cmd.substring(0,index)+" created");
                        }
                    }
                    else System.out.println(cmd.substring(0,index) +" was not created");
                }
                else System.out.println(phdStr+" was not found");
            }
            else if (cmd.contains("pvlist")){
                for (int i =0;i<lvmStorage.getPvs().size();i++){
                    int vgSlot = -1;
                    for (int j = 0; j < lvmStorage.getVgs().size(); j++) {
                        for (int k = 0; k < lvmStorage.getVgs().get(j).getPvs().size(); k++) {
                            if (lvmStorage.getPvs().get(i).getUuid().equals(lvmStorage.getVgs().get(j).getPvs().get(k).getUuid())){vgSlot = j;}
                        }
                    }

                    if (vgSlot != -1){
                        System.out.println(lvmStorage.getPvs().get(i).getName() + " "+
                                "["+lvmStorage.getPvs().get(i).getPhdStored().getSize()+"G] ["+ lvmStorage.getVgs().get(vgSlot).getName()+"] ["+
                                lvmStorage.getPvs().get(i).getUuid()+"]");
                    }
                    else    System.out.println(lvmStorage.getPvs().get(i).getName() + " "+
                            "["+lvmStorage.getPvs().get(i).getPhdStored().getSize()+"G] ["+
                            lvmStorage.getPvs().get(i).getUuid()+"]");
                }
            }
            else if (cmd.contains("vgcreate ")){
                cmd = cmd.substring(9);
                int index = cmd.indexOf(" ");
                int pvObj = -1;
                String pvStr = cmd.substring(index+1);
                for (int i=0;i<lvmStorage.getPvs().size();i++){
                    if (pvStr.equals(lvmStorage.getPvs().get(i).getName())) {
                        pvObj = i;
                    }
                }
                if (pvObj != -1){
                    if (!lvmStorage.pvTaken(lvmStorage.getPvs().get(pvObj)) && !lvmStorage.vgNameTaken(cmd.substring(0,index))){
                        if (lvmStorage.addVg(new Vg(cmd.substring(0,index),lvmStorage.getPvs().get(pvObj)))){
                            System.out.println(cmd.substring(0,index)+" created");
                        }
                    }
                    else System.out.println(cmd.substring(0,index) +" was not created");
                }
                else System.out.println(pvStr+" was not found");
            }
            else if (cmd.contains("vgextend ")){
                cmd = cmd.substring(9);
                int index = cmd.indexOf(" ");
                int vgObj = -1;
                String vgStr = cmd.substring(0,index);
                for (int i = 0; i < lvmStorage.getVgs().size(); i++) {
                    if (vgStr.equals(lvmStorage.getVgs().get(i).getName())){vgObj = i;}
                }
                if (vgObj != -1){
                    int pvObj = -1;
                    String pvStr = cmd.substring(index+1);
                    for (int i=0;i<lvmStorage.getPvs().size();i++){
                        if (pvStr.equals(lvmStorage.getPvs().get(i).getName())) {
                            pvObj = i;
                        }
                    }
                    if (pvObj != -1){
                        if(!lvmStorage.pvTaken(lvmStorage.getPvs().get(pvObj))){
                            lvmStorage.getVgs().get(vgObj).addPV(lvmStorage.getPvs().get(pvObj));
                            System.out.println(pvStr + " added to "+vgStr);
                        }
                        else System.out.println(pvStr+" is already in a Vg");
                    }
                    else System.out.println(pvStr+" was not found");
                }
                else System.out.println(vgStr+" was not found");
            }
            else if (cmd.contains("vglist")){
                for (int i =0;i<lvmStorage.getVgs().size();i++){
                    String pvlist = "[";

                    for (int j = 0; j < lvmStorage.getVgs().get(i).getPvs().size(); j++) {
                        pvlist += lvmStorage.getVgs().get(i).getPvs().get(j).getName();
                        if (j+1 != lvmStorage.getVgs().get(i).getPvs().size()){
                            pvlist += ",";
                        }
                    }
                    pvlist += "]";

                    System.out.println(lvmStorage.getVgs().get(i).getName() + ": total:"+
                            "["+lvmStorage.getVgs().get(i).getSize()+"G] available:["+
                            lvmStorage.getVgs().get(i).getFreeSpace()+"] "+pvlist+" ["+
                            lvmStorage.getPvs().get(i).getUuid()+"]");
                }
            }
            else if (cmd.contains("lvcreate ")){
                cmd = cmd.substring(9);
                int index = cmd.indexOf(" ");
                String temp = cmd.substring(index+1);
                int tempIndex = temp.indexOf(" ") + index+1;
                int vgObj = -1;
                String vgStr = cmd.substring(tempIndex+1);
                for (int i=0;i<lvmStorage.getVgs().size();i++){
                    if (vgStr.equals(lvmStorage.getVgs().get(i).getName())) {
                        vgObj = i;
                    }
                }
                if (vgObj != -1)
                {
                    if (!lvmStorage.lvNameTaken(cmd.substring(0,index))){
                        int fSpace = lvmStorage.getVgs().get(vgObj).getFreeSpace();
                        int lvSize = Integer.parseInt(cmd.substring(index + 1, tempIndex - 1));
                        if (fSpace - lvSize >= 0)
                        {
                            if (lvmStorage.addLv(new Lv(cmd.substring(0,index),lvSize,lvmStorage.getVgs().get(vgObj))))
                            {
                                System.out.println(cmd.substring(0,index)+" created");
                            }
                        }
                        else System.out.println(cmd.substring(tempIndex+1)+" has insufficient space");
                    }
                    else System.out.println(cmd.substring(0,index) +" already exists");
                }
                else System.out.println(vgStr+" was not found");
            }
            else if (cmd.contains("lvlist")){
                for (int i =0;i<lvmStorage.getLvs().size();i++){

                    System.out.println(lvmStorage.getLvs().get(i).getName() + ": ["+
                            +lvmStorage.getLvs().get(i).getSize()+"G] ["+
                            lvmStorage.getLvs().get(i).getHost().getName()+"] ["+
                            lvmStorage.getLvs().get(i).getUuid()+"]");
                }
            }










//WHILE LOOP END
        }



        // MAIN END
    }
    public static String removeExtraSpace(String str){

        for (int i =1; i<str.length();i++){
            String s = String.valueOf(str.charAt(i - 1));
            boolean equals = String.valueOf(str.charAt(i)).equals(" ");
            if (equals && s.equals(" ")){
                str = str.substring(i+1);
                i--;
            }

        }
        return str;
    }

// CLASS END
}
//a