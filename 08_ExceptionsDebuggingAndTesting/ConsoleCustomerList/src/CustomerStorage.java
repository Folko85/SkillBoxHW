import java.util.HashMap;

public class CustomerStorage {
    private HashMap<String, Customer> storage;

    public CustomerStorage() {
        storage = new HashMap<>();
    }

    public void addCustomer(String data) throws IllegalFormatException, IllegalNameException, IllegalPhoneException, IllegalEmailException { // здесь мы прокидываем все исключения, отлавливаем в мейне
        String[] components = data.split("\\s+");
        if (components.length != 4) {
            throw new IllegalFormatException("Неверный формат команды.");
        }

        String name = components[0] + " " + components[1];
        if (!name.matches("([a-zA-Zа-яА-ЯёЁ -]+)")) {
            throw new IllegalNameException("Некорректное имя");
        }
        if (!components[3].matches("(\\s*)?(\\+)?([- ()]?\\d+[- ()]?){10,14}(\\s*)?")) {
            throw new IllegalPhoneException("Некорректный номер телефона");
        }
        if (!components[2].matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}")) {
            throw new IllegalEmailException("Некорректный E-mail");
        }
        storage.put(name, new Customer(name, components[3], components[2]));
    }

    public void listCustomers() {
        storage.values().forEach(System.out::println);
    }

    public void removeCustomer(String name) {
        storage.remove(name);
    }

    public int getCount() {
        return storage.size();
    }
}