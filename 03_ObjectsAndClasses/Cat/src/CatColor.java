import java.util.Random;

public enum CatColor
{
    BLACK,
    ORANGE,
    WHITE,
    GREY,
    BLUE,
    MIXED;

    public static CatColor getRandomColor() {           //добавляем этот метод, чтобы иметь возможность
        Random random = new Random();                    // создавать котов случайного цвета
        return values()[random.nextInt(values().length)];
    }
}
