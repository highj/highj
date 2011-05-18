/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj;

public final class __<Ctor, A, B> {
    
     private final Object data;
    
     public __(Ctor ctor, Object data) {
        if (ctor == null) {
            throw new IllegalArgumentException();
        }
        this.data = data;
    }
    
    public Object read(Ctor ctor) {
        if (ctor == null) {
            throw new IllegalArgumentException();
        }
        return data;
    }     

    @Override
    public String toString() {
        return data.toString();
    }
    
}
