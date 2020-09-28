import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Node implements Comparable<Node> {
    private final String url;             // каждый узел имеет гкд
    private final Node parent;           // ссылку на родительский узел
    private final int depth;             // и глубину( расстояние до корневого узла)
    private Set<Node> subLinks = ConcurrentHashMap.newKeySet();   // а ешё у него есть множество узлов-потомков

    public Node(String url) {
        this.url = url;
        this.depth = 0;
        this.parent = null;
    }

    public Node(String url, Node parent) {
        this.url = url;
        this.depth = parent.getDepth() + 1;
        this.parent = parent;
    }

    public synchronized void addSubLink(Node subLink) {
        this.subLinks.add(subLink);
    }

    public Set<Node> getSubLinks() {
        return subLinks;
    }

    public Node getParent() {
        return parent;
    }

    public int getDepth() {
        return depth;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Вот ради этой хитрой древовидной сортировки и был создан класс
     * здесь под каждой родительской ссылкой дерево дочерних
     * Красивей от этого не стало(хотя я надеялся), многовато горизонтальных ссылок
     * Но стало чуть понятней.
     */
    @Override
    public int compareTo(Node node) {
        if (this.getParent() == null) return -1;
        if (node.getParent() == null) return 1;
        if (node.getParent().equals(this)) return -1;
        if (this.getParent().equals(node)) return 1;
        if (this.getDepth() == node.getDepth()) {
            if (this.getParent().equals(node.getParent())) {
                return this.getUrl().compareTo(node.getUrl());
            } else {
                return this.getParent().compareTo(node.getParent());  // если нет общего предка, то поднимаемся на уровень выше
            }
        } else {
            return (this.getDepth() > node.getDepth()) ? this.getParent().compareTo(node) : this.compareTo(node.getParent());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return Objects.equals(url, node.url);
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    /**
     * Во избежание зацикливания, идентичность объектов оценивается по одному параметру - url
     * Вероятно это плохой стиль, но это самый простой способ избавиться от дублей
     */



    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("\t".repeat(Math.max(0, this.getDepth())));
        return result.append(this.getUrl()).toString();
    }
}