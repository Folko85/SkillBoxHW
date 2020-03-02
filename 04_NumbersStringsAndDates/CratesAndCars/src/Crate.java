/*Каждый ящик будет объектом
содержащим переменную со своим именем, зависящим от номера
Это имя мы будем присваивать,
а потом выводить по запросу*/
public class Crate
{
    private String crateName;
    private int crateNumber;

    public Crate(int number)
    {
        this.crateNumber = number + 1;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Ящик " + this.crateNumber);
        return builder.toString();
    }
}
