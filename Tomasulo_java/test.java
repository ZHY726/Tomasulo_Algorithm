import java.math.BigDecimal;

public class test {
    public static void main(String[] args) {
        double a = 10.1;
        double b = 20.2;
        double c = a+b;

        BigDecimal numberA = new BigDecimal(Double.toString(a));
        BigDecimal numberB = new BigDecimal(Double.toString(b));
        double numberC = numberA.add(numberB).doubleValue();//加
//        double numberC = numberA.subtract(numberB).doubleValue();//减
//        double numberC = numberA.multiply(numberB).doubleValue();//乘

        System.out.println(c);
        System.out.println(numberC);
    }
}
