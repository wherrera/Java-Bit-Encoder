
package bitencoder;

/**
 * Java 1.3 compatible bit encoder
 * @author wherrera
 */
public class BitEncoder
{
    int _value = 0;
    
    public void setValue (int value) {
        _value = value;
    }
    
    public int getValue () {
        return _value;
    }
    
    public void set (int index, boolean value) {
        _value = (value) ? _value | (1 << index) : _value & ~(1 << index);        
    }
    
    public void setByte (int index, byte value) {
        _value &= ~(0xff << index);
        _value |= (value << index);
        System.out.println("value: " + value );  
    }
    
    public boolean get (int index) {
        int x = (1 << index);
        return ( _value & x ) == x;
    }
    
    public byte getByte (int index) {
        return (byte)( 0xff & (_value >> index) );
    }
    
    public String toBitString () {
        String str = new String();
        for(int j=0;j<32;j++) {
            str += get(j) ? "1" : "0";
        }
        return str;
    }
    
    public static void main(String[] args)
    {       
        BitEncoder encoder = new BitEncoder();
        
        encoder.setByte(0, (byte)120);
        encoder.set(8, true);
        encoder.set(9, false);
        encoder.set(10, true);
        encoder.set(11, false);
        
        System.out.println("value: " + encoder.getByte(0) );  
        System.out.println("bit 8: " + encoder.get(8) );  
        System.out.println("bit 9: " + encoder.get(9) );  
        System.out.println("bit 10: " + encoder.get(10) );  
        System.out.println("bit 11: " + encoder.get(11) );  
        System.out.println("bits: " + encoder.toBitString() );        
    }
}