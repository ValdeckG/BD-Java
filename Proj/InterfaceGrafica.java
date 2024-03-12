import javax.swing.*;
import java.awt.event.*;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import javaapplication1.Pessoa;

public class InterfaceGrafica extends JFrame {

    private final ObjectContainer db;
    private JTextField nomeField;
    private JTextField idadeField;

    public InterfaceGrafica(ObjectContainer db) {
        this.db = db;
        initComponents();
    }

    private void initComponents() {
        setTitle("Interface Gráfica Banco de Dados");
        setSize(500, 220);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Evita que o sistema feche a janela diretamente

        addWindowListener(new WindowAdapter() {
          
            public void windowClosing(WindowEvent e) {
                fecharAplicacao();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton inserirButton = new JButton("Inserir Pessoa");
        inserirButton.addActionListener((ActionEvent e) -> {
            inserir();
        });

        JButton listarButton = new JButton("Listar Pessoas");
        listarButton.addActionListener((ActionEvent e) -> {
            listarPessoas();
        });

        JButton atualizarButton = new JButton("Atualizar Pessoa");
        atualizarButton.addActionListener((ActionEvent e) -> {
            atualizarPessoa();
        });

        JButton deletarButton = new JButton("Deletar Pessoa");
        deletarButton.addActionListener((ActionEvent e) -> {
            deletarPessoa();
        });

        nomeField = new JTextField(20);
        idadeField = new JTextField(20);

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Idade:"));
        panel.add(idadeField);
        panel.add(inserirButton);
        panel.add(listarButton);
        panel.add(atualizarButton);
        panel.add(deletarButton);

        getContentPane().add(panel);
    }

    private void inserir() {
        String nome = nomeField.getText();
        int idade = Integer.parseInt(idadeField.getText());
        Pessoa p = new Pessoa(nome, idade);
        db.store(p);
        JOptionPane.showMessageDialog(this, "Pessoa inserida com sucesso!");
    }

    private void listarPessoas() {
        Query query = db.query();
        query.constrain(Pessoa.class);
        ObjectSet<Pessoa> pessoas = query.execute();

        StringBuilder lista = new StringBuilder();
        for (Pessoa pessoa : pessoas) {
            lista.append(pessoa.getNome()).append(", ").append(pessoa.getIdade()).append(" anos\n");
        }
        JOptionPane.showMessageDialog(this, "Lista de Pessoas:\n" + lista.toString());
    }

    private void atualizarPessoa() {
        String nome = nomeField.getText();

        Query query = db.query();
        query.constrain(Pessoa.class);
        query.descend("nome").constrain(nome);
        ObjectSet<Pessoa> result = query.execute();

        if (result.hasNext()) {
            int novaIdade = Integer.parseInt(idadeField.getText());
            Pessoa pessoa = result.next();
            pessoa.setIdade(novaIdade);
            db.store(pessoa);
            JOptionPane.showMessageDialog(this, "Pessoa atualizada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Pessoa não encontrada.");
        }
    }

    private void deletarPessoa() {
        String nome = nomeField.getText();

        Query query = db.query();
        query.constrain(Pessoa.class);
        query.descend("nome").constrain(nome);
        ObjectSet<Pessoa> result = query.execute();

        if (result.hasNext()) {
            Pessoa pessoa = result.next();
            db.delete(pessoa);
            JOptionPane.showMessageDialog(this, "Pessoa deletada com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Pessoa não encontrada.");
        }
    }

    private void fecharAplicacao() {
        int escolha = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (escolha == JOptionPane.YES_OPTION) {
            db.close(); // Fechar o banco de dados
            dispose(); // Fecha a janela atual
            System.exit(0); // Finaliza a aplicação
        }
    }

    public static void main(String[] args) {
        ObjectContainer db = Db4o.openFile("banco.yap");
        java.awt.EventQueue.invokeLater(() -> new InterfaceGrafica(db).setVisible(true));
    }
}
