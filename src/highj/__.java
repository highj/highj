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
    
    public _<LC<Ctor,A>,B> leftCurry() {
        return LC.curry(this);
    }
    
    public _<RC<Ctor,B>,A> rightCurry() {
        return RC.curry(this);
    }

    @Override
    public String toString() {
        return data.toString();
    }
    
    @Override
    public boolean equals(Object o){
        if(data != null && o instanceof __) {
           return data.equals(((__)o).data);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.data == null ? 0 : this.data.hashCode();
    }    
}
