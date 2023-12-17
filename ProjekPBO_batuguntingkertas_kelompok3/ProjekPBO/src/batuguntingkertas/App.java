package batuguntingkertas;

import java.awt.*;
import javax.swing.*;

public class App extends JFrame {

    private static final long serialVersionUID = 1L;

    App() {
    	Menu menu = new Menu();
        this.add(menu);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.BLACK);
    }

    public static void main(String[] args) {
    	try {
			new App();
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

}
