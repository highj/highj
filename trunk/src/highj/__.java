/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj;

public final class __<Ctor extends TC2<Ctor>, A, B> {
    
    private final Class<Ctor> clazz;
    private final Object data;
    
    private __(Object data, Class<Ctor> clazz){
        this.data = data;
        this.clazz = clazz;
    }

    public static class Accessor2<Ctor extends TC2<Ctor>> {
        private final Class<Ctor> clazz;
        
        private Accessor2(Class<Ctor> clazz){
            this.clazz = clazz;
        }
        
        public <A, B> __<Ctor, A,B> make(Object data) {
            return new __<Ctor, A,B>(data, clazz);
        }
        
        public <A, B> Object read(__<Ctor, A, B> a) {
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
    
    public static <Ctor extends TC2<Ctor>> void register(Ctor ctor) {
        ctor.setAccessor(new Accessor2<Ctor>((Class<Ctor>) ctor.getClass()));
    }
    
}
