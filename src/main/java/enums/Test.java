package enums;

public class Test {

    public static void main(String[] args) {

        //返回所有的枚举值
        for (WeekEnum we : WeekEnum.values()) {
            System.out.println(we);
        }


        System.out.println(WeekEnum.WEEK_ENUM_1.getKey()+"----------"+WeekEnum.WEEK_ENUM_1.getValue());

    }
}
