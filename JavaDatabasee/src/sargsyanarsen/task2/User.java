package sargsyanarsen.task2;

public class User {

    private String name;
    private int age;
    private int id;

    public User(int id, String name,int age) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", ege=" + age +
                ", id=" + id +
                '}';
    }
}

