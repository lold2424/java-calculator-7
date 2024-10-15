package calculator;

import camp.nextstep.edu.missionutils.Console;

public class Application {

    public static void main(String[] args) {
        String input = Input();

        if (input.isEmpty()) { // 입력이 null 인 경우
            ResultMessage(0);
            return;
        }

        String[] inputArr = InputArr(input);

        double[] inputDoubleArr = DoubleArr(inputArr);

        double sum = Sum(inputDoubleArr);

        ResultMessage(sum);
    }

    static String Input() { // 입력
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = Console.readLine();
        return input;
    }

    static char CustomSeparator (String input) { // 커스텀 구분자 구분
        if (input.charAt(0) == '/') {
            if (input.charAt(1) == '/' && input.charAt(3) == '\\' && input.charAt(4) == 'n') {
                if (!Character.isDigit(input.charAt(2)) && input.charAt(2) != '.') {
                    if (input.charAt(2) == ',' || input.charAt(2) == ';') {
                        throw new IllegalArgumentException("기본 구분자입니다.");
                    }
                    char separator = input.charAt(2);
                    return separator;
                }
            }
        }
        else if(input.charAt(0) != '/') {
            return 0;
        }
        throw new IllegalArgumentException("구분자를 정확하게 입력해주세요.");
    }

    static String[] InputArr(String input) { // 입력 문자열을 배열로 변환
        String[] inputArr;
        char separator = CustomSeparator(input);

        if (separator == '\\') { // 역슬래시 구분자에 대한 처리
            input = input.substring(5);
            inputArr = input.split("[;, \\\\]");
        } else if (separator != 0) {
            input = input.substring(5);
            inputArr = input.split("[;, " + separator + "]");
        } else {
            inputArr = input.split("[;,]");
        }

        InputException(inputArr, separator);
        return inputArr;
    }

    static void InputException(String[] inputArr, char separator) {
        for (String Num : inputArr) {

            if (Num.isEmpty()) {  // 구분자의 앞뒤에 값이 없는 경우 처리
                throw new IllegalArgumentException("숫자 입력이 제대로 되지 않았습니다.");
            }

            if (Num.equals("0")) {  // 0인 경우 예외 처리
                throw new IllegalArgumentException("0은 입력할 수 없습니다.");
            }

            if (Num.matches("0{2,}")) {  // 두 개 이상의 연속된 0이 있을 경우
                throw new IllegalArgumentException("0은 입력할 수 없습니다.");
            }

            if (Num.contains("-")) {  // 음수인 경우 예외 처리
                throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
            }

            if (!Num.matches("[0-9.]+")) { // 숫자, 소수점, 구분자 이외의 문자가 들어왔는지 확인
                throw new IllegalArgumentException("숫자, 소수점, 구분자 이외의 문자가 들어왔습니다.");
            }

            if (Double.parseDouble(Num) > Integer.MAX_VALUE - 1) { // 너무 큰 숫자를 입력할 경우
                throw new IllegalArgumentException("너무 큰 숫자는 입력할 수 없습니다.");
            }
        }
    }

    static double[] DoubleArr(String[] inputArr) { // String에서 Double로 형 변환
        double[] inputDoubleArr = new double[inputArr.length];
        for (int i = 0; i < inputArr.length; i++) {
            inputDoubleArr[i] = Double.parseDouble(inputArr[i]);
        }
        return inputDoubleArr;
    }

    static double Sum(double[] inputDoubleArr) { // 실수 전부 다 더하기
        double sum = 0;
        for (double Num : inputDoubleArr) {
            sum += Num;
        }
        return sum;
    }

    static boolean IsInteger(double num) { // 결과값이 정수인지 판별
        return num % 1 == 0.0;
    }

    static void ResultMessage(double sum) { // 결과 값 출력
        if(IsInteger(sum)){
            System.out.printf("결과 : " + (int) sum);
        } else {
            System.out.printf("결과 : " + sum);
        }
    }
}
