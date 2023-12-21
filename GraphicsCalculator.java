package td9;


import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent;

public class GraphicsCalculator extends Application {
    Tokenizer tok;
    Label resultat;
    
    @Override
    public void start(Stage stage) {
        stage.show();

        stage.setTitle("Calculatrice");
        stage.setMaxHeight(200);
        stage.setMaxWidth(200);
        stage.setMinHeight(200);
        stage.setMinWidth(200);
        
        resultat = new Label();
        tok = new Tokenizer();
        HBox h = new HBox(resultat);
        HBox h1 = new HBox(b('7'), b('8'), b('9'), b('+'));
        HBox h2 = new HBox(b('4'), b('5'), b('6'), b('-'));
        HBox h3 = new HBox(b('1'), b('2'), b('3'), b('*'));
        HBox h4 = new HBox(b('0'), b('.'), b('C'), b('/'));
        HBox h5 = new HBox(b('('), b(')'), b('$'), b('='));
        VBox vb = new VBox(h,h1,h2,h3,h4,h5);
        Scene scene = new Scene(vb);
        scene.setOnKeyTyped(e      -> handlekey(e));
        stage.setScene(scene); 
    }

    Button b(char c){
        Button bouton = new Button(String.valueOf(c));
        bouton.setMinSize(30, 30);
        bouton.setMaxSize(30,30);
        bouton.setOnAction(value -> {update(c);});
        return bouton;
    }

    void update(char c){
        tok.readChar(c);
        if(c=='C') resultat.setText("0.0");
        else if(c=='+' ||  (c=='-' && tok.isMinUnary==false && tok.isStart==false) ||  c=='*' ||  c=='/' ||  c=='='){
            resultat.setText(String.valueOf(tok.calc.getResult()));
        }
    }
    
    void handlekey(KeyEvent e){
        char c = e.getCharacter().charAt(0);
        tok.readChar(c);
        if(c=='C') resultat.setText("0.0");
        else if(c=='+' ||  (c=='-' && tok.isMinUnary==false && tok.isStart==false) ||  c=='*' ||  c=='/' ||  c=='='){  
            resultat.setText(String.valueOf(tok.calc.getResult()));                                
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}