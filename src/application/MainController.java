package application;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {

   
    @FXML
    private Label Number1, result;

    private ArrayList<Character> a, b;

    @FXML
    public void clickButton(javafx.event.ActionEvent event) {
        char s = event.getSource().toString().charAt(event.getSource().toString().length() - 2);
        Number1.setText(Number1.getText() + s);
    }

    public void clickClear(javafx.event.ActionEvent event) {

        Number1.setText("");
        result.setText("");
    }

    public void clickBack(javafx.event.ActionEvent event) {

        if (!Number1.getText().equals("")) {
            Number1.setText(Number1.getText().substring(0, Number1.getText().length() - 1));
        }
    }

    public boolean isNumber1(String s) {
        boolean flag = true;
        for (char c : s.toCharArray()) {
            if ((c >= '0' && c <= '9') || c == '.') {
                flag = true;
                break;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public boolean isVariable(String s) {
        boolean flag = true;
        for (char c : s.toCharArray()) {
            if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public int getPrec(String c) {

        switch (c) {
            case ")":
                return 0;
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "(":
                return 4;
            default:
                return 3;
        }

    }

    public float operation(char op, float n1, float n2) {
        float res;
        switch (op) {
            case '+':
                res = n1 + n2;
                break;
            case '*':
                res = n1 * n2;
                break;
            case '-':
                res = n1 - n2;
                break;
            default:
                res = n1 / n2;
        }
        return res;
    }

    public boolean isOperand(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    public void clickResult(javafx.event.ActionEvent event) {
        a = new ArrayList<>();
        b = new ArrayList<>();
        
        String s = Number1.getText();
        a.add(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            for (int j = a.size() - 1; j >= 0; j--) {
                if (getPrec(s.charAt(i) + "") < getPrec(a.get(j) + "")) {
                    b.add(a.get(j));
                    System.out.println(b);
                    a.remove(j);
                }
            }
            a.add(s.charAt(i));
        }
      
        if (!b.isEmpty()) {
            for (int i = a.size() - 1; i >= 0; i--) {
                b.add(a.get(i));
            }
        }
        System.out.println(b);
        for (int i=0;i<b.size();i++) {
            if (b.get(i) == '(' || b.get(i) == ')') {
                b.remove(i);
            }
        }
        
        System.out.println(b);
        

        ArrayList<String> c = new ArrayList<>();
        b.forEach((cc) -> {
            c.add(cc + "");
        });
        for (int i = c.size() - 1; i >= 0; i--) {
            if (isOperand(c.get(i).charAt(0)) && isNumber1(c.get(i - 1) + "") && isNumber1(c.get(i - 2) + "")) {
                float n1 = Float.parseFloat(c.get(i - 1)), n2 = Float.parseFloat(c.get(i - 2) + ""), res;
                res = operation(c.get(i).charAt(0), n2, n1);
                c.remove(i);
                c.remove(i - 1);
                c.remove(i - 2);
                c.add(i - 2, res + "");
                i = c.size();
            }
        }
        result.setText(c.get(0));
        
    }

}
