package hibernate.model;

public record Task(int id, String name, String owner, int priority) {
}
