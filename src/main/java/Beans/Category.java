package Beans;

public enum Category {
    FOOD,
    ELECTRICITY,
    SOFTWARE,
    RESTAURANT,
    VACATION;

    public final int value = 1 + ordinal();
}
