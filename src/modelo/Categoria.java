/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

/**
 *
 * @author Y
 */

public enum Categoria {
    ARMARIO("armario"),
    GELADEIRA("geladeira");

    private final String value;

    Categoria(String value) { this.value = value; }

    public String getValor() { return value; }

    public static Categoria fromString(String value) {
        for (Categoria c : Categoria.values()) {
            if (c.value.equalsIgnoreCase(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown categoria: " + value);
    }
}
