/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package highj;

/**
 *
 * @author DGronau
 */
public interface TC<Ctor extends TC<Ctor>> {
    public void setAccessor(_.Accessor<Ctor> accessor);
}
