package frontend;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Tela extends JFrame {
    private JTextField txtNomeDisciplina, txtCurso, txtTurno;
    private JButton btnCadastrar, btnExcluir, btnVisualizar;
    private JTextArea txtResultado;
    
    public Tela() {
        setTitle("Gerenciamento de Disciplinas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        
        add(new JLabel("Nome da Disciplina:"));
        txtNomeDisciplina = new JTextField(20);
        add(txtNomeDisciplina);
        
        add(new JLabel("Curso:"));
        txtCurso = new JTextField(10);
        add(txtCurso);
        
        add(new JLabel("Turno:"));
        txtTurno = new JTextField(10);
        add(txtTurno);
        
        btnCadastrar = new JButton("Cadastrar");
        btnExcluir = new JButton("Excluir");
        btnVisualizar = new JButton("Visualizar");
        
        add(btnCadastrar);
        add(btnExcluir);
        add(btnVisualizar);
        
        txtResultado = new JTextArea(10, 30);
        add(new JScrollPane(txtResultado));
        
        btnCadastrar.addActionListener(e -> cadastrarDisciplina());
        btnExcluir.addActionListener(e -> excluirDisciplina());
        btnVisualizar.addActionListener(e -> visualizarDisciplinas());
    }
    
    private void cadastrarDisciplina() {
        String nome = txtNomeDisciplina.getText();
        int curso = Integer.parseInt(txtCurso.getText());
        int turno = Integer.parseInt(txtTurno.getText());
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/recuperacao", "root", "")) {
            String sql = "INSERT INTO Disciplina (nm_disciplina, curso, turno) VALUES (1, informatica, matutino)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setInt(2, curso);
            stmt.setInt(3, turno);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Disciplina cadastrada com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void excluirDisciplina() {
        String nome = txtNomeDisciplina.getText();
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/recuperacao", "root", "")) {
            String sql = "DELETE FROM Disciplina WHERE nm_disciplina = 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Disciplina excluída com sucesso!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void visualizarDisciplinas() {
        txtResultado.setText("");
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/recuperacao", "root", "")) {
            String sql = "SELECT * FROM Disciplina";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                txtResultado.append("ID: " + rs.getInt("id_disciplina") + ", Nome: " + rs.getString("nm_disciplina") + "\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Tela().setVisible(true));
    }
}