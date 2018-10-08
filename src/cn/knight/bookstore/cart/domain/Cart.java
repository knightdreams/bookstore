package cn.knight.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<String,CartItem> map = new LinkedHashMap<String,CartItem>();
    
    /**
     * 计算合计
     * @return
     */
    public double getTotal() {
        BigDecimal total = new BigDecimal("0");
        for (CartItem cartItem : map.values()) {
            BigDecimal subtotal = new BigDecimal(""+cartItem.getSubtotal());
            total = total.add(subtotal);
        }
        return total.doubleValue();
    }
    
    /**
     * 添加条目
     * @param cartItem
     */
    public void add(CartItem cartItem) {
        if(map.containsKey(cartItem.getBook().getBid())) { // 判断老条目中是否存在原来的条目
            CartItem _cartItem = map.get(cartItem.getBook().getBid()); // 返回原条目
            _cartItem.setCount(_cartItem.getCount()+cartItem.getCount()); // 修改老条目的数量
            map.put(cartItem.getBook().getBid(), _cartItem);
        } else {
            map.put(cartItem.getBook().getBid(), cartItem);
        }
    }
    
    /**
     * 清空条目
     */
    public void clear() {
        map.clear();
    }
    
    /**
     * 删除条目
     * @param bid
     */
    public void remove(String bid) {
        map.remove(bid);
    }
    
    /**
     * 我的购物车
     * @return
     */
    public Collection<CartItem> getCartItems(){
        return map.values();
    }
}
