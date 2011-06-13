/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj;

/**
 *
 * @author DGronau
 */
public final class _<Ctor, T> {
    
    private final Object data;
    
     public _(Ctor ctor, Object data) {
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
    
    @Override
    public boolean equals(Object o){
        if(data != null && o instanceof _) {
           return data.equals(((_)o).data);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.data == null ? 0 : this.data.hashCode();
    }
}
