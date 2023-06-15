package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Conta {
	IntegerProperty id;
	StringProperty nome;
	StringProperty cpf;

	public Conta(int id, String nome, String cpf) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.nome = new SimpleStringProperty(nome);
		this.cpf = new SimpleStringProperty(cpf);
	}
	
	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getNome() {
		return nome.get();
	}

	public void setNome(String nome) {
		this.nome.set(nome);
	}

	public String getCpf() {
		return cpf.get();
	}

	public void setCpf(String cpf) {
		this.cpf.set(cpf);
	}

	public IntegerProperty idProperty() {
		return id;
	}
	
	public StringProperty nameProperty() {
		return nome;
	}
	
	public StringProperty cpfProperty() {
		return cpf;
	}
	
}
