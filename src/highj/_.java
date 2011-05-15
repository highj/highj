/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj;

/**
 *
 * @author DGronau
 */
public final class _<Ctor extends TC<Ctor>, T> {
    
    private final Class<Ctor> clazz;
    private final Object data;
    
    private _(Object data, Class<Ctor> clazz){
        this.data = data;
        this.clazz = clazz;
    }

    public static class Accessor<Ctor extends TC<Ctor>> {
        private final Class<Ctor> clazz;
        
        private Accessor(Class<Ctor> clazz){
            this.clazz = clazz;
        }
        
        public <T> _<Ctor, T> make(Object data) {
            return new _<Ctor, T>(data, clazz);
        }
        
        public <T> Object read(_<Ctor, T> a) {
            if (a.clazz  != clazz) {
                throw new IllegalArgumentException();
            }
            return a.data;
        }
    }

    @Override
    public String toString() {
        return clazz.getSimpleName() + "(" + data + ")";
    }
    
    public static <Ctor extends TC<Ctor>> void register(Ctor ctor) {
        ctor.setAccessor(new Accessor<Ctor>((Class<Ctor>) ctor.getClass()));
    }
    
}
