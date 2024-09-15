import templates.View;

public class Main {
    public static void main(String[] args) {
        View view = new View();
        Controller Controller = new Controller(view);
        view.setVisible(true);
    }
}
