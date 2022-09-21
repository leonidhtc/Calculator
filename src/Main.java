import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println("Calculator.");
        Scanner in = new Scanner(System.in);
        for(;;) {
            System.out.print("Input an expression: ");
            String string = in.nextLine();

            //System.out.printf("Your input: %s \n", "\"" + string + "\"");
            //System.out.printf("Result: %s \n", calc(string));
            System.out.println( calc(string) );
        }
        //in.close();
    }
    public static String calc(String input){
        String validatorArabic1to10 = "^(?=[0-9])([1-9]?|10)$"; // Only Arabic integers from 1 to 10.
        String validatorRoman = "^(?=[MDCLXVI])M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$"; // Only strict standard Roman
        String validatorRomanItoX = "^(?=[XVI])(X|I[XV]|V?I{0,3})$"; // Only strict standard Roman from I to X, no IIII.
        String validatorOperator="^([+,\\-,*,/])$";

        String result = input;
        result = result.replaceAll("\s","")
                .replaceAll("\\+"," + ")
                .replaceAll("\\-"," - ")
                .replaceAll("\\*"," * ")
                .replaceAll("\\/"," / ");

        result = result.replaceAll(validatorRoman, "<ROMAN>");
        result = result.replaceAll(validatorArabic1to10, "<ARABIC_1_to_10>");
        String[] tokens = result.split(" ");
        if(tokens.length!=3) throw new RuntimeException("Error01: Invalid input.");

        if(!Pattern.matches(validatorOperator,tokens[1])) throw new RuntimeException("Error02: Operator missing.");

        if( Pattern.matches(validatorArabic1to10,tokens[0])
                && Pattern.matches(validatorOperator,tokens[1])
                && Pattern.matches(validatorArabic1to10,tokens[2]) ) {
            //System.out.println("arabic operator arabic");
            result=evaluateArabic(tokens[0],tokens[1],tokens[2]);
        } else
        if( Pattern.matches(validatorRomanItoX,tokens[0])
                && Pattern.matches(validatorOperator,tokens[1])
                && Pattern.matches(validatorRomanItoX,tokens[2]) ) {
            //System.out.println("roman operator roman");
            result = evaluateRoman(tokens[0],tokens[1],tokens[2]);
        } else throw new RuntimeException("Error03: Invalid input.");

        return result;
    }

    private static String evaluateArabic(String operand1, String operator, String operand2) {
        int result, op1=Integer.valueOf(operand1), op2=Integer.valueOf(operand2);
        switch (operator) {
            case "+": result= op1 + op2; break;
            case "-": result= op1 - op2; break;
            case "*": result= op1 * op2; break;
            case "/": result= op1 / op2; break;
            default:  throw new RuntimeException("Error04: Invalid operator.");
        }
        return String.valueOf(result);
    }
    private static String evaluateRoman(String operand1, String operator, String operand2) {
        int result, op1=roman2Arabic(operand1), op2=roman2Arabic(operand2);

        switch (operator) {
            case "+": result= op1 + op2; break;
            case "-": result= op1 - op2; break;
            case "*": result= op1 * op2; break;
            case "/": result= op1 / op2; break;
            default:  throw new RuntimeException("Error05: Invalid operator.");
        }
        return arabic2Roman(result);
    }

    private static String arabic2Roman(int num) {
        String result="";
        if(num<=0) throw new RuntimeException("Error07: No corresponding Roman number.");
        if(num==100) {result="C"; num-=100;} //100->0
        if(num>=90) {result="XC"; num-=90;} //90..99 -> 0..9
        if(num>=50) {result="L"; num-=50;}  //50..89 -> 0..39
        if(num>=40) {result="XL"; num-=40;} //40..49 -> 0..9
        if(num>=30) {result=result+"XXX"; num-=30;}//30..39 -> 0..9
        if(num>=20) {result=result+"XX"; num-=20;}//20..29 -> 0..9
        if(num>=10) {result=result+"X"; num-=10;} //10..19 -> 0..9
        if(num>=9) {result=result+"IX"; num-=9;}  //9 -> 0
        if(num>=5) {result=result+"V"; num-=5;} //5..8 -> 0..3
        if(num>=4) {result=result+"IV"; num-=4;} //4 -> 0
        if(num>=3) {result=result+"III"; num-=3;} //3 -> 0
        if(num>=2) {result=result+"II"; num-=2;} //2 -> 0
        if(num>=1) {result=result+"I"; num-=1;} //1 -> 0
        return result;
    }

    private static int roman2Arabic(String roman) {
        int result;
        switch (roman) {
            case "I": result = 1; break;
            case "II": result = 2; break;
            case "III": result = 3; break;
            case "IV": result = 4; break;
            case "V": result = 5; break;
            case "VI": result = 6; break;
            case "VII": result = 7; break;
            case "VIII": result = 8; break;
            case "IX": result = 9; break;
            case "X": result = 10; break;
            default:  throw new RuntimeException("Error06: Not a Roman number from I to X.");
        }
        return result;
    }
}
