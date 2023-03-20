import java.util.UUID;
public class LVM {
    private String Name;
    private UUID uuid;

    public LVM(String name){
        this.Name = name;
        uuid = UUID.randomUUID();

    }

    public String getName() {
        return Name;
    }

    public UUID getUuid() {
        return uuid;
    }
}
