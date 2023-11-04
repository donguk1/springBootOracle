package kopo.poly.service.impl;


public class main {

    public static void main(String[] args) {
        CalculateService calculateService = new CalculateService();
        int plus = calculateService.calculate(1, 1, '+');
        System.out.println(plus);

    }
}
class CalculateService {

    public int calculate(int a, int b, char operator) throws IllegalAccessException {

        if (operator == '+') {
            return a + b;

        } else if (operator == '-') {
            return a - b;

        } else {
            throw new IllegalAccessException("지원하지 않는 연산자 입니다.");
        }
    }

}
