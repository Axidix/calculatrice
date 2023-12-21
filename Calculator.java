package td9;

import java.util.Stack;
import java.util.LinkedList;

public class Calculator {
    public Stack<Double> numbers;
    public Stack<Operator> operators;
    public LinkedList<Double> results;


    public Calculator() {
        this.numbers = new Stack<Double>();
        this.operators = new Stack<Operator>();
        this.results = new LinkedList<Double>();
    }

    @Override
    public String toString() {
        return numbers.toString() + "\n" + operators.toString(); 
    }


    void pushDouble(double d) {
        numbers.push(d);
    }

    void pushOperator(Operator o) {
        operators.push(o);
    }


    public double getResult() {
        if (numbers.size() == 0) throw new RuntimeException();
        return numbers.peek();
    }


    void executeBinOperator(Operator op) {
        double res = 0;
        double d = numbers.pop();
        double g = numbers.pop();
        switch (op){
            case DIV:
                res = g/d;
                break;
            case MULT:
                res = g*d;
                break;
            case PLUS:
                res = g+d;
                break;
            case MINUS:
                res = g-d;
                break;
            case OPEN:
                break;
        }
        numbers.push(res);
    }


    private int precedence(Operator op){
        switch(op) {
            case DIV:
                return 2;
            case MULT:
                return 2;
            case PLUS:
                return 1;
            case MINUS:
                return 1;
            case OPEN:
                return 0;
        }
        return 0;
    }


    void commandOperator(Operator op){
        if (operators.size()==0){
            pushOperator(op);
            return;
        }

        while(precedence(op)<=precedence(operators.peek())){
            executeBinOperator(operators.pop());
            if (operators.size()==0) break;
        }

        pushOperator(op);
    }

    void commandDouble(double d){
        pushDouble(d);
    }

    void commandEqual(){
        while(operators.size()>0) executeBinOperator(operators.pop());
        results.addFirst(numbers.peek());      //POP or PEEK ????? FIRST or LAST ??????
    }

    void commandLPar(){
        pushOperator(Operator.OPEN);
    }

    void commandRPar(){
        while (operators.peek()!=Operator.OPEN) executeBinOperator(operators.pop());
        operators.pop();
    }

    void commandInit(){
        this.numbers = new Stack<Double>();
        this.operators = new Stack<Operator>();
    }

    void commandReadMemory(int i){
        numbers.push(results.get(i-1));
    }


}
