package hibernate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "owners")
public class Owner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ownerName;

    @OneToMany(mappedBy = "owner")
    private Set<TaskClass> tasks;

    public Owner() {
    }

    public Owner(int id, String ownerName, Set<TaskClass> tasks) {
        this.id = id;
        this.ownerName = ownerName;
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Set<TaskClass> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskClass> tasks) {
        this.tasks = tasks;
    }
}
