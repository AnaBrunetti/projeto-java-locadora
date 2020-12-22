/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

/**
 *
 * @author ana
 */
public class Pessoa {
        
    private int id_pes;
    private Endereco endereco;
    private String nome_pes;
    private String cpf;
    private String email;
    private int telefone1;
    private int telefone2;
    private char sexo;

    public int getId_pes() {
        return id_pes;
    }

    public void setId_pes(int id_pes) {
        this.id_pes = id_pes;
    }

    public String getNome_pes() {
        return nome_pes;
    }

    public void setNome_pes(String nome_pes) {
        this.nome_pes = nome_pes;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(int telefone1) {
        this.telefone1 = telefone1;
    }

    public int getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(int telefone2) {
        this.telefone2 = telefone2;
    }

}
