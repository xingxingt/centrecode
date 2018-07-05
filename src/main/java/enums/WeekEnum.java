package enums;

public enum WeekEnum {


    WEEK_ENUM_1("1", "周1"),
    WEEK_ENUM_2("2", "周2"),
    WEEK_ENUM_3("3", "周3"),
    WEEK_ENUM_4("4", "周4"),
    WEEK_ENUM_5("5", "周5");


    // 定义一个 private 修饰的实例变量
    private String key;
    private String value;

    // 定义一个带参数的构造器，枚举类的构造器只能使用 private 修饰
    private WeekEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
