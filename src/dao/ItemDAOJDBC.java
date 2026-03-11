/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package dao;

/**
 *
 * @author Y
 */
import java.util.ArrayList;
import java.util.List;
import modelo.Item;
import java.sql.*;
import modelo.Categoria;


public class ItemDAOJDBC implements ItemDAO {

    Connection conexao = null;
    PreparedStatement sql = null;
    ResultSet rset = null;
    
    @Override
    public int inserir(Item item) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("INSERT INTO itens(nome, categoria, quantidade, validade) ")
                .append("VALUES (?, ?, ?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {          
            linha = DAOGenerico.executarComando(
                    insert, item.getNome(), 
                    item.getCategoria().getValor(), 
                    item.getQuantidade(), 
                    item.getValidade());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }
        
        return linha;
    }

    @Override
    public int editar(Item item) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE itens SET ")
                .append("nome = ?, ")
                .append("categoria = ?,")
                .append("quantidade = ?,")
                .append("validade = ?")
                .append("WHERE id = ?");
        
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            
            linha = DAOGenerico.executarComando(
                    update, item.getNome(), 
                    item.getCategoria().getValor(), 
                    item.getQuantidade(), 
                    item.getValidade(), 
                    item.getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public int apagar(int id) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM itens ")
                .append("WHERE id = ?");
        
        String delete = sqlBuilder.toString();
        int linha = 0;
        try {         
            linha = DAOGenerico.executarComando(delete, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return linha;
    }

    @Override
    public List<Item> listar() {
        List<Item> itemList = new ArrayList<Item>();
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT id, nome, categoria, quantidade, validade ")
                .append("FROM itens");
        
        String select = sqlBuilder.toString();
        
        try (Connection conexao = DAOGenerico.getConexao();
            PreparedStatement sql = conexao.prepareStatement(select);
            ResultSet rset = sql.executeQuery()) {

            while (rset.next()) {
                Item item = new Item();
                item.setId(rset.getInt("id"));
                item.setNome(rset.getString("nome"));
                item.setQuantidade(rset.getInt("quantidade"));
                item.setValidade(rset.getDate("validade"));
                String categoriaStr = rset.getString("categoria"); 
                Categoria categoria = Categoria.fromString(categoriaStr);
                item.setCategoria(categoria);
                
                itemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemList;
    }

    
    //lista por id
    @Override
    public Item listar(int id) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("SELECT id, nome, categoria, quatidade, validade ")
                .append("FROM itens WHERE id =? ");
        String select = sqlBuilder.toString();
        
        Item item = null;

        try {       
            rset = DAOGenerico.executarConsulta(select, id);


            while (rset.next()) {

                item = new Item();
                item.setId(rset.getInt("id"));
                item.setNome(rset.getString("nome"));
                item.setQuantidade(rset.getInt("quantidade"));
                item.setValidade(rset.getDate("validade"));

                String categoriaStr = rset.getString("categoria");
                Categoria categoria = Categoria.fromString(categoriaStr);
                item.setCategoria(categoria);
                
                item.setCategoria(categoria);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fecharConexao();
        }

        return item;        
    }
    
    private void fecharConexao() {
        try {
            if (rset != null) {
                rset.close();
            }
            if (sql != null) {
                sql.close();
            }

            if (conexao != null) {
                conexao.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
