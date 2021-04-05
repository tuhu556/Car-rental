/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class CartDTO {

    private String email;
    private Map<String, CarDTO> cart;

    public CartDTO() {
    }

    public CartDTO(String email, Map<String, CarDTO> cart) {
        this.email = email;
        this.cart = cart;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, CarDTO> getCart() {
        return cart;
    }

    public void setCart(Map<String, CarDTO> cart) {
        this.cart = cart;
    }
    public void add(CarDTO dto) {
        if (this.cart == null) {
            cart = new HashMap<>();
        }
        if (this.cart.containsKey(dto.getCode())) {
            int quantity = this.cart.get(dto.getCode()).getQuantity();
            dto.setQuantity(quantity + dto.getQuantity());
        }
        cart.put(dto.getCode(), dto);
    }
    
    public void delete(String id) {
        if (this.cart == null) {
            return;
        }
        if (this.cart.containsKey(id)) {
            this.cart.remove(id);
        }
    }
    
    public void update(CarDTO dto) {
        if (this.cart != null) {
            if (this.cart.containsKey(dto.getCode())) {
                this.cart.replace(dto.getCode(), dto);
            }
        }
    }
    
}
