/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    conectaDAO conecta;
    
    public ProdutosDAO() {
        this.conecta = new conectaDAO();
        this.conn = this.conecta.connectDB();
        
    }
    
    public void cadastrarProduto (ProdutosDTO produto){
        // Preparando sql statmente para inclusão no BD
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement prep = this.conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
            
        } catch (SQLException ex) {
            System.out.println("Não foi possível cadastrar o produto " + ex.getMessage());
        }

    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        
        String sql = "SELECT * FROM produtos";
        try {
            
            PreparedStatement prep = this.conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while(resultset.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(resultset.getInt("ID"));
                p.setNome(resultset.getString("Nome"));
                p.setValor(resultset.getInt("Valor"));
                p.setStatus(resultset.getString("status"));
                listagem.add(p);
                
            }
            
            
        } catch (SQLException ex) {
            System.out.println("Não foi possível listar os produtos " + ex.getMessage());
        }
        
        return listagem;
        
    }
    

    public void venderProduto (int id){
        // Preparando sql statmente para atualização no BD
        String sql = "UPDATE produtos SET status = ? WHERE Id = ?";
              
        try {
            PreparedStatement prep = this.conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, id);
            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso");
            
        } catch (SQLException ex) {
            System.out.println("Não foi possível vender o produto " + ex.getMessage());
        }
            
    } 
    
    public ArrayList<ProdutosDTO> listarProdutosVendidos(){
        
        ArrayList<ProdutosDTO> listagemVendidos = new ArrayList<>();
        String sql = "SELECT * FROM produtos WHERE status = ?";
        try {
            
            PreparedStatement prep = this.conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            resultset = prep.executeQuery();
            
            while(resultset.next()) {
                ProdutosDTO pVendido = new ProdutosDTO();
                pVendido.setId(resultset.getInt("ID"));
                pVendido.setNome(resultset.getString("Nome"));
                pVendido.setValor(resultset.getInt("Valor"));
                pVendido.setStatus(resultset.getString("status"));
                listagemVendidos.add(pVendido);
                
            }
            
            
        } catch (SQLException ex) {
            System.out.println("Não foi possível listar os produtos vendidos" + ex.getMessage());
        }
        
        return listagemVendidos;
        
    }
    
}

