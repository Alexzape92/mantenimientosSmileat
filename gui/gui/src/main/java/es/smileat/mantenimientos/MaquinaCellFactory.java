package es.smileat.mantenimientos;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class MaquinaCellFactory implements Callback<TableColumn<Mantenimiento, String>, TableCell<Mantenimiento, String>> {

    @Override
    public TableCell<Mantenimiento, String> call(TableColumn<Mantenimiento, String> param) {
        return new TableCell<Mantenimiento, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    Mantenimiento mantenimiento = getTableView().getItems().get(getIndex());
                    setText(mantenimiento.getMaquina().getId());
                }
            }
        };
    }
}
