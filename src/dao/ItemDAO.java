/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Item;

/**
 *
 * @author Y
 */
public interface ItemDAO {
    public int inserir(Item item);
    public int editar(Item item);
    public int apagar(int id);
    public List<Item> listar();
    public Item listar(int id);
   
}
