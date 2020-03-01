/*Каждый ящик будет объектом
содержащим переменную со своим именем, зависящим от номера
Это имя мы будем присваивать,
а потом выводить по запросу*/
public class Crate
{
    private String crateName;
    private int number;

    public Crate(int number)
    {
        this.number = number;
        this.crateName = "Ящик " + (number + 1);
    }

    public String getCrateName()
    {
        return this.crateName;
    }
}
