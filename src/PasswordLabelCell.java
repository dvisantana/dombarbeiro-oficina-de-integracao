import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;

public class PasswordLabelCell extends TableCell<Usuario, String> {
    private Label label;
    private boolean mostrar;

    public PasswordLabelCell(boolean mostrar) {
        label = new Label();
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setGraphic(null);
        this.mostrar = mostrar;
    }

    private String genDotString(int len, String item) {
        String dots = "";

        for (int i = 0; i < len; i++) {
            dots += "\u2022";
        }

        if(mostrar){
            return item;
        }else{
            return dots;
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            label.setText(genDotString(item.length(),item));
            setGraphic(label);
        } else {
            setGraphic(null);
        }
    }
}