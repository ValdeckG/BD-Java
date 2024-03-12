/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

public class Pessoa {
    private String nome;
    private int idade;

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }
   

    Pessoa() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

   

    // Métodos de acesso para o atributo nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Métodos de acesso para o atributo idade
    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public static void main(String[] args) {
        //Pessoa pessoa1 = new Pessoa("João", 30);
        //Pessoa pessoa2 = new Pessoa("Maria", 25);

//        System.out.println("Nome: " + pessoa1.getNome() + ", Idade: " + pessoa1.getIdade());
//        System.out.println("Nome: " + pessoa2.getNome() + ", Idade: " + pessoa2.getIdade());
    }
    
}
