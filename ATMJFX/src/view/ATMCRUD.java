package view;

import model.Conta;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ATMCRUD extends Application {
    private ObservableList<Conta> ContaList;
    private TableView<Conta> tableView;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ContaList = FXCollections.observableArrayList();
        tableView = new TableView<>(ContaList);

        // Colunas da tabela
        TableColumn<Conta, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        TableColumn<Conta, String> nameColumn = new TableColumn<>("Nome");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        TableColumn<Conta, String> cpfColumn = new TableColumn<>("CPF");
        cpfColumn.setCellValueFactory(cellData -> cellData.getValue().cpfProperty());

        tableView.getColumns().addAll(idColumn, nameColumn, cpfColumn);

        Button addButton = new Button("Adicionar");
        addButton.setOnAction(e -> showAddContaDialog());

        Button viewButton = new Button("Visualizar");
        viewButton.setOnAction(e -> showViewContaDialog());

        Button editButton = new Button("Editar");
        editButton.setOnAction(e -> showEditContaDialog());

        Button deleteButton = new Button("Excluir");
        deleteButton.setOnAction(e -> deleteSelectedConta());

        HBox buttonBox = new HBox(10, addButton, viewButton, editButton, deleteButton);
        buttonBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(tableView);
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("ATM CRUD");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddContaDialog() {
        Dialog<Conta> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Conta");
        dialog.setHeaderText("Informe os dados da nova conta");

        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField nameField = new TextField();
        nameField.setPromptText("Nome");
        TextField cpfField = new TextField();
        cpfField.setPromptText("CPF");

        VBox content = new VBox(10, idField, nameField, cpfField);
        content.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(content);

        ButtonType addButton = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String cpf = cpfField.getText();
                return new Conta(id, name, cpf);
            }
            return null;
        });

        dialog.showAndWait().ifPresent(Conta -> {
            ContaList.add(Conta);
            showInfoDialog("Conta adicionada com sucesso!");
        });
    }

    private void showViewContaDialog() {
        Conta selectedConta = tableView.getSelectionModel().getSelectedItem();
        if (selectedConta != null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Visualizar Conta");
            alert.setHeaderText(null);
            alert.setContentText(selectedConta.toString());
            alert.showAndWait();
        } else {
            showWarningDialog("Nenhuma conta selecionada.");
        }
    }

    private void showEditContaDialog() {
        Conta selectedConta = tableView.getSelectionModel().getSelectedItem();
        if (selectedConta != null) {
            Dialog<Conta> dialog = new Dialog<>();
            dialog.setTitle("Editar Conta");
            dialog.setHeaderText("Edite os dados da conta");

            TextField idField = new TextField(Integer.toString(selectedConta.getId()));
            TextField nameField = new TextField(selectedConta.getNome());
            TextField cpfField = new TextField(selectedConta.getCpf());

            VBox content = new VBox(10, idField, nameField, cpfField);
            content.setPadding(new Insets(10));
            dialog.getDialogPane().setContent(content);

            ButtonType saveButton = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButton) {
                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String cpf = cpfField.getText();
                    return new Conta(id, name, cpf);
                }
                return null;
            });

            dialog.showAndWait().ifPresent(updatedConta -> {
                selectedConta.setId(updatedConta.getId());
                selectedConta.setNome(updatedConta.getNome());
                selectedConta.setCpf(updatedConta.getCpf());
                tableView.refresh();
                showInfoDialog("Conta atualizada com sucesso!");
            });
        } else {
            showWarningDialog("Nenhuma conta selecionada.");
        }
    }

    private void deleteSelectedConta() {
        Conta selectedConta = tableView.getSelectionModel().getSelectedItem();
        if (selectedConta != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Excluir Conta");
            alert.setHeaderText(null);
            alert.setContentText("Deseja realmente excluir a conta selecionada?");

            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                ContaList.remove(selectedConta);
                showInfoDialog("Conta excluída com sucesso!");
            }
        } else {
            showWarningDialog("Nenhuma conta selecionada.");
        }
    }

    private void showInfoDialog(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarningDialog(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
