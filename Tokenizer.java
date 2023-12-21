package td9;

import java.lang.Boolean;

public class Tokenizer {
   Boolean isStart;
   Boolean isIntNum;
   Boolean isNonIntNum;
   Boolean isMinUnary;
   Boolean isNeg;
   Boolean isMem;
   int decimalDigits;
   double num;
   Calculator calc;
   
    public Tokenizer(){
        this.isStart = true;
        this.isIntNum = false;
        this.isNonIntNum = false;
        this.isMinUnary = false;
        this.isNeg = false;
        this.isMem = false;
        this.decimalDigits = 0;
        this.num = 0;
        this.calc = new Calculator();
    }


    public void readChar(char c){
        if(isMem){
            calc.commandReadMemory(Character.getNumericValue(c));
            isMem = false;
            return;
        }
        if(isStart){
            calc.commandInit();
        }
        if(c == '$'){
            isMem = true;
            isStart = false;
            return;
        }
        else if (c == '='){
            if(isIntNum || isNonIntNum) pushNum();
            endNum();
            calc.commandEqual();

            
            this.isStart = true;
            this.isIntNum = false;
            this.isNonIntNum = false;
            this.isMinUnary = false;
            this.isNeg = false;
            this.isMem = false;
            this.decimalDigits = 0;
            this.num = 0;
        }
        else if(c=='C'){
            this.isStart = true;
            this.isIntNum = false;
            this.isNonIntNum = false;
            this.isMinUnary = false;
            this.isNeg = false;
            this.isMem = false;
            this.decimalDigits = 0;
            this.num = 0;
            this.calc.commandInit();
        }
        else if (Character.isDigit(c)){
            if(isNonIntNum){
                decimalDigits++;
                num += Character.getNumericValue(c)*Math.pow(10.0,-decimalDigits);
            }
            else{
                isIntNum = true;
                num = 10*num + Character.getNumericValue(c);
            }
            isStart = false;
            isMinUnary = true;
        }
        else if(c == '.') {
            isIntNum = false;
            isNonIntNum = true;
        }
        else if(c == '-') {
            if(isMinUnary) {
                if(isIntNum || isNonIntNum) pushNum();
                endNum();
                calc.commandOperator(Operator.MINUS);
                isMinUnary = false;
            }
            else isNeg = !isNeg;
        }
        else {
            if(isIntNum || isNonIntNum) pushNum();

            endNum();
            isMinUnary = false;
            if(c == '+') calc.commandOperator(Operator.PLUS);
            else if(c == '*') calc.commandOperator(Operator.MULT);
            else if(c == '/') calc.commandOperator(Operator.DIV);
            else if(c == '(') calc.commandLPar();
            else if(c == ')') {calc.commandRPar(); isMinUnary = true;}

            isStart = false;
        }
    }

    void readString(String s){
        for (char c: s.toCharArray()){
            readChar(c);
        }
    }

    public void res(){
        System.out.println(calc.getResult());
    }

    void endNum(){
        //Reset var related to num
        isIntNum = false;
        isNonIntNum = false;
        decimalDigits = 0;
        isNeg = false;
    }


    void pushNum(){
        if(isNeg) calc.pushDouble(-num);
        else calc.pushDouble(num);
        num = 0;
    }

}
